package br.com.antoniogabriel.lirelab.custom.singleimagegrid;

import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.imagegrid.ImageGrid;
import javafx.embed.swing.JFXPanel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SingleImageGridTest {

    private static final JFXPanel INIT_JAVAFX = new JFXPanel();

    @Mock private ImageGrid imageGrid;

    @InjectMocks private SingleImageGrid singleImageGrid = new SingleImageGrid();

    @Test
    public void shouldSetImageHeight() throws Exception {
        int height = 100;

        singleImageGrid.setImageHeight(height);

        verify(imageGrid).setImagesHeight(height);
    }

    @Test
    public void shouldSetImage() throws Exception {
        String imagePath = "some/image/path";
        String thumbnailPath = "some/thumbnail/path";

        singleImageGrid.setImage(new Image(imagePath, thumbnailPath));

        verify(imageGrid).clear();
        verify(imageGrid).addImage(thumbnailPath);
    }

    @Test
    public void shouldSetOnChangeListener() throws Exception {
        BooleanHolder listenerExecuted = new BooleanHolder(false);

        singleImageGrid.setOnChange((image) -> listenerExecuted.setValue(true));

        singleImageGrid.setImage(new Image("", ""));

        assertTrue(listenerExecuted.getValue());
    }

    class BooleanHolder {
        boolean value = false;

        public BooleanHolder(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }
    }
}