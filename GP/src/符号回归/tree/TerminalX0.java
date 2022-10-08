package src.符号回归.tree;

public class TerminalX0 extends TerminalNode {
    public TerminalX0(int mark) {
        super(0.0, "x"+mark);
        setMark(mark);
    }


    public TerminalX0(double number,int mark) {
        super(number, "x"+mark);
        setMark(mark);
    }


    public OperatorNode cloneTree() {
        if (this.rep.contains("x")) {
            return new TerminalX0(this.getMark());
        }
        return new TerminalX0(this.number,this.getMark());
    }
}