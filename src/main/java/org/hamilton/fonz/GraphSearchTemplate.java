package org.hamilton.fonz;

import java.util.ArrayList;

import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
public abstract class GraphSearchTemplate {

    protected final MutableGraph graph;
    protected boolean destinationFound = false;
    protected int time = 0;


    public GraphSearchTemplate(MutableGraph graph) {
        this.graph = graph;
    }

    public Path search(MutableNode src, MutableNode dst) {
        if (src == null || dst == null) {
            return new Path(null, null);
        }

        ArrayList<GraphNode> graphNodes = initNodes(src, dst, (byte) 0);
        GraphNode srcNode = graphNodes.get(graphNodes.size() - 2);
        GraphNode dstNode = graphNodes.get(graphNodes.size() - 1);

        traverse(graphNodes, srcNode, dst);

        return new Path(srcNode, dstNode);
    }

    protected abstract void traverse(ArrayList<GraphNode> graphNodes, GraphNode srcNode, MutableNode dst);

    protected ArrayList<GraphNode> initNodes(MutableNode src, MutableNode dst, byte color) {

        ArrayList<MutableNode> nodes = new ArrayList<>(graph.nodes());  // make an array of the nodes in graph
        ArrayList<GraphNode> graphNodes = new ArrayList<>();            // make an array of graphNode class
        GraphNode srcNode = new GraphNode(src);                         // source node
        GraphNode dstNode = new GraphNode(dst);                         // destination node

        // initialize GraphNode "structs"
        for(int i = 0; i < nodes.size(); i++) {

            // initialize the source node and destination node separately
            if(!nodes.get(i).equals(src) && !nodes.get(i).equals(dst)) {
                graphNodes.add(new GraphNode(nodes.get(i)));

            }
        }

        // initialize source node
        srcNode.setColor(color);        // set source node to grey or white (Seems to not matter actually should see why)
        srcNode.setDst(0);              // distance is 0 since it is source node
        srcNode.setPredecessor(null);   // no predecessor
        graphNodes.add(srcNode);        // add to array

        // initialize destination node
        dstNode.setColor((byte)0);
        dstNode.setDst(Integer.MAX_VALUE);
        dstNode.setPredecessor(null);
        graphNodes.add(dstNode);

        return graphNodes;

    }
}
