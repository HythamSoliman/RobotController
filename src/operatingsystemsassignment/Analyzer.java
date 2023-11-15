package operatingsystemsassignment;
import java.util.Queue;
public class Analyzer extends Thread {
	public Queue<Task> q1;
	public Queue<Result> q2;
	public int capacity = 5;
	public int currentpos = 0;
	public int analysisConstant;

	public Analyzer(Queue<Task> q1, Queue<Result> q2, int analysisConstant) {   // analyzer constructor
		this.q1 = q1;
		this.q2 = q2;
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
		if (q1.size() != 0) {            // condition checking if q1 is not empty 
			Task t = q1.poll();          // takes out the element at the front of the queue and returns it, assigning it as "t"
			Result r = new Result(t.getRandomID(), t.getRandomComplexity(), Math.pow((1 - t.getRandomComplexity()), analysisConstant)); // creating the result, which takes the Id and c from the task 
			if (q2.size() != capacity) {       // if q2 is not full 
				q2.add(r);   // add result to q2 
				System.out.println("analyzing task " + r.getID());
			}
			try {
				Thread.sleep((int)(t.getRandomComplexity())*100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
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