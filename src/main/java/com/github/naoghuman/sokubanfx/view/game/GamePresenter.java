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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Naoghuman
 */
public class GamePresenter implements Initializable, IActionConfiguration, IRegisterActions {
    
    @FXML private AnchorPane apGameArea;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize GamePresenter"); // NOI18N
        
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
    
    private void onKeyRelease(KeyEvent keyEvent) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On KeyRelease: " + keyEvent.getCode()); // NOI18N
        
    }
    
}
