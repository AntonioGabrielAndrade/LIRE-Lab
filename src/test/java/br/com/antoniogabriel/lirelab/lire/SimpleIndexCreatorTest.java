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
public class SimpleIndexCreatorTest {

    private static final String IMG1 = "path1";
    private static final String IMG2 = "path2";

    private static final Document DOC1 = new Document();
    private static final Document DOC2 = new Document();

    private static final List<Feature> FEATURES = Arrays.asList(Feature.CEDD, Feature.TAMURA);
    private static final List<String> PATHS = Arrays.asList(IMG1, IMG2);

    private static final String INDEX_DIR = "/some/index/dir";

    @Mock private LIRE lire;
    @Mock private IndexWriter indexWriter;
    @Mock private GlobalDocumentBuilder docBuilder;
    @Mock private BufferedImage bufImg1;
    @Mock private BufferedImage bufImg2;
    @Mock private IndexCreatorCallback callback;

    private SimpleIndexCreator creator;
    private InOrder inOrder;

    @Before
    public void setUp() throws Exception {
        setupIndexCreator();
        setupInOrder();
        setupExpectations();
    }

    private void setupIndexCreator() {
        creator = new SimpleIndexCreator(lire, INDEX_DIR, FEATURES, PATHS);
        creator.setCallback(callback);
    }

    private void setupInOrder() {
        inOrder = Mockito.inOrder(lire, callback, indexWriter);
    }

    private void setupExpectations() throws IOException {
        given(lire.createDocumentBuilder()).willReturn(docBuilder);
        given(lire.createIndexWriter(INDEX_DIR)).willReturn(indexWriter);

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
