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

import com.github.naoghuman.lib.action.api.IRegisterActions;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.view.preview.PreviewView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 *
 * @author Naoghuman
 */
public class ApplicationPresenter implements Initializable, IRegisterActions {
    
    @FXML private AnchorPane apHiddenLayer;
    @FXML private BorderPane bpGameArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
//        assert (apView != null) : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.registerActions();
        this.hideHiddenLayer();
        
        this.showViewPreview();
    }
    
    public void initializeAfterWindowIsShowing() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize ApplicationPresenter after window is showing"); // NOI18N
    }
    
    private void hideHiddenLayer() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Hide hidden layer");
        
        apHiddenLayer.setVisible(Boolean.FALSE);
        apHiddenLayer.setManaged(Boolean.FALSE);
    }
    
    @Override
    public void registerActions() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Register actions in ApplicationPresenter"); // NOI18N
    }
    
    public void onActionTriggerMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action trigger Menu");
    }
    
    private void showViewPreview() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Show view Preview");
        
        final PreviewView view = new PreviewView();
        final Parent preview = view.getView();
        preview.setOpacity(0.0d);
        bpGameArea.setCenter(preview);
        
        final FadeTransition ft = new FadeTransition();
        ft.setDelay(Duration.millis(250.0d));
        ft.setDuration(Duration.millis(375.0d));
        ft.setFromValue(0.0d);
        ft.setToValue(1.0d);
        ft.setNode(preview);
        
        ft.playFromStart();
    }
    
}
