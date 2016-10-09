package ru.innopolis.borgatin.homework1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by innopolis on 06.10.16.
 *
 * Данный класс предназначен для аккумуляции суммы из полученных ресурсов
 */
class Box {

    private AtomicInteger amount = new AtomicInteger(0);
    final private Main monitor;

    /**
     * Конструктор класса без параметров
     */
    Box(Main monitor) {
        this.monitor = monitor;
    }

    /**
     * Метод предназначен для чтения данных из amount
     * @return возвращает текущее значение поля amount
     */
    /*synchronized*/ int getAmount() {
        return amount.get();
    }

    /**
     * Метод предназначен для увеличения значения amount
     * на величину delta
     * @param delta - число типа int, на которое нужно увеличить значение поля amount
     */
    synchronized void inc(int delta) {
        amount.addAndGet(delta);
        //вызов метода notify у общего с manage-потоком монитора
        synchronized (monitor) {
            monitor.setAwait(false);
            monitor.notify();
        }
    }
}
