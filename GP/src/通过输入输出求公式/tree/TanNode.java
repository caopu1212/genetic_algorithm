package src.通过输入输出求公式.tree;

@NodeImpl(order = 6)
public class TanNode extends Node {
    public TanNode(OperatorNode left) {
        super(0.0, left, null, "Tan");
    }

    public Double operate() {
        return Math.tan(this.getLeft().operate());
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new TanNode(this.getLeft().cloneTree());
    }
}

