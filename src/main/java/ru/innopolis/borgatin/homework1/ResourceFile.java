package ru.innopolis.borgatin.homework1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

/**
 * Класс необходим для загрузки ресурсов, считывания информации из них
 * и записи в класс Box
 */
class ResourceFile implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(ResourceFile.class);

    private String resourcePath;

    private Box box;
    private Validator validator;

    ResourceFile(String resourcePath, Box box)
    {
        this.resourcePath = resourcePath;
        this.box = box;
    }

    @Override
    public void run() {

        double tempDouble;

        int tempInt;

        Validator validator = new PositiveEvenNumValidator(); //Класс для проверки соответствия входных значений соответсвию множеству целых четных чисел
        logger.debug("Validator new instance created");

        try (Reader fr = getReader(resourcePath)) {
            logger.debug("Reader for thread \"{}\" got successfully", Thread.currentThread().getName());

            StreamTokenizer st = new StreamTokenizer(fr);
            logger.debug("StreamTokenizer for thread \"{}\" got successfully", Thread.currentThread().getName());
            while (st.nextToken() != StreamTokenizer.TT_EOF && !Thread.currentThread().isInterrupted()) {
                if (st.ttype == StreamTokenizer.TT_NUMBER ||validator.isValid(st.nval)) {
                    box.inc((int) st.nval);
                } else //если тип считанной лексемы не число, либо она не прошла проверку валидатора (не является положительным четным числом)
                {
                    box.setNeedInterrupt();
                    logger.error("Error in the validation input data\"{}\" got successfully", Thread.currentThread().getName());
                    throw new IllegalArgumentException(); //выбросим исключение
                }
            }
        } catch (IOException | IllegalResourceException e) {
            logger.error("An exception occurred from thread \"{}\": {}", Thread.currentThread().getName(), e.getMessage());
            box.setNeedInterrupt();
        }
        logger.info("Thread \"{}\" is finished ", Thread.currentThread().getName());
    }

    /**
     * Метод предназначен для получения по имени ресурса объекта Reader
     * @param resourcePath имя ресурса - имя файла, либо URL
     * @return объект Reader для заданного resourcePath
     * @throws IOException генерируется при ошибках в получении ресурса
     */
    private Reader getReader(String resourcePath) throws IOException {
        if (!resourcePath.startsWith("http")) {
            logger.debug("Thread \"{}\" -  resource is File", Thread.currentThread().getName());
            File file = new File(resourcePath);
            return new FileReader(file);
        } else
        {
            //получение URL
            logger.debug("Thread \"{}\" -  resource is URL", Thread.currentThread().getName());
            URL url = new URL(resourcePath);
            InputStream inputStream = url.openStream();
            return new InputStreamReader(inputStream);
        }
    }
}
