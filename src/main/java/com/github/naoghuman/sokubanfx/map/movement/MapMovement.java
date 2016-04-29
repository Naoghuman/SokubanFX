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
import com.github.naoghuman.sokubanfx.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.geometry.Direction;
import com.github.naoghuman.sokubanfx.map.animation.EAnimation;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import com.github.naoghuman.sokubanfx.map.collision.CollisionChecker;
import com.github.naoghuman.sokubanfx.map.collision.CollisionResult;
import java.util.List;

/**
 *
 * @author Naoghuman
 */
public class MapMovement {
    
    private static final int TRANSLATE_TO_ONE_TILE = 1;
    
    /*
    TODO refactoring Direction to EDirection (later)
    
    TODO refactoring
       1) unbenennen in checkMovePlayerTo(...): CheckResult
       2) Auswertung des neuen enum/class CheckResult
           - EAnimation (NONE, REALLY_GREAT, WHAT_HAPPEN)
           - EMovement  (MOVE__NONE, MOVE__PLAYER, MOVE__PLAYER_AND_BOX), (Coordinats toMove)
              - Coordinates: Neue Parameter moveToX, moveToY...
       3) Soll eine Animation angezeigt werden?
       4) Soll ein Movement durchgeführt werden?
    */
    /*
        Check are
         - Player -> Wall
         - Player -> Box
            - Player -> Box -> Box
            - Player -> Box -> Wall
            - Player -> Box -> Wall
    */
    public CheckMovementResult checkMovePlayerTo(Direction direction, MapModel mapModel) {
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
        if (collisionResultCheckCollisionPlayerBox.equals(CollisionResult.NO_COLLISION)) {
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
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation REALLY_GOOD for 'player -> box -> place'"); // NOI18N
            
            checkMovementResult.setAnimation(EAnimation.REALLY_GREAT);
            
            final EMovement movement = EMovement.PLAYER_AND_BOX;
            final Coordinates coordinatesBoxToMove = this.tranlateCoordinatesForBoxToMove(direction, mapModel.getPlayer(), mapModel.getBoxes());
            movement.setCoordinatesBoxToMove(coordinatesBoxToMove);
            final Coordinates coordinatesPlayerToMove = this.translateCoordinates(direction, mapModel.getPlayer());
            movement.setCoordinatesPlayerToMove(coordinatesPlayerToMove);
            checkMovementResult.setMovement(movement);
            
            return checkMovementResult;
        }
        
        return CheckMovementResult.getDefault();
    }
    
    public void movePlayerTo(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Move player to direction: " + direction.toString()); // NOI18N

//        final CollisionResult collisionResultCheckCollisionPlayerWall = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
//        if (collisionResultCheckCollisionPlayerWall.equals(CollisionResult.WALL)) {
//            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation WHAT_HAPPEN for 'player -> wall'"); // NOI18N
//            return;
//        }
        
        /*
           CollisionResult.NO_BOX
            - if -> NO animation + MOVEMENT
           CollisionResult.BOX
            - if -> other checks...
        */
//        final CollisionResult collisionResultCheckCollisionPlayerBox = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
//        if (collisionResultCheckCollisionPlayerBox.equals(CollisionResult.NO_BOX)) {
//            this.updateMap(direction, mapModel);
//            return;
//        }
        
//        if (collisionResultCheckCollisionPlayerBox.equals(CollisionResult.BOX)) {
            /*
                CollisionResult.KEEP_GOING
                 - if -> its okay go to other checks
                CollisionResult.WHAT_HAPPEN
                 - if -> NO more movement
            */
//            final CollisionResult collisionResultCheckCollisionPlayerBoxBox = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
//            if (collisionResultCheckCollisionPlayerBoxBox.equals(CollisionResult.WHAT_HAPPEN)) {
//                LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation WHAT_HAPPEN for 'player -> box -> box'"); // NOI18N
//                return;
//            }

            /*
               CollisionResult.KEEP_GOING
                - if -> its okay go to other checks
               CollisionResult.WHAT_HAPPEN
                - if ->  + NO more movement
            */
//            final CollisionResult collisionResultCheckCollisionPlayerBoxWall = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
//            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO add collision check for 'player -> box -> wall'"); // NOI18N

            /*
               CollisionResult.KEEP_GOING
                - if -> do movement
               CollisionResult.REALLY_GOOD
                - if -> play animation + do movement
            */
//            final CollisionResult collisionResultCheckCollisionPlayerBoxPlace = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
//            if (collisionResultCheckCollisionPlayerBoxPlace.equals(CollisionResult.REALLY_GOOD)) {
//                LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation REALLY_GOOD for 'player -> box -> place'"); // NOI18N
//            }
            
//            return;
//        }
        
        
        /*
        animation:
         - Play animation
         - switch(CollisionResult)
            - WHAT_HAPPEN -> :(
            - REALLY_GOOD -> :)
        
            - KEEP_GOING  -> no animation
            - default     -> no animation
        
        shouldUpdate:
         - if (CollisionResult.WHAT_HAPPEN), then no update
        */
        
        /*
        move:
         a) Move/render wall-image and sign (direction.update() - up/down/x, left,right/y)
         b) Update MapModel
             - updateWalls()
             - updatePlaces()
             - updateBoxes()
        */
        
    }
    
    private Coordinates calculateCoordinates(Direction direction, Coordinates coordinates) {
        final Coordinates coordinatesCalculated = Coordinates.getDefault();
        switch(direction) {
            case DOWN : { coordinatesCalculated.setX(coordinates.getX());     coordinatesCalculated.setY(coordinates.getY() + 1); break; }
            case LEFT : { coordinatesCalculated.setX(coordinates.getX() - 1); coordinatesCalculated.setY(coordinates.getY());     break; }
            case RIGHT: { coordinatesCalculated.setX(coordinates.getX() + 1); coordinatesCalculated.setY(coordinates.getY());     break; }
            case UP   : { coordinatesCalculated.setX(coordinates.getX());     coordinatesCalculated.setY(coordinates.getY() - 1); break; }
        }
        
        return coordinatesCalculated;
    }
    
    private Coordinates tranlateCoordinatesForBoxToMove(Direction direction, Coordinates player, List<Coordinates> coordinatesBoxes) {
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
    
    private Coordinates translateCoordinates(Direction direction, Coordinates coordinates) {
        final Coordinates coordinatesCalculated = this.calculateCoordinates(direction, coordinates);
        coordinates.setTranslateX(coordinatesCalculated.getX());
        coordinates.setTranslateY(coordinatesCalculated.getY());
        
        return coordinates;
    }
    
}
