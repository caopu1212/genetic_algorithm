package src.符号回归.tree;


@NodeImpl(order = 2)
public class MultNode extends Node {
  public MultNode(OperatorNode left, OperatorNode right) {
    super(0.0, left, right, "*");
  }
  
  public Double operate() {
    return this.getLeft().operate() * this.getRight().operate();
  }
  
  public boolean isFunction() {
    return true;
  }
  
  public OperatorNode cloneTree() {
    return new MultNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
  }
}


