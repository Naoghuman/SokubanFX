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
package com.github.naoghuman.sokubanfx.resources.image;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

/**
 *
 * @author Naoghuman
 */
public class ImageLoader {
    
    private ObservableMap<String, Image> backgroundImages;
    
    public ImageLoader() {
        this.init();
    }
    
    private void init() {
        backgroundImages = FXCollections.observableHashMap();
    }
    
    public Image loadImage(String name) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load background image: " + name); // NOI18N
        
        final Image image = backgroundImages.computeIfAbsent(
                name,
                key -> {
                    return this.load(key);
                });
        
        return image;
    }
    
    private Image load(String image) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load image: " + image + " from resources"); // NOI18N

        Image img = null;
        try {
            final URI uri = this.getClass().getResource(image).toURI();
            img = new Image(uri.toString(), 1280.0d, 720.0d, true, true);
        } catch (URISyntaxException ex) {
            LoggerFacade.INSTANCE.error(this.getClass(), "Can't load image: " + image, ex); // NOI18N
        }
        
        return img;
    }
    
}
