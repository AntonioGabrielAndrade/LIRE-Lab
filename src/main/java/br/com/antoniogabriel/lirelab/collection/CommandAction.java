package br.com.antoniogabriel.lirelab.collection;

@FunctionalInterface
public interface CommandAction<T> {
    void execute(T param);
}
