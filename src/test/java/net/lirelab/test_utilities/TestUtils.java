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

package net.lirelab.test_utilities;

import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import net.lirelab.collection.PathResolver;
import net.lirelab.lire.Feature;
import javafx.embed.swing.JFXPanel;
import org.apache.commons.io.FileUtils;
import org.testfx.api.FxRobot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class TestUtils {

    public static void startJavaFX() {
        new JFXPanel();
    }

    public static void runOnFxThreadAndWait(Runnable runnable) {
        startJavaFX();
        new FxRobot().interact(runnable);
    }

    public static void deleteWorkDirectory(PathResolver resolver) {
        try {
            File directory =  Paths.get(resolver.getWorkDirectoryPath()).toFile();
            FileUtils.deleteDirectory(directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Collection collection(String name, String imagesPath, Feature... features) {
        try {
            Collection collection = new Collection(name);
            collection.setImagesDirectory(imagesPath);
            collection.setFeatures(asList(features));

            if(Files.isDirectory(Paths.get(imagesPath))) {
                ArrayList<String> paths = net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(imagesPath), true);

                ArrayList<Image> images = new ArrayList<>();
                for (String path : paths) {
                    images.add(new Image(path, path));
                }
                collection.setImages(images);
            }

            return collection;
        } catch (IOException e) {
            throw new RuntimeException("Error creating collection", e);
        }
    }

    public static boolean isHeadless() {
        return getProperty("testfx.robot").equals("glass")
                && getProperty("testfx.headless").equals("true")
                && getProperty("glass.platform").equals("Monocle")
                && getProperty("monocle.platform").equals("Headless")
                && getProperty("java.awt.headless").equals("true");
    }

    private static String getProperty(String property) {
        return System.getProperty(property) != null ?
                System.getProperty(property) : "";
    }
}
