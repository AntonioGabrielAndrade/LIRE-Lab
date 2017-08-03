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

package net.lirelab.acceptance.view;

import net.lirelab.collection.CreateCollectionFXML;
import net.lirelab.test_utilities.FXMLTest;
import net.lirelab.lire.Feature;
import org.junit.Before;
import org.junit.Test;

import static net.lirelab.lire.Feature.CEDD;
import static net.lirelab.lire.Feature.TAMURA;

public class CreateCollectionViewTest extends FXMLTest<CreateCollectionFXML> {

    private static final String EMPTY = "";
    private static final String ANY_NAME = "Some Name";
    private static final String ANY_PATH = "/some/directory/with/images";
    private static final Feature[] ANY_FEATURES = {CEDD, TAMURA};

    private CreateCollectionViewObject view;

    @Before
    public void setUp() throws Exception {
        view = new CreateCollectionViewObject();
    }

    @Test
    public void shouldCloseWhenCancel() throws Exception {
        view.cancel();
        view.checkWindowIsClosed();
    }

    @Test
    public void shouldDisableCreateWhenNameIsEmpty() throws Exception {
        view.unselectAllFeatures();
        view.writeName(EMPTY);
        view.writeImagesDirectory(ANY_PATH);
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenImagesDirectoryIsEmpty() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(EMPTY);
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldDisableCreateWhenNoFeatureIsSelected() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(ANY_PATH);
        view.checkCreateIsDisabled();
    }

    @Test
    public void shouldEnableCreateWhenAllDataIsInformed() throws Exception {
        view.unselectAllFeatures();
        view.writeName(ANY_NAME);
        view.writeImagesDirectory(ANY_PATH);
        view.selectFeatures(ANY_FEATURES);
        view.checkCreateIsEnabled();
    }

}
