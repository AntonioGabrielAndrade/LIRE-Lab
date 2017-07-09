package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndexCreatorTest {

    private static final String IMG1 = "path1";
    private static final String IMG2 = "path2";

    private static final Document DOC1 = new Document();
    private static final Document DOC2 = new Document();

    private static final List<Feature> FEATURES = Arrays.asList(Feature.CEDD, Feature.TAMURA);
    private static final List<String> PATHS = Arrays.asList(IMG1, IMG2);

    private static final String INDEX_DIR = "/some/index/dir";
    private static final String IMAGES_DIR = "/some/images/dir";

    @Mock private LIRE lire;
    @Mock private IndexWriter indexWriter;
    @Mock private GlobalDocumentBuilder docBuilder;
    @Mock private BufferedImage bufImg1;
    @Mock private BufferedImage bufImg2;
    @Mock private IndexCreatorCallback callback;

    private IndexCreator creator;
    private InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        setupIndexCreator();
        setupInOrder();
        setupExpectations();
    }

    private void setupIndexCreator() {
        creator = new IndexCreator(lire, IMAGES_DIR, INDEX_DIR, FEATURES);
        creator.setCallback(callback);
    }

    private void setupInOrder() {
        inOrder = Mockito.inOrder(lire, callback, indexWriter);
    }

    private void setupExpectations() throws IOException {
        given(lire.createDocumentBuilder()).willReturn(docBuilder);
        given(lire.createIndexWriter(INDEX_DIR)).willReturn(indexWriter);
        given(lire.getAllImagesPaths(IMAGES_DIR)).willReturn(PATHS);

        given(lire.getBufferedImage(IMG1)).willReturn(bufImg1);
        given(docBuilder.createDocument(bufImg1, IMG1)).willReturn(DOC1);

        given(lire.getBufferedImage(IMG2)).willReturn(bufImg2);
        given(docBuilder.createDocument(bufImg2, IMG2)).willReturn(DOC2);
    }

    @Test
    public void shouldAddFeaturesToDocumentBuilder() throws Exception {
        creator.create();

        for (Feature feature : FEATURES) {
            verify(docBuilder).addExtractor(feature.getLireClass());
        }
    }

    @Test
    public void shouldAddImagesToIndexAndCloseIndex() throws Exception {
        creator.create();

        inOrder.verify(indexWriter).addDocument(DOC1);
        inOrder.verify(indexWriter).addDocument(DOC2);
        inOrder.verify(lire).closeIndexWriter(indexWriter);
    }

}
