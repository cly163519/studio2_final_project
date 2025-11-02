package manager;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private static ArrayList<Task> tasks = new ArrayList<>();
    private static int money = 0;
    private static List<Runnable> listeners = new ArrayList<>();

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static int getCoins() {
        return money;
    }

    public static void addTask(String title, String category) {
        tasks.add(new Task(title, category));
        notifyListeners();
    }

    public static void deleteTask(String title) {
        tasks.removeIf(t -> t.getTitle().equalsIgnoreCase(title));
        notifyListeners();
    }

    public static void markTaskDone(String title) {
        for (Task t : tasks) {
            if (t.getTitle().equalsIgnoreCase(title) && !t.isCompleted()) {
                t.setCompleted(true);
                money += 5; // reward coins
            }
        }
        notifyListeners();
    }

    public static void addListener(Runnable r) {
        listeners.add(r);
    }

    public static void removeListener(Runnable r) {
        listeners.remove(r);
    }

    private static void notifyListeners() {
        for (Runnable r : listeners) {
            r.run();
        }
    }

    public static void addCoins(int amount) {
        money += amount;
        notifyListeners();
    }
}
