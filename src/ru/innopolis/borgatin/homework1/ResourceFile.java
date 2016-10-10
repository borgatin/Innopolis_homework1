package ru.innopolis.borgatin.homework1;

import java.io.*;
import java.net.URL;

/**
 * Класс необходим для загрузки ресурсов, считывания информации из них
 * и записи в класс Box
 */
class ResourceFile implements Runnable{

    private String resourcePath;

    private Box box;

    ResourceFile(String resourcePath, Box box)
    {
        this.resourcePath = resourcePath;
        this.box = box;
    }

    @Override
    public void run() {

        double tempDouble;

        int tempInt;

        try (Reader fr = getReader(resourcePath)) {
            StreamTokenizer st = new StreamTokenizer(fr);
            while (st.nextToken() != StreamTokenizer.TT_EOF && !Thread.currentThread().isInterrupted()) {
                if (st.ttype == StreamTokenizer.TT_NUMBER) {
                    tempDouble = st.nval;
                    tempInt = (int) tempDouble;
                    if (tempDouble - tempInt != 0.0) //если передано дробное число,
                    {
                        throw new IllegalResourceException(String.format("Ресурс \"%s\" содержит некорректные данные - дробное число", resourcePath)); //выбросим исключение
                    }
                    if (tempInt > 0 && tempInt % 2 == 0) //если прочитанное число четное и положительное
                    {
                        box.inc(tempInt);
                    }
                } else //если тип считанной лексемы не число
                {
                    box.setNeedInterrupt();
                    throw new IllegalArgumentException(); //выбросим исключение
                }
            }
        } catch (IOException | IllegalResourceException e) {
            e.printStackTrace();
            box.setNeedInterrupt();
        }

        if (Thread.currentThread().isInterrupted())
            System.out.println(String.format("Поток для файла \"%s\" прекращен из-за исключения в другом потоке", resourcePath));
        else
            System.out.println(String.format("Поток для файла \"%s\" завершен", resourcePath));

    }

    /**
     * Метод предназначен для получения по имени ресурса объекта Reader
     * @param resourcePath имя ресурса - имя файла, либо URL
     * @return объект Reader для заданного resourcePath
     * @throws IOException генерируется при ошибках в получении ресурса
     */
    private Reader getReader(String resourcePath) throws IOException {
        if (!resourcePath.startsWith("http")) {
            File file = new File(resourcePath);
            return new FileReader(file);
        } else
        {
            //получение URL
            URL url = new URL(resourcePath);
            InputStream inputStream = url.openStream();
            return new InputStreamReader(inputStream);
        }
    }
}
