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

package net.lirelab.collection;

import net.lirelab.lire.IndexCreatorCallback;
import javafx.concurrent.Task;

import java.nio.file.Paths;

public class CreateCollectionTask extends Task<Void> implements IndexCreatorCallback, ThumbnailsCreatorCallback, XMLCreatorCallback {

    private CreateCollectionRunner runner;

    public CreateCollectionTask(CreateCollectionRunner runner) {
        this.runner = runner;

        updateTitle("Create Collection...");
        runner.setIndexCreatorCallback(this);
        runner.setThumbnailsCreatorCallback(this);
        runner.setXmlCreatorCallback(this);
    }

    @Override
    protected Void call() throws Exception {
        runner.run();
        return null;
    }

    @Override
    public void beforeIndexImages() {
        checkCancel();
        updateTitle("Step 1: Create index");
        updateMessage("Indexing all images...");
    }

    @Override
    public void beforeAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        checkCancel();
        updateTitle("Step 1: Create index");
        updateMessage("Indexing image: " + Paths.get(imageFilePath).getFileName().toString() + "...");
    }

    @Override
    public void afterAddImageToIndex(int currentImage, int totalImages, String imageFilePath) {
        checkCancel();
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterIndexAllImages(int totalImages) {
        checkCancel();
        updateMessage("Indexing complete!");
    }

    @Override
    public void updatePercentage(double percentageDone) {
        checkCancel();
        updateProgress(percentageDone, 1.0);
    }

    @Override
    public void beforeCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        checkCancel();
        updateTitle("Step 2: Create thumbnails");
        updateMessage("Creating thumbnail for: " + Paths.get(imagePath).getFileName().toString());
    }

    @Override
    public void afterCreateThumbnail(int currentImage, int totalImages, String imagePath) {
        checkCancel();
        updateProgress(currentImage, totalImages);
    }

    @Override
    public void afterCreateAllThumbnails(int totalImages) {
        checkCancel();
        updateMessage("Done!");
    }

    @Override
    public void beforeCreateXML() {
        checkCancel();
        updateProgress(-1,1);
        updateMessage("Creating collection.xml...");
    }

    @Override
    public void afterCreateXML() {
        checkCancel();
        updateTitle("Completed");
        updateMessage("Collection Created!");
        updateProgress(1,1);
    }

    private void checkCancel() {
        if(isCancelled())
            throw new RuntimeException("Task was cancelled");
    }
}
