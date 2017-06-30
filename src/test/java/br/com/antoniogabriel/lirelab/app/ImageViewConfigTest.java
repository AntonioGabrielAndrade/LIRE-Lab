package br.com.antoniogabriel.lirelab.app;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageViewConfigTest {

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
}