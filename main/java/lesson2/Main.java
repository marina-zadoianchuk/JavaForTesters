package lesson2;

public class Main {
    public static void main(String[] args) {

        String[][] arr = new String[][]{{"1", "2", "8", "4"}, {"2", "2", "2", "3"}, {"1", "2", "2", "2"}, {"18", "2", "2", "2"}};
        try {
            try {
                int result = MyArraySizeException.method(arr);
                System.out.println(result);
            } catch (MyArraySizeException e) {
                System.out.println("Размер массива превышен!");
            }
        }
        catch (MyArrayDataException e) {
            System.out.println("Неправильное значение массива!");
            System.out.println("Ошибка в ячейке: " + e.i + "x" + e.j);
        }

    }
}

