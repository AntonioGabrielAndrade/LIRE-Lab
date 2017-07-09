package br.com.antoniogabriel.lirelab.collection;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

public class Subfolders implements DirectoryStream<Path> {

    private DirectoryStream<Path> folders;

    private Subfolders(Path dir) throws IOException {
        folders = Files.newDirectoryStream(dir, entry -> Files.isDirectory(entry));
    }

    public static Subfolders of(Path dir) throws IOException {
        return new Subfolders(dir);
    }

    @Override
    public Iterator<Path> iterator() {
        return folders.iterator();
    }

    @Override
    public void close() throws IOException {
        folders.close();
    }
}
