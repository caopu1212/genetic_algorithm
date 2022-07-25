package src.通过输入输出求公式.tree.tempNode;

import src.通过输入输出求公式.tree.Node;
import src.通过输入输出求公式.tree.OperatorNode;

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

