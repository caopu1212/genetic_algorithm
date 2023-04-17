package src.SymbolicRegression.tree;

@NodeImpl(order = 4)
public class CosNode extends Node {
    public CosNode(OperatorNode left) {
        super(0.0, left, null, "Cos");
    }

    public Double operate() {
        return Math.cos(this.getLeft().operate());
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new CosNode(this.getLeft().cloneTree());
    }
}

