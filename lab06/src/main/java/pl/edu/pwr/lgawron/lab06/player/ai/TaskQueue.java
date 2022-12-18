package pl.edu.pwr.lgawron.lab06.player.ai;

import java.util.LinkedList;

public class TaskQueue {
    private final LinkedList<String[]> tasks;

    public TaskQueue() {
        this.tasks = new LinkedList<>();
    }

    public synchronized void addTask(String[] s) {
        if (tasks.isEmpty()) {
            tasks.add(s);
            notify();
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
