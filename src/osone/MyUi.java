package osone;

import osone.ConsoleStyles;
import osone.Settings;
import java.util.Locale;
import java.util.Scanner;

public class MyUi {
    static class UserInput {
        private int lambdaValue;
        private double pos0Value;

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
       String blinkOff = ConsoleStyles.blinkOff;
       String clearScreen = ConsoleStyles.clearScreen;
        
		System.out.print(clearScreen); // Clear the screen

	    Scanner lambda = new Scanner(System.in);                  // getting input for lambda from user
		PrintLabelLambda();

		// SR: Set the default value if the input is empty to 2
		String lambdaInput = lambda.nextLine().trim();
		int lambdaValue = 2;
		if (lambdaInput.isEmpty()) {
			System.out.print("\033[F"); // Move cursor up one line
			PrintLabelLambda();
			System.out.println("2" + resetColor);
		}
		else {
			try {
				lambdaValue = Integer.parseInt(lambdaInput);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Replace with default value of 2");
			}
		}

	    Scanner pos0 = new Scanner(System.in);
	    pos0.useLocale(Locale.UK);
	    // getting input from user for initial position
		PrintLabelPos0();
		
		String pos0Input = pos0.nextLine().trim();
		double pos0Value = 1;
		if (pos0Input.isEmpty()) {
			System.out.print("\033[F"); // Move cursor up one line
			PrintLabelPos0();
			System.out.println("1");
		}
		else {
			try {
				pos0Value = Double.parseDouble(pos0Input);
				if (pos0Value > 1) {
					System.out.println("Invalid input. Replace with default value of 1");
					pos0Value = 1;
				}
				if (pos0Value < 0) {
					System.out.println("Invalid input. Replace with default value of 0");
					pos0Value = 0;
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Replace with default value of 1");
			}
		}



	    // double pos0Value = pos0.nextDouble();
		// if (pos0Value > 1) {pos0Value = 1;}
		// if (pos0Value < 0) {pos0Value = 0;}

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

		System.out.print(infoColor + "\tThreads lifetime[" + blueColor);
		System.out.print(Settings.Parameters.lifeTime);
		System.out.print(infoColor + "]\n" + resetColor);
		
		System.out.println(greenColor + boldStyle + "\n\nStarting My Work...\n\n" + resetColor + resetColor);
        
        return new UserInput(lambdaValue, pos0Value);
	}
	static void PrintLabelLambda() {
		String greenColor = ConsoleStyles.greenColor;
       	String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		System.out.print(greenColor + "Enter a value for lambda (or press entre to use the default value 2):\t\t" + resetColor);
	}
	static void PrintLabelPos0() {
       	String greenColor = ConsoleStyles.greenColor;
       	String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
       	String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		System.out.print(greenColor + "Enter robot pos0 between ");
        System.out.print(blueColor + "0" + resetColor);
        System.out.print(greenColor + " and " + blueColor + "1" + resetColor);
		System.out.print(greenColor + " (or press entre to use the default value 1):\t" + resetColor);
	}
	static public void PrintActuateMoveMsg(int SensorId, int TaskId, Double Complexity, Double Distance, Double CurPos, Double NewPos ) {
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
	static public void PrintedSenseCapacityCheck(int tasksCount, int queueCapacity, int sensorIndex) {
		String errorBG = ConsoleStyles.errorBG;
		String infoBG = ConsoleStyles.infoBG;
		String resetBG = ConsoleStyles.resetBG;
		String boldStyle = ConsoleStyles.boldStyle;
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String errorColor = ConsoleStyles.errorColor;
		if (tasksCount > queueCapacity) {
			System.out.println(
				errorBG
				+ boldStyle + errorColor + "Error: Sensor Queue is full !! Sensor[" + blueColor + sensorIndex + errorColor + "] !! tasks count[" + blueColor + tasksCount + errorColor + "]"
				+ " Exceeds the Settings queueCapacity[" + blueColor + queueCapacity + errorColor + "]"
				+ resetBG
				+ resetColor
			);
		} else {
			System.out.println(
				infoBG
				+ boldStyle + infoBG + "Info: Sensor Queue !! Sensor[" + blueColor + sensorIndex + infoBG + "] !! tasks count[" + blueColor + tasksCount + infoBG + "]"
				+ resetBG
				+ resetColor
			);
		}
	}
	static public void PrintedAnalyzerCapacityCheck(int sensorID, int taskID) {
		String errorBG = ConsoleStyles.errorBG;
		String resetBG = ConsoleStyles.resetBG;
		String boldStyle = ConsoleStyles.boldStyle;
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		System.out.println(
			errorBG
			+ boldStyle + errorBG + "Error: Analyzer Queue !! Sensor[" + blueColor + sensorID + errorBG + "] !! for task[" + blueColor + taskID + errorBG + "] has no location at the queue"
			+ resetBG
			+ resetColor
		);
	}
	static public void PrintedSenseNewTaskMsg(int sensorIndex, int taskID, double taskComplexity) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		System.out.println(("** Sensor ID[" + blueColor + sensorIndex + resetColor + "] Task ID[" + blueColor + taskID + resetColor + "] Complexity:[" + blueColor + taskComplexity + resetColor + "]"));
	}
	static public void PrintedComplexityErrorMsg(double taskComplexity) {
		System.out.println(
				ConsoleStyles.errorColor + ConsoleStyles.blackBG + "taskComplexity miss calculation: "
				+ taskComplexity
				+ ConsoleStyles.resetAll
			);
	}
	static public void PrintedAnalyzerMsg(int taskSensorID, int taskID) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		System.out.println("** Sensor ID[" + blueColor + taskSensorID + resetColor + "] Task ID[" + blueColor + taskID + resetColor + "] analyzing ...");
	}
}