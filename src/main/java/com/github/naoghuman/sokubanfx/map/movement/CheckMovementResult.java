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

import com.github.naoghuman.sokubanfx.map.animation.EAnimation;

/**
 *
 * @author Naoghuman
 */
public class CheckMovementResult {
    
    public static final CheckMovementResult getDefault() {
        return new CheckMovementResult();
    }
    
    private CheckMovementResult() { }
    
    private EAnimation animation = EAnimation.NONE;
    private EMovement movement = EMovement.NONE;

    public EAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(EAnimation animation) {
        this.animation = animation;
    }

    public EMovement getMovement() {
        return movement;
    }

    public void setMovement(EMovement movement) {
        this.movement = movement;
    }
    
}
