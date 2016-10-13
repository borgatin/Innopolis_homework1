package ru.innopolis.borgatin.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * Управляющий поток, будет выводить сумму при ее изменении
 */
class ManageThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ManageThread.class);

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
            logger.debug("Thread \"ManageThread\" in synchronized-block");
            while (!Thread.currentThread().isInterrupted()&&!box.isNeedInterrupt()) {
                logger.debug("Thread \"ManageThread\" before synchronized-block");
                synchronized (monitor) {
                    logger.debug("Thread \"ManageThread\" in synchronized-block");
                    monitor.setAwait(true);
                    while (monitor.isAwait()) {
                        logger.debug("Thread \"{}\" before wait-construction", Thread.currentThread().getName());
                        monitor.wait();
                        logger.debug("Thread \"{}\" after wait-construction", Thread.currentThread().getName());
                    }
                    logger.info("Current summ = {}", box.getAmount());
                    System.out.println("Current summ = " + box.getAmount());
                }
                if (box.isNeedInterrupt())
                {
                    for(Thread thread: queue){
                        thread.interrupt();
                        logger.warn("Thread \"{}\" was interrupted", thread.getName());
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.error("Thread \"{}\" was interrupted: {}", Thread.currentThread().getName(), e.getMessage());
        }
    }
}
