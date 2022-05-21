package lesson2;

    public class MyArrayDataException extends java.lang.ArrayStoreException {
        public int i;
        public int j;

        MyArrayDataException(int i, int j) {
            this.i = i;
            this.j = j;

        }

    }