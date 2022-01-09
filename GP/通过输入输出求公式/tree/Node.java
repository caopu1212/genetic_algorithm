package 通过输入输出求公式.tree;

public abstract class Node implements OperatorNode{
  protected Double number;
  private OperatorNode left, right;
  protected String rep;
  private Double fitness;
  private double normalizedFitness;
  
  public Node(Double  terminal, OperatorNode left, OperatorNode right, String rep) {
    this.number = terminal;
    this.left = left;
    this.right = right;
    this.rep = rep;
  }
  
  //Evaluation
  public abstract Double operate();
  
  public boolean isTerminal() {
    return false;
  }
  
  public boolean isFunction() {
    return false;
  }
  
  public OperatorNode getLeft() {
    return left;
  }
  
  public OperatorNode getRight() {
    return right;
  }
  
  public void setLeft(OperatorNode son) {
    this.left = son;
  }
  
  public void setRight(OperatorNode son) {
    this.right = son;
  }
  
  //Copy
  public abstract OperatorNode cloneTree();
  
  public void copyLeft(OperatorNode copy) {
    this.left = copy;
  }
  
  public void copyRight(OperatorNode copy) {
    this.right = copy;
  }
  
  //Printing
  /*public String printContent() {
    return this.getLeft().printContent() + this.rep + this.getRight().printContent();
  }*/
  
  public String printContent() {
    return this.getLeft().printContent() + ' ' + this.getRight().printContent() + ' ' + this.rep;
  }
  
  public void setFitness(Double fitness) {
    this.fitness = fitness;
  }
  
  public Double getFitness() {
    return fitness;
  }
  
  public void setNormalizedFitness(double normFitness) {
    this.normalizedFitness = normFitness;
  }
  
  public double getNormalizedFitness() {
    return normalizedFitness;
  }
  
}