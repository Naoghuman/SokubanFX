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
    
    private MapModel actualMapModel;
    private MapModel originalMapModel;
    
    void playerMoveTo(Direction direction) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Player move to direction: " + direction.toString()); // NOI18N
        
        /*
        check:
         - checkCollisionPlayerSignSign
            - player <-> box <-> box   (oh no, WHAT_HAPPEN animation)
            - player <-> box <-> place (yeah!, REALLY_GOOD animation)
         - checkCollisionPlayerSignWall
            - player <-> box <-> wall  (oh no, WHAT_HAPPEN animation)
         - checkCollisionPlayerWall
            - player <-> wall  (oh no, WHAT_HAPPEN animation)
        */
        final CollisionResult collisionResultCheckCollisionPlayerBox = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, actualMapModel);
        final CollisionResult collisionResultCheckCollisionPlayerWall = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, actualMapModel);
        
        final CollisionResult collisionResultCheckCollisionPlayerBoxBox = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, actualMapModel);
        final CollisionResult collisionResultCheckCollisionPlayerBoxWall = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, actualMapModel);
        
        final CollisionResult collisionResultCheckCollisionPlayerBoxPlace = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, actualMapModel);
        
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
    
    public void putMapModel(MapModel actualMapModel) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Put MapModel"); // NOI18N
        
        this.actualMapModel = actualMapModel;
        this.originalMapModel = actualMapModel;
    }
    
}
