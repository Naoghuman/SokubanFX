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

import com.github.naoghuman.sokubanfx.configuration.IMapConfiguration;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.lib.properties.api.PropertiesFacade;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
    private String getProperty(String propertyKey) {
        return PropertiesFacade.INSTANCE.getProperty(KEY__MAP__RESOURCE_BUNDLE, propertyKey);
    }
    
    public ObservableList<String> loadMapAsStrings(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load map as Strings: " + level); // NOI18N
        
        final ObservableList<String> mapAsStrings = FXCollections.observableArrayList();
        final String mapAsString = this.getProperty(KEY__MAP__POINT + level);
        final String[] splits = mapAsString.split(";"); // NOI18N
        mapAsStrings.addAll(Arrays.asList(splits));
        
        return mapAsStrings;
    }
    
}
