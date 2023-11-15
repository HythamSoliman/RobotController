package operatingsystemsassignment;

import operatingsystemsassignment.ConsoleStyles;
import java.util.LinkedList;
import java.util.Queue;

public class Actuator extends Thread {

	public Queue<Result> actuateQueue = new LinkedList<>();
	private Object produceLock;
	private Object consumeLock;
	public double currentpos;
	public double newpos;
	public double rem;
	public double value;
	public boolean movingRight = true;
	public double pos0;
	
	public void run(){
		while (true) {
			consume();
			try {
				// it is the time to sleep
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// actuating constructor
	public Actuator(Queue<Result> actuateQueue, Object produceLock, Object consumeLock, double pos0) {
		this.actuateQueue = actuateQueue;
		this.produceLock = produceLock;
		this.consumeLock = consumeLock;
		this.pos0 = pos0;
	}
	
    public double getPos0() {
    	return this.pos0;
    }
	public void printedMsg(int TaskId, Double Complexity, boolean first, Double Distance, Double CurPos, Double NewPos ) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String greenColor = ConsoleStyles.greenColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String move_string = "";
		if (first) {
			move_string = " Robot first move.";
		} else {
			move_string = " Robot next moving.";
		}
		System.out.println(
			resetColor + "** "
			+ greenColor + move_string
			+ resetColor + " Task id [" + blueColor + TaskId + resetColor + "] Complexity:[" + blueColor + Complexity + resetColor + "]"
			+ " distance:[" + greenColor + Distance + resetColor + "]"
			+ " old position:[" + greenColor + CurPos + resetColor + "]"
			+ " new position:[" + greenColor + NewPos + resetColor + "]"
		);
	}

	// synchronized consume method 
	public synchronized void consume() {
        while (true) {
            synchronized (this.consumeLock) {
                if (actuateQueue.size() != 0) {
                	Result r = actuateQueue.poll();
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