package org.hamilton.fonz;


public class Main {
    public static void main( String[] args ) {
        DOTGraph dotGraph = new DOTGraph();

        dotGraph.parseGraph("input.dot");
        System.out.println(dotGraph.toString());

        String[] nodesToAdd = {"Z", "X", "Y"};
        dotGraph.addNodes(nodesToAdd);
        System.out.println(dotGraph.toString());

        String addedNode = "G";
        dotGraph.addNode(addedNode);
        System.out.println(dotGraph.toString());

        dotGraph.addEdge("X","Y");
        System.out.println(dotGraph.toString());
        dotGraph.addEdge("X","Z");
        System.out.println(dotGraph.toString());
        dotGraph.addEdge("X","G");
        System.out.println(dotGraph.toString());
/*
        dotGraph.outputDOTGraph("output.dot");

        dotGraph.outputGraphics("output","png");

         */
    }
}
