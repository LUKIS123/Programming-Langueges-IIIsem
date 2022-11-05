package pl.edu.pwr.lgawron.employee;

import pl.edu.pwr.lgawron.employee.flow.EmployeeAppController;

/**
 * @author Lukasz Gawron, 264475
 * sposob budowy: gradle build
 * sposob archiwizowania: gradle jar
 * sposob uruchamiania: java -p lab03.jar -m lab03.main/pl.edu.pwr.lgawron.employee.Main [ID]
 * [ID] oznacza id pracownika
 */

public class Main {
    public static void main(String[] args) {
        EmployeeAppController controller = new EmployeeAppController();
        controller.index();
    }
}

//        String str = "1986-04-08";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(str, formatter);
//        System.out.println(date);
//
//        String s = String.valueOf(date);
//        System.out.println(s);
