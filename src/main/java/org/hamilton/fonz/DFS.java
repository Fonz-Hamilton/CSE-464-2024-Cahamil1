package org.hamilton.fonz;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.ArrayList;

public class DFS extends GraphSearchTemplate{

    public DFS(MutableGraph graph) {
        super(graph);
    }

    /**
     * A depth first search method. Searches the graph
     * @param graphNodes the graph that is parsed
     * @param srcNode the sourced node to be searched
     * @param dst the destination node to be searched
     */
    @Override
    protected void traverse(ArrayList<GraphNode> graphNodes, GraphNode srcNode, MutableNode dst) {
        time = 0;
        srcNode.setTime(time);
        DFSVisit(graphNodes, srcNode,dst);


    }
    /**
     * A helper method for DFS. handles the visits of nodes
     * @param graphNodes An ArrayList of graph nodes
     * @param graphNode A single GraphNode
     * @param dst the destination node
     */
    private void DFSVisit(ArrayList<GraphNode> graphNodes, GraphNode graphNode,MutableNode dst) {
        if (destinationFound) return;       // need this to stop the search when it finds the node
        time++;                             // not needed but was a variable in CLRS algorithms book
        graphNode.setTime(time);             // same as above
        graphNode.setColor((byte) 1);

        if(graphNode.getNode().equals(dst)) {
            destinationFound = true;        // again need to end recursion if destination found
            return;
        }

        for (Link link : graphNode.getNode().links()) {
            Label label = link.to().name();             // find the link
            //test
            //System.out.println("currentNode: " + graphNode.getNode().name() + "; link: " + label.toString());

            // goes through the arrayList and finds matching node using link
            // nodes in this loop are adjacent nodes found through edge link
            for (int i = 0; i < graphNodes.size(); i++) {
                if (graphNodes.get(i).getNode().name().toString().equals(label.toString())) {

                    // if color is white (not visited)
                    if (graphNodes.get(i).getColor() == 0) {
                        graphNodes.get(i).setPredecessor(graphNode);
                        DFSVisit(graphNodes, graphNodes.get(i),dst);
                        if (destinationFound) return;       // set the variable that it was found
                    }
                }
            }

        }
        graphNode.setColor((byte) 2);
        time++;
        graphNode.setFinalTime(time);
    }
}
