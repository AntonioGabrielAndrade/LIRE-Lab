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

package br.com.antoniogabriel.lirelab.acceptance;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.lire.Feature.CEDD;
import static br.com.antoniogabriel.lirelab.lire.Feature.TAMURA;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_IMAGES;
import static br.com.antoniogabriel.lirelab.test_utilities.TestPaths.TEST_ROOT;

public class LireLabAcceptanceTest extends FXMLTest<AppFXML> {

    private static final String ACCEPTANCE_TEST_COLLECTION = "Acceptance_Test_Collection";

    private PathResolver resolver = new PathResolver(TEST_ROOT);
    private CollectionTestHelper collectionHelper = new CollectionTestHelper(resolver);
    private ApplicationRunner app = new ApplicationRunner(collectionHelper);

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(resolver);
            }
        };
    }

    @After
    public void cleanEnvironment() throws Exception {
        collectionHelper.deleteCollection(ACCEPTANCE_TEST_COLLECTION);
    }

    @Test
    public void userJourneyTest() throws Exception {
        app.createCollection(ACCEPTANCE_TEST_COLLECTION, TEST_IMAGES, CEDD, TAMURA);
        app.viewCollection(ACCEPTANCE_TEST_COLLECTION);
        app.searchCollection(ACCEPTANCE_TEST_COLLECTION);
    }
}
