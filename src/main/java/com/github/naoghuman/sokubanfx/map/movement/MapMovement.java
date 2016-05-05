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
    
    public MovementResult checkIsMapFinish(MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check is Map finish"); // NOI18N
        
        // Player -> Box -> Place -> Finished
        final CollisionResult collisionResultPlayerBoxPlaceFinish = CollisionChecker.getDefault().checkCollisionPlayerBoxPlaceFinish(mapModel);
        final MovementResult movementResult = MovementResult.getDefault();
        if (collisionResultPlayerBoxPlaceFinish.equals(CollisionResult.PLAYER_AGAINST__BOX_PLACE_AND_FINISH)) {
            movementResult.setIsMapFinish(Boolean.TRUE);
        }
        
        return movementResult;
    }
    
    public MovementResult checkMovePlayerTo(EDirection direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Check move player to direction: " + direction.toString()); // NOI18N
        
        // Player -> Wall
        final CollisionResult collisionResultPlayerWall = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        final MovementResult movementResult = MovementResult.getDefault();
        if (collisionResultPlayerWall.equals(CollisionResult.PLAYER_AGAINST__WALL)) {
            movementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            movementResult.setMovement(EMovement.NONE);
            
            return movementResult;
        }

        // Player -> Box
        final CollisionResult collisionResultPlayerBox = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        if (collisionResultPlayerBox.equals(CollisionResult.NONE)) {
            movementResult.setAnimation(EAnimation.NONE);
            
            movementResult.setBoxToMove(Coordinates.getDefault());
            movementResult.setMovement(EMovement.PLAYER);
            final Coordinates coordinatesPlayerToMove = this.translateCoordinates(direction, mapModel.getPlayer());
            movementResult.setPlayerToMove(coordinatesPlayerToMove);
            
            return movementResult;
        }
        
        // Player -> Box -> Box
        final CollisionResult collisionResultPlayerBoxBox = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        if (collisionResultPlayerBoxBox.equals(CollisionResult.PLAYER_AGAINST__BOX_BOX)) {
            movementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            movementResult.setMovement(EMovement.NONE);
            
            return movementResult;
        }
        
        // Player -> Box -> Wall
        final CollisionResult collisionResultPlayerBoxWall = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        if (collisionResultPlayerBoxWall.equals(CollisionResult.PLAYER_AGAINST__BOX_WALL)) {
            movementResult.setAnimation(EAnimation.WHAT_HAPPEN);
            movementResult.setMovement(EMovement.NONE);
            
            return movementResult;
        }

        // Player -> Box -> Place
        final CollisionResult collisionResultPlayerBoxPlace = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        if (collisionResultPlayerBoxPlace.equals(CollisionResult.PLAYER_AGAINST__BOX_PLACE)) {
            movementResult.setAnimation(EAnimation.REALLY_GREAT);
            movementResult.setIsMapFinish(Boolean.TRUE);
        }
        
        // Player -> Box -> None
        if (collisionResultPlayerBoxPlace.equals(CollisionResult.PLAYER_AGAINST__BOX_NONE)) {
            movementResult.setAnimation(EAnimation.NONE);
        }
        
        final Coordinates coordinatesBoxToMove = this.tranlateCoordinatesForBoxToMove(direction, mapModel.getPlayer(), mapModel.getBoxes());
        movementResult.setBoxToMove(coordinatesBoxToMove);
        movementResult.setMovement(EMovement.PLAYER_AND_BOX);
        final Coordinates coordinatesPlayerToMove = this.translateCoordinates(direction, mapModel.getPlayer());
        movementResult.setPlayerToMove(coordinatesPlayerToMove);
        
        return movementResult;
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
