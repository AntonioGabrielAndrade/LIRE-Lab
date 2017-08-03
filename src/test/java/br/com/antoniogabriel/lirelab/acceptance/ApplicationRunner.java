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

import static br.com.antoniogabriel.lirelab.test_utilities.TestConstants.*;


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
                .checkProgressMarks(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                //creating thumbnails
                .checkProgressMarks(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
                .checkOkIsEnabledWhenFinish()
                .ok();

        collectionHelper.checkCollectionExists(collectionName);

        appView.checkCollectionIsListed(collectionName);
    }

    public void viewCollection(String collection) throws TimeoutException, InterruptedException {
        CollectionTreeViewObject listView = new CollectionTreeViewObject();
        CollectionGridViewObject gridView = new CollectionGridViewObject();

        listView.selectCollection(collection);

        gridView.checkImagesAreVisible( IMAGE1,
                                        IMAGE2,
                                        IMAGE3,
                                        IMAGE4,
                                        IMAGE5,
                                        IMAGE6,
                                        IMAGE7,
                                        IMAGE8,
                                        IMAGE9,
                                        IMAGE10);


        ImageDialogViewObject imageView = gridView.selectImage(IMAGE1);

        imageView.checkImageIsDisplayed(IMAGE1);

        imageView.close();
    }

    public void searchCollection(String collection) throws TimeoutException {
        AppViewObject appView = new AppViewObject();

        appView.selectCollectionToRun(collection);

        SearchViewObject searchView = appView.search();

        searchView.checkImagesAreVisible( IMAGE1,
                                            IMAGE2,
                                            IMAGE3,
                                            IMAGE4,
                                            IMAGE5,
                                            IMAGE6,
                                            IMAGE7,
                                            IMAGE8,
                                            IMAGE9,
                                            IMAGE10);

        searchView.selectQuery(IMAGE1);

        searchView.waitUntilShowQuery(IMAGE1);

        searchView.waitUntilImagesAreOrderedLike(IMAGE1,
                                                    IMAGE4,
                                                    IMAGE10,
                                                    IMAGE8,
                                                    IMAGE5,
                                                    IMAGE9,
                                                    IMAGE2,
                                                    IMAGE3,
                                                    IMAGE7,
                                                    IMAGE6);
    }
}
