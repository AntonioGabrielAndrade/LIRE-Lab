package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.exception.LireLabException;
import br.com.antoniogabriel.lirelab.lire.IndexCreator;
import br.com.antoniogabriel.lirelab.lire.IndexCreatorCallback;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public class CreateCollectionRunnable implements Runnable {

    private final IndexCreator indexCreator;
    private final ThumbnailsCreator thumbnailsCreator;
    private final XMLCreator xmlCreator;

    private Runnable finish = () -> {};

    public CreateCollectionRunnable(IndexCreator indexCreator,
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
