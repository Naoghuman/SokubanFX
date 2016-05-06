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
import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.map.geometry.EDirection;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.collections.ObservableList;

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
    
    private ECollisionResult calculateCollisionResult(
            Coordinates coordinates, ECollisionResult collisionResultIsNotOkay, 
            ECollisionResult collisionResultIsOkay
    ) {
        ECollisionResult collisionResult = collisionResultIsNotOkay;
        if (!Coordinates.isDefault(coordinates)) {
            collisionResult = collisionResultIsOkay;
        }
        
        return collisionResult;
    }
    
    private Coordinates calculateCoordinates(EDirection direction, Coordinates player, int update) {
        final Coordinates coordinates = Coordinates.getDefault();
        switch(direction) {
            case DOWN : { coordinates.setX(player.getX());          coordinates.setY(player.getY() + update); break; }
            case LEFT : { coordinates.setX(player.getX() - update); coordinates.setY(player.getY());          break; }
            case RIGHT: { coordinates.setX(player.getX() + update); coordinates.setY(player.getY());          break; }
            case UP   : { coordinates.setX(player.getX());          coordinates.setY(player.getY() - update); break; }
        }
        
        return coordinates;
    }
    
    private Coordinates calculateFoundedCoordinates(Coordinates coordinatesToCheck, ObservableList<Coordinates> listCoordinates) {
        final Coordinates coordinatesFounded = Coordinates.getDefault();
        if (!Coordinates.isDefault(coordinatesToCheck)) {
            listCoordinates.stream()
                    .filter(coordinates -> {
                        final boolean shouldCoordinatesUpdate = 
                                coordinates.getX() == coordinatesToCheck.getX()
                                && coordinates.getY() == coordinatesToCheck.getY(); 
                        return shouldCoordinatesUpdate;
                    })
                    .findFirst()
                    .ifPresent(coordinates -> {
                        coordinatesFounded.setX(coordinates.getX());
                        coordinatesFounded.setY(coordinates.getY());
                    });
        }
        
        return coordinatesFounded;
    }
    
    public ECollisionResult checkCollisionPlayerBox(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        final ECollisionResult collisionResult = this.calculateCollisionResult(box, ECollisionResult.NONE, ECollisionResult.PLAYER_AGAINST__BOX);
        
        LoggerFacade.INSTANCE.info(this.getClass(), "Check collision 'player -> box' for direction: " // NOI18N
                + direction.toString() + " returns: " + collisionResult); // NOI18N
        
        return collisionResult;
    }
    
    public ECollisionResult checkCollisionPlayerBoxBox(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        ECollisionResult collisionResult = this.calculateCollisionResult(box, ECollisionResult.NONE, ECollisionResult.PLAYER_AGAINST__BOX);
        
        // Check second box
        if (collisionResult.equals(ECollisionResult.PLAYER_AGAINST__BOX)) {
            final Coordinates boxBox = this.extractCoordinatesForPlayerBoxBox(direction, mapModel);
            collisionResult = this.calculateCollisionResult(boxBox, ECollisionResult.PLAYER_AGAINST__BOX_NONE, ECollisionResult.PLAYER_AGAINST__BOX_BOX);
        }
        
        LoggerFacade.INSTANCE.info(this.getClass(), "Check collision 'player -> box -> box' for direction: " // NOI18N
                + direction.toString() + " returns: " + collisionResult); // NOI18N
        
        return collisionResult;
    }
    
    public ECollisionResult checkCollisionPlayerBoxPlace(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        ECollisionResult collisionResult = this.calculateCollisionResult(box, ECollisionResult.NONE, ECollisionResult.PLAYER_AGAINST__BOX);
        
        // Check second place
        if (collisionResult.equals(ECollisionResult.PLAYER_AGAINST__BOX)) {
            final Coordinates boxPlace = this.extractCoordinatesForPlayerBoxPlace(direction, mapModel);
            collisionResult = this.calculateCollisionResult(boxPlace, ECollisionResult.PLAYER_AGAINST__BOX_NONE, ECollisionResult.PLAYER_AGAINST__BOX_PLACE);
        }
        
        LoggerFacade.INSTANCE.info(this.getClass(), "Check collision 'player -> box -> place' for direction: " // NOI18N
                + direction.toString() + " returns: " + collisionResult); // NOI18N
        
        return collisionResult;
    }
    
    public ECollisionResult checkCollisionPlayerBoxPlaceFinish(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> place -> finish'"); // NOI18N
        
        final ObservableList<Coordinates> places = mapModel.getPlaces();
        final ObservableList<Coordinates> boxes = mapModel.getBoxes();
        
        final AtomicInteger counter = new AtomicInteger(0);
        places.forEach(place -> {
            boxes.stream()
                    .filter(box -> {
                        final boolean shouldCounterIncrement = 
                                place.getX() == box.getX()
                                && place.getY() == box.getY();
                        return shouldCounterIncrement;
                    })
                    .findFirst()
                    .ifPresent(box -> {
                        counter.set(counter.get() + 1);
                    });
        });
        
        // All boxes are on places?
        final int maxPlaces = places.size();
        final boolean allBoxesAreOnPlaces = (maxPlaces == counter.get());
        ECollisionResult collisionResult = ECollisionResult.NONE;
        if (allBoxesAreOnPlaces) {
            collisionResult = ECollisionResult.PLAYER_AGAINST__BOX_PLACE_AND_FINISH;
        }
        
        return collisionResult;
    }
    
    public ECollisionResult checkCollisionPlayerBoxWall(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> box -> wall' for direction: " + direction.toString()); // NOI18N
        
        // Check first box
        final Coordinates box = this.extractCoordinatesForPlayerBox(direction, mapModel);
        ECollisionResult collisionResult = this.calculateCollisionResult(box, ECollisionResult.NONE, ECollisionResult.PLAYER_AGAINST__BOX);
        
        // Check second box
        if (collisionResult.equals(ECollisionResult.PLAYER_AGAINST__BOX)) {
            final Coordinates boxWall = this.extractCoordinatesForPlayerBoxWall(direction, mapModel);
            collisionResult = this.calculateCollisionResult(boxWall, ECollisionResult.PLAYER_AGAINST__BOX_NONE, ECollisionResult.PLAYER_AGAINST__BOX_WALL);
        }
        
        LoggerFacade.INSTANCE.info(this.getClass(), "Check collision 'player -> box -> box' for direction: " // NOI18N
                + direction.toString() + " returns: " + collisionResult); // NOI18N
        
        return collisionResult;
    }
    
    public ECollisionResult checkCollisionPlayerWall(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check collision 'player -> wall' for direction: " + direction.toString()); // NOI18N
        
        final Coordinates wall = this.extractCoordinatesForPlayerWall(direction, mapModel);
        final ECollisionResult collisionResult = this.calculateCollisionResult(wall, ECollisionResult.NONE, ECollisionResult.PLAYER_AGAINST__WALL);
        
        LoggerFacade.INSTANCE.info(this.getClass(), "Check collision 'player -> wall' for direction: " // NOI18N
                + direction.toString() + " returns: " + collisionResult); // NOI18N
        
        return collisionResult;
    }

    private Coordinates extractCoordinatesForPlayerBox(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial box around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesBox = this.calculateCoordinates(direction, player, 1);
        
        // Check if on the potenzial coordinatesBox a box
        final Coordinates coordinatesFoundedBox = this.calculateFoundedCoordinates(coordinatesBox, mapModel.getBoxes());
        
        return coordinatesFoundedBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxBox(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> box' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial sec. box around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesBox = this.calculateCoordinates(direction, player, 2);
        
        // Check if on the potenzial coordinatesBox a boxBox
        final Coordinates coordinatesFoundedBoxBox = this.calculateFoundedCoordinates(coordinatesBox, mapModel.getBoxes());
        
        return coordinatesFoundedBoxBox;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxPlace(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> place' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial place around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesPlace = this.calculateCoordinates(direction, player, 2);
        
        // Check if on the potenzial coordinatesPlace a boxPlace
        final Coordinates coordinatesFoundedBoxPlace = this.calculateFoundedCoordinates(coordinatesPlace, mapModel.getPlaces());
        
        return coordinatesFoundedBoxPlace;
    }
    
    private Coordinates extractCoordinatesForPlayerBoxWall(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> box -> wall' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial sec. wall around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesWall = this.calculateCoordinates(direction, player, 2);
        
        // Check if on the potenzial coordinatesWall a boxWall
        final Coordinates coordinatesFoundedBoxWall = this.calculateFoundedCoordinates(coordinatesWall, mapModel.getWalls());
        
        return coordinatesFoundedBoxWall;
    }

    private Coordinates extractCoordinatesForPlayerWall(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Extract Coordinates for 'player -> wall' for direction: " + direction.toString()); // NOI18N
        
        // Extract the Coordiantes from potenzial wall around the player
        final Coordinates player = mapModel.getPlayer();
        final Coordinates coordinatesWall = this.calculateCoordinates(direction, player, 1);

        // Check if on the potenzial coordinatesWall a wall
        final Coordinates coordinatesFoundedWall = this.calculateFoundedCoordinates(coordinatesWall, mapModel.getWalls());
        
        return coordinatesFoundedWall;
    }
    
}
