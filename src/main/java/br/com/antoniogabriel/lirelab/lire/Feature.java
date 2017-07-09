package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.*;

public enum Feature {
    CEDD(CEDD.class),
    TAMURA(Tamura.class),
    GABOR(Gabor.class),
    FCTH(FCTH.class),
    COLOR_HISTOGRAM(SimpleColorHistogram.class),
    ACCID(ACCID.class),
    AUTO_COLOR_CORRELOGRAM(AutoColorCorrelogram.class),
    BINARY_PATTERNS_PYRAMID(BinaryPatternsPyramid.class),
    COLOR_LAYOUT(ColorLayout.class),
    EDGE_HISTOGRAM(EdgeHistogram.class),
    FUZZY_COLOR_HISTOGRAM(FuzzyColorHistogram.class),
    FUZZY_OPPONENT_HISTOGRAM(FuzzyOpponentHistogram.class),
    JCD(JCD.class),
    JPEG_COEFFICIENT_HISTOGRAM(JpegCoefficientHistogram.class),
    LOCAL_BINARY_PATTERNS(LocalBinaryPatterns.class),
    LUMINANCE_LAYOUT(LuminanceLayout.class),
    OPPONENT_HISTOGRAM(OpponentHistogram.class),
    PHOG(PHOG.class),
    ROTATION_INVARIANT_LOCAL_BINARY_PATTERNS(RotationInvariantLocalBinaryPatterns.class),
    SCALABLE_COLOR(ScalableColor.class);

    private Class<? extends GlobalFeature> lireClass;

    Feature(Class<? extends GlobalFeature> lireClass) {
        this.lireClass = lireClass;
    }

    public Class<? extends GlobalFeature> getLireClass() {
        return lireClass;
    }
}
