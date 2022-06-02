
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class test {
    public static Random random = new Random();

    public static void main(String[] args) {
//        test test = new test();
//        test.BaseGenetic_多维数据集();

    }

    @Test
    public void demo() {
//        for (int j = 0; j < 10; j++) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            if ( random.nextInt(100)> 80) {
                System.out.println("yes");
            }else{
                System.out.println("no");
            }


        }

    }


    @Test
    public void print() {
        ArrayList<Double> list = new ArrayList<>();
        list.add(13.2);
        list.add(12.2);
        list.add(3.3);


        double sum = list.stream()
                .mapToDouble(num -> num)
                .sum();
        System.out.println(sum);

//        if (list.get(0).contains("5")) {

//        }
    }

    @Test
    public void demo1() {
        ArrayList<String> list = new ArrayList<>();
        list.add("3");
        list.add("5");
        list.add("1");
        list.add("2");
        list.add("4");

        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);


    }


    @Data
    @ToString
    class human {
        private String name;
        private String gender;
    }


}
// TODO: 2022/2/2
