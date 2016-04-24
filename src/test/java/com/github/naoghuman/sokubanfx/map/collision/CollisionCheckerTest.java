/*
 * Copyright (C) 2016 PRo
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

import com.github.naoghuman.sokubanfx.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.geometry.Direction;
import com.github.naoghuman.sokubanfx.map.MapModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author PRo
 */
public class CollisionCheckerTest {
    
    private MapModel mapModel;
    
    private List<Coordinates> boxes;
    private List<Coordinates> places;
    private List<Coordinates> walls;
    
    public CollisionCheckerTest() {
    }
    
    @Before
    public void setUp() {
        mapModel = new MapModel();
        mapModel.setPlayer(10, 10);
        
        boxes = new ArrayList<>();
        places = new ArrayList<>();
        walls = new ArrayList<>();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetDefault() {
        CollisionChecker result = CollisionChecker.getDefault();
        assertNotNull("Instance from CollisionChecker muss != NULL", result); // NOI18N
    }

    @Test
    public void testCheckCollisionPlayerBoxWithDirectionDOWN() {
        // direction
        final Direction direction = Direction.DOWN;
        
        // No boxes are in the map or no boxes on x
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on x -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There are boxes on x but not under the player (not y=11)
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10,  8));
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (not y=11) -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There is a box before the player (y=11)
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10, 11)); // <---
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (y=11) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void testCheckCollisionPlayerBoxWithDirectionUP() {
        // direction
        final Direction direction = Direction.UP;
        
        // No boxes are in the map or no boxes on x
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on x -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There are boxes on x but not under the player (not y=9)
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10,  8));
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (not y=9) -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There is a box before the player (y=9)
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10,  9)); // <---
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (y=9) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void testCheckCollisionPlayerBoxWithDirectionLEFT() {
        // direction
        final Direction direction = Direction.LEFT;
        
        // No boxes are in the map or no boxes on y
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on y -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There are boxes on y but not under the player (not x=9)
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates( 8, 10));
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (not x=9) -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There is a box before the player (y=9)
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates( 9, 10)); // <---
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (x=9) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void testCheckCollisionPlayerBoxWithDirectionRIGHT() {
        // direction
        final Direction direction = Direction.RIGHT;
        
        // No boxes are in the map or no boxes on y
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on y -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There are boxes on y but not under the player (not x=11)
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates( 8, 10));
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (not x=11) -> CollisionResult.NO_BOX", CollisionResult.NO_BOX, result);
        
        // There is a box before the player (y=11)
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates(11, 10)); // <---
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (x=11) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
        /*
            DOWN (-1, KeyEvent.VK_S, KeyEvent.VK_DOWN),
            LEFT (+1, KeyEvent.VK_A, KeyEvent.VK_LEFT),
            NONE ( 0),
            RIGHT(-1, KeyEvent.VK_D, KeyEvent.VK_RIGHT),
            UP   (+1, KeyEvent.VK_W, KeyEvent.VK_UP);
        */
//    /**
//     * Test of checkCollisionPlayerBoxBox method, of class CollisionChecker.
//     */
//    @Test
//    public void testCheckCollisionPlayerBoxBox() {
//        System.out.println("checkCollisionPlayerBoxBox");
//        Direction direction = null;
//        MapModel mapModel = null;
//        CollisionChecker instance = null;
//        CollisionResult expResult = null;
//        CollisionResult result = instance.checkCollisionPlayerBoxBox(direction, mapModel);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of checkCollisionPlayerBoxPlace method, of class CollisionChecker.
//     */
//    @Test
//    public void testCheckCollisionPlayerBoxPlace() {
//        System.out.println("checkCollisionPlayerBoxPlace");
//        Direction direction = null;
//        MapModel mapModel = null;
//        CollisionChecker instance = null;
//        CollisionResult expResult = null;
//        CollisionResult result = instance.checkCollisionPlayerBoxPlace(direction, mapModel);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of checkCollisionPlayerBoxWall method, of class CollisionChecker.
//     */
//    @Test
//    public void testCheckCollisionPlayerBoxWall() {
//        System.out.println("checkCollisionPlayerBoxWall");
//        Direction direction = null;
//        MapModel mapModel = null;
//        CollisionChecker instance = null;
//        CollisionResult expResult = null;
//        CollisionResult result = instance.checkCollisionPlayerBoxWall(direction, mapModel);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of checkCollisionPlayerWall method, of class CollisionChecker.
//     */
//    @Test
//    public void testCheckCollisionPlayerWall() {
//        System.out.println("checkCollisionPlayerWall");
//        Direction direction = null;
//        MapModel mapModel = null;
//        CollisionChecker instance = null;
//        CollisionResult expResult = null;
//        CollisionResult result = instance.checkCollisionPlayerWall(direction, mapModel);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
