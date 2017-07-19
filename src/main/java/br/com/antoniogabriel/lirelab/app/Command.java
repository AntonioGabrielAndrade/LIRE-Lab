package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import javafx.scene.Node;

public class Command<T> {

    private final String label;
    private final CommandAction action;
    private final String iconDescription;

    public Command(String label, String iconDescription, CommandAction<T> action) {
        this.label = label;
        this.iconDescription = iconDescription;
        this.action = action;
    }

    public void execute(T param) {
        action.execute(param);
    }

    public String getLabel() {
        return label;
    }

    public Node getIcon() {
        return iconDescription.isEmpty() ?
                null :
                new TangoIconWrapper(iconDescription);
    }
}
