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
import java.util.List;
import java.util.Random;

/**
 *
 * @author Naoghuman
 */
public enum MapFacade implements IMapConfiguration {
    
    INSTANCE;
    
    private MapLoader mapLoader;
    
    MapFacade() {
        this.init();
    }
    
    private void init() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Init MapFacade"); // NOI18N
        
        mapLoader = new MapLoader();
    }
    
    public List<String> loadRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load random map"); // NOI18N
        
        final Random random = new Random();
        final int mapMax = mapLoader.getMapMax();
        
        // Maps are from 1-n, not 0-n
        final int level = random.nextInt(mapMax) + 1;
        
        return this.readMapAsStrings(level);
    }
    
    public MapModel loadMap(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load map: " + level); // NOI18N
        
        return mapLoader.loadMap(level);
    }
    
    public List<String> readMapAsStrings(int level) {
        return mapLoader.readMapAsStrings(level);
    }
    
}
