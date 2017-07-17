package br.com.antoniogabriel.lirelab.collection;

public class Command<T> {

    private final String label;
    private final CommandAction action;

    private String iconDescription = "";

    public Command(String label, CommandAction<T> action) {
        this.label = label;
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

    public void setIconDescription(String iconDescription) {
        this.iconDescription = iconDescription;
    }
}
