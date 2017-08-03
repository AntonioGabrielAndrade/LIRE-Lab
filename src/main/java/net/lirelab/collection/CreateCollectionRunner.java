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

import net.lirelab.app.LireLabException;
import net.lirelab.lire.IndexCreator;
import net.lirelab.lire.IndexCreatorCallback;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class CreateCollectionRunner implements Runnable {

    private final IndexCreator indexCreator;
    private final ThumbnailsCreator thumbnailsCreator;
    private final XMLCreator xmlCreator;

    private Runnable finish = () -> {};

    public CreateCollectionRunner(IndexCreator indexCreator,
                                  ThumbnailsCreator thumbnailsCreator,
                                  XMLCreator xmlCreator) {

        this.indexCreator = indexCreator;
        this.thumbnailsCreator = thumbnailsCreator;
        this.xmlCreator = xmlCreator;
    }

    public void setIndexCreatorCallback(IndexCreatorCallback callback) {
        indexCreator.setCallback(callback);
    }

    public void setThumbnailsCreatorCallback(ThumbnailsCreatorCallback callback) {
        thumbnailsCreator.setCallback(callback);
    }

    public void setXmlCreatorCallback(XMLCreatorCallback callback) {
        xmlCreator.setCallback(callback);
    }

    public void setOnFinish(Runnable finish) {
        if(finish != null) this.finish = finish;
    }

    @Override
    public void run() {
        try {
            indexCreator.create();
            thumbnailsCreator.create();
            xmlCreator.create();
            finish.run();
        } catch (IOException | JAXBException e) {
            throw new LireLabException("Could not create collection", e);
        }
    }
}
