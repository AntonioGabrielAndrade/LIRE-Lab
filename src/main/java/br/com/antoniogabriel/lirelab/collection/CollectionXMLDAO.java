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
        jaxbMarshaller.marshal(collection, System.out);
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
