package manager;

import datacontroller.TaskDataController;
import model.Task;
import util.Constants;
import java.util.List;

/**
 * TaskManager - Manages all tasks
 * Storage: List<Task> tasks (stored here!)
 * File: data/tasks.json
 */
public class TaskManager {
    
    // ========== Fields ==========
    
    private static List<Task> tasks;           // Task list
    private static boolean initialized;        // Init flag
    private static int nextId;                 // Next task ID
    
    
    // ========== Initialization ==========
    
    /**
     * Initialize task manager (load from file)
     */
    public static void init() {
        // TODO: Implementation
    }
    
    
    // ========== Create ==========
    
    /**
     * Create a new task
     * @param title Task title
     * @param description Task description
     * @param category Category (work/life/study)
     * @return Task created task object
     */
    public static Task createTask(String title, String description, String category) {
        // TODO: Implementation
        return null;
    }
    
    
    // ========== Read ==========
    
    /**
     * Get all tasks
     * @return List<Task> all tasks
     */
    public static List<Task> getAllTasks() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get task by ID
     * @param taskId Task ID
     * @return Task task object or null
     */
    public static Task getTaskById(int taskId) {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get uncompleted tasks
     * @return List<Task> uncompleted tasks
     */
    public static List<Task> getUncompletedTasks() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get completed tasks
     * @return List<Task> completed tasks
     */
    public static List<Task> getCompletedTasks() {
        // TODO: Implementation
        return null;
    }
    
    /**
     * Get tasks by category
     * @param category Category to filter
     * @return List<Task> tasks in category
     */
    public static List<Task> getTasksByCategory(String category) {
        // TODO: Implementation
        return null;
    }
    
    
    // ========== Update ==========
    
    /**
     * Complete a task (rewards coins)
     * @param taskId Task ID
     * @return boolean true if success
     */
    public static boolean completeTask(int taskId) {
        // TODO: Implementation
        return false;
    }
    
    /**
     * Update task information
     * @param taskId Task ID
     * @param newTitle New title
     * @param newDescription New description
     * @return boolean true if success
     */
    public static boolean updateTask(int taskId, String newTitle, String newDescription) {
        // TODO: Implementation
        return false;
    }
    
    
    // ========== Delete ==========
    
    /**
     * Delete a task
     * @param taskId Task ID
     * @return boolean true if success
     */
    public static boolean deleteTask(int taskId) {
        // TODO: Implementation
        return false;
    }
}