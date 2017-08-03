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

package net.lirelab.lire;

import net.lirelab.collection.Image;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IndexSearcherTest {

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
    @Mock private IndexSearcherCallback callback;

    private List<Image> images = new ArrayList<Image>();
    private Feature feature = Feature.CEDD;
    private String[] values = {IMG_PATH, IMG_PATH};

    private LireIndexSearcher indexSearcher;

    @Before
    public void setUp() throws Exception {
        images.add(IMAGE1);
        images.add(IMAGE2);

        indexSearcher = new LireIndexSearcher(lire, callback);
        setupExpectations();
    }

    private void setupExpectations() throws IOException {
        given(lire.getBufferedImage(IMG_PATH)).willReturn(bufImg);
        given(lire.createIndexReader(INDEX_DIR)).willReturn(indexReader);
        given(lire.createImageSearcher(images.size(), feature.getLireClass()))
                .willReturn(imageSearcher);
        given(imageSearcher.search(bufImg, indexReader))
                .willReturn(hits);

        given(hits.length()).willReturn(2);

        given(hits.documentID(0)).willReturn(0);
        given(hits.documentID(1)).willReturn(1);

        given(hits.score(0)).willReturn(0.0);
        given(hits.score(1)).willReturn(1.0);

        given(indexReader.document(0)).willReturn(doc1);
        given(indexReader.document(1)).willReturn(doc2);

        given(doc1.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER))
                .willReturn(values);
        given(doc2.getValues(DocumentBuilder.FIELD_NAME_IDENTIFIER))
                .willReturn(values);
    }

    @Test
    public void shouldPassDocumentPositionsToCallback() throws Exception {
        indexSearcher.search(IMG_PATH, INDEX_DIR, -1, feature.getLireClass(), 2);

        verify(callback).imageSearched(IMG_PATH, 0, 0.0);
        verify(callback).imageSearched(IMG_PATH, 1, 1.0);
    }
}
