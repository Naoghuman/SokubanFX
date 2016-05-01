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
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.converter.MapConverter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 *
 * @author Naoghuman
 */
public class PreviewPresenter implements Initializable, IActionConfiguration {
    
//    public static final String PATH_TO_FOLDER__ = "file:resources" + File.separator + "images" + File.separator;
    
    @FXML private ImageView iv;
    @FXML private VBox vbRandomMap;
    
    private PauseTransition ptShowNextRandomMap;
    
//    public static final String PATH_TO_FOLDER__RESOURCES_IMAGES_MAPMARKER__WITH_FILE =
//            "file:" // NOI18N
//            + "resources" // NOI18N
//            + File.separator + "images" // NOI18N
//            + File.separator + "mapmarker" // NOI18N
//            + File.separator;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.INSTANCE.info(this.getClass(), "Initialize PreviewPresenter"); // NOI18N
        
        assert (vbRandomMap != null) : "fx:id=\"vbRandomMap\" was not injected: check your FXML file 'Preview.fxml'."; // NOI18N
        
        this.initializeShowNextRandomMap();
        
        this.onActionNextRandomMap();
        
        // XXX Test
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        final int randomMapIndex = MapFacade.INSTANCE.getRandomMapIndex();
//        final MapModel mm = MapFacade.INSTANCE.loadMap(randomMapIndex);
//        System.out.println("lvl: " + mm.getLevel());
//        System.out.println("col: " + mm.getColumns());
//        System.out.println("row: " + mm.getRows());
//        
//        final MapConverter mc = new MapConverter();
//        final List<String> map1 = mc.convertMapCoordinatesToStrings(mm);
//        map1.stream().forEach(line -> {
//            System.out.println(line);
//        });
//        
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        
//        vbRandomMap.getChildren().clear();
//        vbRandomMap.getChildren().add(this.getLabel("Map: " + randomMapIndex));
//        vbRandomMap.getChildren().add(this.getLabel(""));
//        
//        final List<String> map = mm.getMapAsStrings();
//        map.stream().forEach(line -> {
//            vbRandomMap.getChildren().add(this.getLabel(line));
//        });
        
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
    
    private void initializeShowNextRandomMap() {
        LoggerFacade.INSTANCE.info(this.getClass(), " Initialize show next random Map"); // NOI18N
        
        if (
                ptShowNextRandomMap != null
                && ptShowNextRandomMap.getStatus().equals(Animation.Status.RUNNING)
        ) {
            ptShowNextRandomMap.stop();
        }
        
        ptShowNextRandomMap = new PauseTransition();
        ptShowNextRandomMap.setDuration(Duration.seconds(5.0d));
        ptShowNextRandomMap.setOnFinished((ActionEvent event) -> {
            this.onActionHideMap();
        });
        
        ptShowNextRandomMap.playFromStart();
    }
    
    private Label getLabel(String text) {
        final Label label = new Label(text);
        label.setFont(new Font("Monospaced Regular", 18.0d));
        
        return label;
    }
    
    private void onActionHideMap() {
        final FadeTransition ftHideMap = new FadeTransition();
        ftHideMap.setDuration(Duration.millis(250.0d));
        ftHideMap.setFromValue(1.0d);
        ftHideMap.setToValue(0.0d);
        ftHideMap.setNode(vbRandomMap);
        ftHideMap.setOnFinished((ActionEvent event) -> {
            this.onActionNextRandomMap();
            this.initializeShowNextRandomMap();
        });
        
        ftHideMap.playFromStart();
    }
    
    public void onActionNextRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action next random Map"); // NOI18N
        
        final int randomMapIndex = MapFacade.INSTANCE.getRandomMapIndex();
        final MapModel mm = MapFacade.INSTANCE.loadMap(randomMapIndex);
        this.printMapToConsole(mm);
        
        vbRandomMap.getChildren().clear();
        vbRandomMap.setOpacity(0.0d);
        vbRandomMap.getChildren().add(this.getLabel("Map: " + randomMapIndex)); // NOI18N
        vbRandomMap.getChildren().add(this.getLabel("")); // NOI18N
        
        final List<String> map = mm.getMapAsStrings();
        map.stream().forEach(line -> {
            vbRandomMap.getChildren().add(this.getLabel(line));
        });
        
        final FadeTransition ftShowNextRandomMap = new FadeTransition();
        ftShowNextRandomMap.setDuration(Duration.millis(375.0d));
        ftShowNextRandomMap.setFromValue(0.0d);
        ftShowNextRandomMap.setToValue(1.0d);
        ftShowNextRandomMap.setNode(vbRandomMap);
        
        ftShowNextRandomMap.playFromStart();
    }
    
    public void onActionStartGame() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "On action start Game"); // NOI18N
        
        if (ptShowNextRandomMap.getStatus().equals(Animation.Status.RUNNING)) {
            ptShowNextRandomMap.stop();
        }
        
        ActionFacade.INSTANCE.handle(ON_ACTION__CHANGE_TO_GAMEVIEW);
    }
    
    private void printMapToConsole(MapModel mm) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        
        System.out.println("lvl: " + mm.getLevel());
        System.out.println("col: " + mm.getColumns());
        System.out.println("row: " + mm.getRows());
        
        final MapConverter mc = new MapConverter();
        final List<String> map1 = mc.convertMapCoordinatesToStrings(mm);
        map1.stream().forEach(line -> {
            System.out.println(line);
        });
        
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
    
}
