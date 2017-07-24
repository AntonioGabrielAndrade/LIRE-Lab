package br.com.antoniogabriel.lirelab.test_utilities;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public abstract class InjectableViewTest<T> extends ApplicationTest {

    @Inject
    protected T fxml;

    private List<Module> modules = Arrays.asList(getBindings());

    @Override
    public void init() throws Exception {
        DependencyInjection.init(this, modules);
    }

    /* to be overridden by subclasses */
    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {}
        };
    }
}
