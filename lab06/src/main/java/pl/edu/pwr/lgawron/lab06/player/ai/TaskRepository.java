package pl.edu.pwr.lgawron.lab06.player.ai;

import java.util.LinkedList;

public class TaskRepository {
    private final LinkedList<String[]> tasks;

    public TaskRepository() {
        this.tasks = new LinkedList<>();
    }

    public synchronized void addTask(String[] s) {
        if (tasks.isEmpty()) {
            tasks.add(s);
            notifyAll();
        } else {
            tasks.add(s);
        }
    }

    public synchronized String[] popTask() {
        try {
            while (tasks.isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tasks.pop();
    }
}
