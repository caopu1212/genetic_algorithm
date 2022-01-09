import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Random;

public class test {
    static Random random = new Random();

    public static void main(String[] args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");

        String str1 = "3*3*3+3*3+3";
        Object result1 = null;
        try {
            result1 = engine.eval(str1);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        System.out.println("结果类型:" + result1.getClass().getName() + ",计算结果:" + result1);


//        for (int i = 0; i < 5000; i++) {
//            //random.nextInt(max)%(max-min+1) + min;
//            String randomNum = String.valueOf(random.nextInt(50-20)-20);
//
//            System.out.println(randomNum);
//
//        }

        Double test = 123.0/0;
        System.out.println(test);

    }
}
