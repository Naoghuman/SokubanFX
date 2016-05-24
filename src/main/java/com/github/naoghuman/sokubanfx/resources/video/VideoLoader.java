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
package com.github.naoghuman.sokubanfx.resources.video;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import com.github.naoghuman.sokubanfx.configuration.resources.video.IVideoConfiguration;
import java.net.URISyntaxException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Naoghuman
 */
public class VideoLoader implements IVideoConfiguration {
    
    public MediaPlayer loadVideo(int videoIndex) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load video"); // NOI18N
 
        final StringBuilder sb = new StringBuilder();
        sb.append(PREFIX__VIDEO);
        sb.append(videoIndex);
        sb.append(SUFFIX__FILE);
        
        final String video = PropertiesFacade.INSTANCE.getProperty(KEY__RESOURCE_BUNDLE__VIDEO, sb.toString());
        try {
            final Media media = new Media(this.getClass().getResource(video).toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
            });
            
            return mediaPlayer;
        } catch (URISyntaxException ex) {
            LoggerFacade.INSTANCE.error(this.getClass(), "Can't load video: " + video, ex); // NOI18N
        }
        
        return null;
    }
    
}
