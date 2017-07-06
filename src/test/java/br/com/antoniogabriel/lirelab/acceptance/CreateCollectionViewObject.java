package br.com.antoniogabriel.lirelab.acceptance;


import br.com.antoniogabriel.lirelab.acceptance.custom.ProgressDialogViewObject;
import br.com.antoniogabriel.lirelab.collection.CreateCollectionFXML;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.scene.control.CheckBox;
import javafx.stage.Window;
import org.testfx.api.FxRobot;

import java.util.Set;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.WindowMatchers.isNotShowing;

public class CreateCollectionViewObject extends FxRobot {

    private final Window window;

    public CreateCollectionViewObject() {
        window = window(CreateCollectionFXML.TITLE);
        targetWindow(window);
    }

    public void cancel() {
        clickOn("#cancel");
    }

    public void checkWindowIsClosed() {
        verifyThat(window, isNotShowing());
    }

    public void unselectAllFeatures() {
        for (CheckBox checkBox : tableCheckBoxes()) {
            checkBox.setSelected(false);
        }
    }

    private Set<CheckBox> tableCheckBoxes() {
        return lookup("#featuresTable")
                .lookup(".check-box")
                .queryAll();
    }

    public CreateCollectionViewObject writeName(String name) {
        clickOn("#collection-name").write(name);
        return this;
    }

    public CreateCollectionViewObject writeImagesDirectory(String path) {
        clickOn("#images-directory").write(path);
        return this;
    }

    public CreateCollectionViewObject selectFeatures(Feature... features) {
        for (Feature feature : features) {
            select(feature);
        }
        return this;
    }

    private void select(Feature feature) {
        CheckBox checkBox = checkboxFor(feature);
        boolean oldValue = checkBox.isSelected();

        clickOn(checkBox).interrupt();

        //workaround when sometimes it wont click the checkbox
        if(checkBox.isSelected() == oldValue) {
            checkBox.setSelected(!oldValue);
        }
    }

    private CheckBox checkboxFor(Feature feature) {
        return lookup("#featuresTable")
                .lookup(".table-row-cell").nth(feature.ordinal())
                .lookup(".table-cell").nth(0)
                .lookup(".check-box").query();
    }

    public void checkCreateIsDisabled() {
        verifyThat("#create", isDisabled());
    }

    public void checkCreateIsEnabled() {
        verifyThat("#create", isEnabled());
    }

    public ProgressDialogViewObject create() {
        clickOn("#create");
        return new ProgressDialogViewObject();
    }
}
