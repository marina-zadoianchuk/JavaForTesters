package lesson3.BoxOfFruit;


public class Main {
    public static void main(String[] args) {
        Box<Apple> box1 = new Box<>(new Apple(), new Apple());
        box1.add(new Apple());
        System.out.println("Вес коробки 1: " + box1.getWeight());

        Box<Apple> box2 = new Box<>(new Apple());
        box2.add(new Apple(), new Apple(), new Apple());
        System.out.println("Вес коробки 2: " + box2.getWeight());

        if (box1.compare(box2)) {
            System.out.println("Вес коробок 1 и 2 равен");
        } else {
            System.out.println("Вес коробок 1 и 2 не равен");
        }

        box1.add(box2);
        System.out.println("Вес коробки 1: " + box1.getWeight());

        Box<Orange> box3 = new Box<>(new Orange());
        box3.add(new Orange(), new Orange());
        System.out.println("Вес коробки 3: " + box3.getWeight());

      //программа не дает смешать яблоки и апельсины box1.add(box3);
    }
}
