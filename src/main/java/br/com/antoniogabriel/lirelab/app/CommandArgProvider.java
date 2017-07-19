package br.com.antoniogabriel.lirelab.app;

@FunctionalInterface
public interface CommandArgProvider<T> {
    T provide();
}
