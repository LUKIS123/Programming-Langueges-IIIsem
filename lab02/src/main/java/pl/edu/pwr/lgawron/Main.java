package pl.edu.pwr.lgawron;

import pl.edu.pwr.lgawron.flow.ApplicationFlow;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle fatJar
 *  kod w gradle:
 *     task fatJar(type: Jar) {
 *     manifest {
 *         attributes 'Main-Class': 'pl.edu.pwr.lgawron.Main'
 *     }
 *     baseName = project.name + '-all'
 *     duplicatesStrategy = DuplicatesStrategy.EXCLUDE
 *     from { configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) } }
 *     with jar
 * }
 *
 * sposob uruchamiania: java -jar .\lab02_pop.jar jug_data.txt person_data.txt
 */

public class Main {
    public static void main(String[] args) {
        ApplicationFlow applicationFlow = new ApplicationFlow(args);
        applicationFlow.runApp();
    }
}
