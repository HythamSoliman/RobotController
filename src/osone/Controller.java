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
		String boldStyle = ConsoleStyles.boldStyle;
		String greenColor = ConsoleStyles.greenColor;
		String blinkOn = ConsoleStyles.blinkOn;
		String resetColor = ConsoleStyles.resetColor + ConsoleStyles.resetBold;
		// Section 1: User Input Collection + Section 2: Information + Section 3: Settings
		UserInput userInputValues = MyUi.dataEntry();

		// Section 4: Thread Processing Logs
		// creating instances of the queues FIFO
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
		// Section 5 : Conclusion
		int senseCount = sense.GetTaskCount();
		int senseErrorCount = sense.GetTaskErrorCount();
		int analyzeCount = analyze.GetResultCount();
		int analyzeErrorCount = analyze.GetResultErrorCount();
		int actuateCount = actuate.GetMoveCount();
		int actuateErrorCount = actuate.GetMoveErrorCount();

		System.out.println(errorColor + "\n\nSensor's Tasks Count:      Queued(" + senseCount   + ") Errors or UnQueued due to capacity (" + senseErrorCount   + ")" + resetColor);
		System.out.println(errorColor + "Analyzer Results Count: Processed(" + analyzeCount + ") Errors or UnQueued due to capacity (" + analyzeErrorCount + ")" + resetColor);
		System.out.println(errorColor + "Moves Actions Count:    Processed(" + actuateCount + ") Errors (" + actuateErrorCount + ")" + resetColor);

		if (
				(senseCount != analyzeCount + analyzeErrorCount)
			 || (analyzeCount != actuateCount)
		) {
			System.out.println(errorColor + "\nQueue Processing is different, it may be due to the queue capacity issue");
		} else {
			System.out.println(greenColor + boldStyle + blinkOn + "\nQueue Processing Success\n\n");
		}
		
		// End threads and exit the progarm
        System.exit(0);
	}
}