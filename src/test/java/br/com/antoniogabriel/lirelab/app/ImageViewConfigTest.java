package br.com.antoniogabriel.lirelab.app;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.file.Paths;

import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageViewConfigTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private ImageView imageView;
    @Mock private Region container;
    @Mock private DoubleProperty fitHeightProperty;
    @Mock private ReadOnlyDoubleProperty heightProperty;
    @Mock private DoubleBinding doubleBinding;

    private ImageViewConfig viewConfig = new ImageViewConfig();

    @Test
    public void shouldBindImageHeightToContainerGivenAMultiplicationFactor() throws Exception {
        double factor = 0.5;
        given(imageView.fitHeightProperty()).willReturn(fitHeightProperty);
        given(container.heightProperty()).willReturn(heightProperty);
        given(heightProperty.multiply(factor)).willReturn(doubleBinding);

        viewConfig.bindImageHeight(imageView, container, factor);

        verify(imageView).setPreserveRatio(true);
        verify(fitHeightProperty).bind(doubleBinding);
    }

    @Test
    public void shouldLimitImageViewHeightWhenImageHeightIsBiggerThanLimit() throws Exception {
        String imageURL = Paths.get(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg").toUri().toURL().toString();
        ImageView imageView = new ImageView(imageURL);

        double imageHeight = imageView.getImage().getHeight();
        double limit = 10.0;

        assertTrue(imageHeight > limit);

        viewConfig.limitImageHeight(imageView, limit);

        assertThat(imageView.getFitHeight(), is(limit));
    }

    @Test
    public void shouldPreserveImageViewHeightWhenImageHeightIsSmallerThanLimit() throws Exception {
        String imageURL = Paths.get(TEST_IMAGES + "14474347006_99aa0fd981_k.jpg").toUri().toURL().toString();
        ImageView imageView = new ImageView(imageURL);

        double imageHeight = imageView.getImage().getHeight();
        double limit = 999.0;

        assertTrue(imageHeight < limit);

        viewConfig.limitImageHeight(imageView, limit);

        assertThat(imageView.getFitHeight(), is(imageHeight));
    }
}