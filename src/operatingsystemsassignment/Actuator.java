package operatingsystemsassignment;

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
        
	
public Actuator(Queue<Result> qu2, Object produceLock, Object consumeLock, double pos0) {  // actuating constructor 
		
		this.qu2 = qu2;
		this.produceLock = produceLock;
		this.consumeLock = consumeLock;
		this.pos0 = pos0;
	}
	
    public double getPos0() {
    	
    	return this.pos0; 
    	
    }

	public synchronized void consume()   // synchronized consume method 
    {
        while (true) {
            synchronized (this.consumeLock)
            {

                if (qu2.size() != 0) {
                  

                Result r = qu2.poll(); 
                
                if (r.getID() == 1) {                  // to set the initial position from user input as the first position of the robot
                		if (r.movedist + pos0 <= 1) {
                    			value = r.movedist + pos0;
                    			newpos = value;
                    			System.out.println("Robot moving." + " task id {" + r.getID() + "}" + "task complexity {" +  r.getComp() + "} result (distance to move) {" + r.getMoveDist() + "} old position: {" + getPos0() + "} new position: {" + newpos + "}" ); 
                    			currentpos = newpos; 
                    			}
                        	
                        else  {
                    			rem = -1 + (r.movedist + pos0); 
                    			value = 1 - rem; 
                        		newpos = value;
                        		System.out.println("Robot moving." + " task id {" + r.getID() + "}" + "task complexity {" +  r.getComp() + "} result (distance to move) {" + r.getMoveDist() + "} old position: {" + getPos0() + "} new position: {" + newpos + "}" ); 
                        		currentpos = newpos; 
                        		movingRight = false; 
                    		
                }
           
                }
                else {
            	
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
                }
                
                else
                    {
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
          		System.out.println("Robot moving." + " task id {" + r.getID() + "}" + "task complexity {" +  r.getComp() + "} result (distance to move) {" + r.getMoveDist() + "} old position: {" + currentpos + "} new position: {" + newpos + "}" ); 
          		currentpos = newpos;  // updating the current position as the new one for the next one 
                } 		
                }

                
				synchronized (this.produceLock) {
		            produceLock.notifyAll();
		           }
        
		
        	}
        }
    }
}
	

		
		
	

	

