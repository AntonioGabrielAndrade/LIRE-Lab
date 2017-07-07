package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.index.IndexWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class IndexBuilder {

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
}
