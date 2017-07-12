package br.com.antoniogabriel.lirelab.custom.collection_tree;

import br.com.antoniogabriel.lirelab.collection.Collection;

@FunctionalInterface
public interface CollectionSelectionListener {
    void selected(Collection collection);
}
