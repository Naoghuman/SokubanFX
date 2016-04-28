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
import com.github.naoghuman.sokubanfx.geometry.Direction;
import com.github.naoghuman.sokubanfx.map.collision.CollisionChecker;
import com.github.naoghuman.sokubanfx.map.collision.CollisionResult;

/**
 *
 * @author Naoghuman
 */
public class MapUpdater {
    
    void playerMoveTo(Direction direction, MapModel mapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Player move to direction: " + direction.toString()); // NOI18N
  
        /*
           CollisionResult.NO_WALL
            - if -> then okay, next check
           CollisionResult.WALL
            - if -> WHAT_HAPPEN animation + NO more movement
        */
        final CollisionResult collisionResultCheckCollisionPlayerWall = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        if (collisionResultCheckCollisionPlayerWall.equals(CollisionResult.WALL)) {
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation WHAT_HAPPEN for 'player -> wall'"); // NOI18N
            return;
        }
        
        /*
           CollisionResult.NO_BOX
            - if -> NO animation + MOVEMENT
           CollisionResult.BOX
            - if -> other checks...
        */
        final CollisionResult collisionResultCheckCollisionPlayerBox = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        if (collisionResultCheckCollisionPlayerBox.equals(CollisionResult.BOX)) {
            /*
                CollisionResult.KEEP_GOING
                 - if -> its okay go to other checks
                CollisionResult.WHAT_HAPPEN
                 - if -> NO more movement
            */
            final CollisionResult collisionResultCheckCollisionPlayerBoxBox = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
            if (collisionResultCheckCollisionPlayerBoxBox.equals(CollisionResult.WHAT_HAPPEN)) {
                LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation WHAT_HAPPEN for 'player -> box -> box'"); // NOI18N
                return;
            }

            /*
               CollisionResult.KEEP_GOING
                - if -> its okay go to other checks
               CollisionResult.WHAT_HAPPEN
                - if ->  + NO more movement
            */
            final CollisionResult collisionResultCheckCollisionPlayerBoxWall = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
            LoggerFacade.INSTANCE.trace(this.getClass(), "TODO add collision check for 'player -> box -> wall'"); // NOI18N

            /*
               CollisionResult.KEEP_GOING
                - if -> do movement
               CollisionResult.REALLY_GOOD
                - if -> play animation + do movement
            */
            final CollisionResult collisionResultCheckCollisionPlayerBoxPlace = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
            if (collisionResultCheckCollisionPlayerBoxPlace.equals(CollisionResult.REALLY_GOOD)) {
                LoggerFacade.INSTANCE.trace(this.getClass(), "TODO play animation REALLY_GOOD for 'player -> box -> place'"); // NOI18N
            }
            
            return;
        }
        
        
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
    
}
