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

package br.com.antoniogabriel.lirelab.custom.paginated_collection_grid;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.DialogProvider;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.custom.collection_grid.DisplayImageDialogHandler;
import br.com.antoniogabriel.lirelab.custom.collection_grid.ImageClickHandler;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Pagination;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PaginatedCollectionGrid extends BorderPane {

    private static final String PAGINATED_COLLECTION_GRID_FXML = "paginated-collection-grid.fxml";
    private static final int DEFAULT_PAGE_SIZE = 120;

    public static final String PAGINATION_HIDDEN = "pagination-hidden";

    private PageFactoryProvider pageFactoryProvider = new PageFactoryProvider();

    @FXML private Pagination pagination;
    @FXML private Spinner<Integer> pageSizeSpinner;
    @FXML private Slider gridGapSlider;
    @FXML private Slider imageHeightSlider;

    private List<Image> images = new ArrayList<>();
    private ImageClickHandler handler = (image, event) -> {};

    public PaginatedCollectionGrid() {
        loadFXML();
    }

    private void loadFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PAGINATED_COLLECTION_GRID_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        setupPageSizeSpinner();
    }

    private void setupPageSizeSpinner() {
        pageSizeSpinner.getValueFactory().setValue(DEFAULT_PAGE_SIZE);

        pageSizeSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(!images.isEmpty())
                setCollection(images, handler);
        });
    }

    public void setPageSize(int pageSize) {
        pageSizeSpinner.getValueFactory().setValue(pageSize);
    }

    public int getPageSize() {
        return pageSizeSpinner.getValue();
    }

    public void clear() {
        pagination.setPageFactory(null);
    }

    public void setCollection(Collection collection) throws IOException {
        setCollection(collection, new DisplayImageDialogHandler(new DialogProvider(), new FileUtils()));
    }

    public void setCollection(Collection collection, ImageClickHandler handler) {
        setCollection(collection.getImages(), handler);
    }

    public void setCollection(List<Image> images, ImageClickHandler handler) {
        this.images = images;
        this.handler = handler;
        setupMaxPageSize(images);

        calcPageCount(images, getPageSize());
        setPageFactory(images, getPageSize(), handler, gridGapSlider.valueProperty(), imageHeightSlider.valueProperty());
    }

    private void setupMaxPageSize(List<Image> images) {
        ((SpinnerValueFactory.IntegerSpinnerValueFactory)pageSizeSpinner.getValueFactory()).setMax(images.size());
    }

    private void calcPageCount(List<Image> images, int pageSize) {
        int numberOfImages = images.size();
        int pageCount = divideAndGetCeil(numberOfImages, pageSize);

        pagination.setPageCount(pageCount);
        setControlVisibility(pageCount);
    }

    private int divideAndGetCeil(int a, int b) {
        return a / b + ((a % b == 0) ? 0 : 1);
    }

    private void setControlVisibility(int pageCount) {
        if(pageCount == 1 && !pagination.getStyleClass().contains(PAGINATION_HIDDEN)) {
            pagination.getStyleClass().add(PAGINATION_HIDDEN);
        } else if (pageCount > 1) {
            pagination.getStyleClass().remove(PAGINATION_HIDDEN);
        }
    }

    private void setPageFactory(List<Image> images, int pageSize, ImageClickHandler handler, DoubleProperty gridGap, DoubleProperty imageHeight) {
        pagination.setPageFactory(
                pageFactoryProvider.getPageFactory(images, pageSize, handler, gridGap, imageHeight)
        );
    }
}
