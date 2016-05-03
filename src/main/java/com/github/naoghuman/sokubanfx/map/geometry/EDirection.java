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
package com.github.naoghuman.sokubanfx.map.geometry;

/**
 *
 * @author Naoghuman
 */
public enum EDirection {
    
    DOWN (-1),
    LEFT (+1),
    NONE ( 0),
    RIGHT(-1),
    UP   (+1);
    
    private int updateDirection = 0;
    
    EDirection(int update) {
        this.updateDirection = update;
    }
    
    public int updateDirection() {
        return updateDirection;
    }
    
}
