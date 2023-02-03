package src.符号回归.tree;


//import src.符号回归.Elements.DrawTree;
import src.符号回归.Elements.DrawTree;
import src.符号回归.tree.Node.*;
import src.符号回归.tools.PrintTree;

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
  public int countSubnodes(OperatorNode root);
  public void setFitness(Double fitness);
  public Double getFitness();
  public void setNormalizedFitness(double normFitness);
  public double getNormalizedFitness();
  public int getMark();
  public void setMark(int mark);
  public void drowTree(Node root, DrawTree.Trunk prev, boolean isLeft);



}