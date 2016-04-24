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

/**
 *
 * @author Naoghuman
 */
public enum CollisionResult {
    
    BOX,         // box before player
    KEEP_GOING,  //    movement
    NONE,        //    no result
    NO_BOX,      // no box before player
    REALLY_GOOD, //    movement + :) animation
    WHAT_HAPPEN; // no movement + :( animation
    
}
