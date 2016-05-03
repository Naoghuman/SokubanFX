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
package com.github.naoghuman.sokubanfx.map.converter;

import com.github.naoghuman.sokubanfx.map.MapFacade;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import java.util.List;
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
public class MapConverterTest {
    
    private static MapConverter mapConverter;
    
    public MapConverterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        mapConverter = new MapConverter();
    }
    
    @AfterClass
    public static void tearDownClass() {
        mapConverter = null;
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void convertMapToStringsAndModelLevel1() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(1);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 8);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel2() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(2);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 10);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
        assertEquals(mm1AsStrings.get(9), mm2AsStrings.get(9));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel3() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(3);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 8);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel4() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(4);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 9);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel5() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(5);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 13);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
        assertEquals(mm1AsStrings.get(9), mm2AsStrings.get(9));
        assertEquals(mm1AsStrings.get(10), mm2AsStrings.get(10));
        assertEquals(mm1AsStrings.get(11), mm2AsStrings.get(11));
        assertEquals(mm1AsStrings.get(12), mm2AsStrings.get(12));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel6() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(6);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 10);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
        assertEquals(mm1AsStrings.get(9), mm2AsStrings.get(9));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel7() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(7);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 11);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
        assertEquals(mm1AsStrings.get(9), mm2AsStrings.get(9));
        assertEquals(mm1AsStrings.get(10), mm2AsStrings.get(10));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel8() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(8);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 7);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
    }
    
    @Test
    public void convertMapToStringsAndModelLevel9() {
        MapModel mm1 = MapFacade.INSTANCE.loadMap(9);
        List<String> mm1AsStrings = mm1.getMapAsStrings();
        
        List<String> mm2AsStrings = mapConverter.convertMapCoordinatesToStrings(mm1);
        
        assertEquals(mm1.getRows(), 9);
        assertEquals(mm1AsStrings.size(), mm2AsStrings.size());
        assertEquals(mm1AsStrings.get(0), mm2AsStrings.get(0));
        assertEquals(mm1AsStrings.get(1), mm2AsStrings.get(1));
        assertEquals(mm1AsStrings.get(2), mm2AsStrings.get(2));
        assertEquals(mm1AsStrings.get(3), mm2AsStrings.get(3));
        assertEquals(mm1AsStrings.get(4), mm2AsStrings.get(4));
        assertEquals(mm1AsStrings.get(5), mm2AsStrings.get(5));
        assertEquals(mm1AsStrings.get(6), mm2AsStrings.get(6));
        assertEquals(mm1AsStrings.get(7), mm2AsStrings.get(7));
        assertEquals(mm1AsStrings.get(8), mm2AsStrings.get(8));
    }
    
}
