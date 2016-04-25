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
import com.github.naoghuman.sokubanfx.configuration.IActionConfiguration;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author Naoghuman
 */
public class MainMenuPresenter implements Initializable, IActionConfiguration {
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Initialize MainMenuPresenter");

    }
    
    public void onActionHideMainMenu() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action hide MainMenu");
        
        ActionFacade.INSTANCE.handle(ON_ACTION__HIDE_MAINMENU);
    }
    
}
