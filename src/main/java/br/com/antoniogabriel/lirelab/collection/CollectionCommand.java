package br.com.antoniogabriel.lirelab.collection;

public class CollectionCommand {

    private final String label;
    private final CollectionCommandAction action;

    private String iconDescription = "";

    public CollectionCommand(String label, CollectionCommandAction action) {
        this.label = label;
        this.action = action;
    }

    public void execute(Collection collection) {
        action.execute(collection);
    }

    public String getLabel() {
        return label;
    }

    public String getIconDescription() {
        return iconDescription;
    }

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }
}
