package br.com.antoniogabriel.lirelab.custom;

import br.com.antoniogabriel.lirelab.app.ImageViewFactory;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.FileUtils;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.inject.Inject;

public class LireLabBuiderFactory implements BuilderFactory {

    private ImageViewFactory imageViewFactory;
    private FileUtils fileUtils;
    private PathResolver resolver;
    private ImageGridBuilder imageGridBuilder;

    @Inject
    public LireLabBuiderFactory(ImageViewFactory imageViewFactory,
                                FileUtils fileUtils,
                                PathResolver resolver,
                                ImageGridBuilder imageGridBuilder) {

        this.imageViewFactory = imageViewFactory;
        this.fileUtils = fileUtils;
        this.resolver = resolver;
        this.imageGridBuilder = imageGridBuilder;
    }

    @Override
    public Builder<?> getBuilder(Class<?> type) {
        if(type == ImageGrid.class) {
            return new ImageGridBuilder(imageViewFactory, fileUtils);
        }
        if(type == CollectionGrid.class) {
            return new CollectionGridBuilder(resolver, imageGridBuilder);
        }

        return new JavaFXBuilderFactory().getBuilder(type);
    }
}
