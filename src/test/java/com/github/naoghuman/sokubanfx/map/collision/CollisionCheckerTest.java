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
    public void getDefault() {
        CollisionChecker result = CollisionChecker.getDefault();
        assertNotNull("Instance from CollisionChecker muss != NULL", result); // NOI18N
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionDOWN() {
        // direction
        final Direction direction = Direction.DOWN;
        
        // There is a box before the player (y=11) and no second box (y!=12) before the first box
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10, 11)); // <--- 1.
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and no second box (y!=12) before the first box -> CollisionResult.KEEP_GOING", CollisionResult.KEEP_GOING, result);
        
        // There is a box before the player (y=11) and a second box (y=12) before the first box
        boxes.clear();
        boxes.add(new Coordinates(10,  1));
        boxes.add(new Coordinates(10, 12)); // <--- 2.
        boxes.add(new Coordinates(10, 11)); // <--- 1.
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and a second box (y=12) before the first box -> CollisionResult.WHAT_HAPPEN", CollisionResult.WHAT_HAPPEN, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionUP() {
        // direction
        final Direction direction = Direction.UP;
        
        // There is a box before the player (y=9) and no second box (y!=8) before the first box
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10,  9)); // <--- 1.
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and no second box (y!=8) before the first box -> CollisionResult.KEEP_GOING", CollisionResult.KEEP_GOING, result);
        
        // There is a box before the player (y=9) and a second box (y=8) before the first box
        boxes.clear();
        boxes.add(new Coordinates(10,  1));
        boxes.add(new Coordinates(10,  8)); // <--- 2.
        boxes.add(new Coordinates(10,  9)); // <--- 1.
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and a second box (y=8) before the first box -> CollisionResult.WHAT_HAPPEN", CollisionResult.WHAT_HAPPEN, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionLEFT() {
        // direction
        final Direction direction = Direction.LEFT;
        
        // There is a box before the player (x=9) and no second box (x!=8) before the first box
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates( 9, 10)); // <--- 1.
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and no second box (x!=8) before the first box -> CollisionResult.KEEP_GOING", CollisionResult.KEEP_GOING, result);
        
        // There is a box before the player (x=9) and a second box (x=8) before the first box
        boxes.clear();
        boxes.add(new Coordinates( 1, 10));
        boxes.add(new Coordinates( 8, 10)); // <--- 2.
        boxes.add(new Coordinates( 9, 10)); // <--- 1.
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and a second box (x=8) before the first box -> CollisionResult.WHAT_HAPPEN", CollisionResult.WHAT_HAPPEN, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionRIGHT() {
        // direction
        final Direction direction = Direction.RIGHT;
        
        // There is a box before the player (x=11) and no second box (x!=12) before the first box
        boxes.clear();
        boxes.add(new Coordinates( 2, 10));
        boxes.add(new Coordinates(11, 10)); // <--- 1.
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and no second box (x!=12) before the first box -> CollisionResult.KEEP_GOING", CollisionResult.KEEP_GOING, result);
        
        // There is a box before the player (y=11) and a second box (y=12) before the first box
        boxes.clear();
        boxes.add(new Coordinates( 1, 10));
        boxes.add(new Coordinates(12, 10)); // <--- 2.
        boxes.add(new Coordinates(11, 10)); // <--- 1.
        boxes.add(new Coordinates(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and a second box (x=12) before the first box -> CollisionResult.WHAT_HAPPEN", CollisionResult.WHAT_HAPPEN, result);
    }

    @Test
    public void checkCollisionPlayerBoxWithDirectionDOWN() {
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
        assertEquals("There is a box before the player (y=11) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionUP() {
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
        
        // There is a box behind the player (y=9)
        boxes.clear();
        boxes.add(new Coordinates(10,  2));
        boxes.add(new Coordinates(10,  9)); // <---
        boxes.add(new Coordinates(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There is a box behind the player (y=9) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionLEFT() {
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
        assertEquals("There is a box before the player (y=9) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionRIGHT() {
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
        assertEquals("There is a box before the player (y=11) -> CollisionResult.BOX", CollisionResult.BOX, result);
    }
    
        /*
            DOWN (-1, KeyEvent.VK_S, KeyEvent.VK_DOWN),
            LEFT (+1, KeyEvent.VK_A, KeyEvent.VK_LEFT),
            NONE ( 0),
            RIGHT(-1, KeyEvent.VK_D, KeyEvent.VK_RIGHT),
            UP   (+1, KeyEvent.VK_W, KeyEvent.VK_UP);
        */
    @Test
    public void checkCollisionPlayerWallWithDirectionDOWN() {
        // direction
        final Direction direction = Direction.DOWN;
        
        // No walls are in the map or no walls on x
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on x -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There are walls on x but not under the player (not y=11)
        walls.clear();
        walls.add(new Coordinates(10,  2));
        walls.add(new Coordinates(10,  8));
        walls.add(new Coordinates(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on x but not under the player (not y=11) -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There is a wall before the player (y=11)
        walls.clear();
        walls.add(new Coordinates(10,  2));
        walls.add(new Coordinates(10, 11)); // <---
        walls.add(new Coordinates(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=11) -> CollisionResult.WALL", CollisionResult.WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionUP() {
        // direction
        final Direction direction = Direction.UP;
        
        // No walls are in the map or no walls on x
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on x -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There are walls on x but not under the player (not y=9)
        walls.clear();
        walls.add(new Coordinates(10,  2));
        walls.add(new Coordinates(10,  8));
        walls.add(new Coordinates(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on x but not under the player (not y=9) -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There is a wall behind the player (y=9)
        walls.clear();
        walls.add(new Coordinates(10,  2));
        walls.add(new Coordinates(10,  9)); // <---
        walls.add(new Coordinates(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall behind the player (y=9) -> CollisionResult.WALL", CollisionResult.WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionLEFT() {
        // direction
        final Direction direction = Direction.LEFT;
        
        // No walls are in the map or no walls on y
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on y -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There are walls on y but not under the player (not x=9)
        walls.clear();
        walls.add(new Coordinates( 2, 10));
        walls.add(new Coordinates( 8, 10));
        walls.add(new Coordinates(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on y but not under the player (not x=9) -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There is a wall before the player (y=9)
        walls.clear();
        walls.add(new Coordinates( 2, 10));
        walls.add(new Coordinates( 9, 10)); // <---
        walls.add(new Coordinates(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=9) -> CollisionResult.WALL", CollisionResult.WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionRIGHT() {
        // direction
        final Direction direction = Direction.RIGHT;
        
        // No walls are in the map or no walls on y
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on y -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There are walls on y but not under the player (not x=11)
        walls.clear();
        walls.add(new Coordinates( 2, 10));
        walls.add(new Coordinates( 8, 10));
        walls.add(new Coordinates(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on y but not under the player (not x=11) -> CollisionResult.NO_WALL", CollisionResult.NO_WALL, result);
        
        // There is a wall before the player (y=11)
        walls.clear();
        walls.add(new Coordinates( 2, 10));
        walls.add(new Coordinates(11, 10)); // <---
        walls.add(new Coordinates(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=11) -> CollisionResult.WALL", CollisionResult.WALL, result);
    }

//    public void checkCollisionPlayerBoxPlace() {
//    public void checkCollisionPlayerBoxWall() {
    
}
