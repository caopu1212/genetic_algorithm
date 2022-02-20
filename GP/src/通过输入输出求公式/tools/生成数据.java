package src.通过输入输出求公式.tools;

import java.io.*;

public class 生成数据 {
    /**
     读入TXT文件
     String pathname = "D:\\twitter\\13_9_6\\dataset\\en\\input.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
     File filename = new File(pathname); // 要读取以上路径的input。txt文件
     InputStreamReader reader = new InputStreamReader(
     new FileInputStream(filename)); // 建立一个输入流对象reader
     BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
     String line = "";
     line = br.readLine();
     while (line != null) {
     line = br.readLine(); // 一次读入一行数据
     }
     写入Txt文件
     File writename = new File(".\\result\\en\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
     writename.createNewFile(); // 创建新文件
     BufferedWriter out = new BufferedWriter(new FileWriter(writename));
     out.write("我会写入文件啦\r\n"); // \r\n即为换行
     out.flush(); // 把缓存区内容压入文件
     out.close(); // 最后记得关闭文件
     */


    public static void main(String[] args) {
//        File writename = new File("src\\通过输入输出求公式\\data\\BaseGenetic_多维数据集");
        try {

            File file = new File(生成数据.class.getResource("/test.txt").getFile());
//            File filename = new File("src/通过输入输出求公式/data/test.txt"); // 要读取以上路径的input。txt文件

//            File filename = new File("F:\\python\\genetic_algorithm\\GP\\src\\通过输入输出求公式\\data\\test.txt"); // 要读取以上路径的input。txt文件
            BufferedReader br = new BufferedReader(new FileReader(file));
            String len = null;
            while ((len=br.readLine())!=null){
                System.out.println(len);
            }



//            writename.createNewFile(); // 创建新文件
//            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
//            out.write("我会写入文件啦\r\n"); // \r\n即为换行
//            out.flush(); // 把缓存区内容压入文件
//            out.close(); // 最后记得关闭文件

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
