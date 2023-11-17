package osone;

import osone.ConsoleStyles;
import osone.Settings;

public class MyMath {
    static public double calculate_y (double taskComplexity) {
        String msgStyle = ConsoleStyles.greenColor + ConsoleStyles.blackBG;
        String resetStyle = ConsoleStyles.resetAll;

        // double Y = Math.pow((1 - taskComplexity), analysisConstant); // xxxxxxx for 2022 worksheet

        // The value will be always in range [2, 10] as 0.1 <= taskComplexity <= 0.5
        double value = 1.0 / taskComplexity;
        if (value < 2 || value > 10) {
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
        double Y = calculate_scale(distance);
        return Y;
    }
    static private double calculate_scale (double value) {
        double Y = value;
        double range_min = Math.sqrt(1/0.1); // min Complexity 
        double range_max = Math.sqrt(1/0.5); // max Complexity
        double scale_min = 0;
        double scale_max = Settings.Parameters.maxTaskDistance;
        if (Settings.Parameters.useMaxTaskDistance) {
            Y = (value - range_min) * (scale_max / (range_max - range_min));
        }
        return Y;
    }
}