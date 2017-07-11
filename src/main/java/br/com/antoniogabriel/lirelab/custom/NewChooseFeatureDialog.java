package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.custom.image_dialog.DialogActions;
import br.com.antoniogabriel.lirelab.lire.Feature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.COLOR_HISTOGRAM;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static java.util.Arrays.asList;

public class NewChooseFeatureDialog extends Dialog<Feature> {

    private List<Feature> features;

    private DialogActions dialogActions;

    private Feature selectedFeature;

    private Map<String, Feature> nameToFeature = new HashMap<>();

    public NewChooseFeatureDialog(List<Feature> features) {
        this.features = features;
        this.selectedFeature = features.get(0);

        setDialogActions(new DialogActions(this));
        addFeatureRadio();
        addOkButton();
        dialogActions.setDialogPaneId("choose-feature-dialog");
    }

    private void setDialogActions(DialogActions dialogActions) {
        this.dialogActions = dialogActions == null ?
                new DialogActions(this) :
                dialogActions;
    }

    private void addFeatureRadio() {
        VBox root = new VBox();
        root.setSpacing(5);
        final ToggleGroup group = new ToggleGroup();
        for (Feature feature : features) {
            nameToFeature.put(feature.getFeatureName(), feature);
            RadioButton featureRadio = new RadioButton(feature.getFeatureName());
            featureRadio.setToggleGroup(group);
            root.getChildren().add(featureRadio);
        }

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(group.getSelectedToggle() != null) {
                    selectedFeature = nameToFeature.get(((RadioButton)group.getSelectedToggle()).getText());
                }
            }
        });

        dialogActions.setContent(root);
        dialogActions.setTitle("Select Feature");
        getDialogPane().setPrefWidth(300);
        getDialogPane().setHeaderText("Choose Feature to run query");

    }

    private void addOkButton() {
        this.dialogActions.addButtonType(ButtonType.OK);
        this.dialogActions.addButtonType(ButtonType.CANCEL);

        setResultConverter(new Callback<ButtonType, Feature>() {
            @Override
            public Feature call(ButtonType param) {
                if(param.equals(ButtonType.OK)) return selectedFeature;
                return null;
            }
        });
    }

    public Feature showAndGetFeature() {
        try {
            return showAndWait().get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public Window getWindow() {
        return getDialogPane().getScene().getWindow();
    }

    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(() -> {
            NewChooseFeatureDialog dialog = new NewChooseFeatureDialog(asList(CEDD, TAMURA, COLOR_HISTOGRAM));
            System.out.println(dialog.showAndGetFeature());
        });
    }
}
