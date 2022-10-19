package pl.edu.pwr.lgawron;

import pl.edu.pwr.lgawron.flow.ApplicationFlow;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania: java -jar .\lab02.jar jug_data.txt person_data.txt
 */

public class Main {
    public static void main(String[] args) {
        ApplicationFlow applicationFlow = new ApplicationFlow(args);
        applicationFlow.runApp();
    }
}
