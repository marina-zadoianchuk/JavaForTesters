package lesson3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SwapTwoElements {
    public static void main(String[] args) throws Exception {
        String [] firstArr = {"Hello","World","Happy","Coding"};
        System.out.println("first[] before swapping : "+ Arrays.toString(firstArr));
        List<String> a = Arrays.asList(firstArr);
        Collections.swap(a,0,2);

        System.out.println("first[] after swapping : "+ Arrays.toString(firstArr));

    }
}
