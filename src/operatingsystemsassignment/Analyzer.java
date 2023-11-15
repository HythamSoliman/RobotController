package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import java.util.Queue;

public class Analyzer extends Thread {
	public Queue<Task> task_queue;
	public Queue<Result> actuate_queue;
	public int capacity = 5;
	public int currentpos = 0;
	public int analysisConstant;

	public Analyzer(Queue<Task> task_queue, Queue<Result> actuate_queue, int analysisConstant) {   // analyzer constructor
		this.task_queue = task_queue;
		this.actuate_queue = actuate_queue;
		this.analysisConstant = analysisConstant;
	}

	@Override
	public void run() {     // run method for analyzer
		while (true) {
			consume();
		}
	}

	public synchronized void consume() 
	{
		if (task_queue.size() != 0) {            // condition checking if task_queue is not empty 
			Task task = task_queue.poll();          // takes out the element at the front of the queue and returns it, assigning it as "task"
			// creating the result, which takes the Id and c from the task
			Result r = new Result(task.getRandomID(), task.getRandomComplexity(), Math.pow((1 - task.getRandomComplexity()), analysisConstant));
			if (actuate_queue.size() != capacity) {       // if actuate_queue is not full
				String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
				String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
				System.out.println(("** Task id [" + blueColor + (int)r.getID() + resetColor + "] analyzing ..."));
				actuate_queue.add(r);   // add result to q2 
			}
			try {
				Thread.sleep((int)(task.getRandomComplexity())*100);
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