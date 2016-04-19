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
import com.github.naoghuman.sokubanfx.configuration.IApplicationConfiguration;
import com.github.naoghuman.lib.database.api.DatabaseFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.preferences.api.PreferencesFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Naoghuman
 */
public class StartApplication extends Application implements IApplicationConfiguration {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        
        PropertiesFacade.INSTANCE.register(KEY__APPLICATION__RESOURCE_BUNDLE);
        
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_START);
        final String title = this.getProperty(KEY__APPLICATION__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        final Boolean dropPreferencesFileAtStart = Boolean.FALSE;
        PreferencesFacade.INSTANCE.init(dropPreferencesFileAtStart);
        
        DatabaseFacade.INSTANCE.register(this.getProperty(KEY__APPLICATION__DATABASE));
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        final ApplicationView applicationView = new ApplicationView();
        final ApplicationPresenter applicationPresenter = applicationView.getRealPresenter();
        
        final Scene scene = new Scene(applicationView.getView(), 1280, 720);
        primaryStage.setTitle(this.getProperty(KEY__APPLICATION__TITLE));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
           we.consume();
           
           this.onCloseRequest();
        });
        
        primaryStage.show();
        applicationPresenter.initializeAfterWindowIsShowing();
    }

    @Override
    public void stop() throws Exception {
        Injector.forgetAll();
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(KEY__APPLICATION__RESOURCE_BUNDLE, propertyKey);
    }
    
    private void onCloseRequest() {
        // afterburner.fx
        Injector.forgetAll();
        
        // Database
        DatabaseFacade.INSTANCE.shutdown();
        
        // Message
        final char borderSign = this.getProperty(KEY__APPLICATION__BORDER_SIGN).charAt(0);
        final String message = this.getProperty(KEY__APPLICATION__MESSAGE_STOP);
        final String title = this.getProperty(KEY__APPLICATION__TITLE);
        LoggerFacade.INSTANCE.message(borderSign, 80, String.format(message, title));
        
        // Timer
        final PauseTransition pt = new PauseTransition(DURATION__125);
        pt.setOnFinished((ActionEvent event) -> {
            Platform.exit();
        });
        pt.playFromStart();
    }
    
}
