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
    
    NONE,                      // ...
    PLAYER_AGAINST__BOX,       // player -> box
    PLAYER_AGAINST__BOX_BOX,   // player -> box -> box
    PLAYER_AGAINST__BOX_NONE,  // player -> box -> none
    PLAYER_AGAINST__BOX_PLACE, // player -> box -> place
    PLAYER_AGAINST__BOX_PLACE_AND_FINISH, // player -> box -> place -> finish
    PLAYER_AGAINST__BOX_WALL,  // player -> box -> wall
    PLAYER_AGAINST__WALL;      // player -> wall
    
    // PLAYER_AGAINST_PLACE -> suprice what is here?
    
}
