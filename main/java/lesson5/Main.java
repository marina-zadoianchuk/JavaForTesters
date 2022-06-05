package lesson5;

import java.util.Arrays;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{
        /*
        Реализовать сохранение данных в csv файл;
Реализовать загрузку данных из csv файла. Файл читается целиком.
Структура файла:
Строка заголовок с набором столбцов
Набор строк с целочисленными значениями
Разделитель между столбцами - символ точка с запятой (;)
         */
      //  File file = new File("hw5.csv");
    //    file.createNewFile();

        AppData appData = new AppData();
        appData.load("hw5.csv");

        System.out.println(Arrays.toString(appData.getHeader()));
        System.out.println(Arrays.deepToString(appData.getData()));

        appData.save("hw5-1.csv");
    }
}