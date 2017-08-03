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

package net.lirelab.test_utilities;

import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.Node;
import org.testfx.api.FxRobot;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testfx.util.WaitForAsyncUtils.waitFor;

public class AsyncUtils {

    private static final TimeOut DEFAULT_TIMEOUT = TimeOut.of(5, SECONDS);
    private static final FxRobot ROBOT = new FxRobot();

    public static void waitUntil(ObservableBooleanValue condition) throws TimeoutException {
        waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public static void waitUntil(ObservableBooleanValue condition, TimeOut timeout) throws TimeoutException {
        waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntil(Callable<Boolean> condition) throws TimeoutException {
        waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public static void waitUntil(Callable<Boolean> condition, TimeOut timeout) throws TimeoutException {
        waitFor(timeout.value, timeout.unit, condition);
    }

    public static void waitUntilIsVisible(String query) throws TimeoutException {
        waitUntilVisibilityIs(query, true);
    }

    public static void waitUntilIsVisible(String query, String queryFrom) throws TimeoutException {
        waitUntilVisibilityIs(query, queryFrom, true);
    }

    public static void waitUntilIsNotVisible(String query) throws TimeoutException {
        waitUntilVisibilityIs(query, false);
    }

    public static void waitUntilIsNotVisible(String query, String queryFrom) throws TimeoutException {
        waitUntilVisibilityIs(query, queryFrom, false);
    }

    public static void waitUntilIsDisabled(String query) throws TimeoutException {
        waitUntilDisableValueIs(query, true);
    }

    public static void waitUntilIsDisabled(String query, String queryFrom) throws TimeoutException {
        waitUntilDisableValueIs(query, queryFrom, true);
    }

    public static void waitUntilIsEnabled(String query) throws TimeoutException {
        waitUntilDisableValueIs(query, false);
    }

    public static void waitUntilIsEnabled(String query, String queryFrom) throws TimeoutException {
        waitUntilDisableValueIs(query, queryFrom, false);
    }

    public static FxRobot clickOnContainedElement(String query, String queryFrom) throws TimeoutException {
        Node node = ROBOT.from(ROBOT.lookup(queryFrom)).lookup(query).query();
        ROBOT.clickOn(node);
        return ROBOT;
    }

    private static void waitUntilVisibilityIs(String query, String queryFrom, boolean visibility) throws TimeoutException {
        waitUntil(() -> {
            Node node = ROBOT.from(ROBOT.lookup(queryFrom)).lookup(query).query();
            return (node == null) ? (!visibility) : (node.isVisible() == visibility);
        });
    }

    private static void waitUntilVisibilityIs(String query, boolean visibility) throws TimeoutException {
        waitUntil(() -> {
            Node node = ROBOT.lookup(query).query();
            return (node == null) ? (!visibility) : (node.isVisible() == visibility);
        });
    }

    private static void waitUntilDisableValueIs(String query, String queryFrom, boolean disableValue) throws TimeoutException {
        waitUntil(() -> {
            Node node = ROBOT.from(ROBOT.lookup(queryFrom)).lookup(query).query();
            return (node == null) ? (!disableValue) : (node.isDisable() == disableValue);
        });
    }

    private static void waitUntilDisableValueIs(String query, boolean disableValue) throws TimeoutException {
        waitUntil(() -> {
            Node node = ROBOT.lookup(query).query();
            return (node == null) ? (!disableValue) : (node.isDisable() == disableValue);
        });
    }

    public static void waitUntilElementsAreOrderedLike(String queryFrom,
                                                       String queryElementType,
                                                       String... orderedIds) throws TimeoutException {
        for (int i = 0; i < orderedIds.length; i++) {
            waitUntilElementsOrderIs(queryFrom, queryElementType, orderedIds[i], i);
        }
    }

    public static void waitUntilElementsOrderIs(String queryFrom,
                                                String queryElementType,
                                                String elementId,
                                                int index) throws TimeoutException {
        waitUntil(() -> ROBOT
                        .from(ROBOT.lookup(queryFrom))
                        .lookup(queryElementType)
                        .nth(index).query()
                        .getId()
                        .equals(elementId));
    }
}
