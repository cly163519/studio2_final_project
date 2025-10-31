package manager;

import datacontroller.TaskDataController;
import model.Task;
import util.Constants;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.Objects;
import java.util.stream.Collectors;

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
        if (initialized) {
            return;
        }

        tasks = new ArrayList<>(TaskDataController.loadTasks());
        nextId = tasks.stream()
                .map(Task::getId)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
        initialized = true;
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
        if (!initialized) {
            init();
        }

        Objects.requireNonNull(title, "title");
        Objects.requireNonNull(description, "description");
        Objects.requireNonNull(category, "category");

        // Priority is set to 1 for all tasks by default
        Task task = new Task(nextId++, title, description, 1, category);
        tasks.add(task);
        TaskDataController.saveTasks(tasks);
        return task;
    }


    // ========== Read ==========

    /**
     * Get all tasks
     * @return List<Task> all tasks
     */
    public static List<Task> getAllTasks() {
        if (!initialized) {
            init();
        }
        return new ArrayList<>(tasks);
    }

    /**
     * Get task by ID
     * @param taskId Task ID
     * @return Task task object or null
     */
    public static Task getTaskById(int taskId) {
        if (!initialized) {
            init();
        }

        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        return null;
    }

    /**
     * Get uncompleted tasks
     * @return List<Task> uncompleted tasks
     */
    public static List<Task> getUncompletedTasks() {
        if (!initialized) {
            init();
        }

        return tasks.stream()
                .filter(task -> !task.isCompleted())
                .collect(Collectors.toList());
    }

    /**
     * Get completed tasks
     * @return List<Task> completed tasks
     */
    public static List<Task> getCompletedTasks() {
        if (!initialized) {
            init();
        }

        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by category
     * @param category Category to filter
     * @return List<Task> tasks in category
     */
    public static List<Task> getTasksByCategory(String category) {
        if (!initialized) {
            init();
        }

        String normalized = category == null ? "" : category.trim().toLowerCase();
        return tasks.stream()
                .filter(task -> task.getCategory() != null
                        && task.getCategory().trim().toLowerCase().equals(normalized))
                .collect(Collectors.toList());
    }


    // ========== Update ==========

    /**
     * Complete a task (rewards coins)
     * @param taskId Task ID
     * @return boolean true if success
     */
    public static boolean completeTask(int taskId) {
        Task task = getTaskById(taskId);
        if (task == null || task.isCompleted()) {
            return false;
        }

        task.setCompleted(true);
        TaskDataController.saveTasks(tasks);
        return true;
    }

    /**
     * Update task information
     * @param taskId Task ID
     * @param newTitle New title
     * @param newDescription New description
     * @return boolean true if success
     */
    public static boolean updateTask(int taskId, String newTitle, String newDescription) {
        Task task = getTaskById(taskId);
        if (task == null) {
            return false;
        }

        if (newTitle != null) {
            task.setTitle(newTitle);
        }
        if (newDescription != null) {
            task.setDescription(newDescription);
        }
        TaskDataController.saveTasks(tasks);
        return true;
    }


    // ========== Delete ==========

    /**
     * Delete a task
     * @param taskId Task ID
     * @return boolean true if success
     */
    public static boolean deleteTask(int taskId) {
        if (!initialized) {
            init();
        }

        boolean removed = tasks.removeIf(task -> task.getId() == taskId);
        if (removed) {
            TaskDataController.saveTasks(tasks);
        }
        return removed;
    }
}