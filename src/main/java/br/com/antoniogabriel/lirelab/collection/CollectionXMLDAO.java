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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static br.com.antoniogabriel.lirelab.collection.PathResolver.COLLECTION_XML;


public class CollectionXMLDAO {

    private File targetDir;

    public CollectionXMLDAO(File targetDir) {
        this.targetDir = targetDir;
    }

    public CollectionXMLDAO(String targetDir) {
        this(new File(targetDir));
    }

    public void create(Collection collection) throws JAXBException {
        File file = targetFile();

        JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.marshal(collection, file);
    }

    public Collection readCollection() throws JAXBException {
        File file = targetFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        return (Collection) jaxbUnmarshaller.unmarshal(file);
    }

    private File targetFile() {
        return new File(targetDir, COLLECTION_XML);
    }
}
