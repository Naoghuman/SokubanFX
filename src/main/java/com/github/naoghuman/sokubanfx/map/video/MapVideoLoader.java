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
package com.github.naoghuman.sokubanfx.map.video;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Naoghuman
 */
public class MapVideoLoader {
    
    private static MapVideoLoader instance = null;
    
    public static MapVideoLoader getDefault() {
        if (instance == null) {
            instance = new MapVideoLoader();
        }
        
        return instance;
    }
    
    private MapVideoLoader() {
        this.init();
    }
    
    private void init() {
        
    }
    
    /**
     * That this method works for development and the executable jar following 
     * requirements must be given.<br />
     * a) Development<br />
     * In the project folder must the folder 'video' with the videos exists.
     * <p />
     * b) Executable jar<br />
     * The above described folder 'video' must be manually copied after executing 
     * the command 'mvn jfx:jar' to the folder 'SokubanFX\target\jfx\app'.<br />
     *  - Manually because ant destroyed the videos during the copy action.
     * 
     * @return 
     */
    public MediaPlayer loadVideo() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load video"); // NOI18N

        final String pathWithVideo = System.getProperty("user.dir")+ "/video/bg_checkered-past.mp4";
        final File videoFile = new File(pathWithVideo);
        LoggerFacade.INSTANCE.debug(this.getClass(), "pathWithVideo: " + videoFile.toURI().toString());

        final Media media = new Media(videoFile.toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setOnReady(() -> {
            mediaPlayer.play();
        });
        
        return mediaPlayer;
    }
    
    
}
