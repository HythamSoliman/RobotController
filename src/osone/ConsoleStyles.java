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
    public static String whiteColor = "\u001B[37m";
    public static String greenColor = "\u001B[32m";
    public static String blueColor = "\u001B[34m";
    public static String resetColor = "\u001B[0m";    // Black
    public static String boldStyle = "\u001B[1m";
    public static String resetBold = "\u001B[22m";    // Reset bold style

    // not all command lines support blinking
    static String blinkOn = "\u001B[5m";
    static String blinkOff = "\u001B[25m";
    
    public static String clearScreen = "\u001B[2J\u001B[H"; // Clear screen
    // public static String clearScreen = "\033[H\033[2J"; // Clear screen

    public static final String resetAll = resetBG + resetColor + resetBold + blinkOff;
}