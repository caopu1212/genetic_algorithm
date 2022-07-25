package src.通过输入输出求公式.tree;

@NodeImpl(order = 8)
public class ReciprocalNode extends Node {
    public ReciprocalNode(OperatorNode left) {
        super(0.0, left, null, "1/");
    }

    //依旧解析商
    public Double operate() {
        return 1/  Math.sqrt(1 + Math.pow(this.getLeft().operate(),2));
    }


    public boolean isFunction() {
        return true;
    }

    public OperatorNode cloneTree() {
        return new ReciprocalNode(this.getLeft().cloneTree());
    }
}

