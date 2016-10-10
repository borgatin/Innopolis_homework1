package ru.innopolis.borgatin.homework1;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * Данный класс предназначен для аккумуляции суммы из полученных ресурсов
 */
class Box {
    private BigInteger amount = BigInteger.ZERO;

    final private Main monitor;

    private AtomicBoolean needInterrupt = new AtomicBoolean(false);

    /**
     * Конструктор класса с одним параметром - монитор для блокировки записи
     */
    Box(Main monitor) {
        this.monitor = monitor;
    }

    /**
     * Метод предназначен для чтения данных из amount
     * @return возвращает текущее значение поля amount в String
     */
    String getAmount() {
        return amount.toString();
    }

    /**
     * Метод предназначен для увеличения значения amount
     * на величину delta
     * @param delta - число int, на которое нужно увеличить значение поля amount
     */
    void inc(int delta) {
        //вызов метода notify у общего с manage-потоком монитора
        synchronized (monitor) {
            amount = amount.add(BigInteger.valueOf(delta));
            monitor.setAwait(false);
            monitor.notify();
        }
    }

    /**
     * Метод предназначен для получения информации о том, необходимо ли прекращать все потоки.
     *
     * @return возвращает true если в одном из потоков-ресурсов произошло исключение
     */
    boolean getNeedInterrupt() {
        return needInterrupt.get();
    }

    /**
     * Используется при возникновении исклюючения в одном из потоков-ресурсов
     */
    void setNeedInterrupt() {
        this.needInterrupt.set(true);
    }
}
