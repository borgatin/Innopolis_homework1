package ru.innopolis.borgatin.homework1;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Точка входа в модуль
 */
class Main {
    private boolean await;

    public static void main(String[] args) {

        final Main main = new Main();

        Box box = new Box(main);

        Queue<Thread> queue = new ConcurrentLinkedQueue<>();

        //Создадим управляющий поток
        ManageThread manageThread = new ManageThread(box, main, queue);
        Thread t1 = new Thread(manageThread,"ManageThread");
        t1.setDaemon(true);
        t1.start();

        ResourceFile resourceFile;
        Thread thread;
        for( String resourceName: args){
            resourceFile = new ResourceFile(resourceName, box);
            thread = new Thread(resourceFile, "resourceFile - " + resourceName);
            thread.start();
            queue.add(thread);
            System.out.println("Поток стартован " + resourceName);
        }
    }

    boolean isAwait() {
        return await;
    }

    void setAwait(boolean await) {
        this.await = await;
    }


}
