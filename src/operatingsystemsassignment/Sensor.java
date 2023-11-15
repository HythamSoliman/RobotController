    package operatingsystemsassignment; 
    import java.util.Queue;

    public class Sensor extends Thread {  // sensor extends from thread
    	
    	private Queue<Task> q;
        private int capacity = 5;
    	private int taskid=0;
    	public int lambda; 
    	 
    	
    	public Sensor(Queue<Task> q, int lambda) { // sensor constructor 
    		this.q = q;
    		this.lambda = lambda;   // user input 
    	}
    	
    	public int getLambda() {    // get method for lambda from user input 
    		
    		return this.lambda; 
    		
    	}
    
        @Override
    	public void run() {
           
        	while (true) {
        		
        		produce(); 
    		
        	}    	   
    	            }
    
        
    
    	public int getPoisson(int lambda) {         // poisson distribution equation 
    		  double L = Math.exp(-(lambda));
    		  double p = 1.0;
    		  int k = 0;

    		  do {
    		    k++;
    		    p *= Math.random();
    		  } while (p > L);
              int result = k-1;
      		 
    		  return result;
  
    		  
    		} 
    	
    	
    	
  	public synchronized void produce()   // produce method for sensor; 
  	{
  		
  		double c;   // complexity 
		
		
		for (int i = 0; i < getPoisson(lambda); i++) {    // for loop that goes until the value generated from the poisson distribution, generates number of tasks = poisson distribution
	
			
			if (q.size() <= capacity) {  // condition to check if queue is not full 
			 
    	    	taskid++;              // increment task id to give unique task id 
    	    	c=Math.random()/4;                // compute value for c
    	    	Task task = new Task(taskid, c);       // create the tasks passing them their task id and c
		    	q.add(task);                            // add task to the queue 
	    		System.out.println(("Task id:" + taskid + " Task Complexity:" + c));    
			}
		  }
		 try {
			Thread.sleep(1_000);              // sleep time 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
    		 
    		
    	}
    }
    	 
        class Task {                     
    	
        private int randomID;
        private double randomComplexity; 

        public Task(int randomID, double randomComplexity) {   // passing task the ID and complexity 

           this.randomComplexity = randomComplexity; 
           this.randomID = randomID;


        }	
   
        public double getRandomComplexity() {    // getters for complexity and id
        	
        	return this.randomComplexity;
        }


        public int getRandomID() {
        	
        	return this.randomID; 
        	
        }

      }
    
        

