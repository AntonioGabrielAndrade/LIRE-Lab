package br.com.antoniogabriel.lirelab.lire;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.Tamura;
import net.semanticmetadata.lire.imageanalysis.features.global.Gabor;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import net.semanticmetadata.lire.imageanalysis.features.global.SimpleColorHistogram;

public enum Feature {
    CEDD(CEDD.class),
    TAMURA(Tamura.class),
    GABOR(Gabor.class),
    FCTH(FCTH.class),
    COLOR_HISTOGRAM(SimpleColorHistogram.class);

    private Class<? extends GlobalFeature> lireClass;

    Feature(Class<? extends GlobalFeature> lireClass) {
        this.lireClass = lireClass;
    }

    public Class<? extends GlobalFeature> getLireClass() {
        return lireClass;
    }
}
