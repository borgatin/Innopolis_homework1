package ru.innopolis.borgatin.homework1;

import java.io.*;

/**
 * Класс необходим для загрузки ресурсов, считывания информации из них
 * и записи в класс Box
 */
class ResourceFile implements Runnable{
    private String resourcePath;
    private Box box;
    private double tempDouble;
    ResourceFile(String resourcePath, Box box)
    {
        this.resourcePath = resourcePath;
        this.box = box;
    }

    @Override
    public void run() {
        File file = new File(resourcePath);
        try (FileReader fr = new FileReader(file)) {
                StreamTokenizer st = new StreamTokenizer(fr);
            int tempInt;
            while (st.nextToken()   != StreamTokenizer.TT_EOF)
                {
                    if (st.ttype == StreamTokenizer.TT_NUMBER) {
                        tempDouble = st.nval;
                        tempInt = (int) tempDouble;
                        if (tempInt > 0 && tempInt % 2 == 0 //если прочитанное число четное и положительное
                                && tempDouble - tempInt == 0.0) { //если полученное число целое
                            box.inc(tempInt);
                        }
                    }
                    Thread.sleep(500);
                }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Поток для файла \"%s\" завершен",resourcePath));

        //Считываем данные из ресурса
        //проверям, число ли это.
        //проверям знак числа
        //если положительное
        //добавляем в сумму класса Box
    }
}
