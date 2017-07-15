package br.com.antoniogabriel.lirelab.custom.statusbar;

import br.com.antoniogabriel.lirelab.lire.Feature;

@FunctionalInterface
public interface FeatureSelectionListener {
    void selected(Feature feature);
}
