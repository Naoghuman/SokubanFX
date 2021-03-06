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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class EDirectionTest {
    
    public EDirectionTest() {
    }

    @Test
    public void translate() {
        assertEquals(-1, EDirection.DOWN.translate());
        assertEquals(+1, EDirection.LEFT.translate());
        assertEquals( 0, EDirection.NONE.translate());
        assertEquals(-1, EDirection.RIGHT.translate());
        assertEquals(+1, EDirection.UP.translate());
    }
    
}
