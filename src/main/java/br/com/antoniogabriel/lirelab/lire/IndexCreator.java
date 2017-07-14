package br.com.antoniogabriel.lirelab.lire;

import java.io.IOException;

/**
 * Created by gabriel on 13/07/17.
 */
public interface IndexCreator {
    void create() throws IOException;

    void setCallback(IndexCreatorCallback callback);
}
