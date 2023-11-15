package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import operatingsystemsassignment.Settings;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Scanner;

public class Controller {	
	// ANSI escape codes for used colors
	static String infoColor = ConsoleStyles.infoColor;
	static String errorColor = ConsoleStyles.errorColor;
	static String greenColor = ConsoleStyles.greenColor;
	static String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
	static String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
	static String boldStyle = ConsoleStyles.boldStyle;
	static String resetBold = ConsoleStyles.resetBold;
	static String clearScreen = ConsoleStyles.clearScreen;

	public static void main(String[] args) throws InterruptedException {
		
		System.out.print(clearScreen); // Clear the screen

	    Scanner lambda = new Scanner(System.in);                  // getting input for lambda from user 
	    System.out.print(greenColor + "Enter a double value for lambda:\t\t\t\t\t" + resetColor);
	    int lambdavalue = lambda.nextInt();

	    
	    Scanner analysisConstant = new Scanner(System.in);                 // getting input from user for analysisconstant 
	    System.out.print(greenColor + "Enter a value for the analysis constant:\t\t\t\t" + resetColor);
	    int analysisConstantValue = analysisConstant.nextInt();
	    
	    Scanner pos0 = new Scanner(System.in);
	    pos0.useLocale(Locale.UK);
	    // getting input from user for initial position
		System.out.print(greenColor + "Enter a starting position for the robot between ");
        System.out.print(blueColor + "0" + resetColor);
        System.out.print(greenColor + " and " + blueColor + "1" + resetColor);
		System.out.print(greenColor + ":\t\t" + resetColor);
	    double pos0value = pos0.nextDouble();
		if (pos0value > 1) {pos0value = 1;}
		if (pos0value < 0) {pos0value = 0;}

		// System.out.print(clearScreen); // Clear the screen
		System.out.println("\n\nThank you for passing the values.");
		System.out.print("\tlambda [" + blueColor);
		System.out.print(lambdavalue);
		System.out.print(resetColor + "]");

		System.out.print("\tConstant [" + blueColor);
		System.out.print(analysisConstantValue);
		System.out.print(resetColor + "]");

		System.out.print("\tPos-0 Value [" + blueColor);
		System.out.print(pos0value);
		System.out.print(resetColor + "]");

		System.out.println("\n\nAdditional Info:");
		System.out.print(infoColor + "\tSettings Capacity[" + blueColor);
		System.out.print(Settings.Parameters.queueCapacity);
		System.out.print(infoColor + "]" + resetColor);
		
		
		System.out.println(greenColor + boldStyle + "\n\nStarting My Work...\n\n" + resetColor + resetColor);

		Queue<Task> queue1 = new LinkedList<>();    // creating instances of the queues 
		Queue<Result> queue2 = new LinkedList<>();
		
		Object actuateAnalyzeConsumeLock = new Object();  // lock objects 
		Object actuateAnalyzeProduceLock = new Object();
		Actuator actuate = new Actuator(queue2, actuateAnalyzeProduceLock, actuateAnalyzeConsumeLock, pos0value);	// instance of actuator
		Analyzer analyze = new Analyzer(queue1, queue2, analysisConstantValue);   // instance of analyzer
		Sensor sense = new Sensor(queue1, lambdavalue);    // instance of sensor
		
		Thread thread1 = new Thread(sense);      // creating the threads 
		Thread thread2 = new Thread(analyze);
		Thread thread3 = new Thread(actuate);

		thread1.start();
		thread2.start();
		thread3.start();

        try {
        	Thread.sleep(5_000);
        }
        catch(InterruptedException e) {
        	System.out.println(errorColor + "Interrupted all" + resetColor);
        }
        System.exit(0);
	}
}