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

import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.configuration.IActionConfiguration;
import com.github.naoghuman.sokubanfx.map.MapFacade;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 *
 * @author Naoghuman
 */
public class PreviewPresenter implements Initializable, IActionConfiguration {
    
//    public static final String PATH_TO_FOLDER__ = "file:resources" + File.separator + "images" + File.separator;
    
    @FXML private ImageView iv;
    @FXML private TextArea ta;
    
//    public static final String PATH_TO_FOLDER__RESOURCES_IMAGES_MAPMARKER__WITH_FILE =
//            "file:" // NOI18N
//            + "resources" // NOI18N
//            + File.separator + "images" // NOI18N
//            + File.separator + "mapmarker" // NOI18N
//            + File.separator;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        // XXX Test
        List<String> map = MapFacade.INSTANCE.loadRandomMap();
        for (String line : map) {
            ta.appendText(line);
            ta.appendText("\n");
        }
        
//        Image img = null;
//        try {
//            img = new Image(this.getClass().getResourceAsStream("/com/github/naoghuman/sokubanfx/view/preview/wallA.png"));
////            img = new Image("file:images/smiley0.png");
//        } catch (Exception e) {
//            LoggerFacade.INSTANCE.warn(this.getClass(), String.format(
//                    "Can't load image: %s", "wallA.png"), e); // NOI18N
//        }
//        ResourcesFacade.INSTANCE.register(EResourceType.IMAGE, PATH_TO_FOLDER__);
//        Image img = ResourcesFacade.INSTANCE.loadImage("wallA.png");
//        iv.setImage(img);
    }
    
    public void onActionNextRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action next random map"); // NOI18N
        
        final List<String> map = MapFacade.INSTANCE.loadRandomMap();
        ta.setText(null);
        for (String line : map) {
            ta.appendText(line);
            ta.appendText("\n");
        }
    }
    
    public void onActionStartGame() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action start Game"); // NOI18N
        
        ActionFacade.INSTANCE.handle(ON_ACTION__CHANGE_TO_GAMEVIEW);
    }
    
}
