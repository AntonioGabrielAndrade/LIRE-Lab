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

import br.com.antoniogabriel.lirelab.acceptance.custom.*;
import br.com.antoniogabriel.lirelab.acceptance.view.AppViewObject;
import br.com.antoniogabriel.lirelab.acceptance.view.CreateCollectionViewObject;
import br.com.antoniogabriel.lirelab.acceptance.view.SearchViewObject;
import br.com.antoniogabriel.lirelab.lire.Feature;

import java.util.concurrent.TimeoutException;


public class ApplicationRunner {

    private CollectionTestHelper collectionHelper;

    public ApplicationRunner(CollectionTestHelper collectionHelper) {
        this.collectionHelper = collectionHelper;
    }

    public void createCollection(String collectionName,
                                 String imagesDirectory,
                                 Feature... features) throws TimeoutException {

        AppViewObject appView = new AppViewObject();
        CreateCollectionViewObject createView = appView.createCollection();


        createView.waitUntilWindowIsShowing();
        createView
                .writeName(collectionName)
                .writeImagesDirectory(imagesDirectory)
                .selectFeatures(features);

        ProgressDialogViewObject progressView = createView.create();

        progressView
                //indexing images
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                //creating thumbnails
                .checkProgressMark(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                .checkOkIsEnabledWhenFinish()
                .ok();

        collectionHelper.checkCollectionExists(collectionName);

        appView.checkCollectionIsListed(collectionName);
    }

    public void viewCollection(String collection) throws TimeoutException, InterruptedException {
        CollectionTreeViewObject listView = new CollectionTreeViewObject();
        CollectionGridViewObject gridView = new CollectionGridViewObject();

        listView.selectCollection(collection);

        gridView.checkImagesAreVisible( "14474347006_99aa0fd981_k",
                                        "16903390174_1d670a5849_h",
                                        "17099294578_0ba4068bad_k",
                                        "17338370170_1e620bfb18_h",
                                        "17525978165_86dc26e8cb_h",
                                        "19774866363_757555901c_k",
                                        "25601366680_b57441bb52_k",
                                        "25601374660_78e6a9bba8_k",
                                        "26487616294_b22b87133e_k",
                                        "26489383923_98d419eb0d_k");


        ImageDialogViewObject imageView = gridView.selectImage("14474347006_99aa0fd981_k");

        imageView.checkImageIsDisplayed("14474347006_99aa0fd981_k");

        imageView.close();
    }

    public void searchCollection(String collection) throws TimeoutException {
        AppViewObject appView = new AppViewObject();

        appView.selectCollectionToRun(collection);

        SearchViewObject searchView = appView.search();

        searchView.checkImagesAreVisible( "14474347006_99aa0fd981_k",
                                            "16903390174_1d670a5849_h",
                                            "17099294578_0ba4068bad_k",
                                            "17338370170_1e620bfb18_h",
                                            "17525978165_86dc26e8cb_h",
                                            "19774866363_757555901c_k",
                                            "25601366680_b57441bb52_k",
                                            "25601374660_78e6a9bba8_k",
                                            "26487616294_b22b87133e_k",
                                            "26489383923_98d419eb0d_k");

        searchView.selectQuery("14474347006_99aa0fd981_k");

        searchView.waitUntilShowQuery("14474347006_99aa0fd981_k");

        searchView.waitUntilImagesAreOrderedLike("14474347006_99aa0fd981_k",
                                                    "17338370170_1e620bfb18_h",
                                                    "26489383923_98d419eb0d_k",
                                                    "25601374660_78e6a9bba8_k",
                                                    "17525978165_86dc26e8cb_h",
                                                    "26487616294_b22b87133e_k",
                                                    "16903390174_1d670a5849_h",
                                                    "17099294578_0ba4068bad_k",
                                                    "25601366680_b57441bb52_k",
                                                    "19774866363_757555901c_k");
    }
}
