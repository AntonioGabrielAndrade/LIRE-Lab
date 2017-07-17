package br.com.antoniogabriel.lirelab.collection;

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

    public String getIconDescription() {
        return iconDescription;
    }
}
