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
import com.github.naoghuman.sokubanfx.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.geometry.EDirection;
import com.github.naoghuman.sokubanfx.map.MapFacade;
import com.github.naoghuman.sokubanfx.map.animation.EAnimation;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.movement.CheckMovementResult;
import com.github.naoghuman.sokubanfx.map.movement.EMovement;
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
    
    private MapModel actualMapModel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize GamePresenter"); // NOI18N
        
        this.initializePreferences();
        
        this.loadActualMap();
        this.displayMap();
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
    
    private void displayMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Display Map"); // NOI18N
        
        lMapInfo.setText("Map " + actualMapModel.getLevel()); // NOI18N
        
        final List<String> mapAsStrings = MapFacade.INSTANCE.convertMapCoordinatesToStrings(actualMapModel);
        taMapDisplay.setText(null);
        mapAsStrings.stream().forEach((line) -> {
            taMapDisplay.appendText(line);
            taMapDisplay.appendText("\n"); // NOI18N
        });
    }

    private void evaluateIsMapFinish(boolean isMapFinish) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Evaluate is Map finish"); // NOI18N
        
        // Keep going :)
        if (!isMapFinish) {
            return;
        }
        
        // Map is finish !!
        System.out.println("---------------------> map is finish :))");
        // no movement
        // keyevents=false
        
        // map-level += 1
        final int actualMap = PreferencesFacade.INSTANCE.getInt(
                IMapConfiguration.PROP__ACTUAL_MAP,
                IMapConfiguration.PROP__ACTUAL_MAP__DEFAULT_VALUE);
        PreferencesFacade.INSTANCE.putInt(
                IMapConfiguration.PROP__ACTUAL_MAP,
                actualMap + 1);
        
        // play animation
        // load next map
        // keyevents= true
        this.loadActualMap();
        this.displayMap();
    }
    
    private void evaluatePlayerMoveTo(CheckMovementResult checkMovementResult) {
        final EAnimation animation = checkMovementResult.getAnimation();
        if (
                animation.equals(EAnimation.REALLY_GREAT)
                || animation.equals(EAnimation.WHAT_HAPPEN)
        ) {
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation: " + animation); // NOI18N
            
        }
        
        final EMovement movement = checkMovementResult.getMovement();
        if (
                movement.equals(EMovement.PLAYER)
                || movement.equals(EMovement.PLAYER_AND_BOX)
        ) {
            // Update player
            final Coordinates player = actualMapModel.getPlayer();
            player.setX(player.getTranslateX());
            player.setY(player.getTranslateY());
            
            if (movement.equals(EMovement.PLAYER)) {
                this.displayMap();
                return;
            }
            
            // Update box
            final Coordinates boxToMove = movement.getCoordinatesBoxToMove();
            final List<Coordinates> boxes = actualMapModel.getBoxes();
            for (Coordinates box : boxes) {
                if (
                        box.getX() == boxToMove.getX()
                        && box.getY() == boxToMove.getY()
                ) {
                    box.setX(boxToMove.getTranslateX());
                    box.setY(boxToMove.getTranslateY());
                }
            }
            
            this.displayMap();
        }
    }
    
    private void loadActualMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize Map"); // NOI18N
        
        final int actualMap = PreferencesFacade.INSTANCE.getInt(
                IMapConfiguration.PROP__ACTUAL_MAP,
                IMapConfiguration.PROP__ACTUAL_MAP__DEFAULT_VALUE);
        
        actualMapModel = MapFacade.INSTANCE.loadMap(actualMap);
    }

    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in GamePresenter"); // NOI18N
        
        this.registerOnActionDisplayMap();
        this.registerOnKeyReleased();
    }
    
    private void registerOnActionDisplayMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register on action display Map"); // NOI18N
        
        ActionFacade.INSTANCE.register(
                ON_ACTION__DISPLAY_MAP,
                (ActionEvent event) -> {
                    this.displayMap();
                }
        );
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
        
        final CheckMovementResult checkMovementResult = MapFacade.INSTANCE.playerMoveTo(EDirection.DOWN, actualMapModel);
        this.evaluatePlayerMoveTo(checkMovementResult);
        
        final boolean shouldCheckIfMapIsFinished = checkMovementResult.isCheckIsMapFinish();
        if (shouldCheckIfMapIsFinished) {
            final boolean isMapFinish = MapFacade.INSTANCE.isMapFinish(actualMapModel);
            this.evaluateIsMapFinish(isMapFinish);
        }
    }
    
    public void onActionButtonLeft() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button left"); // NOI18N
        
        final CheckMovementResult checkMovementResult = MapFacade.INSTANCE.playerMoveTo(EDirection.LEFT, actualMapModel);
        this.evaluatePlayerMoveTo(checkMovementResult);
        
        final boolean shouldCheckIfMapIsFinished = checkMovementResult.isCheckIsMapFinish();
        if (shouldCheckIfMapIsFinished) {
            final boolean isMapFinish = MapFacade.INSTANCE.isMapFinish(actualMapModel);
            this.evaluateIsMapFinish(isMapFinish);
        }
    }
    
    public void onActionButtonResetMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button reset Map"); // NOI18N
        
        LoggerFacade.INSTANCE.trace(this.getClass(), "TODO listen keyevents=false"); // NOI18N
        
        this.loadActualMap();
        this.displayMap();
        
        LoggerFacade.INSTANCE.trace(this.getClass(), "TODO listen keyevent=true"); // NOI18N
    }
    
    public void onActionButtonRight() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button right"); // NOI18N
        
        final CheckMovementResult checkMovementResult = MapFacade.INSTANCE.playerMoveTo(EDirection.RIGHT, actualMapModel);
        this.evaluatePlayerMoveTo(checkMovementResult);
        
        final boolean checkIfMapIsFinished = checkMovementResult.isCheckIsMapFinish();
        if (checkIfMapIsFinished) {
            final boolean isMapFinish = MapFacade.INSTANCE.isMapFinish(actualMapModel);
            this.evaluateIsMapFinish(isMapFinish);
        }
    }
    
    public void onActionButtonUp() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action Button up"); // NOI18N
        
        final CheckMovementResult checkMovementResult = MapFacade.INSTANCE.playerMoveTo(EDirection.UP, actualMapModel);
        this.evaluatePlayerMoveTo(checkMovementResult);
        
        final boolean shouldCheckIfMapIsFinished = checkMovementResult.isCheckIsMapFinish();
        if (shouldCheckIfMapIsFinished) {
            final boolean isMapFinish = MapFacade.INSTANCE.isMapFinish(actualMapModel);
            this.evaluateIsMapFinish(isMapFinish);
        }
    }
    
    private void onKeyRelease(KeyEvent keyEvent) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On KeyRelease: " + keyEvent.getCode()); // NOI18N
        
        LoggerFacade.INSTANCE.trace(this.getClass(), "TODO check keyevent"); // NOI18N
    }
    
}
