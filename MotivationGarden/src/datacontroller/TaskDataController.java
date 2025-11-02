package datacontroller;

import model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * TaskDataController - Handles task data persistence
 *
 * NOTE: This is a stub implementation that returns empty data.
 * For full functionality, add Gson library (gson-2.8.9.jar or newer) to the lib folder
 * and implement proper JSON serialization/deserialization.
 *
 * To add Gson:
 * 1. Download gson jar from https://repo1.maven.org/maven2/com/google/code/gson/gson/
 * 2. Place in MotivationGarden/lib/ folder
 * 3. Add to project classpath in your IDE
 */
public class TaskDataController {

    /**
     * Load tasks from storage
     *
     * Currently returns empty list (stub implementation)
     *
     * @return List<Task> empty list of tasks
     */
    public static List<Task> loadTasks() {
        // TODO: Implement JSON file loading once Gson library is added
        // For now, return empty list to allow project to compile and run
        System.out.println("TaskDataController: Stub implementation - returning empty task list");
        return new ArrayList<>();
    }

    /**
     * Save tasks to storage
     *
     * Currently does nothing (stub implementation)
     *
     * @param tasks List of tasks to save
     */
    public static void saveTasks(List<Task> tasks) {
        // TODO: Implement JSON file saving once Gson library is added
        // For now, do nothing to allow project to compile and run
        System.out.println("TaskDataController: Stub implementation - tasks not persisted");
    }
}
