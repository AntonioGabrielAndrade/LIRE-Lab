package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.indexers.parallel.ParallelIndexer;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class LIRE {

    public GlobalDocumentBuilder createDocumentBuilder() {
        return new GlobalDocumentBuilder(false);
    }

    public IndexWriter createIndexWriter(String indexDir) throws IOException {
        return LuceneUtils.createIndexWriter(indexDir, true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
    }

    public BufferedImage getBufferedImage(String imgPath) throws IOException {
        return ImageIO.read(new FileInputStream(imgPath));
    }

    public void closeIndexWriter(IndexWriter indexWriter) throws IOException {
        LuceneUtils.closeWriter(indexWriter);
    }

    public IndexReader createIndexReader(String indexDir) throws IOException {
        return DirectoryReader.open(FSDirectory.open(Paths.get(indexDir)));
    }

    public ParallelIndexer createParallelIndexer(int numberOfThreads, String indexDir, String imagesDir) {
        return new ParallelIndexer(numberOfThreads, indexDir, imagesDir);
    }

    public GenericFastImageSearcher createImageSearcher(int maxHits, Class<? extends GlobalFeature> globalFeature) {
        return new GenericFastImageSearcher(maxHits, globalFeature);
    }
}
