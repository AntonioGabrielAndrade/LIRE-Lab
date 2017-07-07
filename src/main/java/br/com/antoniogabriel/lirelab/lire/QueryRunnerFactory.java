package br.com.antoniogabriel.lirelab.lire;

import br.com.antoniogabriel.lirelab.collection.PathResolver;

public class QueryRunnerFactory {

    public QueryRunner createQueryRunner(PathResolver resolver) {
        return new QueryRunner(resolver);
    }
}
