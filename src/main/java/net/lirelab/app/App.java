/*
 * This file is part of the LIRE-Lab project, a desktop image retrieval tool
 * made on top of the LIRE image retrieval Java library.
 * Copyright (C) 2017  Antonio Gabriel Pereira de Andrade
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.lirelab.app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.lirelab.util.DependencyInjection;

import javax.inject.Inject;

import static net.lirelab.util.FxUtils.runOnFxThreadAndWait;

public class App extends Application {

    private static Image lirelab_16;
    private static Image lirelab_32;
    private static Image lirelab_48;
    private static Image lirelab_128;

    @Inject private AppFXML appFXML;
    @Inject private AboutFXML splash;

    public static void main(String[] args) {
        launch(App.class);
    }

    @Override
    public void init() {
        initAppIcons();
        DependencyInjection.init(this);
        setupAndShowSplashScreen();
    }

    @Override
    public void start(Stage stage) {
        setIconsTo(stage);
        stage.setMaximized(true);
        appFXML.loadIn(stage);
    }

    private void initAppIcons() {
        runOnFxThreadAndWait(() -> {
            lirelab_16 = new Image(App.class.getClassLoader().getResourceAsStream("app_icons/lirelab_16.png"));
            lirelab_32 = new Image(App.class.getClassLoader().getResourceAsStream("app_icons/lirelab_32.png"));
            lirelab_48 = new Image(App.class.getClassLoader().getResourceAsStream("app_icons/lirelab_48.png"));
            lirelab_128 = new Image(App.class.getClassLoader().getResourceAsStream("app_icons/lirelab_128.png"));
        });
    }

    private void setupAndShowSplashScreen() {
        runOnFxThreadAndWait(() -> {
            Stage splashStage = new Stage();
            setIconsTo(splashStage);
            splash.loadIn(splashStage);
        });
    }

    private void setIconsTo(Stage stage) {
        runOnFxThreadAndWait(() -> {
            stage.getIcons().addAll(lirelab_16, lirelab_32, lirelab_48, lirelab_128);
        });
    }
}
