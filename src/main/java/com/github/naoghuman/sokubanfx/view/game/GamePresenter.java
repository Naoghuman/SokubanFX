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
package com.github.naoghuman.sokubanfx.view.game;

import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.sokubanfx.configuration.IActionConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IGameConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IMapConfiguration;
import com.github.naoghuman.sokubanfx.map.MapFacade;
import com.github.naoghuman.sokubanfx.map.MapModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Naoghuman
 */
public class GamePresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private AnchorPane apGameArea;
    @FXML private Label lMapInfo;
    @FXML private TextArea taMapDisplay;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize GamePresenter"); // NOI18N
        
        this.initializePreferences();
        this.initializeMap();
    }
    
    private void initializeMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Map"); // NOI18N
        
        final int actualMap = PreferencesFacade.INSTANCE.getInt(
                IMapConfiguration.PROP__ACTUAL_MAP,
                IMapConfiguration.PROP__ACTUAL_MAP__DEFAULT_VALUE);
        
        final MapModel mapModel = MapFacade.INSTANCE.loadMap(actualMap);
        lMapInfo.setText("Map " + mapModel.getLevel()); // NOI18N
        
        final List<String> mapAsStrings = MapFacade.INSTANCE.convertMapCoordinatesToStrings(mapModel);
        taMapDisplay.setText(null);
        mapAsStrings.stream().forEach((line) -> {
            taMapDisplay.appendText(line);
            taMapDisplay.appendText("\n"); // NOI18N
        });
    }
    
    private void initializePreferences() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Preferences"); // NOI18N
        
        // GameView is initialize
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__GAMEVIEW_IS_INITIALZE,
                Boolean.TRUE);
        
        // Listen in GameView on KeyEvents
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW,
                Boolean.TRUE);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in GamePresenter"); // NOI18N
        
        this.registerOnKeyReleased();
    }

    private void registerOnKeyReleased() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on KeyReleased"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__KEY_RELEASED__FOR_GAME,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    final KeyEvent keyEvent = (KeyEvent) transferData.getObject();
                    this.onKeyRelease(keyEvent);
                }
        );
    }
    
    public void onActionButtonDown() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button down"); // NOI18N
        
    }
    
    public void onActionButtonLeft() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button left"); // NOI18N
        
    }
    
    public void onActionButtonResetMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button reset Map"); // NOI18N
        
    }
    
    public void onActionButtonRight() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button right"); // NOI18N
        
    }
    
    public void onActionButtonUp() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button up"); // NOI18N
        
    }
    
    private void onKeyRelease(KeyEvent keyEvent) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On KeyRelease: " + keyEvent.getCode()); // NOI18N
        
    }
    
}
