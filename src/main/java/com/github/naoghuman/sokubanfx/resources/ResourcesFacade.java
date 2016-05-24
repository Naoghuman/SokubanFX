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
package com.github.naoghuman.sokubanfx.resources;

import com.github.naoghuman.sokubanfx.resources.image.ImageLoader;
import com.github.naoghuman.sokubanfx.resources.video.VideoLoader;

/**
 *
 * @author Naoghuman
 */
public class ResourcesFacade {
    
    private static ResourcesFacade instance = null;
    
    public static ResourcesFacade getDefault() {
        if (instance == null) {
            instance = new ResourcesFacade();
        }
        
        return instance;
    }
    
    private ImageLoader imageLoader = null;
    private VideoLoader videoLoader = null;
    
    private ResourcesFacade() {
        this.init();
    }
    
    private void init() {
        imageLoader = new ImageLoader();
        videoLoader = new VideoLoader();
    }
    
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    
    public VideoLoader getVideoLoader() {
        return videoLoader;
    }
    
}
