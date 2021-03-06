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

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class MapModel {
    
    private int columns = 0;
    private int level = 0;
    private int rows = 0;
    
    private Coordinates player;
    
    private ObservableList<Coordinates> boxes;
    private ObservableList<Coordinates> places;
    private ObservableList<Coordinates> walls;
    
    private ObservableList<String> mapAsStrings;
    
    public MapModel() {
        this.init();
    }
    
    private void init() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Init MapModel"); // NOI18N
        
        boxes = FXCollections.observableArrayList();
        places = FXCollections.observableArrayList();
        walls = FXCollections.observableArrayList();
        
        mapAsStrings = FXCollections.observableArrayList();
    }
    
    public MapModel copy() {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Copy deeply MapModel"); // NOI18N
        
        final MapModel mapModelCopy = new MapModel();
        mapModelCopy.setLevel(this.getLevel());
        mapModelCopy.setPlayer(this.getPlayer());
        mapModelCopy.setColumns(this.getColumns());
        mapModelCopy.setRows(this.getRows());
        
        mapModelCopy.setBoxes(this.getBoxes());
        mapModelCopy.setPlaces(this.getPlaces());
        mapModelCopy.setWalls(this.getWalls());
        
        mapModelCopy.setMapAsStrings(this.getMapAsStrings());
        
        return mapModelCopy;
    }
    
    public int getColumns() {
        return columns;
    }
    
    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getRows() {
        return rows;
    }
    
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    public Coordinates getPlayer() {
        return player;
    }

    public void setPlayer(Coordinates player) {
        this.player = player;
    }

    public void setPlayer(int x, int y) {
        this.player = Coordinates.getDefault(x, y);
    }

    public ObservableList<Coordinates> getBoxes() {
        return boxes;
    }
    
    public void addBox(int x, int y) {
        boxes.add(Coordinates.getDefault(x, y));
    }

    public void setBoxes(ObservableList<Coordinates> boxes) {
        this.boxes.clear();
        this.boxes.addAll(boxes);
    }

    public ObservableList<Coordinates> getPlaces() {
        return places;
    }
    
    public void addPlace(int x, int y) {
        places.add(Coordinates.getDefault(x, y));
    }

    public void setPlaces(ObservableList<Coordinates> places) {
        this.places.clear();
        this.places.addAll(places);
    }

    public ObservableList<Coordinates> getWalls() {
        return walls;
    }
    
    public void addWall(int x, int y) {
        walls.add(Coordinates.getDefault(x, y));
    }

    public void setWalls(ObservableList<Coordinates> walls) {
        this.walls.clear();
        this.walls.addAll(walls);
    }
    
    public ObservableList<String> getMapAsStrings() {
        return mapAsStrings;
    }
    
    public void setMapAsStrings(ObservableList<String> mapAsStrings) {
        this.mapAsStrings.clear();
        this.mapAsStrings.addAll(mapAsStrings);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("lvl   : ").append(level)
                .append(", columns: ").append(this.getColumns())
                .append(", rows: ").append(this.getRows()).append("\n"); // NOI18N
        sb.append("player: ").append(player.toString()).append("\n"); // NOI18N
        
        final StringBuilder sbBoxes = new StringBuilder();
        boxes.stream().forEach((box) -> {
            sbBoxes.append(box.toString());
            sbBoxes.append(", "); // NOI18N
        });
        sbBoxes.delete(sbBoxes.length() - 2, sbBoxes.length());
        sb.append("boxes : ").append(sbBoxes.toString()).append("\n"); // NOI18N
        
        final StringBuilder sbPlaces = new StringBuilder();
        places.stream().forEach((place) -> {
            sbPlaces.append(place.toString());
            sbPlaces.append(", "); // NOI18N
        });
        sbPlaces.delete(sbPlaces.length() - 2, sbPlaces.length());
        sb.append("places: ").append(sbPlaces.toString()).append("\n"); // NOI18N
        
        final StringBuilder sbWalls = new StringBuilder();
        walls.stream().forEach((wall) -> {
            sbWalls.append(wall.toString());
            sbWalls.append(", "); // NOI18N
        });
        
        sbWalls.delete(sbWalls.length() - 2, sbWalls.length());
        sb.append("walls : ").append(sbWalls.toString()).append("\n"); // NOI18N
        
        return sb.toString();
    }
    
}
