package ui;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import manager.TaskManager;
import model.Task;
import util.Constants;

/**
 * ToDoUI - Helper class for task management
 * Provides utility methods for task operations and default task creation
 * No longer creates a separate window - integrated into main GUI
 */
public class ToDoUI {

    /**
     * Ensure default tasks exist in the system
     * Creates predefined tasks for each category if they don't already exist
     */
    public static void ensureDefaultTasks() {
        Map<String, CategoryDefinition> categories = buildCategories();

        Set<String> existingTitles = TaskManager.getAllTasks().stream()
                .map(Task::getTitle)
                .filter(title -> title != null)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        categories.values().forEach(category -> category.items().forEach(item -> {
            String normalizedTitle = item.toLowerCase();
            if (!existingTitles.contains(normalizedTitle)) {
                TaskManager.createTask(item, item, category.key());
                existingTitles.add(normalizedTitle);
            }
        }));
    }

    /**
     * Get display name for a category
     * @param category Category key
     * @return Display name with emoji
     */
    public static String getCategoryDisplayName(String category) {
        switch (category) {
            case Constants.CATEGORY_EASY_WINS:
                return "🟢 Easy Wins";
            case Constants.CATEGORY_NUTRITION:
                return "🥦 Nutrition";
            case Constants.CATEGORY_MOVEMENT:
                return "🏃 Movement";
            case Constants.CATEGORY_CONNECTION:
                return "💬 Connection";
            case Constants.CATEGORY_PRODUCTIVITY:
                return "⏰ Productivity";
            default:
                return category;
        }
    }

    /**
     * Build category definitions with default tasks
     * @return Map of category key to CategoryDefinition
     */
    private static Map<String, CategoryDefinition> buildCategories() {
        Map<String, CategoryDefinition> map = new LinkedHashMap<>();

        map.put(Constants.CATEGORY_EASY_WINS, new CategoryDefinition(
                Constants.CATEGORY_EASY_WINS,
                "🟢 Easy Wins",
                List.of(
                        "Get out of bed",
                        "Change clothes",
                        "Take 3 deep breaths",
                        "Wash face",
                        "Look in the mirror")));

        map.put(Constants.CATEGORY_NUTRITION, new CategoryDefinition(
                Constants.CATEGORY_NUTRITION,
                "🥦 Nutrition",
                List.of(
                        "Drink water / tea",
                        "Eat healthy meals",
                        "Buy healthy snacks",
                        "Add vegetables to meal",
                        "Try a new recipe")));

        map.put(Constants.CATEGORY_MOVEMENT, new CategoryDefinition(
                Constants.CATEGORY_MOVEMENT,
                "🏃 Movement",
                List.of(
                        "Take a stretch break",
                        "Go for a 5-minute walk in nature",
                        "Dance to a song I like",
                        "Take the stairs",
                        "Do 5 squats")));

        map.put(Constants.CATEGORY_CONNECTION, new CategoryDefinition(
                Constants.CATEGORY_CONNECTION,
                "💬 Connection",
                List.of(
                        "Say thank you to someone",
                        "Express gratitude to a loved one",
                        "Compliment someone",
                        "Give someone a warm hug",
                        "Cook a family meal together")));

        map.put(Constants.CATEGORY_PRODUCTIVITY, new CategoryDefinition(
                Constants.CATEGORY_PRODUCTIVITY,
                "⏰ Productivity",
                List.of(
                        "Write down my goals for tomorrow",
                        "Break a task into smaller steps",
                        "Set a deadline for a task",
                        "Check off one item from my to-do list",
                        "Finish work on time")));

        return map;
    }

    /**
     * Internal class to hold category definitions
     */
    private static class CategoryDefinition {
        private final String key;
        private final String displayName;
        private final List<String> items;

        private CategoryDefinition(String key, String displayName, List<String> items) {
            this.key = key;
            this.displayName = displayName;
            this.items = List.copyOf(items);
        }

        private String key() {
            return key;
        }

        private String displayName() {
            return displayName;
        }

        private List<String> items() {
            return items;
        }
    }
}
