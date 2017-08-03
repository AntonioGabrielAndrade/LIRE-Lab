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

package net.lirelab.collection;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class Subfolders implements DirectoryStream<Path> {

    private DirectoryStream<Path> folders;

    private Subfolders(Path dir) throws IOException {
        folders = Files.newDirectoryStream(dir, entry -> Files.isDirectory(entry));
    }

    public static Subfolders of(Path dir) throws IOException {
        return new Subfolders(dir);
    }

    @Override
    public Iterator<Path> iterator() {
        return folders.iterator();
    }

    @Override
    public void close() throws IOException {
        folders.close();
    }
}
