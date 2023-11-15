package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import java.util.LinkedList;
import java.util.Queue;

public class Actuator extends Thread {

	public Queue<Result> qu2 = new LinkedList<>();
	private Object produceLock;
	private Object consumeLock;
	public double currentpos;
	public double newpos;
	public double x;
	public double rem;
	public double value;
	public boolean movingRight = true;
	public double pos0;
	
	public void run(){
		while (true) {
			consume();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// actuating constructor
	public Actuator(Queue<Result> qu2, Object produceLock, Object consumeLock, double pos0) {
		this.qu2 = qu2;
		this.produceLock = produceLock;
		this.consumeLock = consumeLock;
		this.pos0 = pos0;
	}
	
    public double getPos0() {
    	return this.pos0;
    }
	public void printedMsg(int TaskId, Double Complexity, boolean first, Double Distance, Double CurPos, Double NewPos ) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String redColor = ConsoleStyles.redColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;

		System.out.print("** Task id [" + blueColor + TaskId + resetColor + "]");
		System.out.print(" Complexity:[" + blueColor + Complexity + resetColor + "]");
		if (first) {
			System.out.println(" Robot first move with result");
		} else {
			System.out.println(" Robot next moving with result");
		}
		System.out.println("\t\tdistance to move:\t[" + redColor + Distance + resetColor + "]");
		System.out.println("\t\told position:\t\t[" + redColor + CurPos + resetColor + "]");
		System.out.println("\t\tnew position:\t\t[" + redColor + NewPos + resetColor + "]\n");
	}

	// synchronized consume method 
	public synchronized void consume() {
        while (true) {
            synchronized (this.consumeLock) {
                if (qu2.size() != 0) {
                	Result r = qu2.poll();
					// to set the initial position from user input as the first position of the robot
					if (r.getID() == 1) {
                		if (r.movedist + pos0 <= 1) {
                    			value = r.movedist + pos0;
                    			newpos = value;
								printedMsg((int)r.getID(), r.getComp(), true, r.getMoveDist(), getPos0(), newpos);
                    			currentpos = newpos;
						} else {
							rem = -1 + (r.movedist + pos0);
							value = 1 - rem;
							newpos = value;
							printedMsg((int)r.getID(), r.getComp(), true, r.getMoveDist(), getPos0(), newpos);
							currentpos = newpos;
							movingRight = false;
                		}
                	} else {
						if (movingRight == true)
						{
							if (r.movedist + currentpos <= 1) {
								value = r.movedist + currentpos;
								newpos = value;
								}
							
							else  {
								rem = -1 + (r.movedist + currentpos);
								value = 1 - rem;
								newpos = value;
								movingRight = false;
							}
						} else {
							if (currentpos - r.getMoveDist() >= 0) {
								value =  currentpos - r.getMoveDist();
								newpos = value;
							}
							else  {
								value = Math.abs(currentpos - r.getMoveDist());
								newpos = value;
								movingRight = true;
							}
						}
						printedMsg((int)r.getID(), r.getComp(), false, r.getMoveDist(), currentpos, newpos);
						// updating the current position as the new one for the next one 
						currentpos = newpos;
					}
				}
				synchronized (this.produceLock) {
		            produceLock.notifyAll();
		        }
        	}
        }
    }
}