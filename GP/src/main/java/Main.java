import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String test = br.readLine();
        String[] list = test.split(" ");
        int totalNode = Integer.valueOf(list[0]);
        int totalEdge = Integer.valueOf(list[1]);

        ArrayList<Graph> graphs = new ArrayList<>();

        for (int i = 0; i < totalEdge; i++) {

            String line = br.readLine();

            String[] tempList = line.split(" ");

            String currenNode = tempList[0];
            String currenEdge = tempList[1];

            // 若已存在该顶点则添加在该顶点下
            if (graphs.size() != 0) {
                boolean founded = false;
                for (Graph exsitedGraph : graphs) {
                    if (currenNode.equals(exsitedGraph.getNode())) {
                        exsitedGraph.getEdges().add(currenEdge);
                        founded = true;
                        break;
                    }
                }
                // 遍历完若没有的话则添加新的
                if (!founded) {
                    Graph graph = new Graph();
                    graph.setNode(currenNode);
                    graph.setNewEdges(currenEdge);
                    graphs.add(graph);
                }

            } else {
                Graph graph = new Graph();
                graph.setNode(currenNode);
                graph.setNewEdges(currenEdge);
                graphs.add(graph);
            }
        }
        System.out.println("123");
    }


    public static boolean DFS(ArrayList<Graph> graphs, String record, Graph currentPosition) {

        int nodePosition = 0;
        int edgePosition = 0;

        //定位当前位置
        for (int i = 0; i < graphs.size(); i++) {
            if (graphs.get(i).getNode().equals(currentPosition.getNode())) {
                nodePosition = i;
                for (int j = 0; j < graphs.get(i).getEdges().size(); j++) {
                    if (currentPosition.getEdges().get(0).equals(graphs.get(i).getEdges().get(j))) {
                        edgePosition = j;
                    }
                }
            }
        }


        Graph currentGraph = graphs.get(nodePosition);
        String currentNode = currentGraph.getNode();
        ArrayList<String> currentGraphEdges = currentGraph.getEdges();


        if (record.equals(null)) {
            record = currentNode;
        }


        //若是当前node的edge的最后一个, 则跳到下一个
        if (edgePosition == currentGraphEdges.size() - 1) {
            //找下一个 当前node位置+1 小于等于 总size,
            if (nodePosition + 1 <= graphs.size()) {
                // 下一个是下一个节点的第一个边
                String nextEdge = graphs.get(nodePosition + 1).getEdges().get(0);
                if (record.contains(nextEdge)) {
                    return true;
                }
                record = record + nextEdge;
                //记录当前处理位置
                currentPosition.setNode(graphs.get(nodePosition + 1).getNode());
                currentPosition.setNewEdges(nextEdge);

            } else {
                // 否则说明是最后一个数据 , 返回false
                return false;
            }
        } else {
            //找下一个 若路径里出现重复的则说明有环
            String nextEdge = currentGraphEdges.get(edgePosition + 1);
            if (record.contains(nextEdge)) {
                return true;
            }
            record = record + nextEdge;
            //记录当前处理位置
            currentPosition.setNode(currentNode);
            currentPosition.setNewEdges(nextEdge);
        }



        return DFS(graphs, record, currentPosition);

    }


}

class Graph {
    private String Node;
    private ArrayList<String> edges;

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }

    public ArrayList<String> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<String> edges) {
        this.edges = edges;
    }

    public void setNewEdges(String edge) {
        ArrayList<String> edges = new ArrayList<>();
        edges.add(edge);
        this.edges = edges;
    }
}