package br.com.antoniogabriel.lirelab.collection;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import net.semanticmetadata.lire.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ThumbnailBuilder {
    public void createDirectory(String thumbnailsDir) throws IOException {
        Files.createDirectory(Paths.get(thumbnailsDir));
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