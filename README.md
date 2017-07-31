# LIRE-Lab

[![Build Status](https://travis-ci.org/AntonioGabrielAndrade/LIRE-Lab.svg)](https://travis-ci.org/AntonioGabrielAndrade/LIRE-Lab)
[![Latest Release](https://img.shields.io/github/release/AntonioGabrielAndrade/LIRE-Lab.svg)](https://github.com/AntonioGabrielAndrade/LIRE-Lab/releases/latest)

LIRE-Lab is an image retrieval desktop tool developed on top of the [LIRE](http://www.lire-project.net/) Java library created by Mathias Lux.
LIRE-Lab provides an easy way to index collections of images and run queries using the LIRE features.

# Prerequisites

You will need Java 8u40 or higher to build and run LIRE-Lab.  You can download it from the [Oracle Website](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

# How To Run LIRE-Lab

LIRE-Lab is currently distributed only in jar format. You can download the latest release [here](https://github.com/AntonioGabrielAndrade/LIRE-Lab/releases/latest) in a .zip file.
After extract the .zip file you can run the system with a double click on the .jar file or typing the following command in terminal:
~~~
$ java -jar <jar-file-name>
~~~

# How To Build LIRE-Lab

You can build the entire system with the following command:
~~~
$ mvn clean package
~~~

# How To Run The GUI Tests

By default the GUI tests runs as part of the standard Maven build, in no-headless mode (showing the TestFX robot acting in the GUI):
~~~
$ mvn clean test
~~~

If you want to run the tests in headless mode, run:
~~~
$ mvn clean test -Dheadless=true
~~~