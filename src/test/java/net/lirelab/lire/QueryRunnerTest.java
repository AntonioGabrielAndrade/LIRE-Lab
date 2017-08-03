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

import net.lirelab.collection.Collection;
import net.lirelab.collection.Image;
import net.lirelab.collection.PathResolver;
import net.lirelab.util.CollectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerTest {

    private static final String IMG_PATH = "/img/path";
    private static final String THUMB_PATH = "/thumb/path";

    private static final String INDEX_DIR = "/some/dir";

    private static final Image IMAGE1 = new Image(IMG_PATH, THUMB_PATH);
    private static final Image IMAGE2 = new Image(IMG_PATH, THUMB_PATH);

    @Mock private LIRE lire;
    @Mock private CollectionUtils collectionUtils;
    @Mock private PathResolver resolver;
    @Mock private LireIndexSearcher lireIndexSearcher;
    @Mock private ImagesSearchedCallback callback;

    private List<Image> images = new ArrayList<Image>();
    private Feature feature = Feature.CEDD;
    private Collection collection = new Collection("Collection");
    private Image queryImage = new Image(IMG_PATH, THUMB_PATH);

    private QueryRunner queryRunner;

    @Before
    public void setUp() throws Exception {
        queryRunner = new TestableQueryRunner();
        images.add(IMAGE1);
        images.add(IMAGE2);
        collection.setImages(images);
        setupExpectations();
    }

    private void setupExpectations() throws IOException {
        given(resolver.getIndexDirectoryPath(collection.getName()))
                .willReturn(INDEX_DIR);

        given(callback.getImages()).willReturn(images);
    }

    @Test
    public void shouldRunQuery() throws Exception {
        List<Image> result = queryRunner.runQuery(collection, feature, queryImage);

        verify(lireIndexSearcher).search(IMG_PATH, INDEX_DIR, -1, feature.getLireClass(), 2);
        assertThat(result, is(images));
    }

    private class TestableQueryRunner extends QueryRunner {
        public TestableQueryRunner() {
            super(resolver, lire, collectionUtils);
        }

        @Override
        protected LireIndexSearcher createIndexSearcher(ImagesSearchedCallback callback) {
            return lireIndexSearcher;
        }

        @Override
        protected ImagesSearchedCallback createSearcherCallback(Collection collection) {
            return callback;
        }
    }
}