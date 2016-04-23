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
package com.github.naoghuman.sokubanfx.map;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author Naoghuman
 */
class MapLoader implements IMapConfiguration {
    
    MapLoader() {
        this.init();
    }
    
    private void init() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Init MapLoader"); // NOI18N
        
        PropertiesFacade.INSTANCE.register(KEY__MAP__RESOURCE_BUNDLE);
    }
    
    public int getMapMax() {
        return Integer.valueOf(this.getProperty(KEY__MAP__MAX));
    }
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(KEY__MAP__RESOURCE_BUNDLE, propertyKey);
    }
    
    MapModel loadMap(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load map: " + level); // NOI18N
        
        final List<String> mapAsStrings = this.readMapAsStrings(level);
        mapAsStrings.stream().forEach((str) -> {
            System.out.println(str);
        });
        
        return null;
    }
    
    public List<String> readMapAsStrings(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Read map as Strings: " + level); // NOI18N
        
        final List<String> mapAsStrings = FXCollections.observableArrayList();
        
//        try {
//            URI uri = this.getClass().getResource("/maps/map" + level + ".txt").toURI();
//            mapAsStrings.addAll(Files.readAllLines(Paths.get(uri),//"/maps/map" + level + ".txt"), // NOI8N
//                    StandardCharsets.UTF_8));
//        } catch (IOException | URISyntaxException ex) {
//            LoggerFacade.INSTANCE.error(this.getClass(), "Can't load map: " + level, ex); // NOI18N
//        }
        
        final String mapAsString = this.getProperty(KEY__MAP__POINT + level);
        final String[] splits = mapAsString.split(";"); // NOI18N
        mapAsStrings.addAll(Arrays.asList(splits));
        
        return mapAsStrings;
    }
    
}
