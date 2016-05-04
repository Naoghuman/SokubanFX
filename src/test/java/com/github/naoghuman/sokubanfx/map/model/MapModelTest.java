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
package com.github.naoghuman.sokubanfx.map.model;

import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class MapModelTest {
    
    public MapModelTest() {
    }
    
    private static ObservableList<Coordinates> boxes;
    private static ObservableList<Coordinates> places;
    private static ObservableList<Coordinates> walls;
    private static ObservableList<String> mapAsStrings;
    
    @BeforeClass
    public static void setUpClass() {
        boxes = FXCollections.observableArrayList();
        places = FXCollections.observableArrayList();
        walls = FXCollections.observableArrayList();
        mapAsStrings = FXCollections.observableArrayList();
    }
    
    @Before
    public void setUp() {
        boxes.clear();
        places.clear();
        walls.clear();
        mapAsStrings.clear();
    }

    @Test
    public void copy() {
        MapModel m1 = new MapModel();
        m1.setLevel(2);
        m1.setColumns(5);
        m1.setRows(10);
        m1.setPlayer(3, 5);
        boxes.add(Coordinates.getDefault(2, 3));
        m1.setBoxes(boxes);
        places.add(Coordinates.getDefault(4, 5));
        m1.setPlaces(places);
        walls.add(Coordinates.getDefault(6, 7));
        m1.setWalls(walls);
        mapAsStrings.add("hello sokuban");
        m1.setMapAsStrings(mapAsStrings);
        
        MapModel m2 = m1.copy();
        
        assertEquals(m1.getLevel(), m2.getLevel());
        
        assertEquals(m1.getColumns(), m2.getColumns());
        assertEquals(m1.getRows(), m2.getRows());
        
        assertNotNull(m1.getPlayer());
        assertEquals(m1.getPlayer().getX(), m2.getPlayer().getX());
        assertEquals(m1.getPlayer().getY(), m2.getPlayer().getY());
        
        assertEquals(m1.getLevel(), m2.getLevel());
        
        assertNotNull(m1.getBoxes());
        assertEquals(1, m1.getBoxes().size());
        assertEquals(m1.getBoxes().get(0).getX(), m2.getBoxes().get(0).getX());
        assertEquals(m1.getBoxes().get(0).getY(), m2.getBoxes().get(0).getY());
        
        assertNotNull(m1.getPlaces());
        assertEquals(1, m1.getPlaces().size());
        assertEquals(m1.getPlaces().get(0).getX(), m2.getPlaces().get(0).getX());
        assertEquals(m1.getPlaces().get(0).getY(), m2.getPlaces().get(0).getY());
        
        assertNotNull(m1.getWalls());
        assertEquals(1, m1.getWalls().size());
        assertEquals(m1.getWalls().get(0).getX(), m2.getWalls().get(0).getX());
        assertEquals(m1.getWalls().get(0).getY(), m2.getWalls().get(0).getY());
        
        assertNotNull(m1.getMapAsStrings());
        assertEquals(1, m1.getMapAsStrings().size());
        assertEquals(m1.getMapAsStrings().get(0), m2.getMapAsStrings().get(0));
    }

    @Test
    public void getColumns() {
        MapModel m1 = new MapModel();
        
        assertEquals(0, m1.getColumns());
    }

    @Test
    public void setColumns() {
        MapModel m1 = new MapModel();
        m1.setColumns(10);
        
        assertEquals(10, m1.getColumns());
    }

    @Test
    public void getLevel() {
        MapModel m1 = new MapModel();
        
        assertEquals(0, m1.getLevel());
    }

    @Test
    public void setLevel() {
        MapModel m1 = new MapModel();
        m1.setLevel(55);
        
        assertEquals(55, m1.getLevel());
    }

    @Test
    public void getRows() {
        MapModel m1 = new MapModel();
        
        assertEquals(0, m1.getRows());
    }

    @Test
    public void setRows() {
        MapModel m1 = new MapModel();
        m1.setRows(17);
        
        assertEquals(17, m1.getRows());
    }

    @Test
    public void getPlayer() {
        MapModel m1 = new MapModel();
        
        assertNull(m1.getPlayer());
    }

    @Test
    public void setPlayer_Coordinates() {
        MapModel m1 = new MapModel();
        Coordinates c = Coordinates.getDefault();
        c.setTranslateX(111);
        c.setTranslateY(222);
        c.setX(9);
        c.setY(99);
        m1.setPlayer(c);
        
        assertNotNull(m1.getPlayer());
        assertEquals(111, m1.getPlayer().getTranslateX());
        assertEquals(222, m1.getPlayer().getTranslateY());
        assertEquals(9, m1.getPlayer().getX());
        assertEquals(99, m1.getPlayer().getY());
    }

    @Test
    public void setPlayer_int_int() {
        MapModel m1 = new MapModel();
        m1.setPlayer(10, 10);
        
        assertNotNull(m1.getPlayer());
        assertEquals(10, m1.getPlayer().getX());
        assertEquals(10, m1.getPlayer().getY());
    }

    @Test
    public void getBoxes() {
        MapModel m1 = new MapModel();
        
        assertNotNull(m1.getBoxes());
        assertEquals(0, m1.getBoxes().size());
    }

    @Test
    public void addBox() {
        MapModel m1 = new MapModel();
        m1.addBox(2, 3);
        
        assertNotNull(m1.getBoxes());
        assertEquals(1, m1.getBoxes().size());
        assertEquals(2, m1.getBoxes().get(0).getX());
        assertEquals(3, m1.getBoxes().get(0).getY());
    }

    @Test
    public void setBoxes() {
        MapModel m1 = new MapModel();
        boxes.add(Coordinates.getDefault(2, 3));
        m1.setBoxes(boxes);
        
        assertNotNull(m1.getBoxes());
        assertEquals(1, m1.getBoxes().size());
        assertEquals(2, m1.getBoxes().get(0).getX());
        assertEquals(3, m1.getBoxes().get(0).getY());
    }

    @Test
    public void getPlaces() {
        MapModel m1 = new MapModel();
        
        assertNotNull(m1.getPlaces());
        assertEquals(0, m1.getPlaces().size());
    }

    @Test
    public void addPlace() {
        MapModel m1 = new MapModel();
        m1.addPlace(2, 3);
        
        assertNotNull(m1.getPlaces());
        assertEquals(1, m1.getPlaces().size());
        assertEquals(2, m1.getPlaces().get(0).getX());
        assertEquals(3, m1.getPlaces().get(0).getY());
    }

    @Test
    public void setPlaces() {
        MapModel m1 = new MapModel();
        places.add(Coordinates.getDefault(2, 3));
        m1.setPlaces(places);
        
        assertNotNull(m1.getPlaces());
        assertEquals(1, m1.getPlaces().size());
        assertEquals(2, m1.getPlaces().get(0).getX());
        assertEquals(3, m1.getPlaces().get(0).getY());
    }

    @Test
    public void getWalls() {
        MapModel m1 = new MapModel();
        
        assertNotNull(m1.getWalls());
        assertEquals(0, m1.getWalls().size());
    }

    @Test
    public void addWall() {
        MapModel m1 = new MapModel();
        m1.addWall(2, 3);
        
        assertNotNull(m1.getWalls());
        assertEquals(1, m1.getWalls().size());
        assertEquals(2, m1.getWalls().get(0).getX());
        assertEquals(3, m1.getWalls().get(0).getY());
    }

    @Test
    public void setWalls() {
        MapModel m1 = new MapModel();
        walls.add(Coordinates.getDefault(2, 3));
        m1.setWalls(walls);
        
        assertNotNull(m1.getWalls());
        assertEquals(1, m1.getWalls().size());
        assertEquals(2, m1.getWalls().get(0).getX());
        assertEquals(3, m1.getWalls().get(0).getY());
    }

    @Test
    public void getMapAsStrings() {
        MapModel m1 = new MapModel();
        
        assertNotNull(m1.getMapAsStrings());
        assertEquals(0, m1.getMapAsStrings().size());
    }

    @Test
    public void setMapAsStrings() {
        MapModel m1 = new MapModel();
        mapAsStrings.add("hello sokuban");
        m1.setMapAsStrings(mapAsStrings);
        
        assertNotNull(m1.getMapAsStrings());
        assertEquals(1, m1.getMapAsStrings().size());
        assertEquals("hello sokuban", m1.getMapAsStrings().get(0));
    }
    
}
