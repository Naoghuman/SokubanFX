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

import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.converter.MapConverter;
import com.github.naoghuman.sokubanfx.map.movement.MapMovement;
import com.github.naoghuman.sokubanfx.configuration.IMapConfiguration;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.map.geometry.EDirection;
import com.github.naoghuman.sokubanfx.map.movement.CheckMovementResult;
import java.util.Random;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public enum MapFacade implements IMapConfiguration {
    
    INSTANCE;
    
    private static final int[] INDEX_PREVIEW_MAPS = { 
        1, 4, 8, 9, 10, 13, 15, 17, 19, 22, 24, 
        57, 62, 66, 69, 70, 71, 72, 73, 74, 76//, 
//        81, 82, 83, 85, 86, 88, 89, 90, 92, 93, 
//        96, 97, 98, 99, 100, 101, 103, 107, 111, 
//        113, 114, 115, 117, 128, 129, 130, 132, 
//        133, 134, 135, 136, 137, 138, 139, 146, 
//        147, 149, 151, 152, 153, 158, 159, 160
    };
    
    private MapConverter mapConverter;
    private MapLoader mapLoader;
    private MapMovement mapMovement;
    
    private int randomMapIndex = -1;
    
    MapFacade() {
        this.init();
    }
    
    private void init() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Init MapFacade"); // NOI18N
        
        mapConverter = new MapConverter();
        mapLoader = new MapLoader();
        mapMovement = new MapMovement();
    }
    
    public ObservableList<String> convertMapCoordinatesToStrings(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Convert Map Coordinates to Strings"); // NOI18N
        
        return mapConverter.convertMapCoordinatesToStrings(mapModel);
    }
    
    public int getRandomMapIndex() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Get random Map index"); // NOI18N
        
        final Random random = new Random();
        final int oldRandomMapIndex = randomMapIndex;
        int counter = 0;
        while (
                oldRandomMapIndex == randomMapIndex
                && counter <= 3
        ) {
            randomMapIndex = INDEX_PREVIEW_MAPS[random.nextInt(INDEX_PREVIEW_MAPS.length)];
            ++counter;
        }
        
        return randomMapIndex;
    }

    public boolean isMapFinish(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Is Map finish"); // NOI18N
        
        final CheckMovementResult checkMovementResult = mapMovement.checkIsMapFinish(mapModel);
        
        return checkMovementResult.isCheckIsMapFinish();
    }
    
    public MapModel loadMap(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load map: " + level); // NOI18N
        
        final ObservableList<String> mapAsStrings = mapLoader.loadMapAsStrings(level);
        final MapModel mapModel = mapConverter.convertStringsToMap(level, mapAsStrings);
        
        return mapModel;
    }
    
    public CheckMovementResult playerMoveTo(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Player move to direction: " + direction); // NOI18N

        final CheckMovementResult checkMovementResult = mapMovement.checkMovePlayerTo(direction, mapModel);
        return checkMovementResult;
    }
    
}
