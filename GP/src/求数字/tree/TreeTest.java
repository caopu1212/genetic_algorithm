package src.求数字.tree;


public class TreeTest {
    public static Double fitness(Double x){
        return Math.pow(x,2)*-1 +10;
    }


  public static void main(String[] args) {
//    OperatorNode root = new MultNode(new TerminalNode(9, null, null), new TerminalNode(11, null, null));
//    System.out.println(root.operate(root.getLeft(), root.getRight()));
//    System.out.println(21*23+36+36*5-24+10*39-16*10*13);
    //92069250

      TreeTest treeTest = new TreeTest();

      System.out.println(treeTest.fitness(-1.0));





  }
}