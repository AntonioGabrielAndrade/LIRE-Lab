package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

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

    @Test
    public void shouldCreateCollectionStepByStep() throws Exception {
        IndexCreator creator = new IndexCreator(indexBuilder);

        creator.setFeatures(FEATURES);
        creator.setIndexDir(INDEX_DIR);
        creator.setImagesDir(IMAGES_DIR);
        creator.setCallback(callback);

        when(indexBuilder.createDocumentBuilder()).thenReturn(docBuilder);

        when(indexBuilder.createIndexWriter(INDEX_DIR)).thenReturn(indexWriter);
        when(indexBuilder.getAllImagesPaths(IMAGES_DIR)).thenReturn(PATHS);

        when(indexBuilder.getBufferedImage(IMG1)).thenReturn(bufImg1);
        when(indexBuilder.createDocument(bufImg1, IMG1)).thenReturn(DOC1);

        when(indexBuilder.getBufferedImage(IMG2)).thenReturn(bufImg2);
        when(indexBuilder.createDocument(bufImg2, IMG2)).thenReturn(DOC2);

        creator.create();

        InOrder inOrder = Mockito.inOrder(indexBuilder, callback);

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
