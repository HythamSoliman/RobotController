package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import operatingsystemsassignment.Settings;
import java.util.Queue;

public class Sensor extends Thread {
	private Queue<Task> taskQueue;
	private int capacity = Settings.Parameters.queueCapacity;
	private int taskid=0;
	private int lambda;

	public Sensor(Queue<Task> taskQueue, int lambda) { // sensor constructor
		this.taskQueue = taskQueue;
		this.lambda = lambda;  // user input 
	}
	public int getLambda() {    // get method for lambda from user input
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
		double L = Math.exp(-(lambda));
		double p = 1.0;
		int k = 0;
		do {
			k++;
			p *= Math.random();
		} while (p > L);
		int result = k-1;
		return result;
	}
	// produce method for sensor
	public synchronized void produce() {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String errorColor = ConsoleStyles.errorColor;
		// complexity a single real-valued number corresponding to the com-plexity of the task (0.1 <= c <= 0.5)
		double c;
		// generates number of tasks = poisson distribution
		int number_tasks = getPoisson(lambda);
		if (number_tasks > capacity) {
			System.out.println(
				errorColor + "!!! Sensor !!! num of tasks[" + blueColor + number_tasks + resetColor + errorColor + "]"
				+ " > CAPACITY[" + blueColor + capacity + resetColor + errorColor + "]"
				+ resetColor
			);
		}
		// for loop that goes until the value generated from the poisson distribution
		for (int i = 0; i < number_tasks; i++) {
			if (taskQueue.size() <= capacity) {  		// condition to check if queue is not full
				taskid++;             				// increment task id to give unique task id
				c = Math.random()/4;               	// compute value for c
				System.out.println(("** Task id [" + blueColor + taskid + resetColor + "] Complexity:[" + blueColor + c + resetColor + "]"));
				Task task = new Task(taskid, c);	// create the tasks passing them their task id and c
				taskQueue.add(task);                	// add task to the queue 
			}
		}
		try {
			Thread.sleep(1_000);             // sleep time 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
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