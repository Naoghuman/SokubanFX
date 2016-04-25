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
package com.github.naoghuman.sokubanfx.map.collision;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.geometry.Direction;
import com.github.naoghuman.sokubanfx.map.MapModel;
import java.util.List;

/**
 *
 * @author Naoghuman
 */
public class CollisionChecker {
    
    private static CollisionChecker instance = null;
    
    public static CollisionChecker getDefault() {
        if (instance == null) {
            instance = new CollisionChecker();
        }
        
        return instance;
    }
    
    private CollisionChecker() {
        
    }
    
    private CollisionResult calculateCollisionResult(
            Coordinates coordinates, CollisionResult collisionResultDefault, 
            CollisionResult collisionResultExpected
    ) {
        CollisionResult collisionResult = collisionResultDefault;
        if (!Coordinates.isDefault(coordinates)) {
            collisionResult = collisionResultExpected;
        }
        
        return collisionResult;
    }
    
    private Coordinates calculateCoordinates(Direction direction, Coordinates player, int update) {
        final Coordinates coordinates = Coordinates.getDefault();
        switch(direction) {
            case DOWN : { coordinates.setX(player.getX());     coordinates.setY(player.getY() + update); break; }
            case LEFT : { coordinates.setX(player.getX() - update); coordinates.setY(player.getY());     break; }
            case RIGHT: { coordinates.setX(player.getX() + update); coordinates.setY(player.getY());     break; }
            case UP   : { coordinates.setX(player.getX());     coordinates.setY(player.getY() - update); break; }
        }
        
        return coordinates;
    }
    
    private Coordinates calculateFoundedCoordinates(Coordinates coordinatesToCheck, List<Coordinates> listCoordinates) {
        final Coordinates coordinatesFounded = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesToCheck)) {
            for (Coordinates coordinates : listCoordinates) {
                if (coordinates.getX() == coordinatesToCheck.getX() && coordinates.getY() == coordinatesToCheck.getY()) {
                    coordinatesFounded.setX(coordinates.getX());
                    coordinatesFounded.setY(coordinates.getY());
                    break;
                }
            }
        }
        
        return coordinatesFounded;
    }
    
    public CollisionResult checkCollisionPlayerBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        final CollisionResult collisionResult = this.calculateCollisionResult(box, CollisionResult.NO_BOX, CollisionResult.BOX);
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerBoxBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        CollisionResult collisionResult = this.calculateCollisionResult(box, CollisionResult.NO_BOX, CollisionResult.BOX);
        
        // Check second box
        if (collisionResult.equals(CollisionResult.BOX)) {
            final Coordinates boxBox = this.extractCoordinatesForPlayerBoxBox(direction, mapModel);
            collisionResult = this.calculateCollisionResult(boxBox, CollisionResult.KEEP_GOING, CollisionResult.WHAT_HAPPEN);
        }
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerBoxPlace(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        CollisionResult collisionResult = this.calculateCollisionResult(box, CollisionResult.NO_BOX, CollisionResult.BOX);
        
        // Check second place
        if (collisionResult.equals(CollisionResult.BOX)) {
            final Coordinates boxPlace = this.extractCoordinatesForPlayerBoxPlace(direction, mapModel);
            collisionResult = this.calculateCollisionResult(boxPlace, CollisionResult.KEEP_GOING, CollisionResult.REALLY_GOOD);
        }
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerBoxWall(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> wall' for direction: " + direction.toString()); // NOI18N
        
        CollisionResult collisionResult = CollisionResult.NONE;
        
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerWall(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> wall' for direction: " + direction.toString()); // NOI18N
        
        final Coordinates wall = this.extractCoordinatesForPlayerWall(direction, mapModel);
        final CollisionResult collisionResult = this.calculateCollisionResult(wall, CollisionResult.NO_WALL, CollisionResult.WALL);
        
        return collisionResult;
    }

    private Coordinates extractCoordinatesForPlayerBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial box around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesBox = this.calculateCoordinates(direction, player, 1);
        
        // Check if on the potenzial coordinatesBox a box
        final Coordinates coordinatesFoundedBox = this.calculateFoundedCoordinates(coordinatesBox, mapModel.getBoxes());
        
        return coordinatesFoundedBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial sec. box around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesBox = this.calculateCoordinates(direction, player, 2);
        
        // Check if on the potenzial coordinatesBox a boxBox
        final Coordinates coordinatesFoundedBoxBox = this.calculateFoundedCoordinates(coordinatesBox, mapModel.getBoxes());
        
        return coordinatesFoundedBoxBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxPlace(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial place around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesPlace = this.calculateCoordinates(direction, player, 2);
        
        // Check if on the potenzial coordinatesPlace a boxPlace
        final Coordinates coordinatesFoundedBoxPlace = this.calculateFoundedCoordinates(coordinatesPlace, mapModel.getPlaces());
        
        return coordinatesFoundedBoxPlace;
    }

    private Coordinates extractCoordinatesForPlayerWall(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> wall' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial wall around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesWall = this.calculateCoordinates(direction, player, 1);

        // Check if on the potenzial coordinatesWall a wall
        final Coordinates coordinatesFoundedWall = this.calculateFoundedCoordinates(coordinatesWall, mapModel.getWalls());
        
        return coordinatesFoundedWall;
    }
    
}
