package br.com.antoniogabriel.lirelab.app;

import java.io.IOException;
import java.util.Properties;

public class Version {
    static {
        try {
            final Properties properties = new Properties();
            properties.load(Version.class.getClassLoader().getResourceAsStream("project.properties"));
            VERSION = properties.getProperty("version");
            ARTIFACT_ID = properties.getProperty("artifactId");
        } catch (IOException e) {
            throw new RuntimeException("Could not read project.properties", e);
        }
    }

    public static final String VERSION;
    public static final String ARTIFACT_ID;
}
