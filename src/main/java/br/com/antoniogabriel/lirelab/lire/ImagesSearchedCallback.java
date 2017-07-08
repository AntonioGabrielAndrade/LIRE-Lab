package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

class ImagesSearchedCallback implements IndexSearcherCallback {

    private CollectionUtils collectionUtils;
    private Collection collection;

    private List<Image> images = new ArrayList<>();

    public ImagesSearchedCallback(CollectionUtils collectionUtils, Collection collection) {
        this.collectionUtils = collectionUtils;
        this.collection = collection;
    }

    @Override
    public void imageSearched(String imgPath, int position, double score) {
        Image image = createImage(collection, imgPath);
        image.setScore(score);
        image.setPosition(position);
        images.add(image);
    }

    public List<Image> getImages() {
        return images;
    }

    private Image createImage(Collection collection, String fileName) {
        return new Image(fileName, collectionUtils.getThumbnailPathFromImagePath(collection, fileName));
    }
}
