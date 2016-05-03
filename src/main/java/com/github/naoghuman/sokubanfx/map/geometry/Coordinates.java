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
package com.github.naoghuman.sokubanfx.map.geometry;

/**
 *
 * @author Naoghuman
 */
public class Coordinates {
    
    private static final Coordinates DEFAULT = new Coordinates(-1, -1);
    
    public static Coordinates getDefault() {
        return new Coordinates(DEFAULT.getX(), DEFAULT.getY());
    }
    
    public static Coordinates getDefault(int x, int y) {
        return new Coordinates(x, y);
    }
    
    public static boolean isDefault(Coordinates other) {
        return other.getX() == DEFAULT.getX() && other.getY() == DEFAULT.getY();
    }
    
    private int translateX = 0;
    private int translateY = 0;
    private int x;
    private int y;

    private Coordinates(int x, int y) {
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
    
    public int getTranslateX() {
        return translateX;
    }
    
    public void setTranslateX(int translateX) {
        this.translateX = translateX;
    }
    
    public int getTranslateY() {
        return translateY;
    }
    
    public void setTranslateY(int translateY) {
        this.translateY = translateY;
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
        final StringBuilder sb = new StringBuilder();
        sb.append("(x=").append(x); // NOI18N
        sb.append(", y=").append(y); // NOI18N
        sb.append(", t-x=").append(translateX); // NOI18N
        sb.append(", t-y=").append(translateY); // NOI18N
        sb.append(")"); // NOI18N
        
        return sb.toString();
    }
    
}
