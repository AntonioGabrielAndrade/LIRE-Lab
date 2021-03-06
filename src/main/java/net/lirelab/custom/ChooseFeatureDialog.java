/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lirelab.custom;

import net.lirelab.custom.image_dialog.DialogActions;
import net.lirelab.lire.Feature;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static net.lirelab.lire.Feature.CEDD;
import static net.lirelab.lire.Feature.COLOR_HISTOGRAM;
import static net.lirelab.lire.Feature.TAMURA;
import static java.util.Arrays.asList;

public class ChooseFeatureDialog extends Dialog<Feature> {

    private List<Feature> features;

    private DialogActions dialogActions;

    private Feature selectedFeature;

    private Map<String, Feature> nameToFeature = new HashMap<>();

    public ChooseFeatureDialog(List<Feature> features) {
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
        dialogActions.setContent(root);
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

//        dialogActions.setContent(root);
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
            ChooseFeatureDialog dialog = new ChooseFeatureDialog(asList(CEDD, TAMURA, COLOR_HISTOGRAM));
            System.out.println(dialog.showAndGetFeature());
        });
    }
}
