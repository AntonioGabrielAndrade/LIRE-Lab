package br.com.antoniogabriel.lirelab.collection;


import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.ProgressDialogView;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.stage.Window;
import org.testfx.api.FxRobot;

import java.util.Set;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.WindowMatchers.isNotShowing;

public class CreateCollectionView extends FxRobot {

    private final Window window;

    public CreateCollectionView() {
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
        Set<CheckBox> checkBoxes = lookup("#featuresTable")
                .lookup(".check-box")
                .queryAll();

        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    public CreateCollectionView writeName(String name) {
        clickOn("#collection-name").write(name);
        return this;
    }

    public CreateCollectionView writeImagesDirectory(String path) {
        clickOn("#images-directory").write(path);
        return this;
    }

    public CreateCollectionView selectFeatures(Feature... features) {
        for (Feature feature : features) {
            Node checkBox = lookup("#featuresTable")
                    .lookup(".table-row-cell").nth(feature.ordinal())
                    .lookup(".table-cell").nth(0)
                    .lookup(".check-box").query();
            clickOn(checkBox).interrupt();
        }
        return this;
    }

    public void checkCreateIsDisabled() {
        verifyThat("#create", isDisabled());
    }

    public void checkCreateIsEnabled() {
        verifyThat("#create", isEnabled());
    }

    public ProgressDialogView create() {
        clickOn("#create");
        return new ProgressDialogView();
    }
}
