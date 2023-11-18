package osone;

import osone.MyMath;
import osone.ConsoleStyles;
import osone.Settings;
import java.util.Queue;

public class Sensor extends Thread {
	private Queue<Task> taskQueue;
	private int lambda;
	private int taskID = 0;
	private int tasksErrorCount = 0;

	// Extract settings parameters
	private int queueCapacity = Settings.Parameters.queueCapacity;
	private int sensorsCount = Settings.Parameters.sensorsCount;

	// sensor constructor
	public Sensor(Queue<Task> taskQueue, int lambda) {
		this.taskQueue = taskQueue;
		// from user input
		this.lambda = lambda;
		if (sensorsCount < 2) {
			MyUi.PrintMsgErrorSensorCount();
			System.exit(0);
		}
	}
	// get method for lambda from user input
	public int GetLambda() {
		return this.lambda;
	}
	public int GetTaskCount() {
		return this.taskID;
	}
	public int GetTaskErrorCount() {
		return this.tasksErrorCount;
	}

	@Override
	public void run() {
		while (true) {
			generateTasks();
		}
	}
	
	// generateTasks method for sensor
	public synchronized void generateTasks() {
		// SR: complexity a single real-valued number corresponding to the com-plexity of the task (0.1 <= taskComplexity <= 0.5)
		double taskComplexity;

		for (int sensorIndex = 0; sensorIndex < sensorsCount; sensorIndex++) {
			// SR: generates number of tasks = poisson distribution
			// Issue: Noted that poisson distribution sometimes return 0
			// Solution: so we do loop till we get a bigger value
			int tasksCount = 0;
			while (tasksCount == 0) {
				tasksCount = MyMath.GetPoisson(lambda);
			}
			MyUi.PrintedSenseCapacityCheck(tasksCount, queueCapacity, sensorIndex);
			// for loop that goes until the value generated from the poisson distribution
			for (int taskIndex = 0; taskIndex < tasksCount; taskIndex++) {
				if (taskQueue.size() < queueCapacity) { // condition to check if queue is not full
					taskID++;             				// SR: increment task id to have a unique task id
					taskComplexity = MyMath.CalculateComplexity();
					MyUi.PrintedSenseNewTaskMsg(sensorIndex, taskID, taskComplexity);
					Task task = new Task(sensorIndex, taskID, taskComplexity);	// create the tasks passing them their task id and taskComplexity
					taskQueue.add(task);                // add task to the queue 
				} else {
					tasksErrorCount++;
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

	public Task(int sensorID, int taskID, double taskComplexity) {
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
		double taskComplexity = this.taskComplexity;
		// SR: validate the used taskComplexity value as per the CourseWork sheet
		if (taskComplexity < .1 || taskComplexity > .5) {
			MyUi.PrintedComplexityErrorMsg(taskComplexity);
		}
		return taskComplexity;
	}
}