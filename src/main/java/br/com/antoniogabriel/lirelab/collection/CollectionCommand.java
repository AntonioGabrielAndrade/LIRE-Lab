package br.com.antoniogabriel.lirelab.collection;

public class CollectionCommand {

    private final String label;
    private final CollectionCommandAction action;

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
}
