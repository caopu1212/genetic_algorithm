package src.通过输入输出求公式.tree;

public abstract class TerminalNode extends Node {
  public TerminalNode(Double number, String rep) {
    super(number, null, null, rep);
  }
  
  public boolean isTerminal() {
    return true;
  }
  
  public Double operate() {
    return this.number;
  }
  
  public String printContent() {
    return this.rep;
  }
}