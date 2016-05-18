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
package com.github.naoghuman.sokubanfx.application;

import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import com.github.naoghuman.sokubanfx.configuration.IActionConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IApplicationConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IGameConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IMainMenuConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IPreviewConfiguration;
import com.github.naoghuman.sokubanfx.map.image.MapImageLoader;
import com.github.naoghuman.sokubanfx.view.mainmenu.MainMenuView;
import com.github.naoghuman.sokubanfx.view.game.GamePresenter;
import com.github.naoghuman.sokubanfx.view.game.GameView;
import com.github.naoghuman.sokubanfx.view.preview.PreviewPresenter;
import com.github.naoghuman.sokubanfx.view.preview.PreviewView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author Naoghuman
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, IApplicationConfiguration, IRegisterActions {

    @FXML private AnchorPane apHiddenLayer;
    @FXML private BorderPane bpGameArea;
    @FXML private BorderPane bpMenuArea;
    @FXML private ImageView ivBackground;
    @FXML private Label lMenuButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
        assert (apHiddenLayer != null) : "fx:id=\"apHiddenLayer\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (bpGameArea != null)    : "fx:id=\"bpGameArea\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (bpMenuArea != null)    : "fx:id=\"bpMenuArea\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (ivBackground != null)  : "fx:id=\"ivBackground\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        assert (lMenuButton != null)   : "fx:id=\"lMenuButton\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.initializeMenuButton();
        
        this.registerActions();
        this.hideHiddenLayer();
        
        this.showBackgroundImage();
        this.showViewPreview();
    }
    
    private void initializeMenuButton() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MenuButton"); // NOI18N
        
        lMenuButton.setText(null);
        lMenuButton.setCursor(Cursor.HAND);
        
        final FontIcon fiBlockMenu = new FontIcon(Elusive.LINES);
        fiBlockMenu.setIconSize(56);
        lMenuButton.setGraphic(fiBlockMenu);
    }
    
    public void initializeAfterWindowIsShowing() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ApplicationPresenter after window is showing"); // NOI18N
    }
    
    private void hideHiddenLayer() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Hide hidden layer"); // NOI18N
        
        apHiddenLayer.setVisible(Boolean.FALSE);
        apHiddenLayer.setManaged(Boolean.FALSE);
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
        
        this.registerOnActionChangeToGameView();
        this.registerOnActionHideMainMenu();
        this.registerOnActionShowBackgroundImage();
        this.registerOnActionShowMainMenu();
    }
    
    private void registerOnActionChangeToGameView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action change to GameView"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__CHANGE_TO_GAMEVIEW,
                (ActionEvent event) -> {
                    PreferencesFacade.INSTANCE.putBoolean(
                            IPreviewConfiguration.PROP__KEY_RELEASED__FOR_PREVIEW, 
                            Boolean.FALSE);
        
                    this.onActionChangeToGameView();
                }
        );
    }
    
    private void registerOnActionHideMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action hide MainMenu"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__HIDE_MAINMENU,
                (ActionEvent event) -> {
                    this.onActionHideMainMenu();
                }
        );
    }
    
    private void registerOnActionShowBackgroundImage() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action show background image"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__SHOW_BACKGROUND_IMAGE,
                (ActionEvent event) -> {
                    final TransferData transferData = (TransferData) event.getSource();
                    this.onActionShowBackgroundImage(transferData.getString());
                }
        );
    }
    
    private void registerOnActionShowMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action show MainMenu"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__SHOW_MAINMENU,
                (ActionEvent event) -> {
                    this.onActionShowMainMenu();
                }
        );
    }
    
    private void onActionChangeToGameView() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action change to GameView"); // NOI18N
        
        // Hide PreviewView
        final FadeTransition ftHidePreviewView = new FadeTransition();
        ftHidePreviewView.setDelay(Duration.millis(125.0d));
        ftHidePreviewView.setDuration(Duration.millis(250.0d));
        ftHidePreviewView.setFromValue(1.0d);
        ftHidePreviewView.setToValue(0.0d);
        ftHidePreviewView.setNode(bpGameArea.getCenter());
        ftHidePreviewView.setOnFinished((ActionEvent event) -> {
            bpGameArea.setCenter(null);
        });
        
        // Init GameView
        final GameView gameView = new GameView();
        final GamePresenter gamePresenter = gameView.getRealPresenter();
        gamePresenter.registerActions();
        
        final Parent game = gameView.getView();
        game.setOpacity(0.0d);
        
        // Little pause
        final PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.millis(250.0d));
        ftHidePreviewView.setOnFinished((ActionEvent event) -> {
            bpGameArea.setCenter(game);
        });
        
        // Show GameView
        final FadeTransition ftShowGameView = new FadeTransition();
        ftShowGameView.setDuration(Duration.millis(375.0d));
        ftShowGameView.setFromValue(0.0d);
        ftShowGameView.setToValue(1.0d);
        ftShowGameView.setNode(game);
        
        // Animation
        final SequentialTransition st = new SequentialTransition();
        st.getChildren().addAll(ftHidePreviewView, pt, ftShowGameView);
        
        st.playFromStart();
    }
    
    private void onActionHideMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action hide MainMenu"); // NOI18N
        
        // Listen in Preview or GameView on KeyEvents
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW, 
                Boolean.TRUE);
        PreferencesFacade.INSTANCE.putBoolean(
                IPreviewConfiguration.PROP__KEY_RELEASED__FOR_PREVIEW, 
                IPreviewConfiguration.PROP__KEY_RELEASED__FOR_PREVIEW__DEFAULT_VALUE);
        
        // MainMenuView
        final Node menu = bpMenuArea.getCenter();
        
        final FadeTransition ftHideMenuView = new FadeTransition();
        ftHideMenuView.setDelay(Duration.millis(200.0d));
        ftHideMenuView.setDuration(Duration.millis(375.0d));
        ftHideMenuView.setFromValue(1.0d);
        ftHideMenuView.setToValue(0.0d);
        ftHideMenuView.setNode(menu);
        ftHideMenuView.setOnFinished((ActionEvent event) -> {
            bpMenuArea.setMouseTransparent(Boolean.TRUE);
            bpMenuArea.setCenter(null);
        });
        
        // Move menu
        final TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDelay(Duration.millis(200.0d));
        translateTransition.setDuration(Duration.millis(375.0d));
        translateTransition.setFromX(0.0d);
        translateTransition.setToX(300.0d);
        translateTransition.setNode(menu);
        
        // HiddenLayer
        final FadeTransition ftHideHiddenLayer = new FadeTransition();
        ftHideHiddenLayer.setDelay(Duration.millis(275.0d));
        ftHideHiddenLayer.setDuration(Duration.millis(250.0d));
        ftHideHiddenLayer.setFromValue(1.0d);
        ftHideHiddenLayer.setToValue(0.0d);
        ftHideHiddenLayer.setNode(apHiddenLayer);
        ftHideHiddenLayer.setOnFinished((ActionEvent event) -> {
            lMenuButton.setDisable(Boolean.FALSE);
            apHiddenLayer.setVisible(Boolean.FALSE);
            apHiddenLayer.setManaged(Boolean.FALSE);
            
            // MainMenuView is hidden
            PreferencesFacade.INSTANCE.putBoolean(
                    IMainMenuConfiguration.PROP__MAIN_MENU_IS_SHOWN,
                    Boolean.FALSE);
        });
        
        // Animate
        final ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(ftHideMenuView, translateTransition, ftHideHiddenLayer);
        
        pt.playFromStart();
    }
    
    private void onActionShowBackgroundImage(String image) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show background image: " + image); // NOI18N
        
        ivBackground.setFitWidth(1280.0d);
        ivBackground.setFitHeight(720.0d);
        ivBackground.setImage(null);
        
        final Image img = MapImageLoader.getDefault().getBackgroundImage(image);
        ivBackground.setImage(img);
    }
    
    public void onActionShowMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action show MainMenu"); // NOI18N
        
        // Dont listen in Preview and GameView on KeyEvents
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW, 
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW__DEFAULT_VALUE);
        PreferencesFacade.INSTANCE.putBoolean(
                IPreviewConfiguration.PROP__KEY_RELEASED__FOR_PREVIEW, 
                Boolean.FALSE);
        
        // MainMenuView is shown
        PreferencesFacade.INSTANCE.putBoolean(
                IMainMenuConfiguration.PROP__MAIN_MENU_IS_SHOWN,
                Boolean.TRUE);
        
        // Button
        lMenuButton.setDisable(Boolean.TRUE);
        
        // HiddenLayer
        apHiddenLayer.setOpacity(0.0d);
        apHiddenLayer.setVisible(Boolean.TRUE);
        apHiddenLayer.setManaged(Boolean.TRUE);
        
        final FadeTransition ftShowHiddenLayer = new FadeTransition();
        ftShowHiddenLayer.setDelay(Duration.millis(125.0d));
        ftShowHiddenLayer.setDuration(Duration.millis(250.0d));
        ftShowHiddenLayer.setFromValue(0.0d);
        ftShowHiddenLayer.setToValue(1.0d);
        ftShowHiddenLayer.setNode(apHiddenLayer);
        
        // Init MainMenuView
        final MainMenuView mainMenuView = new MainMenuView();
        final Parent menu = mainMenuView.getView();
        menu.setOpacity(0.0d);
        bpMenuArea.setCenter(null);
        bpMenuArea.setCenter(menu);
        
        final FadeTransition ftShowMenuView = new FadeTransition();
        ftShowMenuView.setDelay(Duration.millis(200.0d));
        ftShowMenuView.setDuration(Duration.millis(375.0d));
        ftShowMenuView.setFromValue(0.0d);
        ftShowMenuView.setToValue(1.0d);
        ftShowMenuView.setNode(menu);
        ftShowMenuView.setOnFinished((ActionEvent event) -> {
            bpMenuArea.setMouseTransparent(Boolean.FALSE);
        });
        
        // Move menu
        final TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDelay(Duration.millis(200.0d));
        translateTransition.setDuration(Duration.millis(375.0d));
        translateTransition.setFromX(300.0d);
        translateTransition.setToX(0.0d);
        translateTransition.setNode(menu);
        
        // Animate
        final ParallelTransition pt = new ParallelTransition();
        pt.getChildren().addAll(ftShowHiddenLayer, ftShowMenuView, translateTransition);
        
        pt.playFromStart();
    }
    
    private void showBackgroundImage() {
        final String defaultBackgroundImage = PropertiesFacade.INSTANCE.getProperty(
                KEY__APPLICATION__RESOURCE_BUNDLE,
                KEY__APPLICATION__DEFAULT_BACKGROUND_IMAGE);
        this.onActionShowBackgroundImage(defaultBackgroundImage);
    }
    
    private void showViewPreview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show view Preview"); // NOI18N
        
        final PreviewView view = new PreviewView();
        final PreviewPresenter presenter = view.getRealPresenter();
        presenter.registerActions();
        
        final Parent preview = view.getView();
        preview.setOpacity(0.0d);
        bpGameArea.setCenter(preview);
        
        final FadeTransition ftHidePreviewView = new FadeTransition();
        ftHidePreviewView.setDelay(Duration.millis(250.0d));
        ftHidePreviewView.setDuration(Duration.millis(375.0d));
        ftHidePreviewView.setFromValue(0.0d);
        ftHidePreviewView.setToValue(1.0d);
        ftHidePreviewView.setNode(preview);
        
        ftHidePreviewView.playFromStart();
    }
    
}
