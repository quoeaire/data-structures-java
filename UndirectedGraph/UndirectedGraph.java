package UndirectedGraph;

import java.util.List;

/**
 * Class for an undirected graph. Unable to clear, you must create a new one.
 * @author Matthew Smith
 */
public class UndirectedGraph<NodeType, EdgeType> {

    /**
     * Simple node class.
     */
    private class Node {
        NodeType data;
        List<EdgeType> edges;

        Node(NodeType data) {
            this.data = data;
        }
    }

    /**
     * Simple directed edge class.
     */
    private class Edge {
        Node source, destination;
        EdgeType weight;

        Edge(EdgeType weight, Node source, Node destination) {
            this.weight = weight;
            this.source = source;
            this.destination = destination;
        }
    }

    /**
     * Constructor. You must supply it with data to create a graph.
     */
    public UndirectedGraph(NodeType data) {

    }

    /**
     * Add a node to the p
     * @param data
     * @param from
     * @param weight
     */
    public void addNode(NodeType data, NodeType from, EdgeType weight) {

    }

    /**
     * Removes the desired node and connecting edges from the tree.
     * @param data
     */
    public void removeNode(NodeType data) {

    }

    /**
     * Determines if the graph includes supplied data.
     * @param data
     */
    public void contains(NodeType data) {

    }

    /**
     * Returns the size of the graph.
     * @return
     */
    public int size() {
        return -1;
    }

    /**
     * Determines if the graph is empty or not.
     * @return
     */
    public boolean isEmpty() {
        return true;
    }

    /**
     * Returns a string representation of the graph.
     */
    @Override
    public String toString() {
        return "";
    }
}