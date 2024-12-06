package org.hamilton.fonz;

import guru.nidi.graphviz.model.MutableNode;

public class GraphNode {
    private MutableNode node;
    private GraphNode predecessor;
    private byte color;
    private int dst;
    private int time;

    /**
     * Constructor for GraphNode class
     * @param node the new node
     */
    public GraphNode(MutableNode node) {
        this.node = node;
        this.predecessor = null;
        this.color = 0;
        this.dst = Integer.MAX_VALUE;
    }

    /**
     * Sets the node
     * @param node the node that is to be sets
     */
    public void setNode(MutableNode node) {
        this.node = node;
    }

    /**
     * Gets the node
     * @return MutableNode
     */
    public MutableNode getNode() {
        return node;
    }

    /**
     * sets the predecessor node
     * @param predecessor the node that is to be the predecessor
     */
    public void setPredecessor(GraphNode predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * returns the predecessor node
     * @return GraphNode
     */
    public GraphNode getPredecessor() {
        return predecessor;
    }

    /**
     * Sets the color for BFS and DFS search
     * @param color A byte. color is 0,1,2
     */
    public void setColor(byte color) {
        this.color = color;
    }

    /**
     * returns the color For BFS and DFS search
     * @return byte
     */
    public byte getColor() {
        return color;
    }

    /**
     * sets the distance between nodes
     * @param dst the distance between nodes
     */
    public void setDst(int dst) {
        this.dst = dst;
    }

    /**
     * returns the distance
     * @return int
     */
    public int getDst() {
        return dst;
    }

    /**
     * sets the time for DFS search
     * @param time the time to be set
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * returns the time for DFS search
     * @return int
     */
    public int getTime() {
        return time;
    }
}
