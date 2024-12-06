package org.hamilton.fonz;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalk extends GraphSearchTemplate{

    public RandomWalk(MutableGraph graph) {
        super(graph);
    }
    @Override
    protected void traverse(ArrayList<GraphNode> graphNodes, GraphNode srcNode, MutableNode dst) {

        Random random = new Random();

        GraphNode currentNode = srcNode;
        List<GraphNode> visitedNodes = new ArrayList<>();

        while (true) {
            visitedNodes.add(currentNode);
            System.out.print("visted path: ");
            for(GraphNode node : visitedNodes) {
                System.out.print(node.getNode().name() + " ");
            }
            System.out.println();
            // case where the src and dst are the same
            if (currentNode.getNode().equals(dst)) {
                return;
            }

            List<GraphNode> neighbors = new ArrayList<>();
            for(Link link : currentNode.getNode().links()) {
                Label label = link.to().name();             // find the link

                for (int j = 0; j < graphNodes.size(); j++) {
                    GraphNode neighbor = graphNodes.get(j);
                    if (neighbor.getNode().name().equals(label)) {

                        neighbors.add(neighbor);
                    }
                }
            }

            if (neighbors.isEmpty()) {

                System.out.println("No path to destination, restarting");
                currentNode = srcNode; // Restart at the source node
                visitedNodes.clear(); // Clear the visited path since it's a new attempt
                continue; // Restart the loop

            }

            int randomIndex = random.nextInt(neighbors.size());
            GraphNode nextNode = neighbors.get(randomIndex);

            // Update the current node
            nextNode.setPredecessor(currentNode);
            currentNode = nextNode;
        }


    }
}
