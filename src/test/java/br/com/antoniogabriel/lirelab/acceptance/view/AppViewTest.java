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

package br.com.antoniogabriel.lirelab.acceptance.view;

import br.com.antoniogabriel.lirelab.app.AppFXML;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.test_utilities.FXMLTest;
import com.google.inject.AbstractModule;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.TEST_ROOT;

public class AppViewTest extends FXMLTest<AppFXML> {

    private AppViewObject view;

    @Override
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(PathResolver.class).toInstance(new PathResolver(TEST_ROOT));
            }
        };
    }

    @Override
    protected void configStage(Stage stage) {
        stage.setMaximized(true);
    }

    @Before
    public void setUp() throws Exception {
        view = new AppViewObject();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        view.checkMenus();
        view.checkToolBar();
    }

    @Test
    public void shouldShowAboutInformation() throws Exception {
        view.openAboutDialog();
        view.checkAboutDialog();
    }
}
