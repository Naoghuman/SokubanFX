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
import com.github.naoghuman.sokubanfx.geometry.Direction;
import com.github.naoghuman.sokubanfx.map.animation.EAnimation;
import com.github.naoghuman.sokubanfx.map.movement.CheckMovementResult;
import com.github.naoghuman.sokubanfx.map.movement.EMovement;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Naoghuman
 */
public enum MapFacade implements IMapConfiguration {
    
    INSTANCE;
    
    private MapConverter mapConverter;
    private MapLoader mapLoader;
    private MapMovement mapMovement;
    
    MapFacade() {
        this.init();
    }
    
    private void init() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Init MapFacade"); // NOI18N
        
        mapConverter = new MapConverter();
        mapLoader = new MapLoader();
        mapMovement = new MapMovement();
    }
    
    public List<String> convertMapCoordinatesToStrings(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Convert Map Coordinates to Strings"); // NOI18N
        
        return mapConverter.convertMapCoordinatesToStrings(mapModel);
    }
    
    public int getRandomMapIndex() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Get random Map index"); // NOI18N
        
        final Random random = new Random();
        final int mapMax = mapLoader.getMapMax();
        final int randomMapIndex = random.nextInt(mapMax) + 1;
        
        return randomMapIndex;
    }
    
    public List<String> loadRandomMap() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load random map"); // NOI18N
        
        final Random random = new Random();
        final int mapMax = mapLoader.getMapMax();
        
        // Maps are from 1-n, not 0-n
        final int level = random.nextInt(mapMax) + 1;
        
        MapModel mapModel = this.loadMap(level); // XXX
        System.out.println(mapModel.toString()); // XXX
        
        return this.readMapAsStrings(level);
    }
    
    public MapModel loadMap(int level) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Load map: " + level); // NOI18N
        
        final List<String> mapAsStrings = mapLoader.loadMapAsStrings(level);
        final MapModel mapModel = mapConverter.convertStringsToMap(level, mapAsStrings);
        
        return mapModel;
    }
    
    public void playerMoveTo(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Player move to direction: " + direction); // NOI18N

        final CheckMovementResult checkMovementResult = mapMovement.checkMovePlayerTo(direction, mapModel);
        final EAnimation animation = checkMovementResult.getAnimation();
        if (
                animation.equals(EAnimation.REALLY_GREAT)
                || animation.equals(EAnimation.WHAT_HAPPEN)
        ) {
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation: " + animation); // NOI18N
            
        }
        
        final EMovement movement = checkMovementResult.getMovement();
        if (
                movement.equals(EMovement.PLAYER)
                || movement.equals(EMovement.PLAYER_AND_BOX)
        ) {
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO do movement: " + movement + " to direction: " + direction); // NOI18N
            
            
            
        }
    }
    
    public List<String> readMapAsStrings(int level) {
        return mapLoader.loadMapAsStrings(level);
    }
    
}
