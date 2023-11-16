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

	@Override
	public void run() {     // run method for analyzer
		while (true) {
			consume();
		}
	}

	public synchronized void consume() {
		if (taskQueue.size() != 0) {            // condition checking if taskQueue is not empty 
			Task task = taskQueue.poll();          // takes out the element at the front of the queue and returns it, assigning it as "task"
			// creating the result, which takes the Id and c from the task
			Result r = new Result(task.getTaskID(), task.getTaskComplexity(), Math.pow((1 - task.getTaskComplexity()), analysisConstant));
			if (actuateQueue.size() != capacity) {       // if actuateQueue is not full
				String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
				String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
				System.out.println(("** Task id [" + blueColor + (int)r.getID() + resetColor + "] analyzing ..."));
				actuateQueue.add(r);   // add result to q2 
			}
			try {
				Thread.sleep((int)(task.getTaskComplexity())*100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {

		}
	}
}

class Result { 
	public double id;
	public double comp;
	public double movedist;
	public double analysisConstant;
	
	public Result(double id, double comp, double movedist) {
		this.id = id;
		this.comp = comp;
		this.movedist = movedist;
	}
	public double getID() {
		return this.id;
	}
	public double getComp() {
		return this.comp;
	}
	public double getMoveDist() {
		return this.movedist;
	}
	public double getAnalysisConstant() {
		return this.analysisConstant;
	}
}