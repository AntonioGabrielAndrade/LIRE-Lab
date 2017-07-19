package br.com.antoniogabriel.lirelab.app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuBarController implements Initializable {

    @FXML private Menu fileMenu;
    @FXML private Menu helpMenu;

    private ApplicationCommands applicationCommands;
    private CommandTriggerFactory commandTriggerFactory;

    @Inject
    public MenuBarController(ApplicationCommands applicationCommands, CommandTriggerFactory commandTriggerFactory) {
        this.applicationCommands = applicationCommands;
        this.commandTriggerFactory = commandTriggerFactory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupFileMenu();
        setupHelpMenu();
    }

    private void setupFileMenu() {
        List<Command<Void>> commands = applicationCommands.getFileMenuCommands();
        for (Command<Void> command : commands) {
            MenuItem menuItem = commandTriggerFactory.createMenuItem(command, () -> null);
            menuItem.setId(command.getNodeId());
            fileMenu.getItems().add(menuItem);
        }
    }

    private void setupHelpMenu() {
        List<Command<Void>> commands = applicationCommands.getHelpMenuCommands();
        for (Command<Void> command : commands) {
            MenuItem menuItem = commandTriggerFactory.createMenuItem(command, () -> null);
            menuItem.setId(command.getNodeId());
            helpMenu.getItems().add(menuItem);
        }
    }
}
