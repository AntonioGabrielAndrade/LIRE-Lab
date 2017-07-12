package br.com.antoniogabriel.lirelab.acceptance.custom;

import br.com.antoniogabriel.lirelab.custom.feature_table.FeatureTable;
import br.com.antoniogabriel.lirelab.lire.Feature;
import br.com.antoniogabriel.lirelab.util.FeatureUtils;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class FeatureTableAcceptanceTest extends ApplicationTest {

    private FeatureTable table;

    private FeatureTableViewObject view = new FeatureTableViewObject();
    private FeatureUtils featureUtils = new FeatureUtils();
    private Feature[] features = Feature.values();

    @Override
    public void start(Stage stage) throws Exception {
        table = new FeatureTable();
        table.setItems(featureUtils.toViewableFeatures(features));
        Scene scene = new Scene(table, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void shouldShowAllFeatures() throws Exception {
        view.waitUntilFeaturesAreVisible(features);
    }

    @Test
    public void shouldSelectAllFeatures() throws Exception {
        view.selectAll();
        view.checkFeaturesAreSelected(features);
    }
}
