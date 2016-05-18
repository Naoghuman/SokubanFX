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
package com.github.naoghuman.sokubanfx.map.image;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

/**
 *
 * @author Naoghuman
 */
public class MapImageLoader {
    
    private static final String PATH_TO_IMAGE = 
            "com" + File.separator // NOI18N
            + "github" + File.separator // NOI18N
            + "naoghuman" + File.separator // NOI18N
            + "sokubanfx" + File.separator // NOI18N
            + "map" + File.separator // NOI18N
            + "image" + File.separator; // NOI18N
    
    private static MapImageLoader instance = null;
    
    public static MapImageLoader getDefault() {
        if (instance == null) {
            instance = new MapImageLoader();
        }
        
        return instance;
    }
    
    private ObservableMap<String, Image> backgroundImages;
    
    private MapImageLoader() {
        this.init();
    }
    
    private void init() {
        backgroundImages = FXCollections.observableHashMap();
    }
    
    public Image getBackgroundImage(String backgroundImage) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load background image: " + backgroundImage); // NOI18N
        
        final Image image = backgroundImages.computeIfAbsent(
                backgroundImage,
                key -> {
                    return this.loadImage(key);
                });
        
        return image;
    }
    
    private Image loadImage(String image) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load image: " + image + " from resources"); // NOI18N
        
        final Image img = new Image(PATH_TO_IMAGE + image, 1280.0d, 720.0d, true, true);
        return img;
    }
    
}
