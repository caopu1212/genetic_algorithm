package src.通过输入输出求公式.tree;

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
}