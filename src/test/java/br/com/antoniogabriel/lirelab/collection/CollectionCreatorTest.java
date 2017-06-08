package br.com.antoniogabriel.lirelab.collection;

import br.com.antoniogabriel.lirelab.main.Feature;
import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CollectionCreatorTest {

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
    @Mock private CreateCollectionCallback callback;

    @Test
    public void shouldCreateCollectionStepByStep() throws Exception {
        CollectionCreator creator = new CollectionCreator(indexBuilder);

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

        verify(indexBuilder).createDocumentBuilder();
        verify(indexBuilder).addFeaturesToDocumentBuilder(FEATURES);

        verify(indexBuilder).createIndexWriter(INDEX_DIR);
        verify(indexBuilder).getAllImagesPaths(IMAGES_DIR);

        verify(callback).beforeAddImageToIndex(1, PATHS.size(), IMG1);
        verify(indexBuilder).getBufferedImage(IMG1);
        verify(indexBuilder).createDocument(bufImg1, IMG1);
        verify(indexBuilder).addDocument(DOC1);
        verify(callback).afterAddImageToIndex(1, PATHS.size(), IMG1);

        verify(callback).beforeAddImageToIndex(2, PATHS.size(), IMG2);
        verify(indexBuilder).getBufferedImage(IMG2);
        verify(indexBuilder).createDocument(bufImg2, IMG2);
        verify(indexBuilder).addDocument(DOC2);
        verify(callback).afterAddImageToIndex(2, PATHS.size(), IMG2);

        verify(indexBuilder).closeIndexWriter();
        verify(callback).afterIndexAllImages(PATHS.size());
    }
}
