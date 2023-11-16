package osone;

import osone.ConsoleStyles;
import osone.Settings;
import java.util.Queue;

public class Sensor extends Thread {
	private Queue<Task> taskQueue;
	private int lambda;

	private int queueCapacity = Settings.Parameters.queueCapacity;
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
			generateTasks();
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
	// generateTasks method for sensor
	public synchronized void generateTasks() {
		String errorBG = ConsoleStyles.errorBG;
		String infoBG = ConsoleStyles.infoBG;
		String resetBG = ConsoleStyles.resetBG;
		String boldStyle = ConsoleStyles.boldStyle;
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String errorColor = ConsoleStyles.errorColor;
		// complexity a single real-valued number corresponding to the com-plexity of the task (0.1 <= taskComplexity <= 0.5)
		double taskComplexity;

		for (int sensorIndex = 0; sensorIndex < sensorsCount; sensorIndex++) {
			// generates number of tasks = poisson distribution
			int tasksCount = getPoisson(lambda);
			if (tasksCount > queueCapacity) {
				System.out.println(
					errorBG
					+ boldStyle + errorColor + "Error: Queue is full !! Sensor[" + blueColor + sensorIndex + errorColor + "] !! tasks count[" + blueColor + tasksCount + errorColor + "]"
					+ " Exceeds the Settings queueCapacity[" + blueColor + queueCapacity + errorColor + "]"
					+ resetBG
					+ resetColor
				);
			} else {
				System.out.println(
					infoBG
					+ boldStyle + infoBG + "Info !! Sensor[" + blueColor + sensorIndex + infoBG + "] !! tasks count[" + blueColor + tasksCount + infoBG + "]"
					+ resetBG
					+ resetColor
				);
			}
			// for loop that goes until the value generated from the poisson distribution
			for (int taskIndex = 0; taskIndex < tasksCount; taskIndex++) {
				if (taskQueue.size() < queueCapacity) { // condition to check if queue is not full
					taskid++;             				// SR: increment task id to have a unique task id
					taskComplexity = Math.random()/4;	// compute value for taskComplexity
					System.out.println(("** Sensor ID[" + blueColor + sensorIndex + resetColor + "] Task ID[" + blueColor + taskid + resetColor + "] Complexity:[" + blueColor + taskComplexity + resetColor + "]"));
					Task task = new Task(sensorIndex, taskid, taskComplexity);	// create the tasks passing them their task id and taskComplexity
					taskQueue.add(task);                // add task to the queue 
				}
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
	private int sensorID;
	private int taskID;
	private double taskComplexity;

	public Task(int sensorID, int taskID, double taskComplexity) {   // passing task ID and complexity
		this.sensorID = sensorID;
		this.taskID = taskID;
		this.taskComplexity = taskComplexity;
	}
	public int getTaskSensorID() {
		return this.sensorID;
	}
	public int getTaskID() {
		return this.taskID;
	}
	public double getTaskComplexity() {    // getters for complexity and id
		return this.taskComplexity;
	}
}