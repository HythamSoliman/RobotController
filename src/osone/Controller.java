package osone;

import osone.ConsoleStyles;
import osone.Settings;
import osone.MyUi;
import osone.MyUi.UserInput;

import java.util.LinkedList;
import java.util.Queue;

public class Controller {
	public static void main(String[] args) throws InterruptedException {
		String errorColor = ConsoleStyles.errorColor;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;

		UserInput userInputValues = MyUi.dataEntry();

		// creating instances of the queues 
		Queue<Task> queue1 = new LinkedList<>();
		Queue<Result> queue2 = new LinkedList<>();
		
		Object actuateConsumeLock = new Object();
		Object actuateProduceLock = new Object();
		Sensor sense = new Sensor(queue1,  userInputValues.getLambdaValue());
		Analyzer analyze = new Analyzer(queue1, queue2);   // instance of analyzer
		Actuator actuate = new Actuator(queue2, actuateProduceLock, actuateConsumeLock, userInputValues.getPos0Value());
		
		// SR: creating the main 3 stages / threads
		Thread thread1 = new Thread(sense);
		Thread thread2 = new Thread(analyze);
		Thread thread3 = new Thread(actuate);

		thread1.start();
		thread2.start();
		thread3.start();

        try {
        	Thread.sleep(Settings.Parameters.lifeTime);
        }
        catch(InterruptedException e) {
        	System.out.println(errorColor + "Interrupted all" + resetColor);
        }
        System.exit(0);
	}
}