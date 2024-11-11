package org.hamilton.fonz;

import guru.nidi.graphviz.model.MutableNode;

public class GraphNode {
    private MutableNode node;
    private GraphNode predecessor;
    private byte color;
    private int dst;
    private int time;

    public GraphNode(MutableNode node) {
        this.node = node;
        this.predecessor = null;
        this.color = 0;
        this.dst = Integer.MAX_VALUE;
    }

    public void setNode(MutableNode node) {
        this.node = node;
    }
    public MutableNode getNode() {
        return node;
    }

    public void setPredecessor(GraphNode predecessor) {
        this.predecessor = predecessor;
    }
    public GraphNode getPredecessor() {
        return predecessor;
    }

    public void setColor(byte color) {
        this.color = color;
    }
    public byte getColor() {
        return color;
    }
    public void setDst(int dst) {
        this.dst = dst;
    }
    public int getDst() {
        return dst;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getTime() {
        return time;
    }
}
