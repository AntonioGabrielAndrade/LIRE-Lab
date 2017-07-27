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

package br.com.antoniogabriel.lirelab.preloader;

import br.com.antoniogabriel.lirelab.app.AboutFXML;
import javafx.animation.FadeTransition;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class SplashScreenPreloader extends Preloader {

    private Stage stage;

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.initStyle(StageStyle.UNDECORATED);

        AboutFXML aboutFXML = new AboutFXML(new FXMLLoader());
        aboutFXML.loadIn(stage);
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            if (stage.isShowing()) {
                // fade out, hide stage at the end of animation
                final FadeTransition fadeTransition =
                        new FadeTransition(
                                Duration.millis(1000), stage.getScene().getRoot());
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                final EventHandler<ActionEvent> eventHandler = t -> stage.hide();
                fadeTransition.setOnFinished(eventHandler);
                fadeTransition.play();
            } else {
                stage.hide();
            }
        }
    }
}
