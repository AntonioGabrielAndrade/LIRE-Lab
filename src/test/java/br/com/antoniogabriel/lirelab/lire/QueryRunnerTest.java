package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;
import net.semanticmetadata.lire.builders.DocumentBuilder;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.searchers.ImageSearchHits;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerTest {

    private static final String IMG_PATH = "/img/path";
    private static final String THUMB_PATH = "/thumb/path";

    private static final String INDEX_DIR = "/some/dir";

    private static final Image IMAGE1 = new Image(IMG_PATH, THUMB_PATH);
    private static final Image IMAGE2 = new Image(IMG_PATH, THUMB_PATH);

    @Mock private LIRE lire;
    @Mock private BufferedImage bufImg;
    @Mock private IndexReader indexReader;
    @Mock private GenericFastImageSearcher imageSearcher;
    @Mock private ImageSearchHits hits;
    @Mock private Document doc1;
    @Mock private Document doc2;
    @Mock private CollectionUtils collectionUtils;
    @Mock private PathResolver resolver;

    private List<Image> images = new ArrayList<Image>();
    private Feature feature = CEDD;
    private Collection collection = new Collection("Collection");
    private Image queryImage = new Image(IMG_PATH, THUMB_PATH);
    private String[] values = {IMG_PATH, IMG_PATH};

    private QueryRunner queryRunner;

    @Before
    public void setUp() throws Exception {
        images.add(IMAGE1);
        images.add(IMAGE2);

        queryRunner = new QueryRunner(resolver, lire, collectionUtils);
        collection.setImages(images);
        setupExpectations();
    }

    private void setupExpectations() throws IOException {
        given(resolver.getIndexDirectoryPath(collection.getName()))
                .willReturn(INDEX_DIR);
        given(lire.getBufferedImage(IMG_PATH)).willReturn(bufImg);
        given(lire.createIndexReader(INDEX_DIR)).willReturn(indexReader);
        given(lire.createImageSearcher(images.size(), feature.getLireClass()))
                .willReturn(imageSearcher);
        given(imageSearcher.search(bufImg, indexReader))
                .willReturn(hits);

        given(hits.length()).willReturn(2);

        given(hits.documentID(0)).willReturn(0);
        given(hits.documentID(1)).willReturn(1);

        given(indexReader.document(0)).willReturn(doc1);
        given(indexReader.document(1)).willReturn(doc2);

        given(doc1.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER))
                .willReturn(values);
        given(doc2.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER))
                .willReturn(values);

        given(collectionUtils.getThumbnailPathFromImagePath(collection, IMG_PATH))
                .willReturn(THUMB_PATH);
    }

    @Test
    public void shouldRunQueryStepByStep() throws Exception {
        Collection result = queryRunner.runQuery(collection, feature, queryImage);

        assertThat(result.getImages(), is(images));
    }
}