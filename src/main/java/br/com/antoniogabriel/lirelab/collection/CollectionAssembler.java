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

import br.com.antoniogabriel.lirelab.app.LireLabException;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CollectionAssembler {

    private PathResolver resolver;
    private CollectionUtils collectionUtils;

    @Inject
    public CollectionAssembler(PathResolver resolver, CollectionUtils collectionUtils) {
        this.resolver = resolver;
        this.collectionUtils = collectionUtils;
    }

    public Collection assembleCollectionFrom(Path collectionPath) throws JAXBException {
        CollectionXMLDAO dao = new CollectionXMLDAO(collectionPath.toFile());
        Collection collection = dao.readCollection();

        collection.setImages(getImagesOf(collection));

        return collection;
    }

    private List<Image> getImagesOf(Collection collection) {

        List<Image> results = new ArrayList<>();

        try {
            Path path = indexPath(collection);
            if(!Files.exists(path)) return results;

            IndexReader ir = DirectoryReader.open(FSDirectory.open(path));

            int num = ir.numDocs();
            for ( int i = 0; i < num; i++)
            {
                Document d = ir.document(i);
                String imagePath = d.getField(DocumentBuilder.FIELD_NAME_IDENTIFIER).stringValue();
                String thumbnailPath = collectionUtils.getThumbnailPathFromImagePath(collection, imagePath);
                Image image = new Image(imagePath, thumbnailPath);
                image.setDocId(i);
                results.add(image);
            }
            ir.close();

        } catch (IOException e) {
            throw new LireLabException("Could not read index", e);
        }

        return results;
    }

    private Path indexPath(Collection collection) {
        return Paths.get(resolver.getIndexDirectoryPath(collection.getName()));
    }

}
