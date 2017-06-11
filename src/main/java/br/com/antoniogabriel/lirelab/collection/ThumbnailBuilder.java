package br.com.antoniogabriel.lirelab.collection;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import net.semanticmetadata.lire.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ThumbnailBuilder {
    public void createDirectory(String thumbnailsDir) throws IOException {
        Path path = Paths.get(thumbnailsDir);
        if(!Files.exists(path))
            Files.createDirectory(path);
    }

    public List<String> getAllImagePaths(String imagesDir) throws IOException {
        return FileUtils.getAllImages(new File(imagesDir), true);
    }

    public void createThumbnail(String srcImagePath, String thumbnailsDir) throws IOException {
        Thumbnails.of(srcImagePath)
                .height(100)
                .toFiles(new File(thumbnailsDir), Rename.SUFFIX_DOT_THUMBNAIL);
    }
}