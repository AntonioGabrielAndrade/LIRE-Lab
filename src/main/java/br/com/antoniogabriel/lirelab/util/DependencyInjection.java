package br.com.antoniogabriel.lirelab.util;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.Module;

import java.util.Collection;
import java.util.Collections;


public class DependencyInjection {

    public static void init(Object root) {
        init(root, Collections.emptyList());
    }

    public static void init(Object root, Collection<Module> modules) {
        GuiceContext context = new GuiceContext(root, () -> modules);
        context.init();
    }
}
