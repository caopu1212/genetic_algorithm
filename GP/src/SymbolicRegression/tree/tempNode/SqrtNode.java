package src.SymbolicRegression.tree.tempNode;

import src.SymbolicRegression.tree.Node;
import src.SymbolicRegression.tree.OperatorNode;

//@NodeImpl(order = 9)
public class SqrtNode extends Node {
    public SqrtNode(OperatorNode left) {
        super(0.0, left, null, "sqrt");
    }

    public Double operate() {
        //防止为0, 用根号下1+x
        return Math.sqrt(Math.sqrt(1 + this.getLeft().operate()));
    }

    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new SqrtNode(this.getLeft().cloneTree());
    }
}

