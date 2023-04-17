package src.符号回归.tree;

import java.util.Scanner;

public class TerminalX1 extends TerminalNode {
    public TerminalX1() {
        super(0.0, "x1");
    }


    public TerminalX1(double number) {
        super(number, String.valueOf(number));
    }

    public OperatorNode cloneTree() {
        if (this.rep == "x1") {
            return new TerminalX1();
        }
        return new TerminalX1(this.number);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        System.out.print(num + ":" + " ");
        for (int i = 2; i <= num; i++) {
            if (num % i == 0) {
                num /= i;
                if (num == 1) System.out.println(i);
                else System.out.print(i + " ");
                i = 1;
            }
        }
    }
}