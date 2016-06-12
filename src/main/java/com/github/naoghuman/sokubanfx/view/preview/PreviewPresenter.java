/*
 * Copyright (C) 2016 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.sokubanfx.view.preview;

import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.sokubanfx.configuration.application.IActionConfiguration;
import com.github.naoghuman.sokubanfx.configuration.view.preview.IPreviewConfiguration;
import com.github.naoghuman.sokubanfx.map.MapFacade;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.converter.MapConverter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author Naoghuman
 */
public class PreviewPresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private Label lPlayGame;
    @FXML private VBox vbRandomMap;
    
    private PauseTransition ptShowNextRandomMap;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize PreviewPresenter"); // NOI18N
        
        assert (vbRandomMap != null) : "fx:id=\"vbRandomMap\" was not injected: check your FXML file 'Preview.fxml'."; // NOI18N
        
        this.initializeButtonPlayGame();
        this.initializeShowNextRandomMap();
        
        this.onActionNextRandomMap();
    }
    
    private void initializeButtonPlayGame() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize button PlayGame"); // NOI18N
        
        lPlayGame.setText(null);
        lPlayGame.setCursor(Cursor.HAND);
        
        final FontIcon fiPlayAlt = new FontIcon(Elusive.PLAY_ALT);
        fiPlayAlt.setIconSize(56);
        lPlayGame.setGraphic(fiPlayAlt);
    }
    
    private void initializeShowNextRandomMap() {
        LoggerFacade.INSTANCE.info(this.getClass(), " Initialize show next random Map"); // NOI18N
        
        if (
                ptShowNextRandomMap != null
                && ptShowNextRandomMap.getStatus().equals(Animation.Status.RUNNING)
        ) {
            ptShowNextRandomMap.stop();
        }
        
        ptShowNextRandomMap = new PauseTransition();
        ptShowNextRandomMap.setDuration(Duration.seconds(15.0d));
        ptShowNextRandomMap.setOnFinished((ActionEvent event) -> {
            this.onActionHideMap();
        });
        
        ptShowNextRandomMap.playFromStart();
    }
    
    private Label getLabel(String text) {
        final Label label = new Label(text);
        label.setFont(new Font("Monospaced Regular", 16.0d));
        
        return label;
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in PreviewPresenter"); // NOI18N
        
        this.initializePreferences();
        
        this.registerOnActionManagedMapPlayer();
        this.registerOnKeyReleased();
    }
    
    private void initializePreferences() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize Preferences"); // NOI18N

        // Listen in GameView on KeyEvents
        PreferencesFacade.INSTANCE.putBoolean(
                IPreviewConfiguration.PROP__KEY_RELEASED__FOR_PREVIEW,
                Boolean.TRUE);
    }
    
    private void registerOnActionManagedMapPlayer() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action managed Map player"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__MANAGED_MAP_PLAYER,
                (ActionEvent event) -> {
                    this.onActionManagedMapPlayer();
                }
        );
    }

    private void registerOnKeyReleased() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on KeyReleased"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__KEY_RELEASED__FOR_PREVIEW,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final KeyEvent keyEvent = (KeyEvent) transferData.getObject();
                    this.onKeyRelease(keyEvent);
                }
        );
    }
    
    private void onActionHideMap() {
        final FadeTransition ftHideMap = new FadeTransition();
        ftHideMap.setDuration(Duration.millis(375.0d));
        ftHideMap.setFromValue(1.0d);
        ftHideMap.setToValue(0.0d);
        ftHideMap.setNode(vbRandomMap);
        ftHideMap.setOnFinished((ActionEvent event) -> {
            this.onActionNextRandomMap();
            this.initializeShowNextRandomMap();
        });
        
        ftHideMap.playFromStart();
    }
    
    private void onActionManagedMapPlayer() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action managed Map player"); // NOI18N
        
        if (ptShowNextRandomMap == null) {
            return;
        }
        
        if (ptShowNextRandomMap.getStatus().equals(Animation.Status.RUNNING)) {
            ptShowNextRandomMap.pause();
            return;
        }
        
        if (ptShowNextRandomMap.getStatus().equals(Animation.Status.PAUSED)) {
            ptShowNextRandomMap.play();
        }
    }
    
    private void onActionNextRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action next random Map"); // NOI18N
        
        final int randomMapIndex = MapFacade.INSTANCE.getRandomMapIndex();
        final MapModel mm = MapFacade.INSTANCE.loadMap(randomMapIndex);
        this.printMapToConsole(mm);
        
        vbRandomMap.getChildren().clear();
        vbRandomMap.setOpacity(0.0d);
        vbRandomMap.getChildren().add(this.getLabel("Map: " + randomMapIndex)); // NOI18N
        vbRandomMap.getChildren().add(this.getLabel("")); // NOI18N
        
        final ObservableList<String> map = mm.getMapAsStrings();
        map.stream().forEach(line -> {
            vbRandomMap.getChildren().add(this.getLabel(line));
        });
        
        final FadeTransition ftShowNextRandomMap = new FadeTransition();
        ftShowNextRandomMap.setDuration(Duration.millis(550.0d));
        ftShowNextRandomMap.setFromValue(0.0d);
        ftShowNextRandomMap.setToValue(1.0d);
        ftShowNextRandomMap.setNode(vbRandomMap);
        
        ftShowNextRandomMap.playFromStart();
    }
    
    public void onActionStartGame() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action start Game"); // NOI18N
        
        if (ptShowNextRandomMap.getStatus().equals(Animation.Status.RUNNING)) {
            ptShowNextRandomMap.stop();
        }
        
        ActionFacade.INSTANCE.handle(ON_ACTION__CHANGE_TO_GAMEVIEW);
    }
    
    /*
    * ENTER SPACE -> play game
    * ESC         -> close application (from ApplicationView)
    */
    private void onKeyRelease(KeyEvent keyEvent) {
        final KeyCode keyCode = keyEvent.getCode();
        LoggerFacade.INSTANCE.debug(this.getClass(), "On KeyRelease: " + keyCode); // NOI18N

        if (
                keyCode.equals(KeyCode.ENTER)
                || keyCode.equals(KeyCode.SPACE)
        ) {
            this.onActionStartGame();
        }
    }
    
    private void printMapToConsole(MapModel mm) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        System.out.println("lvl: " + mm.getLevel());
        System.out.println("col: " + mm.getColumns());
        System.out.println("row: " + mm.getRows());
        
        final MapConverter mc = new MapConverter();
        final ObservableList<String> map1 = mc.convertMapCoordinatesToStrings(mm);
        map1.stream().forEach(line -> {
            System.out.println(line);
        });
        
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
    
}
