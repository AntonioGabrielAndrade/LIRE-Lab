package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.custom.TangoIconWrapper;
import javafx.scene.Node;

public class Command<T> {

    private final String label;
    private final String nodeId;
    private final CommandAction action;
    private final String iconDescription;

    public Command(String label,
                   String iconDescription,
                   String nodeId,
                   CommandAction<T> action) {

        this.label = label;
        this.iconDescription = iconDescription;
        this.nodeId = nodeId;
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

    public String getNodeId() {
        return nodeId;
    }
}
