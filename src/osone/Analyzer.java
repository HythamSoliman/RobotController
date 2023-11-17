package osone;

import osone.MyMath;
import osone.ConsoleStyles;
import osone.Settings;
import java.util.Queue;

public class Analyzer extends Thread {
	private Queue<Task> taskQueue;
	private Queue<Result> actuateQueue;
	private int capacity = Settings.Parameters.queueCapacity;
	private int currentpos = 0;

	public Analyzer(Queue<Task> taskQueue, Queue<Result> actuateQueue) {   // analyzer constructor
		this.taskQueue = taskQueue;
		this.actuateQueue = actuateQueue;
	}

	// run method for analyzer
	@Override
	public void run() {
		while (true) {
			consume();
		}
	}

	public synchronized void consume() {
		// condition checking if taskQueue is not empty
		if (taskQueue.size() != 0) {
			// takes out the element at the front of the queue and returns it, assigning it as "task"
			Task task = taskQueue.poll();
			int taskSensorID = task.getTaskSensorID();
			double taskComplexity = task.getTaskComplexity();
			double Y = MyMath.calculate_y(taskComplexity);
			 
			// SR: creating the result, which takes the Id and c from the task
			Result result = new Result(taskSensorID, task.getTaskID(), taskComplexity, Y);

			// if actuateQueue is not full
			if (actuateQueue.size() < capacity) {
				String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
				String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
				System.out.println("** Sensor ID[" + blueColor + taskSensorID + resetColor + "] Task ID[" + blueColor + result.getResultTaskID() + resetColor + "] analyzing ...");
				actuateQueue.add(result);   // add result to actuateQueue 
			}
			try {
				Thread.sleep(((int)taskComplexity)*100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {

		}
	}
}

class Result {
	private int sensorID;
	private int taskID;
	private double complexity;
	private double moveDistance;
	private double analysisConstant;
	
	public Result(int sensorID, int taskID, double complexity, double moveDistance) {
		this.sensorID = sensorID;
		this.taskID = taskID;
		this.complexity = complexity;
		this.moveDistance = moveDistance;
	}
	public int getResultSensorID() {
		return this.sensorID;
	}
	public int getResultTaskID() {
		return this.taskID;
	}
	public double getResultComplexity() {
		return this.complexity;
	}
	public double getResultMoveDistance() {
		return this.moveDistance;
	}
}