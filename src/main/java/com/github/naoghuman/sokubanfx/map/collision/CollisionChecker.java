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
    
    public CollisionResult checkCollisionPlayerBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        CollisionResult collisionResult = CollisionResult.NO_BOX;
        if (!Coordinates.isDefault(box)) {
            collisionResult = CollisionResult.BOX;
        }
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerBoxBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        CollisionResult collisionResult = CollisionResult.NO_BOX;
        if (!Coordinates.isDefault(box)) {
            // First box is found
            collisionResult = CollisionResult.BOX;
        }
        
        // Check second box
        if (collisionResult.equals(CollisionResult.BOX)) {
            final Coordinates boxBox = this.extractCoordinatesForPlayerBoxBox(direction, mapModel);
            collisionResult = CollisionResult.KEEP_GOING;
            if (!Coordinates.isDefault(boxBox)) {
                // Second box is found
                collisionResult = CollisionResult.WHAT_HAPPEN;
            }
        }
        
        return collisionResult;
    }
    
    public CollisionResult checkCollisionPlayerBoxPlace(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        CollisionResult collisionResult = CollisionResult.NO_BOX;
        if (!Coordinates.isDefault(box)) {
            // First box is found
            collisionResult = CollisionResult.BOX;
        }
        
        // Check second place
        if (collisionResult.equals(CollisionResult.BOX)) {
            final Coordinates boxPlace = this.extractCoordinatesForPlayerBoxPlace(direction, mapModel);
            collisionResult = CollisionResult.KEEP_GOING;
            if (!Coordinates.isDefault(boxPlace)) {
                // Second place is found
                collisionResult = CollisionResult.REALLY_GOOD;
            }
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
        
        final Coordinates box = this.extractCoordinatesForPlayerWall(direction, mapModel);
        CollisionResult collisionResult = CollisionResult.NO_WALL;
        if (!Coordinates.isDefault(box)) {
            collisionResult = CollisionResult.WALL;
        }
        
        return collisionResult;
    }

    private Coordinates extractCoordinatesForPlayerBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial box around the player
        final Coordinates coordinatesBox = Coordinates.getDefault();
        final Coordinates player = mapModel.getPlayer();
        switch(direction) {
            case DOWN : { coordinatesBox.setX(player.getX());     coordinatesBox.setY(player.getY() + 1); break; }
            case LEFT : { coordinatesBox.setX(player.getX() - 1); coordinatesBox.setY(player.getY());     break; }
            case RIGHT: { coordinatesBox.setX(player.getX() + 1); coordinatesBox.setY(player.getY());     break; }
            case UP   : { coordinatesBox.setX(player.getX());     coordinatesBox.setY(player.getY() - 1); break; }
        }
        
        // Check if on the potenzial coordinatesBox a box
        final Coordinates coordinatesFoundedBox = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesBox)) {
            final List<Coordinates> boxes = mapModel.getBoxes();
            for (Coordinates box : boxes) {
                if (box.getX() == coordinatesBox.getX() && box.getY() == coordinatesBox.getY()) {
                    coordinatesFoundedBox.setX(box.getX());
                    coordinatesFoundedBox.setY(box.getY());
                    break;
                }
            }
        }
        
        return coordinatesFoundedBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxBox(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial sec. box around the player
        final Coordinates coordinatesBox = Coordinates.getDefault();
        final Coordinates player = mapModel.getPlayer();
        switch(direction) {
            case DOWN : { coordinatesBox.setX(player.getX());     coordinatesBox.setY(player.getY() + 2); break; }
            case LEFT : { coordinatesBox.setX(player.getX() - 2); coordinatesBox.setY(player.getY());     break; }
            case RIGHT: { coordinatesBox.setX(player.getX() + 2); coordinatesBox.setY(player.getY());     break; }
            case UP   : { coordinatesBox.setX(player.getX());     coordinatesBox.setY(player.getY() - 2); break; }
        }
        
        // Check if on the potenzial coordinatesBox a boxBox
        final Coordinates coordinatesFoundedBoxBox = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesBox)) {
            final List<Coordinates> boxes = mapModel.getBoxes();
            for (Coordinates box : boxes) {
                if (box.getX() == coordinatesBox.getX() && box.getY() == coordinatesBox.getY()) {
                    coordinatesFoundedBoxBox.setX(box.getX());
                    coordinatesFoundedBoxBox.setY(box.getY());
                    break;
                }
            }
        }
        
        return coordinatesFoundedBoxBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxPlace(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial place around the player
        final Coordinates coordinatesPlace = Coordinates.getDefault();
        final Coordinates player = mapModel.getPlayer();
        switch(direction) {
            case DOWN : { coordinatesPlace.setX(player.getX());     coordinatesPlace.setY(player.getY() + 2); break; }
            case LEFT : { coordinatesPlace.setX(player.getX() - 2); coordinatesPlace.setY(player.getY());     break; }
            case RIGHT: { coordinatesPlace.setX(player.getX() + 2); coordinatesPlace.setY(player.getY());     break; }
            case UP   : { coordinatesPlace.setX(player.getX());     coordinatesPlace.setY(player.getY() - 2); break; }
        }
        
        // Check if on the potenzial coordinatesPlace a boxPlace
        final Coordinates coordinatesFoundedBoxPlace = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesPlace)) {
            final List<Coordinates> places = mapModel.getPlaces();
            for (Coordinates place : places) {
                if (place.getX() == coordinatesPlace.getX() && place.getY() == coordinatesPlace.getY()) {
                    coordinatesFoundedBoxPlace.setX(place.getX());
                    coordinatesFoundedBoxPlace.setY(place.getY());
                    break;
                }
            }
        }
        
        return coordinatesFoundedBoxPlace;
    }

    private Coordinates extractCoordinatesForPlayerWall(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> wall' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial wall around the player
        final Coordinates coordinatesWall = Coordinates.getDefault();
        final Coordinates player = mapModel.getPlayer();
        switch(direction) {
            case DOWN : { coordinatesWall.setX(player.getX());     coordinatesWall.setY(player.getY() + 1); break; }
            case LEFT : { coordinatesWall.setX(player.getX() - 1); coordinatesWall.setY(player.getY());     break; }
            case RIGHT: { coordinatesWall.setX(player.getX() + 1); coordinatesWall.setY(player.getY());     break; }
            case UP   : { coordinatesWall.setX(player.getX());     coordinatesWall.setY(player.getY() - 1); break; }
        }
        
        // Check if on the potenzial coordinatesWall a wall
        final Coordinates coordinatesFoundedWall = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesWall)) {
            final List<Coordinates> walls = mapModel.getWalls();
            for (Coordinates wall : walls) {
                if (wall.getX() == coordinatesWall.getX() && wall.getY() == coordinatesWall.getY()) {
                    coordinatesFoundedWall.setX(wall.getX());
                    coordinatesFoundedWall.setY(wall.getY());
                    break;
                }
            }
        }
        
        return coordinatesFoundedWall;
    }
    
}
