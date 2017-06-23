package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageViewFactoryTest {

    private static final String INEXISTENT_FILE_PATH = "some/path/that/dont/exists";

    private static final String EXISTENT_FILE_PATH = "thumb/16903390174_1d670a5849_h.thumbnail.jpg";
    private static final String EXISTENT_FILE_NAME_WITH_NO_EXTENSIONS = "16903390174_1d670a5849_h";

    private ImageViewFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ImageViewFactory();
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileDontExists() throws Exception {
        factory.create(INEXISTENT_FILE_PATH);
    }

    @Test
    public void shouldCreateImageViewWhenFileExists() throws Exception {
        ImageView imageView = factory.create(existentFilePath());
        assertNotNull(imageView);
    }

    @Test
    public void shouldSetIdAsFilenameWithoutThumbnailAndJPGExtension() throws Exception {
        ImageView imageView = factory.create(existentFilePath());
        assertThat(imageView.getId(), equalTo(EXISTENT_FILE_NAME_WITH_NO_EXTENSIONS));
    }

    private String existentFilePath() {
        return getClass().getResource(EXISTENT_FILE_PATH).getFile();
    }
}