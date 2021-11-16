package src.求数字.tree;

import src.求数字.gp.NumberGuessing;

public abstract class Node implements OperatorNode {
    protected int number;
    private OperatorNode left, right;
    protected String rep;
    private int fitness;
//    private double normalizedFitness;

    static class StopMsgException extends RuntimeException {
    }

    public Node(int terminal, OperatorNode left, OperatorNode right, String rep) {
        this.number = terminal;
        this.left = left;
        this.right = right;
        this.rep = rep;
    }

    //Evaluation
    public abstract int operate();

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
        return this.getRight().printContent() + ' ' + this.getLeft().printContent() + ' ' + this.rep;
    }

    // 操作符结点数
    public int countSize(OperatorNode node) {
        if (node.isTerminal())
            return 0;
        return 1 + countSize(node.getLeft()) + countSize(node.getRight());
    }


    // 找到对应顺序的节点
    public OperatorNode getNode(OperatorNode node, int index, int target) {
        if (NumberGuessing.count == target)
            return node;
        NumberGuessing.count++;

        OperatorNode resultNode = null;

        if (node.getLeft() != null) {
            getNode(node.getLeft(), index, target);
            if (resultNode != null) {
                return resultNode;
            }
        }
        if (node.getRight() != null) {
            resultNode = getNode(node.getRight(), index, target);
            if (resultNode != null) {
                return resultNode;
            }
        }
        return resultNode;
    }


    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getFitness() {
        return fitness;
    }

//    public void setNormalizedFitness(double normFitness) {
//        this.normalizedFitness = normFitness;
//    }
//
//    public double getNormalizedFitness() {
//        return normalizedFitness;
//    }

}