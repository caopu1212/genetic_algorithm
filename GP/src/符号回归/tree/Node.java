package src.符号回归.tree;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public abstract class Node implements OperatorNode {
    protected Double number;
    private OperatorNode left, right;
    protected String rep;
    private Double fitness;
    private double normalizedFitness;
    private int mark;


    public static List<Class<? extends Node>> getNodeClassList() {
//      Reflections f = new Reflections("src.通过输入输出求公式.tree",new TypeAnnotationsScanner());
        Reflections f = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(
                                new TypeAnnotationsScanner(),
                                new SubTypesScanner(false)
                        )
                        .setUrls(ClasspathHelper.forPackage("src.符号回归.tree")));
        Set<Class<?>> types = f.getTypesAnnotatedWith(NodeImpl.class);
        List<Class<? extends Node>> result = new ArrayList<>();
        for (int i = 0; i < types.size(); i++) {
            result.add(null);
        }
//        List<Class<? extends Node>> result = new ArrayList<>(4);
        types.forEach(item -> {
            NodeImpl an = item.getAnnotation(NodeImpl.class);
            final int order = an.order();
//            if (Objects.nonNull(result.get(order)))
//                throw new RuntimeException("duplicate order:" + order);
            System.out.println(String.format("%d: %s", order, item.getSimpleName()));
            result.set(order, (Class<? extends Node>) item);
//            result.add((Class<? extends Node>) item);
        });
        return result;
    }


    public Node(Double terminal, OperatorNode left, OperatorNode right, String rep) {
        this.number = terminal;
        this.left = left;
        this.right = right;
        this.rep = rep;
    }

    //Evaluation
    public abstract Double operate();

    public boolean isTerminal() {
        return false;
    }

    public boolean isFunction() {
        return false;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }


    public OperatorNode getLeft() {
        return left;
    }

    public OperatorNode getRight() {
        return right;
    }

    public void setLeft(OperatorNode son) {
        this.left = son;
    }

    public void setRight(OperatorNode son) {
        this.right = son;
    }

    //Copy
    public abstract OperatorNode cloneTree();

    public void copyLeft(OperatorNode copy) {
        this.left = copy;
    }

    public void copyRight(OperatorNode copy) {
        this.right = copy;
    }

    //Printing
  /*public String printContent() {
    return this.getLeft().printContent() + this.rep + this.getRight().printContent();
  }*/

    public String printContent() {
        if (this.getRight() == null) {
            return this.getLeft().printContent() + ' ' + this.rep;
        }
        return this.getLeft().printContent() + ' ' + this.getRight().printContent() + ' ' + this.rep;
    }

    //计算子节点数量
    public int countSubnodes(OperatorNode root) {
        int nodes = 0;
        if (root.isTerminal())
            return 0;
        if (root.getRight() == null) {
            // 单参数运算符的情况
            nodes = 1 + countSubnodes(root.getLeft());
        } else {
            nodes = 1 + countSubnodes(root.getLeft()) + countSubnodes(root.getRight());
        }

        return nodes;
    }


    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setNormalizedFitness(double normFitness) {
        this.normalizedFitness = normFitness;
    }

    public double getNormalizedFitness() {
        return normalizedFitness;
    }

}