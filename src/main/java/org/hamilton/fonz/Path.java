package org.hamilton.fonz;
import guru.nidi.graphviz.model.MutableNode;

import java.util.List;
import java.util.ArrayList;

public class Path {
    private GraphNode src;
    private GraphNode dst;
    private String pathString = "";

    public Path( GraphNode src, GraphNode dst) {

        this.src = src;
        this.dst = dst;

    }

    private void pathBuilder(GraphNode src, GraphNode dst) {
        //test
        //System.out.println("dstNode: " + dst.getNode().name());
        if(dst.getNode().equals(src.getNode())) {

            pathString = pathString + src.getNode().name();

        }
        else if(dst.getPredecessor() == null) {
            //System.out.println("this is predecessor: " + dst.getPredecessor().getNode().name());
            System.out.println("No path exists");
        }
        else{

            pathBuilder(src, dst.getPredecessor());
            pathString = pathString + " -> ";
            pathString = pathString + dst.getNode().name();
        }
    }

    public String printPath() {
        pathString = "";
        pathBuilder(src, dst);
        return pathString;
    }
}


