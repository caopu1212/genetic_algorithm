package 通过输入输出求公式.tree;

public class TerminalX extends TerminalNode {
  public TerminalX() {
    super(0.0, "x");
  }
  
  public TerminalX(Double number) {
    super(number, String.valueOf(number));
  }
  
  public OperatorNode cloneTree() {
    if (this.rep == "x") {
      return new TerminalX();
    }
    return new TerminalX(this.number);
  }
}