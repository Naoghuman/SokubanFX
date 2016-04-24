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

import com.github.naoghuman.sokubanfx.geometry.Direction;

/**
 *
 * @author Naoghuman
 */
public class MapUpdater {
    
    private MapModel mapModelOriginal;
    
    void storeOriginalMap(MapModel mapModelOriginal) {
        this.mapModelOriginal = mapModelOriginal;
    }
    
    void playerMoveTo(Direction direction) {
        /*
        check:
         - checkCollisionPlayerSignSign
            - player <-> box <-> box   (oh no, WHAT_HAPPEN animation)
            - player <-> box <-> place (yeah!, REALLY_GOOD animation)
         - checkCollisionPlayerSignWall
            - player <-> box <-> wall  (oh no, WHAT_HAPPEN animation)
         - checkCollisionPlayerWall
            - player <-> wall  (oh no, WHAT_HAPPEN animation)
        
         - checkCollisionXYs return CollisionResult
            - CollisionResult.WHAT_HAPPEN
               - Play WHAT_HAPPEN animation :(
               - No updateXy() methods will be performed.
                  - Hit: Player need to restart the level.
            - CollisionResult.KEEP_GOING
               - No animation
               - Perform updateXy() methods.
            - CollisionResult.REALLY_GOOD
               - Play REALLY_GOOD animation :)
               - Perform updateXy() methods.

        animation:
         - Play animation
         - switch(CollisionResult)
            - WHAT_HAPPEN -> :(
            - REALLY_GOOD -> :)
        
            - KEEP_GOING  -> no animation
            - default     -> no animation
        
        shouldUpdate:
         - if (CollisionResult.WHAT_HAPPEN), then no update
        
        move:
         a) Move/render wall-image and sign (direction.update() - up/down/x, left,right/y)
         b) Update MapModel
             - updateWalls()
             - updatePlaces()
             - updateBoxes()
        */
    }
    
}
