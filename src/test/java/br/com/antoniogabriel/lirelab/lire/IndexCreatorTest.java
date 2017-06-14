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
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

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

    @Mock private IndexBuilder indexBuilder;
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
        setupExpectationsForIndexBuilder();
    }

    private void setupIndexCreator() {
        creator = new IndexCreator(indexBuilder, IMAGES_DIR, INDEX_DIR, FEATURES);
        creator.setCallback(callback);
    }

    private void setupInOrder() {
        inOrder = Mockito.inOrder(indexBuilder, callback);
    }

    private void setupExpectationsForIndexBuilder() throws IOException {
        given(indexBuilder.createDocumentBuilder()).willReturn(docBuilder);

        given(indexBuilder.createIndexWriter(INDEX_DIR)).willReturn(indexWriter);
        given(indexBuilder.getAllImagesPaths(IMAGES_DIR)).willReturn(PATHS);

        given(indexBuilder.getBufferedImage(IMG1)).willReturn(bufImg1);
        given(indexBuilder.createDocument(bufImg1, IMG1)).willReturn(DOC1);

        given(indexBuilder.getBufferedImage(IMG2)).willReturn(bufImg2);
        given(indexBuilder.createDocument(bufImg2, IMG2)).willReturn(DOC2);
    }

    @Test
    public void shouldCreateIndexStepByStep() throws Exception {
        creator.create();

        inOrder.verify(indexBuilder).createDocumentBuilder();
        inOrder.verify(indexBuilder).addFeaturesToDocumentBuilder(FEATURES);

        inOrder.verify(indexBuilder).createIndexWriter(INDEX_DIR);
        inOrder.verify(indexBuilder).getAllImagesPaths(IMAGES_DIR);

        inOrder.verify(callback).beforeAddImageToIndex(1, PATHS.size(), IMG1);
        inOrder.verify(indexBuilder).getBufferedImage(IMG1);
        inOrder.verify(indexBuilder).createDocument(bufImg1, IMG1);
        inOrder.verify(indexBuilder).addDocument(DOC1);
        inOrder.verify(callback).afterAddImageToIndex(1, PATHS.size(), IMG1);

        inOrder.verify(callback).beforeAddImageToIndex(2, PATHS.size(), IMG2);
        inOrder.verify(indexBuilder).getBufferedImage(IMG2);
        inOrder.verify(indexBuilder).createDocument(bufImg2, IMG2);
        inOrder.verify(indexBuilder).addDocument(DOC2);
        inOrder.verify(callback).afterAddImageToIndex(2, PATHS.size(), IMG2);

        inOrder.verify(indexBuilder).closeIndexWriter();
        inOrder.verify(callback).afterIndexAllImages(PATHS.size());
    }
}
