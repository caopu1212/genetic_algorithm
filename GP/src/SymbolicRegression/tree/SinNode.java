package src.SymbolicRegression.tree;

@NodeImpl(order = 3)
public class SinNode extends Node {
    public SinNode(OperatorNode left) {
        super(0.0, left, null, "Sin");
    }

    public Double operate() {
        return Math.sin(this.getLeft().operate());
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new SinNode(this.getLeft().cloneTree());
    }
}

