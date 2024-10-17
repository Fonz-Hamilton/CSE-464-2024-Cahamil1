package org.hamilton.fonz;


public class Main {
    public static void main( String[] args ) {
        DOTGraph dotGraph = new DOTGraph();

        dotGraph.parseGraph("input.dot");

        String[] nodesToAdd = {"Z", "X", "Y"};
        dotGraph.addNodes(nodesToAdd);
        System.out.println(dotGraph.toString());

    }
}
