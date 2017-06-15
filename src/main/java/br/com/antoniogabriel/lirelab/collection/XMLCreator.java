package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.lire.Feature;

import javax.xml.bind.JAXBException;
import java.util.List;

public class XMLCreator {
    private final String collectionName;
    private final String imagesDir;
    private final List<Feature> features;
    private CollectionXMLDAO xmlDAO;
    private XMLCreatorCallback callback;

    public XMLCreator(String collectionName,
                      String imagesDir,
                      List<Feature> features,
                      CollectionXMLDAO xmlDAO) {

        this.collectionName = collectionName;
        this.imagesDir = imagesDir;
        this.features = features;
        this.xmlDAO = xmlDAO;
        this.callback = callback;
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
}
