package br.com.antoniogabriel.lirelab.custom;

import griffon.javafx.support.tangoicons.TangoIcon;
import javafx.beans.NamedArg;
import javafx.scene.image.ImageView;

public class TangoIconWrapper extends ImageView {

    public TangoIconWrapper(@NamedArg("description") String description) {
        super(new TangoIcon(description));
    }
}
