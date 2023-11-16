package osone;

import osone.ConsoleStyles;
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
	public void printedMsg(int SensorId, int TaskId, Double Complexity, boolean first, Double Distance, Double CurPos, Double NewPos ) {
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
			+ resetColor + " Sensor id [" + blueColor + SensorId + resetColor + "]"
			+ " Task id [" + blueColor + TaskId + resetColor + "] Complexity:[" + blueColor + Complexity + resetColor + "]"
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
                	Result analysisResult = actuateQueue.poll();
					// to set the initial position from user input as the first position of the robot
					if (analysisResult.getResultTaskID() == 1) {
                		if (analysisResult.getResultMoveDist() + pos0 <= 1) {
                    			value = analysisResult.getResultMoveDist() + pos0;
                    			newpos = value;
								printedMsg(
									analysisResult.getResultSensorID(), 
									analysisResult.getResultTaskID(),
									analysisResult.getResultComplexity(),
									true,
									analysisResult.getResultMoveDist(),
									getPos0(),
									newpos
								);
                    			currentpos = newpos;
						} else {
							rem = -1 + (analysisResult.getResultMoveDist() + pos0);
							value = 1 - rem;
							newpos = value;
							printedMsg(
								analysisResult.getResultSensorID(),
								analysisResult.getResultTaskID(),
								analysisResult.getResultComplexity(),
								true,
								analysisResult.getResultMoveDist(),
								getPos0(),
								newpos
							);
							currentpos = newpos;
							movingRight = false;
                		}
                	} else {
						if (movingRight == true)
						{
							if (analysisResult.getResultMoveDist() + currentpos <= 1) {
								value = analysisResult.getResultMoveDist() + currentpos;
								newpos = value;
								}
							else  {
								rem = -1 + (analysisResult.getResultMoveDist() + currentpos);
								value = 1 - rem;
								newpos = value;
								movingRight = false;
							}
						} else {
							if (currentpos - analysisResult.getResultMoveDist() >= 0) {
								value =  currentpos - analysisResult.getResultMoveDist();
								newpos = value;
							}
							else  {
								value = Math.abs(currentpos - analysisResult.getResultMoveDist());
								newpos = value;
								movingRight = true;
							}
						}
						printedMsg(
							analysisResult.getResultSensorID(),
							analysisResult.getResultTaskID(),
							analysisResult.getResultComplexity(),
							false,
							analysisResult.getResultMoveDist(),
							currentpos,
							newpos
						);
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