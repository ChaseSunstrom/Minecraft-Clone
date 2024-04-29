import game.*;
import game.events.Event;
import game.events.EventSubscription;
import game.events.SubscriptionTopic;
import game.util.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

class Main {
    public static void main(String[] args) {
        // Initialize the thread pool with a certain number of threads, e.g., 4
        ThreadPool.initialize(10);

        ArrayList<Future<String>> taskFutures = new ArrayList<>();

        // Loop to enqueue the task 10 times
        for (int i = 0; i < 100; i++) {
            int taskNumber = i + 1;  // Task number for identification in output
            Future<String> task = ThreadPool.enqueue(
                    ThreadPool.TaskPriority.NORMAL,
                    false,
                    () -> {
                        System.out.println("Executing Task " + taskNumber);
                        Thread.sleep(1000);  // Simulate some work by sleeping for 1 second
                        return "Task " + taskNumber + " Completed";
                    }
            );
            taskFutures.add(task);  // Store the Future to retrieve results later
        }

        // Retrieve and print the results of all tasks
        taskFutures.forEach(future -> {
            try {
                System.out.println(future.get());  // Wait for each task to complete and print its result
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        // Shut down the thread pool to clean up resources
        ThreadPool.shutdown();
    }

    public static void OnEvent(Event event) {
        System.out.println(event.toString());
    }
}
