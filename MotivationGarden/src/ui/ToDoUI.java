package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import manager.TaskManager;
import model.Task;
import util.Constants;

/**
 * Simple Swing based UI for browsing predefined to-do items grouped by category.
 */
public class ToDoUI {

    private final JFrame frame;
    private final JTextArea taskDisplay;
    private final Map<String, CategoryDefinition> categories;

    public ToDoUI() {
        TaskManager.init();
        categories = buildCategories();
        ensureDefaultTasks();

        frame = new JFrame("Motivation Garden - To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel(new GridLayout(1, categories.size(), 8, 8));

        categories.values().forEach(category -> {
            JButton button = new JButton(category.displayName());
            button.setFont(button.getFont().deriveFont(Font.BOLD, 13f));
            button.addActionListener(event -> showTasks(category));
            buttonPanel.add(button);
        });

        taskDisplay = new JTextArea();
        taskDisplay.setEditable(false);
        taskDisplay.setLineWrap(true);
        taskDisplay.setWrapStyleWord(true);
        taskDisplay.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(taskDisplay);
        scrollPane.setPreferredSize(new Dimension(480, 320));

        frame.add(buttonPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        categories.values().stream().findFirst().ifPresent(this::showTasks);
        frame.setVisible(true);
    }

    private Map<String, CategoryDefinition> buildCategories() {
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

    private void ensureDefaultTasks() {
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

    private void showTasks(CategoryDefinition category) {
        List<Task> tasks = TaskManager.getTasksByCategory(category.key());
        if (tasks.isEmpty()) {
            taskDisplay.setText(category.displayName() + "\n\nNo to-do items found.");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(category.displayName()).append("\n\n");
        tasks.forEach(task -> builder
                .append(task.isCompleted() ? "[x] " : "[ ] ")
                .append(task.getTitle())
                .append('\n'));

        taskDisplay.setText(builder.toString());
        taskDisplay.setCaretPosition(0);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoUI::new);
    }
}