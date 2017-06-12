package br.com.antoniogabriel.lirelab.app;

import br.com.antoniogabriel.lirelab.collection.CreateCollectionView;
import org.testfx.api.FxRobot;

public class AppView extends FxRobot {

    MenuView menuView = new MenuView();
    ToolBarView toolBarView = new ToolBarView();
    WelcomeView welcomeView = new WelcomeView();

    public void checkMenus() {
        menuView.checkMenuBar();
        menuView.checkFileMenu();
        menuView.checkHelpMenu();
    }

    public void checkToolBar() {
        toolBarView.checkStructure();
    }

    public void checkWelcomeView() {
        welcomeView.checkStructure();
    }

    public CreateCollectionView createCollection() {
        clickOn("#welcome-create-collection-button")
                .interrupt();

        return new CreateCollectionView();
    }
}
