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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public class MapConverter {
    
    public ObservableList<String> convertMapCoordinatesToStrings(MapModel mapModel) {
        final ObservableList<String> mapAsStrings = FXCollections.observableArrayList();
        final int columns = mapModel.getColumns();
        final int rows = mapModel.getRows();
        final int level = mapModel.getLevel();
        
        final Coordinates player = mapModel.getPlayer();
        final ObservableList<Coordinates> boxes = mapModel.getBoxes();
        final ObservableList<Coordinates> places = mapModel.getPlaces();
        final ObservableList<Coordinates> walls = mapModel.getWalls();

        // - = Empty sign
        for (int ro = 0; ro < rows; ro++) {
            final StringBuilder sb = new StringBuilder();
            for (int col = 0; col < columns; col++) {
                sb.append("-"); // NOI18N
            }
            mapAsStrings.add(sb.toString());
        }
        
        // A, B = Walls from the level
        for (int ro = 1; ro <= rows; ro++) {
            for (int col = 1; col <= columns; col++) {
                for (Coordinates wall : walls) {
                    if (wall.getX() == col && wall.getY() == ro) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(ro - 1));
                        sb.replace(col - 1, col, level % 2 != 0 ? "A" : "B"); // NOI18N
                        mapAsStrings.remove(ro - 1);
                        mapAsStrings.add(ro - 1, sb.toString());
                    }
                }
            }
        }
        
        // 2 = Place where a box is needed
        for (int ro = 1; ro <= rows; ro++) {
            for (int col = 1; col <= columns; col++) {
                for (Coordinates place : places) {
                    if (place.getX() == col && place.getY() == ro) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(ro - 1));
                        sb.replace(col - 1, col, "2"); // NOI18N
                        mapAsStrings.remove(ro - 1);
                        mapAsStrings.add(ro - 1, sb.toString());
                    }
                }
            }
        }
        
        // 1 = Box which the player should move to the place
        for (int ro = 1; ro <= rows; ro++) {
            for (int col = 1; col <= columns; col++) {
                for (Coordinates box : boxes) {
                    if (box.getX() == col && box.getY() == ro) {
                        final StringBuilder sb = new StringBuilder();
                        sb.append(mapAsStrings.get(ro - 1));
                        sb.replace(col - 1, col, "1"); // NOI18N
                        mapAsStrings.remove(ro - 1);
                        mapAsStrings.add(ro - 1, sb.toString());
                    }
                }
            }
        }
        
        // 0 = Player :)
        for (int ro = 1; ro <= rows; ro++) {
            for (int col = 1; col <= columns; col++) {
                if (player.getX() == col && player.getY() == ro) {
                    final StringBuilder sb = new StringBuilder();
                    sb.append(mapAsStrings.get(ro - 1));
                    sb.replace(col - 1, col, "0"); // NOI18N
                    mapAsStrings.remove(ro - 1);
                    mapAsStrings.add(ro - 1, sb.toString());
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
