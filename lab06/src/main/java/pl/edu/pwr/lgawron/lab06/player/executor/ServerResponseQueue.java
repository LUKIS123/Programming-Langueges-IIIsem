package pl.edu.pwr.lgawron.lab06.player.executor;

import java.util.LinkedList;

public class ServerResponseQueue {
    private final LinkedList<String[]> tasks;
    private static final Object lock = new Object();

    public ServerResponseQueue() {
        this.tasks = new LinkedList<>();
    }

    public void addTask(String[] s) {
        synchronized (lock) {
            if (tasks.isEmpty()) {
                tasks.add(s);
                lock.notifyAll();
            } else {
                tasks.add(s);
            }
        }
    }

    public String[] popTask() {
        synchronized (lock) {
            try {
                while (tasks.isEmpty()) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tasks.pop();
        }
    }

    public LinkedList<String[]> getTasks() {
        return tasks;
    }
}
