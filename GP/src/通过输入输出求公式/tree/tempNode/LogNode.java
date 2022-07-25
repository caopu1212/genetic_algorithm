package src.通过输入输出求公式.tree.tempNode;

import src.通过输入输出求公式.tree.Node;
import src.通过输入输出求公式.tree.OperatorNode;

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

