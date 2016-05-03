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

import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.map.geometry.EDirection;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
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

    @Test
    public void getDefault() {
        CollisionChecker result = CollisionChecker.getDefault();
        assertNotNull("Instance from CollisionChecker muss != NULL", result); // NOI18N
    }
    
    @Test
    public void checkCollisionPlayerBoxWallWithDirectionDOWN() {
        // direction
        final EDirection direction = EDirection.DOWN;
        
        // There is a box before the player (y=11) and no second box (y!=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and no second box (y!=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=11) and a wall at (y=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10, 12)); // <--- 2.
        walls.add(Coordinates.getDefault(10, 14));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and a wall at (y=12) -> CollisionResult.PLAYER_AGAINST__BOX_WALL", CollisionResult.PLAYER_AGAINST__BOX_WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWallWithDirectionUP() {
        // direction
        final EDirection direction = EDirection.UP;
        
        // There is a box before the player (y=9) and no second box (y!=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and no second box (y!=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=9) and a wall at (y=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10,  8)); // <--- 2.
        walls.add(Coordinates.getDefault(10, 14));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and a wall at (y=8) -> CollisionResult.PLAYER_AGAINST__BOX_WALL", CollisionResult.PLAYER_AGAINST__BOX_WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWallWithDirectionLEFT() {
        // direction
        final EDirection direction = EDirection.LEFT;
        
        // There is a box before the player (x=9) and no second box (x!=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and no second box (x!=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (x=9) and a wall at (x=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault( 8, 10)); // <--- 2.
        walls.add(Coordinates.getDefault(14, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and a wall at (x=8) -> CollisionResult.PLAYER_AGAINST__BOX_WALL", CollisionResult.PLAYER_AGAINST__BOX_WALL, result);
    }
    
    
    @Test
    public void checkCollisionPlayerBoxWallWithDirectionRIGHT() {
        // direction
        final EDirection direction = EDirection.RIGHT;
        
        // There is a box before the player (x=11) and no second box (x!=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and no second box (x!=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (x=11) and a wall at (x=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault(12, 10)); // <--- 2.
        walls.add(Coordinates.getDefault(14, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxWall(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and a wall at (x=12) -> CollisionResult.PLAYER_AGAINST__BOX_WALL", CollisionResult.PLAYER_AGAINST__BOX_WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionDOWN() {
        // direction
        final EDirection direction = EDirection.DOWN;
        
        // There is a box before the player (y=11) and no second box (y!=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and no second box (y!=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=11) and a second box (y=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10, 12)); // <--- 2.
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and a second box (y=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_BOX", CollisionResult.PLAYER_AGAINST__BOX_BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionUP() {
        // direction
        final EDirection direction = EDirection.UP;
        
        // There is a box before the player (y=9) and no second box (y!=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and no second box (y!=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=9) and a second box (y=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10,  8)); // <--- 2.
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and a second box (y=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_BOX", CollisionResult.PLAYER_AGAINST__BOX_BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionLEFT() {
        // direction
        final EDirection direction = EDirection.LEFT;
        
        // There is a box before the player (x=9) and no second box (x!=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and no second box (x!=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (x=9) and a second box (x=8) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault( 8, 10)); // <--- 2.
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and a second box (x=8) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_BOX", CollisionResult.PLAYER_AGAINST__BOX_BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxBoxWithDirectionRIGHT() {
        // direction
        final EDirection direction = EDirection.RIGHT;
        
        // There is a box before the player (x=11) and no second box (x!=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and no second box (x!=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=11) and a second box (y=12) before the first box
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault(12, 10)); // <--- 2.
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxBox(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and a second box (x=12) before the first box -> CollisionResult.PLAYER_AGAINST__BOX_BOX", CollisionResult.PLAYER_AGAINST__BOX_BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxPlaceWithDirectionDOWN() {
        // direction
        final EDirection direction = EDirection.DOWN;
        
        // There is a box before the player (y=11) but no place at (y=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        places.clear();
        mapModel.setPlaces(places);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (y=11) but no place at (y=12) -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=11) and a place at (y=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10, 11)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        places.clear();
        places.add(Coordinates.getDefault(10,  2));
        places.add(Coordinates.getDefault(10, 12)); // <--- 2.
        places.add(Coordinates.getDefault(10, 14));
        mapModel.setPlaces(places);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (y=11) and a place at (y=12) -> CollisionResult.PLAYER_AGAINST__BOX_PLACE", CollisionResult.PLAYER_AGAINST__BOX_PLACE, result);
    }

    @Test
    public void checkCollisionPlayerBoxPlaceWithDirectionUP() {
        // direction
        final EDirection direction = EDirection.UP;
        
        // There is a box before the player (y=9) but no place at (y=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        places.clear();
        mapModel.setPlaces(places);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (y=9) but no place at (y=8) -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (y=9) and a place at (y=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  1));
        boxes.add(Coordinates.getDefault(10,  9)); // <--- 1.
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        places.clear();
        places.add(Coordinates.getDefault(10,  2));
        places.add(Coordinates.getDefault(10,  8)); // <--- 2.
        places.add(Coordinates.getDefault(10, 14));
        mapModel.setPlaces(places);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (y=9) and a place at (y=8) -> CollisionResult.PLAYER_AGAINST__BOX_PLACE", CollisionResult.PLAYER_AGAINST__BOX_PLACE, result);
    }

    @Test
    public void checkCollisionPlayerBoxPlaceWithDirectionLEFT() {
        // direction
        final EDirection direction = EDirection.LEFT;
        
        // There is a box before the player (x=9) but no place at (x=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        places.clear();
        mapModel.setPlaces(places);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (x=9) but no place at (x=8) -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (x=9) and a place at (x=8)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        places.clear();
        places.add(Coordinates.getDefault( 2, 10));
        places.add(Coordinates.getDefault( 8, 10)); // <--- 2.
        places.add(Coordinates.getDefault(14, 10));
        mapModel.setPlaces(places);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (x=9) and a place at (x=8) -> CollisionResult.PLAYER_AGAINST__BOX_PLACE", CollisionResult.PLAYER_AGAINST__BOX_PLACE, result);
    }

    @Test
    public void checkCollisionPlayerBoxPlaceWithDirectionRIGHT() {
        // direction
        final EDirection direction = EDirection.RIGHT;
        
        // There is a box before the player (x=11) but no place at (x=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        places.clear();
        mapModel.setPlaces(places);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (x=11) but no place at (x=12) -> CollisionResult.PLAYER_AGAINST__BOX_NONE", CollisionResult.PLAYER_AGAINST__BOX_NONE, result);
        
        // There is a box before the player (x=11) and a place at (x=12)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 1, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <--- 1.
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        places.clear();
        places.add(Coordinates.getDefault( 2, 10));
        places.add(Coordinates.getDefault(12, 10)); // <--- 2.
        places.add(Coordinates.getDefault(14, 10));
        mapModel.setPlaces(places);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBoxPlace(direction, mapModel);
        assertEquals("There is a box before the player (x=11) and a place at (x=12) -> CollisionResult.PLAYER_AGAINST__BOX_PLACE", CollisionResult.PLAYER_AGAINST__BOX_PLACE, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionDOWN() {
        // direction
        final EDirection direction = EDirection.DOWN;
        
        // No boxes are in the map or no boxes on x
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on x -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are boxes on x but not under the player (not y=11)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  8));
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (not y=11) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a box before the player (y=11)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10, 11)); // <---
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) -> CollisionResult.PLAYER_AGAINST__BOX", CollisionResult.PLAYER_AGAINST__BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionUP() {
        // direction
        final EDirection direction = EDirection.UP;
        
        // No boxes are in the map or no boxes on x
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on x -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are boxes on x but not under the player (not y=9)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  8));
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on x but not under the player (not y=9) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a box behind the player (y=9)
        boxes.clear();
        boxes.add(Coordinates.getDefault(10,  2));
        boxes.add(Coordinates.getDefault(10,  9)); // <---
        boxes.add(Coordinates.getDefault(10, 15));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There is a box behind the player (y=9) -> CollisionResult.PLAYER_AGAINST__BOX", CollisionResult.PLAYER_AGAINST__BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionLEFT() {
        // direction
        final EDirection direction = EDirection.LEFT;
        
        // No boxes are in the map or no boxes on y
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on y -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are boxes on y but not under the player (not x=9)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 8, 10));
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (not x=9) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a box before the player (y=9)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 9, 10)); // <---
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There is a box before the player (y=9) -> CollisionResult.PLAYER_AGAINST__BOX", CollisionResult.PLAYER_AGAINST__BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerBoxWithDirectionRIGHT() {
        // direction
        final EDirection direction = EDirection.RIGHT;
        
        // No boxes are in the map or no boxes on y
        boxes.clear();
        mapModel.setBoxes(boxes);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("No boxes are in the map or no boxes on y -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are boxes on y but not under the player (not x=11)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault( 8, 10));
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There are boxes on y but not under the player (not x=11) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a box before the player (y=11)
        boxes.clear();
        boxes.add(Coordinates.getDefault( 2, 10));
        boxes.add(Coordinates.getDefault(11, 10)); // <---
        boxes.add(Coordinates.getDefault(15, 10));
        mapModel.setBoxes(boxes);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerBox(direction, mapModel);
        assertEquals("There is a box before the player (y=11) -> CollisionResult.PLAYER_AGAINST__BOX", CollisionResult.PLAYER_AGAINST__BOX, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionDOWN() {
        // direction
        final EDirection direction = EDirection.DOWN;
        
        // No walls are in the map or no walls on x
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on x -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are walls on x but not under the player (not y=11)
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10,  8));
        walls.add(Coordinates.getDefault(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on x but not under the player (not y=11) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a wall before the player (y=11)
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10, 11)); // <---
        walls.add(Coordinates.getDefault(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=11) -> CollisionResult.PLAYER_AGAINST__WALL", CollisionResult.PLAYER_AGAINST__WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionUP() {
        // direction
        final EDirection direction = EDirection.UP;
        
        // No walls are in the map or no walls on x
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on x -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are walls on x but not under the player (not y=9)
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10,  8));
        walls.add(Coordinates.getDefault(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on x but not under the player (not y=9) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a wall behind the player (y=9)
        walls.clear();
        walls.add(Coordinates.getDefault(10,  2));
        walls.add(Coordinates.getDefault(10,  9)); // <---
        walls.add(Coordinates.getDefault(10, 15));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall behind the player (y=9) -> CollisionResult.PLAYER_AGAINST__WALL", CollisionResult.PLAYER_AGAINST__WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionLEFT() {
        // direction
        final EDirection direction = EDirection.LEFT;
        
        // No walls are in the map or no walls on y
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on y -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are walls on y but not under the player (not x=9)
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault( 8, 10));
        walls.add(Coordinates.getDefault(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on y but not under the player (not x=9) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a wall before the player (y=9)
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault( 9, 10)); // <---
        walls.add(Coordinates.getDefault(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=9) -> CollisionResult.PLAYER_AGAINST__WALL", CollisionResult.PLAYER_AGAINST__WALL, result);
    }
    
    @Test
    public void checkCollisionPlayerWallWithDirectionRIGHT() {
        // direction
        final EDirection direction = EDirection.RIGHT;
        
        // No walls are in the map or no walls on y
        walls.clear();
        mapModel.setWalls(walls);
        
        CollisionResult result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("No walls are in the map or no walls on y -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There are walls on y but not under the player (not x=11)
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault( 8, 10));
        walls.add(Coordinates.getDefault(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There are walls on y but not under the player (not x=11) -> CollisionResult.NO_COLLISION", CollisionResult.NONE, result);
        
        // There is a wall before the player (y=11)
        walls.clear();
        walls.add(Coordinates.getDefault( 2, 10));
        walls.add(Coordinates.getDefault(11, 10)); // <---
        walls.add(Coordinates.getDefault(15, 10));
        mapModel.setWalls(walls);
        
        result = CollisionChecker.getDefault().checkCollisionPlayerWall(direction, mapModel);
        assertEquals("There is a wall before the player (y=11) -> CollisionResult.PLAYER_AGAINST__WALL", CollisionResult.PLAYER_AGAINST__WALL, result);
    }
    
}
