package osone;

import osone.ConsoleStyles;
import java.util.LinkedList;
import java.util.Queue;

public class Actuator extends Thread {

	private Queue<Result> actuateQueue = new LinkedList<>();
	private Object produceLock;
	private Object consumeLock;
	private double currentpos;
	private double newpos;
	private double rem;
	private double value;
	private boolean movingRight = true;
	private double pos0;
	
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
	public void printedMsg(int SensorId, int TaskId, Double Complexity, Double Distance, Double CurPos, Double NewPos ) {
		String blueColor = ConsoleStyles.blueColor + ConsoleStyles.boldStyle;
		String greenColor = ConsoleStyles.greenColor + ConsoleStyles.boldStyle;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		String move_string =  "Robot moving.";
		System.out.println(
			resetColor + "** "
			+ greenColor + move_string
			+ resetColor + " Sensor ID[" + blueColor + SensorId + resetColor + "]"
			+ " Task ID[" + blueColor + TaskId + resetColor + "] Complexity[" + blueColor + Complexity + resetColor + "]"
			+ " Y:[" + greenColor + Distance + resetColor + "]"
			+ " old pos:[" + greenColor + CurPos + resetColor + "]"
			+ " new pos:[" + greenColor + NewPos + resetColor + "]"
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
                		if (analysisResult.getResultMoveDistance() + pos0 <= 1) {
                    			value = analysisResult.getResultMoveDistance() + pos0;
                    			newpos = value;
								printedMsg(
									analysisResult.getResultSensorID(), 
									analysisResult.getResultTaskID(),
									analysisResult.getResultComplexity(),
									analysisResult.getResultMoveDistance(),
									getPos0(),
									newpos
								);
                    			currentpos = newpos;
						} else {
							rem = -1 + (analysisResult.getResultMoveDistance() + pos0);
							value = 1 - rem;
							newpos = value;
							printedMsg(
								analysisResult.getResultSensorID(),
								analysisResult.getResultTaskID(),
								analysisResult.getResultComplexity(),
								analysisResult.getResultMoveDistance(),
								getPos0(),
								newpos
							);
							currentpos = newpos;
							movingRight = false;
                		}
                	} else {
						if (movingRight == true)
						{
							if (analysisResult.getResultMoveDistance() + currentpos <= 1) {
								value = analysisResult.getResultMoveDistance() + currentpos;
								newpos = value;
							} else  {
								rem = -1 + (analysisResult.getResultMoveDistance() + currentpos);
								value = 1 - rem;
								newpos = value;
								movingRight = false;
							}
						} else {
							if (currentpos - analysisResult.getResultMoveDistance() >= 0) {
								value =  currentpos - analysisResult.getResultMoveDistance();
								newpos = value;
							}
							else  {
								value = Math.abs(currentpos - analysisResult.getResultMoveDistance());
								newpos = value;
								movingRight = true;
							}
						}
						printedMsg(
							analysisResult.getResultSensorID(),
							analysisResult.getResultTaskID(),
							analysisResult.getResultComplexity(),
							analysisResult.getResultMoveDistance(),
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