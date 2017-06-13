package br.com.antoniogabriel.lirelab.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ThumbnailsCreatorTest {

    private static final String IMG1 = "/some/img/path";
    private static final String IMG2 = "/other/img/path";

    private static final String THUMBNAILS_DIR = "/some/dir";
    private static final String IMAGES_DIR = "/another/dir";

    private static final List<String> IMAGES = Arrays.asList(IMG1, IMG2);

    @Mock private ThumbnailBuilder builder;
    @Mock private ThumbnailsCreatorCallback callback;

    private ThumbnailsCreator creator;

    @Before
    public void setUp() throws Exception {
        creator = new ThumbnailsCreator(builder);

        creator.setThumbnailsDir(THUMBNAILS_DIR);
        creator.setImagesDir(IMAGES_DIR);
        creator.setCallback(callback);
    }

    @Test
    public void shouldCreateThumbnails() throws Exception {

        when(builder.getAllImagePaths(IMAGES_DIR)).thenReturn(IMAGES);

        creator.create();

        verify(builder).createDirectory(THUMBNAILS_DIR);
        verify(builder).getAllImagePaths(IMAGES_DIR);

        verify(callback).beforeCreateThumbnail(1, IMAGES.size(), IMG1);
        verify(builder).createThumbnail(IMG1, THUMBNAILS_DIR);
        verify(callback).afterCreateThumbnail(1, IMAGES.size(), IMG1);

        verify(callback).beforeCreateThumbnail(2, IMAGES.size(), IMG2);
        verify(builder).createThumbnail(IMG1, THUMBNAILS_DIR);
        verify(callback).afterCreateThumbnail(2, IMAGES.size(), IMG2);

        verify(callback).afterCreateAllThumbnails(IMAGES.size());
    }
}
