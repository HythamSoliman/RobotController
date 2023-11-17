package osone;

import osone.ConsoleStyles;
import osone.Settings;

public class MyMath {
    // poisson distribution equation = number of sensor/tasks
	static public int GetPoisson(int lambda) {
		double L = Math.exp(-lambda);
		double p = 1.0;
		int k = 0;
		do {
			k++;
			p *= Math.random();
		} while (p > L);
		return k-1;
	}
    // 0.1 <= taskComplexity <= 0.5 ...... [2, 10] ..... [1.414, 3.162]
    static public double CalculateY (double taskComplexity) {
        String msgStyle = ConsoleStyles.greenColor + ConsoleStyles.blackBG;
        String resetStyle = ConsoleStyles.resetAll;

        // The value will be always in range [2, 10] as 0.1 <= taskComplexity <= 0.5
        double value = 1.0 / taskComplexity;
        if (value < 2.0 || value > 10.0) {
            System.out.println(
                msgStyle + "distance inbetween value miss calculation: " + value
                + " C: " + taskComplexity
                + resetStyle
            );
        }

        // The result will be always in range [1.414, 3.162]
        double distance = Math.sqrt(value);
        if (distance < 1.414 || distance > 3.162) {
            System.out.println(
                msgStyle + "distance miss calculation: " + distance
                + " value: " + value
                + " C: " + taskComplexity
                + resetStyle
            );
        }
        return CalculateScale(distance);
    }
    static private double CalculateScale (double value) {
        double Y = value;
        double range_min = Math.sqrt(1/0.1); // min Complexity 
        double range_max = Math.sqrt(1/0.5); // max Complexity
        double scale_min = 0;
        double scale_max = Settings.Parameters.maxTaskDistance;
        if (Settings.Parameters.useMaxTaskDistance) {
            // Apply linear scalling
            Y = (value - range_min) * (scale_max / (range_max - range_min));
        }
        return Y;
    }
    static public double CalculateComplexity () {
		double taskComplexity = 0.1 + Math.random() * 0.4;
        return taskComplexity;
    }
}