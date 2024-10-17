package org.hamilton.fonz;


public class Main {
    public static void main( String[] args ) {
        DOTGraph dotGraph = new DOTGraph();

        dotGraph.parseGraph("input.dot");

    }
}
