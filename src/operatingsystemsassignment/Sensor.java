package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import operatingsystemsassignment.Settings;
import java.util.Queue;

public class Sensor extends Thread {
	private Queue<Task> taskQueue;
	private int lambda;

	private int capacity = Settings.Parameters.queueCapacity;
	private int sensorsCount = Settings.Parameters.sensorsCount;
	private int taskid=0;

	// sensor constructor
	public Sensor(Queue<Task> taskQueue, int lambda) {
		this.taskQueue = taskQueue;
		// from user input
		this.lambda = lambda;
	}
	// get method for lambda from user input
	public int getLambda() {
		return this.lambda;
	}
	@Override
	public void run() {
		while (true) {
			produce();
		}
	}
	// poisson distribution equation
	public int getPoisson(int lambda) {
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;
		do {
			k++;
			p *= Math.random();
		} while (p > L);
		return k-1;
	}
	// produce method for sensor
	public synchronized void produce() {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String errorColor = ConsoleStyles.errorColor;
		// complexity a single real-valued number corresponding to the com-plexity of the task (0.1 <= taskComplexity <= 0.5)
		double taskComplexity;
		// generates number of tasks = poisson distribution
		int tasksCount = getPoisson(lambda);
		if (tasksCount > capacity) {
			System.out.println(
				errorColor + "!!! Sensor !!! tasks count[" + blueColor + tasksCount + resetColor + errorColor + "]"
				+ " Exceeds the Settings capacity [" + blueColor + capacity + resetColor + errorColor + "]"
				+ resetColor
			);
		}
		// for loop that goes until the value generated from the poisson distribution
		for (int i = 0; i < tasksCount; i++) {
			if (taskQueue.size() <= capacity) {  	// condition to check if queue is not full
				taskid++;             				// increment task id to give unique task id
				taskComplexity = Math.random()/4;	// compute value for taskComplexity
				System.out.println(("** Task id [" + blueColor + taskid + resetColor + "] Complexity:[" + blueColor + taskComplexity + resetColor + "]"));
				Task task = new Task(taskid, taskComplexity);	// create the tasks passing them their task id and taskComplexity
				taskQueue.add(task);                // add task to the queue 
			}
		}
		try {
			// it is the time to sleep
			Thread.sleep(1_000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Task {
	private int taskID;
	private double taskComplexity;

	public Task(int taskID, double taskComplexity) {   // passing task ID and complexity
		this.taskComplexity = taskComplexity;
		this.taskID = taskID;
	}
	public double getTaskComplexity() {    // getters for complexity and id
		return this.taskComplexity;
	}
	public int getTaskID() {
		return this.taskID;
	}
}