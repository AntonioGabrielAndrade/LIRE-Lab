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

import net.lirelab.util.DependencyInjection;
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
