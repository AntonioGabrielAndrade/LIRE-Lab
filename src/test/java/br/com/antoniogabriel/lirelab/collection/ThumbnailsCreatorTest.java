package br.com.antoniogabriel.lirelab.collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ThumbnailsCreatorTest {

    private static final String IMG1 = "/some/img/path";
    private static final String IMG2 = "/other/img/path";

    private static final String THUMBNAILS_DIR = "/some/dir";

    private static final List<String> IMAGES = Arrays.asList(IMG1, IMG2);

    @Mock private ThumbnailBuilder builder;
    @Mock private ThumbnailsCreatorCallback callback;

    private ThumbnailsCreator creator;
    private InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        setupThumbnailsCreator();
        setupInOrder();
    }

    private void setupThumbnailsCreator() {
        creator = new ThumbnailsCreator(builder, THUMBNAILS_DIR, IMAGES);
        creator.setCallback(callback);
    }

    private void setupInOrder() {
        inOrder = Mockito.inOrder(builder, callback);
    }

    @Test
    public void shouldCreateThumbnails() throws Exception {
        creator.create();

        inOrder.verify(builder).createDirectory(THUMBNAILS_DIR);

        inOrder.verify(callback).beforeCreateThumbnail(1, IMAGES.size(), IMG1);
        inOrder.verify(builder).createThumbnail(IMG1, THUMBNAILS_DIR);
        inOrder.verify(callback).afterCreateThumbnail(1, IMAGES.size(), IMG1);

        inOrder.verify(callback).afterCreateAllThumbnails(IMAGES.size());
    }
}
