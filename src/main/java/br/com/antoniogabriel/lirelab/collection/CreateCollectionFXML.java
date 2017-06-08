package br.com.antoniogabriel.lirelab.collection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class CreateCollectionFXML {

    public static Parent load() throws IOException {
        return FXMLLoader.load(
                CreateCollectionFXML.class.getResource(
                        "create-collection.fxml"));
    }

}
