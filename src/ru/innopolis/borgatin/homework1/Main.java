package ru.innopolis.borgatin.homework1;

import java.util.ArrayList;
import java.util.List;

/**
 * Точка входа в модуль
 */
class Main {
    private boolean await;

    public static void main(String[] args) {
        String[] args2 = new String[3];
        args2[0] = "file.txt";
        args2[1] = "file2.txt";
        args2[2] = "file3.txt";
        //TODO: удалить все строки выше до main

        final Main main = new Main();
        //Создадим объект-коробку для хранения
        Box box = new Box(main);


        //Создадим управляющий поток
        ManageThread manageThread = new ManageThread(box, main);
        Thread t1 = new Thread(manageThread);
        t1.setDaemon(true);
        t1.start();

        ResourceFile resourceFile;
        for( String resourceName: args2){ //TODO: переименовать args2 в args
            //добавляем экземпляр класса ResourceFile в созданный экзэкъютор
            resourceFile = new ResourceFile(resourceName, box);
            new Thread(resourceFile).start();
        }
    }

    boolean isAwait() {
        return await;
    }

    void setAwait(boolean await) {
        this.await = await;
    }
}
