package ru.innopolis.borgatin.homework1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Точка входа в модуль
 */
class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private boolean await;

    public static void main(String[] args) {

        final Main main = new Main();

        Box box = new Box(main);

        Queue<Thread> queue = new ConcurrentLinkedQueue<>();

        //Создадим управляющий поток
        logger.debug("Before create Runnable object for Manage Thread");
        ManageThread manageThread = new ManageThread(box, main, queue);
        logger.debug("Runnable object for Manage Thread is created");
        Thread t1 = new Thread(manageThread,"ManageThread");
        logger.debug("Manage Thread is created");
        t1.setDaemon(true);
        logger.debug("Manage Thread was set deamon");
        t1.start();
        logger.info("Manage Thread is started");

        ResourceFile resourceFile;
        Thread thread;
        int resourceIndex = 0, resourcesCount = args.length;
        logger.info("Beginning {} processing resource", resourcesCount);

        for( String resourceName: args) {
            logger.debug("Begin process {} resource (name = \"{}\")", resourceIndex+1, resourceName);
            if (!box.isNeedInterrupt()) {
                resourceFile = new ResourceFile(resourceName, box);
                logger.debug("Runnable resource file was created");
                thread = new Thread(resourceFile, "resourceFile - " + resourceName);
                thread.start();
                logger.debug("Runnable resource file was created");
                queue.add(thread);
                logger.info("Thread for resources file \"{}\" started ({} of {})", resourceName, ++resourceIndex, resourcesCount);
            } else {
                logger.debug("Exit from cycle process resources because of interruption");
                break;
            }
        }
        logger.info("Main Thread is finished");
    }

    boolean isAwait() {
        return await;
    }

    void setAwait(boolean await) {
        this.await = await;
        logger.debug("The field \"await\" class \"Main\" set \"{}\" (old value - \"{}\")", await, isAwait());
    }


}
