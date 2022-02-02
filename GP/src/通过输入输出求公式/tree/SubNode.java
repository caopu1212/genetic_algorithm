package src.通过输入输出求公式.tree;
@NodeImpl(order = 1)
public class SubNode extends Node {
  public SubNode(OperatorNode left, OperatorNode right) {
    super(0.0, left, right, "-");
  }
  
  public Double operate() {
    return this.getLeft().operate() - this.getRight().operate();
  }
  
  public boolean isFunction() {
    return true;
  }
  
  public OperatorNode cloneTree() {
    return new SubNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
  }
}