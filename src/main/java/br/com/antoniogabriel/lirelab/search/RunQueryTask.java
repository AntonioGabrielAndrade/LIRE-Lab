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

package br.com.antoniogabriel.lirelab.search;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.CollectionService;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.lire.Feature;
import com.google.common.base.Stopwatch;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;

import java.util.List;

class RunQueryTask extends Task<List<Image>> {

    private final CollectionService service;
    private final Collection collection;
    private final Feature feature;
    private final Image queryImage;
    private Stopwatch stop;

    public RunQueryTask(CollectionService service,
                        Collection collection,
                        Feature feature,
                        Image queryImage) {

        this.service = service;
        this.collection = collection;
        this.feature = feature;
        this.queryImage = queryImage;
    }

    @Override
    protected List<Image> call() throws Exception {
        Stopwatch timer = Stopwatch.createStarted();
        List<Image> images = service.runQuery(collection, feature, queryImage);
        stop = timer.stop();

        return images;
    }

    public void addValueListener(ChangeListener<List<Image>> listener) {
        valueProperty().addListener(listener);
    }

    public boolean hasElapsedTime() {
        return stop != null;
    }

    public String getElapsedTime() {
        return stop.toString();
    }
}
