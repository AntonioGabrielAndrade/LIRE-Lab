package br.com.antoniogabriel.lirelab.collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;


public class CollectionXMLDAO {

    private File targetDir;

    public CollectionXMLDAO(File targetDir) {
        this.targetDir = targetDir;
    }

    public void create(Collection collection) throws JAXBException {
        File file = new File(targetDir, "collection.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(Collection.class);

        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        jaxbMarshaller.marshal(collection, file);
        jaxbMarshaller.marshal(collection, System.out);
    }
}
