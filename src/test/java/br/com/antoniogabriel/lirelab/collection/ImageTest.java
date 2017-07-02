package br.com.antoniogabriel.lirelab.collection;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ImageTest {

    @Test
    public void shouldGetImageNameAsFilenameWithNoExtensions() throws Exception {
        String imagePath = "path/to/image1.jpg";
        String thumbnailPath = "path/to/image1.thumbnail.jpg";
        Image image = new Image(imagePath, thumbnailPath);

        assertThat(image.getImageName(), equalTo("image1"));
    }
}