package osone;
public class ConsoleStyles {
    // ANSI escape codes for used colors
    public static final String resetBG = "\u001B[0m";
    public static final String redBG = "\u001B[41m";
    public static final String blackBG = "\u001B[40m";
    public static final String infoBG = "\u001B[43m"; // yellowBG
    public static final String errorBG = "\u001B[104m"; // lightBlueBG

    public static String infoColor = "\u001B[33;1m"; // darkYellowColor
    public static String errorColor = "\u001B[31m"; // redColor
    public static String greenColor = "\u001B[32m";
    public static String blueColor = "\u001B[34m";
    public static String resetColor = "\u001B[0m";    // Black
    public static String boldStyle = "\u001B[1m";
    public static String resetBold = "\u001B[22m";    // Reset bold style
    public static String clearScreen = "\u001B[2J\u001B[H"; // Clear screen
}