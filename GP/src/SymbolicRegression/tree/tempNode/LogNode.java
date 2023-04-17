package src.SymbolicRegression.tree.tempNode;

import src.SymbolicRegression.tree.Node;
import src.SymbolicRegression.tree.OperatorNode;

//@NodeImpl(order = 7)
public class LogNode extends Node {
    public LogNode(OperatorNode left) {
        super(0.0, left, null, "Log");
    }

    public Double operate() {
        return Math.log(this.getLeft().operate());
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new LogNode(this.getLeft().cloneTree());
    }
}

