package ru.innopolis.borgatin.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(Box.class);

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
        logger.debug("Class \"Box\" method \"inc\" before synchronized-block (Thread name - \"{}\")", Thread.currentThread().getName());
        synchronized (monitor) {
            logger.debug("Class \"Box\" method \"inc\" in synchronized-block");
            amount = amount.add(BigInteger.valueOf(delta));
            logger.debug("Class \"Box\" method \"inc\" amount inc. by {}", delta);
            monitor.setAwait(false);
            monitor.notify();
        }
    }

    /**
     * Метод предназначен для получения информации о том, необходимо ли прекращать все потоки.
     *
     * @return возвращает true если в одном из потоков-ресурсов произошло исключение
     */
    boolean isNeedInterrupt() {
        return needInterrupt.get();
    }

    /**
     * Используется при возникновении исключения в одном из потоков-ресурсов
     * для сообщения об этом другим потокам-ресурсам
     */
    void setNeedInterrupt() {
        this.needInterrupt.set(true);
        logger.debug("Class \"Box\" filed \"needInterrupt\" set value \"true\"");
    }
}
