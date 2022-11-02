package pl.edu.pwr.lgawron.businesslogic.utility.parse;

public class ConsoleInputDataParser {
    public static int getId(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Please enter correct number format!");
            throw new RuntimeException();
        }
    }
}
