package ru.innopolis.borgatin.homework1;

/**
 * Управляющий поток, будет выводить сумму при ее изменении
 */
class ManageThread implements Runnable {
    private Box box;
    private final Main monitor;

    ManageThread(Box box, Main monitor){
        this.box = box;
        this.monitor = monitor;
    }
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (monitor) {
                    System.out.println("Текущая сумма равна: " + box.getAmount());
                    monitor.setAwait(true);
                    while (monitor.isAwait()) {
                        monitor.wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
