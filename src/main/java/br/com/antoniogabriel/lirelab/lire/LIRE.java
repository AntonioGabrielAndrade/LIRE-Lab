/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
