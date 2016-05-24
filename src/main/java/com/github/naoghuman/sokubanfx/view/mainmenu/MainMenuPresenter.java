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
package com.github.naoghuman.sokubanfx.view.mainmenu;

import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.configuration.application.IActionConfiguration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 *
 * @author Naoghuman
 */
public class MainMenuPresenter implements Initializable, IActionConfiguration {
    
    @FXML private Label lMenuButton;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MainMenuPresenter");

        assert (lMenuButton != null) : "fx:id=\"lMenuButton\" was not injected: check your FXML file 'Preview.fxml'."; // NOI18N
        
        this.initializeMenuButton();
    }
    
    private void initializeMenuButton() {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize MenuButton"); // NOI18N
        
        lMenuButton.setText(null);
        lMenuButton.setCursor(Cursor.HAND);
        
        final FontIcon fiBlockMenu = new FontIcon(Elusive.REMOVE_SIGN);
        fiBlockMenu.setIconSize(56);
        lMenuButton.setGraphic(fiBlockMenu);
    }
    
    public void onActionHideMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action hide MainMenu");
        
        ActionFacade.INSTANCE.handle(ON_ACTION__HIDE_MAINMENU);
    }
    
}
