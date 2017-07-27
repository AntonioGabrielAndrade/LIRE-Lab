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

import javax.xml.bind.JAXBException;
import java.util.List;

public class XMLCreator {

    private final String collectionName;
    private final String imagesDir;
    private final List<Feature> features;
    private CollectionXMLDAO xmlDAO;
    private XMLCreatorCallback callback = new DumbXMLCreatorCallback();

    public XMLCreator(String collectionName,
                      String imagesDir,
                      List<Feature> features,
                      CollectionXMLDAO xmlDAO) {

        this.collectionName = collectionName;
        this.imagesDir = imagesDir;
        this.features = features;
        this.xmlDAO = xmlDAO;
    }

    public void create() throws JAXBException {
        callback.beforeCreateXML();

        Collection collection = new Collection();
        collection.setName(collectionName);
        collection.setFeatures(features);
        collection.setImagesDirectory(imagesDir);

        xmlDAO.create(collection);
        callback.afterCreateXML();
    }

    public void setCallback(XMLCreatorCallback callback) {
        this.callback = callback;
    }

    private class DumbXMLCreatorCallback implements XMLCreatorCallback {
        @Override
        public void beforeCreateXML() {}

        @Override
        public void afterCreateXML() {}
    }
}
