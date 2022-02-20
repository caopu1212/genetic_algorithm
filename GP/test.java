
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

import java.lang.invoke.LambdaConversionException;
import java.lang.reflect.Member;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class test {
    public static Random random = new Random();

    public static void main(String[] args) {
//        test test = new test();
//        test.BaseGenetic_多维数据集();

    }

    @Test
    public void demo() {
        human human = new human();
        human.setName("jack");

        human human2 = new human();
        human2.setName("jack");

        System.out.println(human.getName());

        String name = human.getName();
        if (name == null) {
            throw new RuntimeException("name is null");
        }
        String name1 = human2.getName();
        if (name1 == null) {
            throw new RuntimeException("name is null");
        }
    }


    @Test
    public void print() {
        ArrayList<String> list = new ArrayList<>();
        list.add("123");
        list.add("1234");
        list.add("1235");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }

        if (list.get(0).contains("5")) {

        }
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
