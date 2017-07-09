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
    JCD(JCD.class, "Tamura"),
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
