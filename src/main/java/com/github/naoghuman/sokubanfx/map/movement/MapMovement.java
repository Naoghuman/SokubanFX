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
package com.github.naoghuman.sokubanfx.map.movement;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.map.geometry.EDirection;
import com.github.naoghuman.sokubanfx.map.animation.EAnimation;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.collision.CollisionChecker;
import com.github.naoghuman.sokubanfx.map.collision.CollisionResult;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class MapMovement {
    
    private Coordinates calculateCoordinates(EDirection direction, Coordinates coordinates) {
        final Coordinates coordinatesCalculated = Coordinates.getDefault();
        switch(direction) {
            case DOWN : { coordinatesCalculated.setX(coordinates.getX());     coordinatesCalculated.setY(coordinates.getY() + 1); break; }
            case LEFT : { coordinatesCalculated.setX(coordinates.getX() - 1); coordinatesCalculated.setY(coordinates.getY());     break; }
            case RIGHT: { coordinatesCalculated.setX(coordinates.getX() + 1); coordinatesCalculated.setY(coordinates.getY());     break; }
            case UP   : { coordinatesCalculated.setX(coordinates.getX());     coordinatesCalculated.setY(coordinates.getY() - 1); break; }
        }
        
        return coordinatesCalculated;
    }
    
    public CheckMovementResult checkIsMapFinish(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check is Map finish"); // NOI18N
        
        // Player -> Box -> Place -> Finished
        final CollisionResult collisionResultCheckCollisionPlayerBoxPlaceFinish = 
                CollisionChecker.getDefault().checkCollisionPlayerBoxPlaceFinish(mapModel);
        
        final CheckMovementResult checkMovementResult = CheckMovementResult.getDefault();
        if (collisionResultCheckCollisionPlayerBoxPlaceFinish.equals(CollisionResult.PLAYER_AGAINST__BOX_PLACE_AND_FINISH)) {
            checkMovementResult.setCheckIsMapFinish(Boolean.TRUE);
        }
        
        return checkMovementResult;
    }
    
    public CheckMovementResult checkMovePlayerTo(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check move player to direction: " + direction.toString()); // NOI18N
        
        // Player -> Wall
        final CollisionResult collisionResultCheckCollisionPlayerWall = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        final CheckMovementResult checkMovementResult = CheckMovementResult.getDefault();
        if (collisionResultCheckCollisionPlayerWall.equals(CollisionResult.PLAYER_AGAINST__WALL)) {
            checkMovementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            checkMovementResult.setMovement(EMovement.NONE);
            
            return checkMovementResult;
        }

        // Player -> Box
        final CollisionResult collisionResultCheckCollisionPlayerBox = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        if (collisionResultCheckCollisionPlayerBox.equals(CollisionResult.NONE)) {
            checkMovementResult.setAnimation(EAnimation.NONE);
            
            final EMovement movement = EMovement.PLAYER;
            movement.setCoordinatesBoxToMove(Coordinates.getDefault());
            final Coordinates coordinatesPlayerToMove = this.translateCoordinates(direction, mapModel.getPlayer());
            movement.setCoordinatesPlayerToMove(coordinatesPlayerToMove);
            checkMovementResult.setMovement(movement);
            
            return checkMovementResult;
        }
        
        // Player -> Box -> Box
        final CollisionResult collisionResultCheckCollisionPlayerBoxBox = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        if (collisionResultCheckCollisionPlayerBoxBox.equals(CollisionResult.PLAYER_AGAINST__BOX_BOX)) {
            checkMovementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            checkMovementResult.setMovement(EMovement.NONE);
            
            return checkMovementResult;
        }
        
        // Player -> Box -> Wall
        final CollisionResult collisionResultCheckCollisionPlayerBoxWall = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        if (collisionResultCheckCollisionPlayerBoxWall.equals(CollisionResult.PLAYER_AGAINST__BOX_WALL)) {
            checkMovementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            checkMovementResult.setMovement(EMovement.NONE);
            
            return checkMovementResult;
        }

        // Player -> Box -> Place
        final CollisionResult collisionResultCheckCollisionPlayerBoxPlace = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        if (collisionResultCheckCollisionPlayerBoxPlace.equals(CollisionResult.PLAYER_AGAINST__BOX_PLACE)) {
            checkMovementResult.setAnimation(EAnimation.REALLY_GREAT);
            checkMovementResult.setCheckIsMapFinish(Boolean.TRUE);
        }
        
        // Player -> Box -> None
        if (collisionResultCheckCollisionPlayerBoxPlace.equals(CollisionResult.PLAYER_AGAINST__BOX_NONE)) {
            checkMovementResult.setAnimation(EAnimation.NONE);
        }
        
        final EMovement movement = EMovement.PLAYER_AND_BOX;
        final Coordinates coordinatesBoxToMove = this.tranlateCoordinatesForBoxToMove(direction, mapModel.getPlayer(), mapModel.getBoxes());
        movement.setCoordinatesBoxToMove(coordinatesBoxToMove);
        final Coordinates coordinatesPlayerToMove = this.translateCoordinates(direction, mapModel.getPlayer());
        movement.setCoordinatesPlayerToMove(coordinatesPlayerToMove);
        checkMovementResult.setMovement(movement);
        
        return checkMovementResult;
    }
    
    private Coordinates tranlateCoordinatesForBoxToMove(EDirection direction, Coordinates player, ObservableList<Coordinates> coordinatesBoxes) {
        final Coordinates coordinatesCalculatedBox = this.calculateCoordinates(direction, player);
        Coordinates coordinatesCalculatedBoxToMove = Coordinates.getDefault();
        for (Coordinates coordinatesBox : coordinatesBoxes) {
            if (
                    coordinatesBox.getX() == coordinatesCalculatedBox.getX()
                    && coordinatesBox.getY() == coordinatesCalculatedBox.getY()
            ) {
                coordinatesCalculatedBoxToMove = this.calculateCoordinates(direction, coordinatesCalculatedBox);
                coordinatesCalculatedBoxToMove.setTranslateX(coordinatesCalculatedBoxToMove.getX());
                coordinatesCalculatedBoxToMove.setTranslateY(coordinatesCalculatedBoxToMove.getY());
                coordinatesCalculatedBoxToMove.setX(coordinatesBox.getX());
                coordinatesCalculatedBoxToMove.setY(coordinatesBox.getY());
                
                break;
            }
        }
    
        return coordinatesCalculatedBoxToMove;
    }
    
    private Coordinates translateCoordinates(EDirection direction, Coordinates coordinates) {
        final Coordinates coordinatesCalculated = this.calculateCoordinates(direction, coordinates);
        coordinates.setTranslateX(coordinatesCalculated.getX());
        coordinates.setTranslateY(coordinatesCalculated.getY());
        
        return coordinates;
    }
    
}
