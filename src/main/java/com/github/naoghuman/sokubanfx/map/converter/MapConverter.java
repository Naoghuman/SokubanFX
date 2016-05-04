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

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import com.github.naoghuman.sokubanfx.map.geometry.Coordinates;
import com.github.naoghuman.sokubanfx.map.model.MapModel;
import java.util.stream.IntStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class MapConverter {
    
    public ObservableList<String> convertMapCoordinatesToStrings(MapModel mapModel) {
        final ObservableList<String> mapAsStrings = FXCollections.observableArrayList();
        final int rows = mapModel.getRows();
        final int columns = mapModel.getColumns();

        // - = Empty sign
        IntStream.range(0, rows)
                .forEach(row -> {
                    final StringBuilder sb = new StringBuilder();
                    IntStream.range(0, columns)
                            .forEach(column -> {
                                sb.append("-"); // NOI18N
                            });
                    mapAsStrings.add(sb.toString());
                });
        
        // A, B = Walls from the level
        final ObservableList<Coordinates> walls = mapModel.getWalls();
        final int level = mapModel.getLevel();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                for (Coordinates wall : walls) {
                    if (wall.getX() == column && wall.getY() == row) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(row - 1));
                        sb.replace(column - 1, column, level % 2 != 0 ? "A" : "B"); // NOI18N
                        mapAsStrings.remove(row - 1);
                        mapAsStrings.add(row - 1, sb.toString());
                    }
                }
            }
        }
        
        // 2 = Place where a box is needed
        final ObservableList<Coordinates> places = mapModel.getPlaces();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                for (Coordinates place : places) {
                    if (place.getX() == column && place.getY() == row) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(row - 1));
                        sb.replace(column - 1, column, "2"); // NOI18N
                        mapAsStrings.remove(row - 1);
                        mapAsStrings.add(row - 1, sb.toString());
                    }
                }
            }
        }
        
        // 1 = Box which the player should move to the place
        final ObservableList<Coordinates> boxes = mapModel.getBoxes();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                for (Coordinates box : boxes) {
                    if (box.getX() == column && box.getY() == row) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(row - 1));
                        sb.replace(column - 1, column, "1"); // NOI18N
                        mapAsStrings.remove(row - 1);
                        mapAsStrings.add(row - 1, sb.toString());
                    }
                }
            }
        }
        
        // 0 = Player :)
        final Coordinates player = mapModel.getPlayer();
        for (int row = 1; row <= rows; row++) {
            for (int column = 1; column <= columns; column++) {
                if (player.getX() == column && player.getY() == row) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(mapAsStrings.get(row - 1));
                    sb.replace(column - 1, column, "0"); // NOI18N
                    mapAsStrings.remove(row - 1);
                    mapAsStrings.add(row - 1, sb.toString());
                }
            }
        }

        return mapAsStrings;
    }
    
    /*
        # Possible tiles are:
        #   -       = Empty sign
        #   A, B    = Walls from the level
        #   0       = Player :)
        #   1       = Box which the player will move
        #   2       = Place where a box is needed
    */
    public MapModel convertStringsToMap(final int level, final ObservableList<String> mapAsStrings) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Convert strings to MapModel"); // NOI18N
        
        final MapModel mapModel = new MapModel();
        mapModel.setLevel(level);
        mapModel.setMapAsStrings(mapAsStrings);
        
        int columns = 0;
        int rows = 0;
        for (String line : mapAsStrings) {
            for (int x = 0; x < line.length(); x++) {
                columns = Math.max(columns, x + 1);
                
                final Character c = line.charAt(x);
                final int x1 = x + 1;
                final int y1 = rows + 1;
                switch(c) {
                    case 'A': // NOI18N
                    case 'B': { mapModel.addWall(x1, y1);   break; } // NOI18N
                    
                    case '0': { mapModel.setPlayer(x1, y1); break; } // NOI18N
                    case '1': { mapModel.addBox(x1, y1);    break; } // NOI18N
                    case '2': { mapModel.addPlace(x1, y1);  break; } // NOI18N
                    
                    case '-': // NOI18N
                    default: { }
                }
            }
            
            rows = rows + 1;
        }
        
        mapModel.setColumns(columns);
        mapModel.setRows(rows);
        
        return mapModel;
    }
    
}
