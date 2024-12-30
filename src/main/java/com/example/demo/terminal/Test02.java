package com.example.demo.terminal;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class Test02 {
    public static void main(String[] args) {
        try {
            System.out.println("start");
            //需传入的参数
            String a = "aaa", b = "bbb", c = "ccc", d = "ddd";
            //设置命令行传入参数
            String[] args1 = new String[]{"python", "D:\\reptile-data-platform\\lib\\pythonCode\\test.py", a, b, c, d};
            Process pr = Runtime.getRuntime().exec(args1);
            //TODO:该方法只能传递字符串
            //Process pr = Runtime.getRuntime().exec("python E:\\test.py  E:\\test1.mp4");

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    pr.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            System.out.println(pr.waitFor());
            ;
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
