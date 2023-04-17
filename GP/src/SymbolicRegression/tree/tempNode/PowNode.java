package src.SymbolicRegression.tree.tempNode;

import src.SymbolicRegression.tree.Node;
import src.SymbolicRegression.tree.OperatorNode;

//@NodeImpl(order = 8)
public class PowNode extends Node {
    public PowNode(OperatorNode left, OperatorNode right) {
        super(0.0, left, right, "pow");
    }

    public Double operate() {
        return Math.pow(this.getLeft().operate(), this.getRight().operate());
    }
    public boolean isFunction() {

        return true;
    }

    public OperatorNode cloneTree() {
        return new PowNode(this.getLeft().cloneTree(), this.getRight().cloneTree());
    }


}