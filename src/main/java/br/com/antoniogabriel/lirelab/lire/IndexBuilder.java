package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.utils.FileUtils;
import net.semanticmetadata.lire.utils.LuceneUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class IndexBuilder {
    private GlobalDocumentBuilder documentBuilder;
    private IndexWriter indexWriter;

    public GlobalDocumentBuilder createDocumentBuilder() {
        this.documentBuilder = new GlobalDocumentBuilder(false);
        return documentBuilder;
    }

    public void addFeaturesToDocumentBuilder(List<Feature> features) {
        for (Feature feature : features) {
            documentBuilder.addExtractor(feature.getLireClass());
        }
    }

    public IndexWriter createIndexWriter(String indexDir) throws IOException {
        indexWriter = LuceneUtils.createIndexWriter(indexDir, true, LuceneUtils.AnalyzerType.WhitespaceAnalyzer);
        return indexWriter;
    }

    public List<String> getAllImagesPaths(String imagesDir) throws IOException {
        return FileUtils.getAllImages(new File(imagesDir), true);
    }

    public BufferedImage getBufferedImage(String imgPath) throws IOException {
        return ImageIO.read(new FileInputStream(imgPath));
    }

    public Document createDocument(BufferedImage bufferedImage, String imgPath) {
        return documentBuilder.createDocument(bufferedImage, imgPath);
    }

    public void closeIndexWriter() throws IOException {
        LuceneUtils.closeWriter(indexWriter);
    }

    public void addDocument(Document document) throws IOException {
        indexWriter.addDocument(document);
    }
}
