package br.com.antoniogabriel.lirelab;

import br.com.antoniogabriel.lirelab.util.DependencyInjection;
import br.com.antoniogabriel.lirelab.util.FXML;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import javafx.stage.Stage;
import org.testfx.framework.junit.ApplicationTest;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ControllerTest<T extends FXML, C> extends ApplicationTest {

    @Inject
    protected C controller;

    @Inject
    protected T fxml;

    private List<Module> modules = Arrays.asList(getBindings());

    @Override
    public void start(Stage stage) throws Exception {
        DependencyInjection.init(this, modules);
        controller = fxml.getController();
    }

    protected AbstractModule getBindings() {
        return new AbstractModule() {
            @Override
            protected void configure() {
            }
        };
    }
}
