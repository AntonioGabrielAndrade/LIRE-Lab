/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
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

package net.lirelab.app;

import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {
    static {
        try {
            final Properties properties = new Properties();
            properties.load(ProjectProperties.class.getClassLoader().getResourceAsStream("project.properties"));
            PROJECT_NAME = properties.getProperty("project.name");
            PROJECT_VERSION = properties.getProperty("project.version");
            LIRE_VERSION = properties.getProperty("lire.version");
            ARTIFACT_ID = properties.getProperty("project.artifactId");
        } catch (IOException e) {
            throw new RuntimeException("Could not read project.properties", e);
        }
    }

    public static final String PROJECT_NAME;
    public static final String PROJECT_VERSION;
    public static final String LIRE_VERSION;
    public static final String ARTIFACT_ID;
}
