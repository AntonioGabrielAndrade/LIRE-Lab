package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.Collection;
import br.com.antoniogabriel.lirelab.collection.Image;
import br.com.antoniogabriel.lirelab.collection.PathResolver;

public class QueryRunnerFactory {

    public QueryRunner createQueryRunner(Collection collection, Feature feature, Image queryImage, PathResolver resolver) {
        return new QueryRunner(collection, feature, queryImage, resolver);
    }
}
