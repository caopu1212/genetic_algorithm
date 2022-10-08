package src.符号回归.tree.tempNode;

import src.符号回归.tree.Node;
import src.符号回归.tree.OperatorNode;

//@NodeImpl(order = 6)
public class ExpNode extends Node {
    public ExpNode(OperatorNode left) {
        super(0.0, left, null, "Exp");
    }

    public Double operate() {
        return Math.exp(this.getLeft().operate());
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new ExpNode(this.getLeft().cloneTree());
    }
}

