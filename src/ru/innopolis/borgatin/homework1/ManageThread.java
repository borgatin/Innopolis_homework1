package ru.innopolis.borgatin.homework1;

import java.util.Queue;

/**
 * Управляющий поток, будет выводить сумму при ее изменении
 */
class ManageThread implements Runnable {

    private Box box;

    private final Main monitor;

    private Queue<Thread> queue;

    ManageThread(Box box, Main monitor, Queue<Thread> queue){
        this.box = box;
        this.monitor = monitor;
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()&&!box.getNeedInterrupt()) {
                synchronized (monitor) {
                    monitor.setAwait(true);
                    while (monitor.isAwait()) {
                        monitor.wait();
                    }
                    System.out.println("Текущая сумма равна: " + box.getAmount());
                }
                if (box.getNeedInterrupt())
                {
                    for(Thread thread: queue){
                        thread.interrupt();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
