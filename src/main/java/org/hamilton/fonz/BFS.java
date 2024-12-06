package org.hamilton.fonz;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements GraphSearchStrategy {
    private final GraphSearchTemplate template;

    public BFS(MutableGraph graph) {
        this.template = new GraphSearchTemplate(graph) {
            @Override
            protected void traverse(ArrayList<GraphNode> graphNodes, GraphNode srcNode, MutableNode dst) {
                srcNode.setColor((byte)1);
                Queue<GraphNode> queue = new LinkedList<>();
                queue.add(srcNode);             // start with source node

                while(!queue.isEmpty()) {
                    GraphNode currentNode = queue.remove();     // pop from queue
                    //test
                    //System.out.println(currentNode.getNode().name());

                    // should cycle through all edges of node
                    for(Link link : currentNode.getNode().links()) {
                        Label label = link.to().name();             // find the link
                        //test
                        //System.out.println("currentNode: " + currentNode.getNode().name() +"; link: " + label.toString());

                        // goes through the arrayList and finds matching node using link
                        // nodes in this loop are adjacent nodes found through edge link
                        for(int i = 0; i < graphNodes.size(); i++) {
                            if(graphNodes.get(i).getNode().name().toString().equals(label.toString())) {
                                // if color is white (not visited)
                                if(graphNodes.get(i).getColor() == 0) {

                                    graphNodes.get(i).setColor((byte)1);    // set color to gray
                                    graphNodes.get(i).setDst(currentNode.getDst() + 1);
                                    graphNodes.get(i).setPredecessor(currentNode);

                                    //test
                                    //System.out.println(graphNodes.get(i).getNode().name()+ " is node and its Predecessor in DOTGraph: " + graphNodes.get(i).getPredecessor().getNode().name());
                                    //System.out.println("Node added to Q: " + graphNodes.get(i).getNode().name());
                                    queue.add(graphNodes.get(i));
                                }
                            }
                        }
                    }
                    currentNode.setColor((byte)2);  // node is done ("black")
                }

            }
        };
    }

    /**
     * A Breadth first search method. Searches the graph
     * @param graphNodes the graph that is parsed
     * @param srcNode the sourced node to be searched
     * @param dst the destination node to be searched
     */

    @Override
    public Path search(MutableNode src, MutableNode dst) {
        return template.search(src, dst);
    }
}
