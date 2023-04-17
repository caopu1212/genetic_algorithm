package src.SymbolicRegression.tree;


@NodeImpl(order = 5)
public class DiviNode extends Node {

  public DiviNode(OperatorNode left, OperatorNode right) {
    super(0.0, left, right, "/");
  }



  //analytic quotient(AQ)解析商  a/根号下(1+b^2), 就是若分母为0, 则返回分子
  public Double operate() {
    return this.getLeft().operate() /  Math.sqrt(1 + Math.pow(this.getRight().operate(),2)) ;
  }
  
  public boolean isFunction() {
    return true;
  }
  
  public OperatorNode cloneTree() {
    return new DiviNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
  }
}


