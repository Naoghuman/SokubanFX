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
package com.github.naoghuman.sokubanfx.configuration;

/**
 *
 * @author Naoghuman
 */
public interface IVideoConfiguration {
    
    public static final String KEY__RESOURCE_BUNDLE__VIDEO = "/com/github/naoghuman/sokubanfx/video/video.properties"; // NOI18N
    public static final String KEY__VIDEO_MAX = "video.max"; // NOI18N
    
    public static final String PREFIX__VIDEO = "video"; // NOI18N
    
    public static final String PROP__CHOOSEN_VIDEO = "PROP__CHOOSEN_VIDEO"; // NOI18N
    public static final int PROP__CHOOSEN_VIDEO__DEFAULT_VALUE = 1;
    
    public static final String SUFFIX__FILE = ".file"; // NOI18N
    public static final String SUFFIX__IMAGE = ".image"; // NOI18N
    public static final String SUFFIX__TITLE = ".title"; // NOI18N
    
}
