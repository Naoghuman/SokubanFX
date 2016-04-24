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
package com.github.naoghuman.sokubanfx.geometry;

import java.awt.event.KeyEvent;

/**
 *
 * @author Naoghuman
 */
public enum Direction {
    
    DOWN (-1, KeyEvent.VK_S, KeyEvent.VK_DOWN),
    LEFT (+1, KeyEvent.VK_A, KeyEvent.VK_LEFT),
    NONE ( 0),
    RIGHT(-1, KeyEvent.VK_D, KeyEvent.VK_RIGHT),
    UP   (+1, KeyEvent.VK_W, KeyEvent.VK_UP);
    
    private int[] keyCodes = {};
    
    private int updateDirection = 0;
    
    Direction(int update, int... keyCodes) {
        this.updateDirection = update;
        this.keyCodes = keyCodes;
    }
    
    public Direction getDirection(int keyCode) {
        Direction direction;
        switch(keyCode) {
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:  { direction = DOWN;  break; }
            
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:  { direction = LEFT;  break; }
            
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT: { direction = RIGHT; break; }
            
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:    { direction = UP;    break; }
            
            default: { direction = NONE; }
        }
        
        return direction;
    }
    
    public int updateDirection() {
        return updateDirection;
    }
    
}
