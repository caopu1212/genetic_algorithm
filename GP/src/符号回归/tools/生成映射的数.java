package src.符号回归.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class 生成映射的数 {


    public static void main(String[] args) {
        File writename = new File("F:\\python\\genetic_algorithm\\GP\\dataset\\logistic.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件

        try {
            writename.createNewFile(); // 创建新文件
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));

            double μ = 4;
            Random random = new Random();
            double x = random.nextDouble();
            for (int j = 0; j < 10000; j++) {
                double last_x = x;
                 x = x * μ * (1 - x);

                out.write(last_x + " " + x + "\r\n"); // \r\n即为换行
            }

            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
