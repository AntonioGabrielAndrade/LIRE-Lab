package br.com.antoniogabriel.lirelab.collection;

@FunctionalInterface
public interface CollectionCommandAction {
    void execute(Collection collection);
}
