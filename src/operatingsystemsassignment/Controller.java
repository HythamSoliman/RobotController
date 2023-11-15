package operatingsystemsassignment;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;
import java.util.Scanner;


public class Controller {	

	public static void main(String[] args) throws InterruptedException {
		
	    Scanner lambda = new Scanner(System.in);                   // getting input for lambda from user 
	    System.out.println("Enter a double value for lambda: ");
	    int lambdavalue = lambda.nextInt();
	    
	    Scanner analysisConstant = new Scanner(System.in);                  // getting input from user for analysisconstant 
	    System.out.println("Enter a value for the analysis constant: ");
	    int analysisConstantValue = analysisConstant.nextInt();
	    
	    Scanner pos0 = new Scanner(System.in); 
	    pos0.useLocale(Locale.UK);
	    System.out.println("Enter a starting positon for the robot between 0 and 1: ");  // getting input from user for initial position
	    double pos0value = pos0.nextDouble();
		
		Queue<Task> queue1 = new LinkedList<>();     // creating instances of the queues 
		Queue<Result> queue2 = new LinkedList<>();
		
		
		Object actuateAnalyzeConsumeLock = new Object();   // lock objects 
		Object actuateAnalyzeProduceLock = new Object();
		Actuator actuate = new Actuator(queue2, actuateAnalyzeProduceLock, actuateAnalyzeConsumeLock, pos0value);	// instance of actuator
		Analyzer analyze = new Analyzer(queue1, queue2, analysisConstantValue);    // instance of analyzer
		Sensor sense = new Sensor(queue1, lambdavalue);     // instance of sensor
		
		Thread thread1 = new Thread(sense);       // creating the threads 
		Thread thread2 = new Thread(analyze);
		Thread thread3 = new Thread(actuate);
	
		
	
		thread1.start(); 
		thread2.start(); 
		thread3.start(); 
		

		
        try {
        	
        	Thread.sleep(5_000);
        		
        }
        catch(InterruptedException e) {
        	System.out.println("Interrupted all");
        }
        
        System.exit(0);

		
	}

}
