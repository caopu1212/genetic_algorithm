package src.通过输入输出求公式.tree;

public interface OperatorNode {
  public Double operate();
  public boolean isTerminal();
  public boolean isFunction();
  public OperatorNode getLeft();
  public OperatorNode getRight();
  public void setLeft(OperatorNode son);
  public void setRight(OperatorNode son);
  public OperatorNode cloneTree();
  public void copyLeft(OperatorNode copy);
  public void copyRight(OperatorNode copy);
  public String printContent();
  public void setFitness(Double fitness);
  public Double getFitness();
  public void setNormalizedFitness(double normFitness);
  public double getNormalizedFitness();
  public int getMark();
  public void setMark(int mark);



}