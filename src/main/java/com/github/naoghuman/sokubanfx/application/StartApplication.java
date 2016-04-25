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

import static javafx.application.Application.launch;

import com.airhacks.afterburner.injection.Injector;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.action.api.TransferData;
import com.github.naoghuman.sokubanfx.configuration.IApplicationConfiguration;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import com.github.naoghuman.sokubanfx.configuration.IActionConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IGameConfiguration;
import com.github.naoghuman.sokubanfx.configuration.IMainMenuConfiguration;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Naoghuman
 */
public class StartApplication extends Application implements IActionConfiguration, IApplicationConfiguration {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        
        PropertiesFacade.INSTANCE.register(KEY__APPLICATION__RESOURCE_BUNDLE);
        
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_START);
        final String title = this.getProperty(KEY__APPLICATION__TITLE) + this.getProperty(KEY__APPLICATION__VERSION);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.INSTANCE.init(dropPreferencesFileAtStart);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        final ApplicationView applicationView = new ApplicationView();
        final ApplicationPresenter applicationPresenter = applicationView.getRealPresenter();
        
        final Scene scene = new Scene(applicationView.getView(), 1280, 720);
        scene.setOnKeyReleased((KeyEvent event) -> {
            this.onKeyReleased(event);
        });
        primaryStage.setTitle(this.getProperty(KEY__APPLICATION__TITLE) + this.getProperty(KEY__APPLICATION__VERSION));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
        applicationPresenter.initializeAfterWindowIsShowing();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(KEY__APPLICATION__RESOURCE_BUNDLE, propertyKey);
    }
    
    private void onCloseRequest() {
        // Cleanup for next start
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__GAMEVIEW_IS_INITIALZE,
                Boolean.FALSE);
        PreferencesFacade.INSTANCE.putBoolean(
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW,
                Boolean.FALSE);
        PreferencesFacade.INSTANCE.putBoolean(
                IMainMenuConfiguration.PROP__MAIN_MENU_IS_SHOWN,
                Boolean.FALSE);
        
        // afterburner.fx
        Injector.forgetAll();
        
        // Message
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_STOP);
        final String title = this.getProperty(KEY__APPLICATION__TITLE) + this.getProperty(KEY__APPLICATION__VERSION);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        // Timer
        final PauseTransition pt = new PauseTransition(DURATION__125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
    private void onKeyReleased(KeyEvent event) {
        // Listen to Application events
        final KeyCode keyCode = event.getCode();
        if (keyCode == KeyCode.ESCAPE) {
            event.consume();
            this.onCloseRequest();
            
            return;
        }
        
        // MainMenu
        if (keyCode == KeyCode.BACK_SPACE) {
            event.consume();
            final boolean isMainMenuShown = PreferencesFacade.INSTANCE.getBoolean(
                    IMainMenuConfiguration.PROP__MAIN_MENU_IS_SHOWN,
                    IMainMenuConfiguration.PROP__MAIN_MENU_IS_SHOWN__DEFAULT_VALUE);
            ActionFacade.INSTANCE.handle(isMainMenuShown ? ON_ACTION__HIDE_MAINMENU : ON_ACTION__SHOW_MAINMENU);
            
            return;
        }
        
        // GameView
        final boolean isGameViewInitialize = PreferencesFacade.INSTANCE.getBoolean(
                IGameConfiguration.PROP__GAMEVIEW_IS_INITIALZE, 
                IGameConfiguration.PROP__GAMEVIEW_IS_INITIALZE__DEFAULT_VALUE);
        final boolean shouldOnKeyReleaseForGameView = PreferencesFacade.INSTANCE.getBoolean(
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW, 
                IGameConfiguration.PROP__KEY_RELEASED__FOR_GAMEVIEW__DEFAULT_VALUE);
        if (isGameViewInitialize && shouldOnKeyReleaseForGameView) {
            final TransferData transferData = new TransferData();
            transferData.setActionId(ON_ACTION__KEY_RELEASED__FOR_GAME);
            transferData.setObject(event);

            ActionFacade.INSTANCE.handle(transferData);
        }
    }
    
}
