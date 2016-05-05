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

import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Naoghuman
 */
public class MapMovementTest {
    
    public MapMovementTest() {
    }

    @Test
    public void testCheckIsMapFinishFalse() {
        MapMovement mm = new MapMovement();
        
        MapModel mmo = new MapModel();
        ObservableList<Coordinates> boxes = FXCollections.observableArrayList();
        boxes.add(Coordinates.getDefault(5, 5));
        boxes.add(Coordinates.getDefault(15, 15));
//        boxes.add(Coordinates.getDefault(25, 25)); // <-- not all boxes are on places
        mmo.setBoxes(boxes);
        
        ObservableList<Coordinates> places = FXCollections.observableArrayList();
        places.add(Coordinates.getDefault(5, 5));
        places.add(Coordinates.getDefault(15, 15));
        places.add(Coordinates.getDefault(25, 25));
        mmo.setPlaces(places);
        
        MovementResult mr = mm.checkIsMapFinish(mmo);
        
        assertFalse(mr.isMapFinish());
    }

    @Test
    public void testCheckIsMapFinishTrue() {
        MapMovement mm = new MapMovement();
        
        MapModel mmo = new MapModel();
        ObservableList<Coordinates> boxes = FXCollections.observableArrayList();
        boxes.add(Coordinates.getDefault(5, 5));
        boxes.add(Coordinates.getDefault(15, 15));
        boxes.add(Coordinates.getDefault(25, 25)); // <-- all boxes are on places
        mmo.setBoxes(boxes);
        
        ObservableList<Coordinates> places = FXCollections.observableArrayList();
        places.add(Coordinates.getDefault(5, 5));
        places.add(Coordinates.getDefault(15, 15));
        places.add(Coordinates.getDefault(25, 25));
        mmo.setPlaces(places);
        
        MovementResult mr = mm.checkIsMapFinish(mmo);
        
        assertTrue(mr.isMapFinish());
    }

    /*
        Not needed, because the method is in CollisionCheckerTest#checkCollisionPlayerXyz(...)
        tested.
    */
//    @Test
//    public void testCheckMovePlayerTo() {
//        MapMovement instance = new MapMovement();
//        MovementResult result = instance.checkMovePlayerTo(direction, mapModel);
//    }
    
}
