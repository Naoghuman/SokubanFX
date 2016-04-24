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
package com.github.naoghuman.sokubanfx.geometry;

/**
 *
 * @author Naoghuman
 */
public class Coordinates {
    
    private static final Coordinates DEFAULT = new Coordinates(-1, -1);
    
    public static Coordinates getDefault() {
        return new Coordinates(DEFAULT.getX(), DEFAULT.getY());
    }
    
    public static boolean isDefault(Coordinates other) {
        return other.getX() == DEFAULT.getX() && other.getY() == DEFAULT.getY();
    }
    
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.x;
        hash = 29 * hash + this.y;
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Coordinates other = (Coordinates) obj;
        if (this.x != other.x) {
            return false;
        }
        
        if (this.y != other.y) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "(x=" + x + ",y=" + y + ")"; // NOI18N
    }
    
}
