package src.通过输入输出求公式.tree;

public class TerminalX2 extends TerminalNode {
  public TerminalX2() {
    super(0.0, "x2");
  }

  public TerminalX2(Double number) {
    super(number, String.valueOf(number));
  }
  
  public OperatorNode cloneTree() {
    if (this.rep == "x2") {
      return new TerminalX2();
    }
    return new TerminalX2(this.number);
  }
}