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

package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.*;

public enum Feature {
    CEDD(CEDD.class, "CEDD"),
    TAMURA(Tamura.class, "Tamura"),
    GABOR(Gabor.class, "Gabor"),
    FCTH(FCTH.class, "FCTH"),
    COLOR_HISTOGRAM(SimpleColorHistogram.class, "Simple Color Histogram"),
    ACCID(ACCID.class, "ACCID"),
    AUTO_COLOR_CORRELOGRAM(AutoColorCorrelogram.class, "Auto Color Correlogram"),
    BINARY_PATTERNS_PYRAMID(BinaryPatternsPyramid.class, "Binary Patterns Pyramid"),
    COLOR_LAYOUT(ColorLayout.class, "Color Layout"),
    EDGE_HISTOGRAM(EdgeHistogram.class, "Edge Histogram"),
    FUZZY_COLOR_HISTOGRAM(FuzzyColorHistogram.class, "Fuxxy Color Histogram"),
    FUZZY_OPPONENT_HISTOGRAM(FuzzyOpponentHistogram.class, "Fuzzy Opponent Histogram"),
    JCD(JCD.class, "JCD"),
    JPEG_COEFFICIENT_HISTOGRAM(JpegCoefficientHistogram.class, "Jpeg Coefficient Histogram"),
    LOCAL_BINARY_PATTERNS(LocalBinaryPatterns.class, "Local Binary Patterns"),
    LUMINANCE_LAYOUT(LuminanceLayout.class, "Luminance Layout"),
    OPPONENT_HISTOGRAM(OpponentHistogram.class, "Opponent Histogram"),
    PHOG(PHOG.class, "PHOG"),
    ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS(RotationInvariantLocalBinaryPatterns.class, "Rotation Invariant Local Binary Patterns"),
    SCALABLE_COLOR(ScalableColor.class, "Scalable Color");

    private Class<? extends GlobalFeature> lireClass;

    private String featureName;

    Feature(Class<? extends GlobalFeature> lireClass, String featureName) {
        this.lireClass = lireClass;
        this.featureName = featureName;
    }

    public Class<? extends GlobalFeature> getLireClass() {
        return lireClass;
    }

    public String getFeatureName() {
        return featureName;
    }

    @Override
    public String toString() {
        return featureName;
    }
}
