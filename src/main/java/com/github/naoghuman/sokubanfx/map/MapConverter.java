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
package com.github.naoghuman.sokubanfx.map;

import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.List;

/**
 *
 * @author Naoghuman
 */
public class MapConverter {
    // TODO change to englisch (also in properties file)
    /*
        # Possible tiles are:
        #   -       = Empty sign
        #   A, B    = Walls from the level
        #   0       = Player :)
        #   1       = Place where a box is needed
        #   2       = Box which the player will move
    */
    public MapModel convertStringsToMap(final int level, final List<String> mapAsStrings) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "Convert strings to MapModel"); // NOI18N
        
        final MapModel mapModel = new MapModel();
        mapModel.setLevel(level);
        
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
                    case 'B': { mapModel.addWall(x1, y1); break; } // NOI18N
                    
                    case '0': { mapModel.setPlayer(x1, y1); break; } // NOI18N
                    case '1': { mapModel.addBox(x1, y1); break; } // NOI18N
                    case '2': { mapModel.addPlace(x1, y1); break; } // NOI18N
                    
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
