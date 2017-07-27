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

package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"name", "imagesDirectory", "features"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection {

    @XmlElement
    private String name = "";

    @XmlElement
    private String imagesDirectory = "";

    @XmlElementWrapper(name="features")
    @XmlElement(name="feature")
    private List<Feature> features = new ArrayList<>();

    @XmlTransient
    private List<Image> images = new ArrayList<>();

    public Collection() {}

    public Collection(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setImagesDirectory(String imagesDirectory) {
        this.imagesDirectory = imagesDirectory;
    }

    public String getImagesDirectory() {
        return imagesDirectory;
    }

    public List<Image> getImages() {
        return images;
    }

    public List<Image> getImagesInRange(int fromIndex, int toIndex) {
        return getImages().subList(fromIndex, toIndex);
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int totalImages() {
        return images.size();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Collection that = (Collection) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (features != null ? !features.equals(that.features) : that.features != null) return false;
        return imagesDirectory != null ? imagesDirectory.equals(that.imagesDirectory) : that.imagesDirectory == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (features != null ? features.hashCode() : 0);
        result = 31 * result + (imagesDirectory != null ? imagesDirectory.hashCode() : 0);
        return result;
    }

}
