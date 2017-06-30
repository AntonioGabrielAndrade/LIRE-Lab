package br.com.antoniogabriel.lirelab.util;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.PathResolver;
import net.coobird.thumbnailator.name.Rename;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class CollectionUtils {


    private PathResolver resolver;

    @Inject
    public CollectionUtils(PathResolver resolver) {
        this.resolver = resolver;
    }

    public List<String> getThumbnailsPaths(Collection collection) throws IOException {
        return net.semanticmetadata.lire.utils.FileUtils.getAllImages(new File(resolver.getThumbnailsDirectoryPath(collection.getName())), false);
    }

    public String getThumbnailPathFromImagePath(Collection collection, String imagePath) {
        String thumbnailsDirectoryPath = resolver.getThumbnailsDirectoryPath(collection.getName());
        String originalImageFilename = Paths.get(imagePath).getFileName().toString();
        String thumbnailFilename = Rename.SUFFIX_DOT_THUMBNAIL.apply(originalImageFilename, null);
        return thumbnailsDirectoryPath + "/" + thumbnailFilename;
    }
}
