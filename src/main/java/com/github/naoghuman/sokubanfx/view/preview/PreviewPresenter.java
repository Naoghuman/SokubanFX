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

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.map.MapFacade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 *
 * @author Naoghuman
 */
public class PreviewPresenter implements Initializable {
    
    @FXML private TextArea ta;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // XXX Test
        List<String> map = MapFacade.INSTANCE.loadNextRandomMap();
        for (String line : map) {
            ta.appendText(line);
            ta.appendText("\n");
        }
    }
    
    public void onActionNextRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action next random map"); // NOI18N
        
        final List<String> map = MapFacade.INSTANCE.loadNextRandomMap();
        ta.setText(null);
        for (String line : map) {
            ta.appendText(line);
            ta.appendText("\n");
        }
    }
    
}
