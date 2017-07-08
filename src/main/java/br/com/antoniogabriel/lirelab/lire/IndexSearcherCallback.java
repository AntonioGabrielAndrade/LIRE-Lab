package br.com.antoniogabriel.lirelab.lire;

public interface IndexSearcherCallback {
    void imageSearched(String imgPath, int position, double score);
}
