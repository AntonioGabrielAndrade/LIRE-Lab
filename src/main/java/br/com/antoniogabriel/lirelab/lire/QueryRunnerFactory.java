package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.PathResolver;
import br.com.antoniogabriel.lirelab.util.CollectionUtils;

public class QueryRunnerFactory {

    public QueryRunner createQueryRunner(PathResolver resolver) {
        return new QueryRunner(resolver, new LIRE(), new CollectionUtils(resolver));
    }
}
