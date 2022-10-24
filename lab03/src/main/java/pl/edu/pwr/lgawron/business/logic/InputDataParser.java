package pl.edu.pwr.lgawron.business.logic;

public class InputDataParser {
    public static int getId(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Please enter correct number format!");
            throw new RuntimeException();
        }
    }
}
