package org.hamilton.fonz;


public class Main {
    public static void main( String[] args ) {
        DOTGraph dotGraph = new DOTGraph();

        dotGraph.parseGraph("input.dot");
        System.out.println(dotGraph.toString());

        /**
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



        System.out.println(dotGraph.toString());
        dotGraph.removeNode("G");
        System.out.println(dotGraph.toString());

        dotGraph.removeNodes(nodesToAdd);
        System.out.println(dotGraph.toString());


        //dotGraph.removeEdge("a","b");
        //System.out.println(dotGraph.toString());

        //dotGraph.test("e");

        //dotGraph.removeEdge("a","h");
        //System.out.println(dotGraph.toString());
         **/

        Path path = dotGraph.graphSearch(dotGraph.getNode("a"), dotGraph.getNode("d"), DOTGraph.Algorithm.DFS);
        System.out.println("DFS: \n" + path.printPath());


        path = dotGraph.graphSearch(dotGraph.getNode("a"), dotGraph.getNode("d"), DOTGraph.Algorithm.BFS);
        System.out.println("BFS: \n" + path.printPath());


    }
}
