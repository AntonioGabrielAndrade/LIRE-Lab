package br.com.antoniogabriel.lirelab.acceptance;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LireLabAcceptanceTest {

    private ApplicationRunner runner = new ApplicationRunner();

    @Before
    public void setUp() throws Exception {
        runner.setUpApp();
    }

    @After
    public void tearDown() throws Exception {
        runner.tearDownApp();
    }

    @Test
    public void shouldShowBasicUIStructure() throws Exception {
        runner.checkMenus();
        runner.checkToolBar();
        runner.checkWelcomeView();
    }

}
