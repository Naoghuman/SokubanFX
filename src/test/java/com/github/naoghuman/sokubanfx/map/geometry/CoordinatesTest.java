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

import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class CoordinatesTest {
    
    public CoordinatesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void getDefault_0args() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertEquals(-1, coo.getX());
        assertEquals(-1, coo.getY());
        assertEquals(0, coo.getTranslateX());
        assertEquals(0, coo.getTranslateY());
    }

    @Test
    public void getDefault_int_int() {
        Coordinates coo = Coordinates.getDefault(9, 8);
        
        assertNotNull(coo);
        assertEquals(9, coo.getX());
        assertEquals(8, coo.getY());
        assertEquals(0, coo.getTranslateX());
        assertEquals(0, coo.getTranslateY());
    }

    @Test
    public void isDefault() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertTrue(Coordinates.isDefault(coo));
    }

    @Test
    public void getX() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertEquals(-1, coo.getX());
    }

    @Test
    public void setX() {
        Coordinates coo = Coordinates.getDefault(12, 11);
        
        assertNotNull(coo);
        assertEquals(12, coo.getX());
        
        coo.setX(15);
        assertEquals(15, coo.getX());
    }

    @Test
    public void getY() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertEquals(-1, coo.getY());
    }

    @Test
    public void setY() {
        Coordinates coo = Coordinates.getDefault(11, 13);
        
        assertNotNull(coo);
        assertEquals(13, coo.getY());
        
        coo.setY(15);
        assertEquals(15, coo.getY());
    }

    @Test
    public void getTranslateX() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertEquals(0, coo.getTranslateX());
    }

    @Test
    public void setTranslateX() {
        Coordinates coo = Coordinates.getDefault(12, 11);
        
        assertNotNull(coo);
        assertEquals(0, coo.getTranslateX());
        
        coo.setTranslateX(15);
        assertEquals(15, coo.getTranslateX());
    }

    @Test
    public void getTranslateY() {
        Coordinates coo = Coordinates.getDefault();
        
        assertNotNull(coo);
        assertEquals(0, coo.getTranslateY());
    }

    @Test
    public void setTranslateY() {
        Coordinates coo = Coordinates.getDefault(12, 11);
        
        assertNotNull(coo);
        assertEquals(0, coo.getTranslateY());
        
        coo.setTranslateY(15);
        assertEquals(15, coo.getTranslateY());
    }

    @Test
    public void testHashCode() {
        Coordinates coo1 = Coordinates.getDefault(8, 9);
        Coordinates coo2 = Coordinates.getDefault(8, 9);
        
        assertNotNull(coo1);
        assertNotNull(coo2);
        assertEquals(coo1.hashCode(), coo2.hashCode());
    }

    @Test
    public void testEquals() {
        Coordinates coo1 = Coordinates.getDefault();
        Coordinates coo2 = Coordinates.getDefault();
        
        assertNotNull(coo1);
        assertNotNull(coo2);
        assertTrue(coo1.equals(coo2));
    }

    @Test
    public void testToString() {
        Coordinates coo = Coordinates.getDefault(5, 7);
        
        assertEquals("(x=5, y=7, t-x=0, t-y=0)", coo.toString());
    }
    
}
