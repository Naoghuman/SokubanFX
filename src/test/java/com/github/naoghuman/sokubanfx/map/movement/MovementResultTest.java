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

import com.github.naoghuman.sokubanfx.map.animation.EAnimation;
import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class MovementResultTest {
    
    public MovementResultTest() {
    }

    @Test
    public void testGetDefault() {
        MovementResult mr = MovementResult.getDefault();
        
        assertEquals(EAnimation.NONE, mr.getAnimation());
        assertNotNull(mr.getBoxToMove());
        assertEquals(Coordinates.getDefault(), mr.getBoxToMove());
        assertEquals(EMovement.NONE, mr.getMovement());
        assertNotNull(mr.getPlayerToMove());
        assertEquals(Coordinates.getDefault(), mr.getPlayerToMove());
        assertFalse(mr.isMapFinish());
    }

    @Test
    public void getBoxToMove() {
        MovementResult mr = MovementResult.getDefault();
        
        assertNotNull(mr.getBoxToMove());
        assertEquals(Coordinates.getDefault(), mr.getBoxToMove());
    }

    @Test
    public void setBoxToMove() {
        MovementResult mr = MovementResult.getDefault();
        mr.setBoxToMove(Coordinates.getDefault(2, 7));
        
        assertNotNull(mr.getBoxToMove());
        assertEquals(2, mr.getBoxToMove().getX());
        assertEquals(7, mr.getBoxToMove().getY());
    }

    @Test
    public void getPlayerToMove() {
        MovementResult mr = MovementResult.getDefault();
        
        assertNotNull(mr.getPlayerToMove());
        assertEquals(Coordinates.getDefault(), mr.getPlayerToMove());
    }

    @Test
    public void setPlayerToMove() {
        MovementResult mr = MovementResult.getDefault();
        mr.setPlayerToMove(Coordinates.getDefault(2, 7));
        
        assertNotNull(mr.getPlayerToMove());
        assertEquals(2, mr.getPlayerToMove().getX());
        assertEquals(7, mr.getPlayerToMove().getY());
    }

    @Test
    public void isMapFinish() {
        MovementResult mr = MovementResult.getDefault();
        
        assertFalse(mr.isMapFinish());
    }

    @Test
    public void setIsMapFinish() {
        MovementResult mr = MovementResult.getDefault();
        mr.setIsMapFinish(true);
        
        assertTrue(mr.isMapFinish());
    }

    @Test
    public void getAnimation() {
        MovementResult mr = MovementResult.getDefault();
        
        assertEquals(EAnimation.NONE, mr.getAnimation());
    }

    @Test
    public void setAnimation() {
        MovementResult mr = MovementResult.getDefault();
        mr.setAnimation(EAnimation.REALLY_GREAT);
        
        assertEquals(EAnimation.REALLY_GREAT, mr.getAnimation());
    }

    @Test
    public void getMovement() {
        MovementResult mr = MovementResult.getDefault();
        
        assertEquals(EMovement.NONE, mr.getMovement());
    }

    @Test
    public void setMovement() {
        MovementResult mr = MovementResult.getDefault();
        mr.setMovement(EMovement.PLAYER_AND_BOX);
        
        assertEquals(EMovement.PLAYER_AND_BOX, mr.getMovement());
    }
    
}
