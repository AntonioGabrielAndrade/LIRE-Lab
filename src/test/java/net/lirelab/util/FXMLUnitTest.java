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

package net.lirelab.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FXMLUnitTest {

    @Mock private Window window;
    @Mock private Stage stage;
    @Mock private Scene scene;
    @Mock private FXMLLoader loader;
    @Mock private Object controller;

    private FXML fxml;

    @Before
    public void setUp() throws Exception {
        fxml = new TestableFXML();
    }

    @Test
    public void shouldLoadInStageOwnedByGivenWindow() throws Exception {
        fxml.loadOwnedBy(window);

        verify(stage).initOwner(window);
    }

    @Test
    public void shouldSetStageAsModalWhenStageHasOwner() throws Exception {
        given(stage.getOwner()).willReturn(window);

        fxml.loadOwnedBy(window);

        verify(stage).initModality(Modality.WINDOW_MODAL);
    }

    @Test
    public void shouldResetLoaderBeforeLoadFXML() throws Exception {
        fxml.loadIn(stage);

        InOrder inOrder = Mockito.inOrder(loader);

        inOrder.verify(loader).setRoot(null);
        inOrder.verify(loader).setController(null);
        inOrder.verify(loader).load();
    }

    @Test
    public void shouldLoadController() throws Exception {
        given(loader.getController()).willReturn(controller);

        fxml.loadIn(stage);

        assertThat(fxml.getController(), equalTo(controller));
    }

    private class TestableFXML extends FXML {

        public TestableFXML() {
            super(loader);
        }

        @Override
        public String getFXMLResourceName() {
            return "";
        }

        @Override
        protected Stage createStage() {
            return stage;
        }

        @Override
        protected Scene createScene(Parent root) {
            return scene;
        }
    }
}