
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class test {


    public static void main(String[] args) {

        ArrayList<Double> list = new ArrayList<Double>();
        list.add(12.5);
        list.add(1.0);
        list.add(15.5);

        System.out.println(list.stream().mapToDouble(a ->a).sum());

    }

    private static member createNewMember(String name, String gender) {
        member member = new member();
        member.setName(name);
        member.setGender(gender);
        return member;
    }


    private static String print(String s, String dd) {

        System.out.println(s);

        return s;
    }

}

@Data
@ToString
class member {
    private String name;
    private String age;
    private String gender;
}