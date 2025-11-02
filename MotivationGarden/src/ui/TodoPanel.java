package ui;

import manager.TaskManager;
import model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TodoPanel extends JPanel {

    private DefaultListModel<Task> listModel = new DefaultListModel<>();
    private JList<Task> taskList = new JList<>(listModel);
    private JLabel coinsLabel = new JLabel();

    public TodoPanel() {
        setLayout(new BorderLayout());

        coinsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        coinsLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(coinsLabel, BorderLayout.NORTH);

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(taskList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addBtn = new JButton("Add Task");
        JButton deleteBtn = new JButton("Delete Task");
        JButton markBtn = new JButton("Mark Done");
        buttonPanel.add(addBtn);
        buttonPanel.add(markBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addTask());
        deleteBtn.addActionListener(e -> deleteTask());
        markBtn.addActionListener(e -> markTaskDone());

        TaskManager.addListener(this::refreshTaskList);
        refreshTaskList();
    }

    private void refreshTaskList() {
        SwingUtilities.invokeLater(() -> {
            listModel.clear();
            ArrayList<Task> tasks = TaskManager.getTasks();
            for (Task t : tasks) {
                listModel.addElement(t);
            }
            coinsLabel.setText("💰 Coins: " + TaskManager.getCoins());
        });
    }

    private void addTask() {
        String title = JOptionPane.showInputDialog(this, "Enter task:");
        if (title != null && !title.trim().isEmpty()) {
            TaskManager.addTask(title.trim());
        }
    }

    private void deleteTask() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            TaskManager.deleteTask(selected.getTitle());
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to delete!");
        }
    }

    private void markTaskDone() {
        Task selected = taskList.getSelectedValue();
        if (selected != null) {
            TaskManager.markTaskDone(selected.getTitle());
        } else {
            JOptionPane.showMessageDialog(this, "Select a task to mark done!");
        }
    }
}
