package lesson7;

import java.io.IOException;
import java.util.Scanner;

public class UserInterfaceView {
    private Controller controller = new Controller();

    public void runInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите название города на английском или 0 для выхода: ");
            String city = scanner.nextLine();

            if (city.equals("0")) {
                System.out.println("Спасибо за пользование программой!");
                break;
            }
            System.out.println("Введите 1 для получения погоды на сегодня; " +
                    "Введите 5 для прогноза на 5 дней; " +
                     "Введите 2 для получения данных из базы; " +
                    "Для выхода введите 0:");

            String command = scanner.nextLine();

            //TODO* Сделать метод валидации пользовательского ввода
            if (command.equals("0") || command.equals("1") || command.equals("5") || command.equals("2")) {

            } else if (command.equals("0")) {
                System.out.println("Спасибо за пользование программой!");
                break;
            } else {
                System.out.println("Неверный ввод!");
                break;
            }
            try {
                controller.getWeather(command, city);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
