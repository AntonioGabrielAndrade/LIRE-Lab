package br.com.antoniogabriel.lirelab.app;

@FunctionalInterface
public interface CommandAction<T> {
    void execute(T param);
}
