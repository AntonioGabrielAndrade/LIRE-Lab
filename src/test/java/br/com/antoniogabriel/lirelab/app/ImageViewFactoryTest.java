package br.com.antoniogabriel.lirelab.app;

import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageViewFactoryTest {

    private static final String INEXISTENT_FILE = "a_file";

    private static final String EXISTENT_FILE = "14474347006_99aa0fd981_k.thumbnail.jpg";
    private static final String EXISTENT_FILE_NO_EXTENSIONS = "14474347006_99aa0fd981_k";

    private static final String EXISTENT_FILE_IN_SUB_DIR = "thumb/16903390174_1d670a5849_h.thumbnail.jpg";
    private static final String EXISTENT_FILE_IN_SUB_DIR_NO_EXTENSIONS = "16903390174_1d670a5849_h";

    private ImageViewFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new ImageViewFactory();
    }

    @Test(expected = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileDontExists() throws Exception {
        factory.create(INEXISTENT_FILE);
    }

    @Test
    public void shouldCreateImageViewWhenFileExists() throws Exception {
        String file = getClass().getResource(EXISTENT_FILE).getFile();
        ImageView imageView = factory.create(file);
        assertNotNull(imageView);
    }

    @Test
    public void shouldSetIdEqualsFilenameWithoutThumbnailAndJPGExtension() throws Exception {
        String file = getClass().getResource(EXISTENT_FILE).getFile();
        ImageView imageView = factory.create(file);
        assertThat(imageView.getId(), equalTo(EXISTENT_FILE_NO_EXTENSIONS));
    }

    @Test
    public void shouldConsiderOnlyFilenameForId() throws Exception {
        String file = getClass().getResource(EXISTENT_FILE_IN_SUB_DIR).getFile();
        ImageView imageView = factory.create(file);
        assertThat(imageView.getId(), equalTo(EXISTENT_FILE_IN_SUB_DIR_NO_EXTENSIONS));
    }
}