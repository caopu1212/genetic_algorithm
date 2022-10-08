package src.符号回归.tree;
@NodeImpl(order = 1)
public class MinNode extends Node {
  public MinNode(OperatorNode left, OperatorNode right) {
    super(0.0, left, right, "-");
  }
  
  public Double operate() {
    return this.getLeft().operate() - this.getRight().operate();
  }
  
  public boolean isFunction() {
    return true;
  }
  
  public OperatorNode cloneTree() {
    return new MinNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
  }
}