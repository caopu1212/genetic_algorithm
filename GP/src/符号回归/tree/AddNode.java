package src.符号回归.tree;
@NodeImpl(order = 0)
public class AddNode extends Node {
  public AddNode(OperatorNode left, OperatorNode right) {
    super(0.0, left, right, "+");
  }
  
  public Double operate() {
    return this.getLeft().operate() + this.getRight().operate();
  }
  
  public boolean isFunction() {

    return true;
  }
  
  public OperatorNode cloneTree() {
    return new AddNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
  }




}