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

import java.nio.file.Paths;

public class Image {

    private final String imagePath;
    private final String thumbnailPath;

    private double score = -1;
    private int position = -1;
    private int docId = -1;

    public Image(String imagePath, String thumbnailPath) {
        this.imagePath = imagePath;
        this.thumbnailPath = thumbnailPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public String getImageName() {
        return Paths.get(imagePath).getFileName().toString().replace(".jpg", "");
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getDocId() {
        return docId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (imagePath != null ? !imagePath.equals(image.imagePath) : image.imagePath != null) return false;
        return thumbnailPath != null ? thumbnailPath.equals(image.thumbnailPath) : image.thumbnailPath == null;
    }

    @Override
    public int hashCode() {
        int result = imagePath != null ? imagePath.hashCode() : 0;
        result = 31 * result + (thumbnailPath != null ? thumbnailPath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return getImageName();
    }

}
