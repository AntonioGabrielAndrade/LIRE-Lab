package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.searchers.GenericFastImageSearcher;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class LIRE {

    public GlobalDocumentBuilder createDocumentBuilder() {
        return new GlobalDocumentBuilder(false);
    }

    public IndexWriter createIndexWriter(String indexDir) throws IOException {
        return LuceneUtils.createIndexWriter(indexDir, true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
    }

    public List<String> getAllImagesPaths(String imagesDir) throws IOException {
        return FileUtils.getAllImages(new File(imagesDir), true);
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

    public GenericFastImageSearcher createImageSearcher(int maxHits, Class<? extends GlobalFeature> globalFeature) {
        return new GenericFastImageSearcher(maxHits, globalFeature);
    }
}
