package br.com.antoniogabriel.lirelab.custom.collectiontree;

import br.com.antoniogabriel.lirelab.collection.Collection;

@FunctionalInterface
public interface CollectionSelectionListener {
    void selected(Collection collection);
}
