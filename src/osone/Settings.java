package osone;
public class Settings {
    public class Parameters {
        // tasks and actuate queue max items count
        public static int queueCapacity = 10;

        // Specify how many sensors the system is going to handle (to achieve many, it should be more than 2)
        public static int sensorsCount = 5;

        // The time in millisecons for the threads to execute thier tasks, it is controller by the main thread
        public static int lifeTime = 5_000;

        // 1 means full distance, given that min is 0 to achieve range [0, maxTaskDistance]
        public static boolean useMaxTaskDistance = true;
        public static double maxTaskDistance = 1;
    }
}