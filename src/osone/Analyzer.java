package osone;

import osone.ConsoleStyles;
import osone.Settings;
import java.util.Queue;

public class Analyzer extends Thread {
	private Queue<Task> taskQueue;
	private Queue<Result> actuateQueue;
	private int capacity = Settings.Parameters.queueCapacity;
	private int currentpos = 0;
	private int analysisConstant;

	public Analyzer(Queue<Task> taskQueue, Queue<Result> actuateQueue, int analysisConstant) {   // analyzer constructor
		this.taskQueue = taskQueue;
		this.actuateQueue = actuateQueue;
		this.analysisConstant = analysisConstant;
	}

	// run method for analyzer
	@Override
	public void run() {
		while (true) {
			consume();
		}
	}

	public synchronized void consume() {
		if (taskQueue.size() != 0) {            	// condition checking if taskQueue is not empty 
			Task task = taskQueue.poll();          	// takes out the element at the front of the queue and returns it, assigning it as "task"
			double taskComplexity = task.getTaskComplexity();
			int taskSensorID = task.getTaskSensorID();
			// creating the result, which takes the Id and c from the task
			Result result = new Result(taskSensorID, task.getTaskID(), taskComplexity, Math.pow((1 - taskComplexity), analysisConstant));
			// Result result = new Result(taskSensorID, task.getTaskID(), taskComplexity, Math.sqrt(1 / taskComplexity));
			if (actuateQueue.size() != capacity) {       // if actuateQueue is not full
				String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
				String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
				System.out.println(("** Sensor ID[" + blueColor + taskSensorID + resetColor + "] Task ID[" + blueColor + result.getResultTaskID() + resetColor + "] analyzing ..."));
				actuateQueue.add(result);   // add result to q2 
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
	public int sensorID;
	public int taskID;
	public double complexity;
	public double moveDistance;
	public double analysisConstant;
	
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
	public double getResultMoveDist() {
		return this.moveDistance;
	}
}