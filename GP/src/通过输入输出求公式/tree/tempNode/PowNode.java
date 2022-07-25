package src.通过输入输出求公式.tree.tempNode;

import src.通过输入输出求公式.tree.Node;
import src.通过输入输出求公式.tree.OperatorNode;

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