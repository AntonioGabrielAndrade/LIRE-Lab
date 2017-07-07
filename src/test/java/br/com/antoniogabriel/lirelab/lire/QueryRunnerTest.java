package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerTest {

    private static final String IMG_PATH = "/img/path";
    private static final String THUMB_PATH = "/thumb/path";

    private static final String INDEX_DIR = "/some/dir";

    private static final Image IMAGE1 = new Image(IMG_PATH, THUMB_PATH);
    private static final Image IMAGE2 = new Image(IMG_PATH, THUMB_PATH);

    @Mock private LIRE lire;
    @Mock private CollectionUtils collectionUtils;
    @Mock private PathResolver resolver;
    @Mock private LireIndexSearcher lireIndexSearcher;
    @Mock private QueryRunner.ImagesSearchedCallback callback;

    private List<Image> images = new ArrayList<Image>();
    private Feature feature = CEDD;
    private Collection collection = new Collection("Collection");
    private Image queryImage = new Image(IMG_PATH, THUMB_PATH);

    private QueryRunner queryRunner;

    @Before
    public void setUp() throws Exception {
        images.add(IMAGE1);
        images.add(IMAGE2);

        queryRunner = new TestableQueryRunner();
        collection.setImages(images);
        setupExpectations();
    }

    private void setupExpectations() throws IOException {
        given(resolver.getIndexDirectoryPath(collection.getName()))
                .willReturn(INDEX_DIR);

        given(callback.getImages()).willReturn(images);
    }

    @Test
    public void shouldRunQueryStepByStep() throws Exception {
        Collection result = queryRunner.runQuery(collection, feature, queryImage);

        verify(lireIndexSearcher).search(IMG_PATH, INDEX_DIR, feature.getLireClass(), 2);
        assertThat(result.getImages(), is(images));
    }

    private class TestableQueryRunner extends QueryRunner {
        public TestableQueryRunner() {
            super(resolver, lire, collectionUtils);
        }

        @Override
        protected LireIndexSearcher createIndexSearcher(ImagesSearchedCallback callback) {
            return lireIndexSearcher;
        }

        @Override
        protected ImagesSearchedCallback createSearcherCallback(Collection collection) {
            return callback;
        }
    }
}