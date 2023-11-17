package osone;

import osone.ConsoleStyles;
import osone.Settings;
import java.util.Locale;
import java.util.Scanner;

public class MyUi {
    static class UserInput {
        int lambdaValue;
        double pos0Value;

        public UserInput(int lambdaValue, double pos0Value) {
            this.lambdaValue = lambdaValue;
            this.pos0Value = pos0Value;
        }
        public int getLambdaValue() {
            return this.lambdaValue;
        }
        public double getPos0Value() {
            return this.pos0Value;
        }
    }
	static public UserInput dataEntry() {
       String infoColor = ConsoleStyles.infoColor;
       String errorColor = ConsoleStyles.errorColor;
       String greenColor = ConsoleStyles.greenColor;
       String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
       String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
       String boldStyle = ConsoleStyles.boldStyle;
       String resetBold = ConsoleStyles.resetBold;
       String blinkOn = ConsoleStyles.blinkOn;
       String blinkOff = ConsoleStyles.blinkOff;
       String clearScreen = ConsoleStyles.clearScreen;
        
		System.out.print(clearScreen); // Clear the screen

	    Scanner lambda = new Scanner(System.in);                  // getting input for lambda from user 
	    System.out.print(greenColor + "Enter a double value for lambda:\t\t\t\t\t" + resetColor);
	    int lambdaValue = lambda.nextInt();

	    Scanner pos0 = new Scanner(System.in);
	    pos0.useLocale(Locale.UK);
	    // getting input from user for initial position
		System.out.print(blinkOn + greenColor + "Enter a starting position for the robot between ");
        System.out.print(blinkOn + blueColor + "0" + resetColor);
        System.out.print(blinkOn + greenColor + " and " + blueColor + "1" + resetColor);
		System.out.print(blinkOn + greenColor + ":\t\t" + resetColor + blinkOff);
	    double pos0Value = pos0.nextDouble();
		if (pos0Value > 1) {pos0Value = 1;}
		if (pos0Value < 0) {pos0Value = 0;}

		// System.out.print(clearScreen); // Clear the screen
		System.out.println("\n\nThank you for passing the values.");
		System.out.print("\tlambda [" + blueColor);
		System.out.print(lambdaValue);
		System.out.print(resetColor + "]");

		System.out.print("\tPos-0 Value [" + blueColor);
		System.out.print(pos0Value);
		System.out.print(resetColor + "]");

		System.out.println("\n\nAdditional Info: [if you would like to change below values, please feel free to edit the {src/osone/Settings.java} file]");
		System.out.print(infoColor + "\tSettings Queue Capacity[" + blueColor);
		System.out.print(Settings.Parameters.queueCapacity);
		System.out.print(infoColor + "]\n" + resetColor);

		System.out.print(infoColor + "\tSettings Sensors Count[" + blueColor);
		System.out.print(Settings.Parameters.sensorsCount);
		System.out.print(infoColor + "]\n" + resetColor);

		System.out.print(infoColor + "\tlife time[" + blueColor);
		System.out.print(Settings.Parameters.lifeTime);
		System.out.print(infoColor + "]\n" + resetColor);
		
		System.out.println(greenColor + boldStyle + "\n\nStarting My Work...\n\n" + resetColor + resetColor);
        
        return new UserInput(lambdaValue, pos0Value);
	}
	static public void printedMsg(int SensorId, int TaskId, Double Complexity, Double Distance, Double CurPos, Double NewPos ) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String greenColor = ConsoleStyles.greenColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String move_string =  "Robot moving.";
		System.out.println(
			resetColor + "** "
			+ greenColor + move_string
			+ resetColor + " Sensor ID[" + blueColor + SensorId + resetColor + "]"
			+ " Task ID[" + blueColor + TaskId + resetColor + "] Complexity[" + blueColor + Complexity + resetColor + "]"
			+ " Y:[" + greenColor + Distance + resetColor + "]"
			+ " old pos:[" + greenColor + CurPos + resetColor + "]"
			+ " new pos:[" + greenColor + NewPos + resetColor + "]"
		);
	}
}