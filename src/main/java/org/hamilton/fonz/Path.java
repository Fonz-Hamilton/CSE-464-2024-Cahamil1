package org.hamilton.fonz;

/**
 * Makes a path between nodes
 */
public class Path {
    private final GraphNode src;
    private final GraphNode dst;
    private String pathString = "";

    /**
     * Constructor for Path
     * @param src the source node
     * @param dst the destination node
     */
    public Path( GraphNode src, GraphNode dst) {
        this.src = src;
        this.dst = dst;
    }

    /**
     * Helper method for printPath. Builds the string to represent the graph
     * @param src the source node
     * @param dst the destination node
     */
    private void pathBuilder(GraphNode src, GraphNode dst) {
        //test
        //System.out.println("dstNode: " + dst.getNode().name());
        if(src != null && dst != null) {
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
        else {
            System.out.println("No path exists");
        }

    }

    /**
     * Prints the path between nodes
     * @return String
     */
    public String printPath() {
        pathString = "";
        if (src != null && dst != null) {
            pathBuilder(src, dst);
            return pathString;
        }
        else {
            return "No path exists";
        }

    }
}
