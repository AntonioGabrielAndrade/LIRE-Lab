package br.com.antoniogabriel.lirelab.app;


import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import javafx.event.ActionEvent;

import javax.inject.Inject;
import java.io.IOException;

public class AppController {

    private CreateCollectionFXML createCollectionFXML;
    private DialogProvider dialogProvider;

    @Inject
    public AppController(CreateCollectionFXML createCollectionFXML, DialogProvider dialogProvider) {
        this.createCollectionFXML = createCollectionFXML;
        this.dialogProvider = dialogProvider;
    }

    public void openCreateCollectionDialog(ActionEvent event) throws IOException {
        createCollectionFXML.loadOwnedBy(dialogProvider.getWindowFrom(event));
    }

    public void searchCollection(ActionEvent event) {
//        throw new UnsupportedOperationException();
    }
}
