package org.hamilton.fonz;


import guru.nidi.graphviz.model.MutableGraph;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


public class DOTGraphTest {
    private DOTGraph dotGraph;

    @Before
    public void setUp() {
        dotGraph = new DOTGraph();
    }

    @Test
    public void testParseGraphValid() {
        // check for DOT file in resource folder
        String validFilePath = "testGraph.dot";
        dotGraph.parseGraph(validFilePath);


        Assert.assertNotNull(dotGraph);
        Assert.assertNotNull(dotGraph.toString());

        System.out.println(dotGraph.toString()); // Output for verification
    }

    @Test
    public void testParseGraphInvalid() {
        String invalidFilePath = "NoGraphHere.dot";
        dotGraph.parseGraph(invalidFilePath);


        Assert.assertNull(dotGraph.getGraph());
    }

    @Test
    public void testAddNode() {
        dotGraph.parseGraph("testGraph.dot");
        dotGraph.addNode("C");
        Assert.assertTrue(dotGraph.toString().contains("C"));
    }

    @Test
    public void testAddDuplicateNode() {
        dotGraph.parseGraph("testGraph.dot");
        dotGraph.addNode("B");

        int graphSize = dotGraph.getSize();

        dotGraph.addNode("B");


        assertEquals(graphSize, dotGraph.getSize());
    }

    @Test
    public void testAddNodes() {
        dotGraph.parseGraph("testGraph.dot");
        String[] addNodes = {"C", "D", "E"};
        dotGraph.addNodes(addNodes);


        for (int i = 0; i < addNodes.length; i++) {
            String node = addNodes[i];
            Assert.assertTrue(dotGraph.toString().contains(node));
        }
    }
    @Test
    public void testAddEdge() {

        dotGraph.parseGraph("testGraph.dot");


        String srcLabel = "D";
        String dstLabel = "F";
        dotGraph.addNode(srcLabel);
        dotGraph.addNode(dstLabel);

        dotGraph.addEdge(srcLabel, dstLabel);

        Assert.assertTrue(dotGraph.toString().contains("\"" + srcLabel + "\"" + " -> " + "\""+ dstLabel + "\""));
    }
    @Test
    public void testOutputDOTGraph() throws IOException {
        dotGraph.parseGraph("testGraph.dot");
        String path = "output.dot";

        dotGraph.outputDOTGraph(path);
        File outputFile = new File(path);

        // check if the output file exists
        Assert.assertTrue(outputFile.exists());
        outputFile.delete(); // Delete all the stuff
    }

    @Test
    public void testOutputGraphics() throws IOException {
        dotGraph.parseGraph("testGraph.dot");
        String path = "output.png";

        dotGraph.outputGraphics(path, "png");
        File outputFile = new File(path);

        // Check if the output file exists
        Assert.assertTrue(outputFile.exists());
        outputFile.delete(); // Delete all the stuff
    }

    @Test
    public void testRemoveNodeCorrect() {
        dotGraph.parseGraph("testGraph.dot");


        // Remove a node and an edge
        dotGraph.removeNode("A");


        // Assertions: Check that node "B" and edge "A" -> "C" are removed
        Assert.assertFalse(dotGraph.toString().contains("A"));
    }
    @Test
    public void testRemoveNodeIncorrect() {
        dotGraph.parseGraph("testGraph.dot");
        String label = "l";

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            dotGraph.removeNode(label);
        });
        assertEquals("Node with label \"" + label + "\" does not exist in the graph.", exception.getMessage());
    }

    @Test
    public void testRemoveEdgeCorrect() {
        dotGraph.parseGraph("testGraph.dot");
        String srcLabel = "A";
        String dstLabel = "B";
        dotGraph.removeEdge(srcLabel, dstLabel);

        Assert.assertFalse(dotGraph.toString().contains("\"" + srcLabel + "\"" + " -> " + "\""+ dstLabel + "\""));
    }

    @Test
    public void testRemoveEdgeIncorrect() {
        dotGraph.parseGraph("testGraph.dot");
        String srcLabel = "c";
        String dstLabel = "l";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dotGraph.removeEdge(srcLabel, dstLabel);
        });

        assertEquals("Edge from " + srcLabel + " to " + dstLabel + " does not exist.", exception.getMessage());

    }

    @Test
    public void testRemoveNodeAndEdgesCorrect() {
        dotGraph.parseGraph("testGraph.dot");
        dotGraph.addNode("v");
        dotGraph.addEdge("v", "A");

        dotGraph.removeNode("v");
        dotGraph.removeEdge("A", "B");
        Assert.assertFalse(dotGraph.toString().contains("v"));
        Assert.assertFalse(dotGraph.toString().contains("\"" + "A" + "\"" + " -> " + "\""+ "B"+ "\""));
    }

    @Test
    public void testBfs() {
        dotGraph.parseGraph("testGraph.dot");
        dotGraph.addNode("C");
        dotGraph.addNode("D");
        dotGraph.addEdge("B", "C");
        dotGraph.addEdge("A", "D");

        Path path = dotGraph.graphSearch(dotGraph.getNode("A"), dotGraph.getNode("C"), DOTGraph.Algorithm.BFS);
        Assert.assertEquals(path.printPath(), "A -> B -> C");
    }

    @Test
    public void testDfs() {
        dotGraph.parseGraph("testGraph.dot");
        dotGraph.addNode("C");
        dotGraph.addNode("D");
        dotGraph.addEdge("B", "C");
        dotGraph.addEdge("A", "D");

        Path path = dotGraph.graphSearch(dotGraph.getNode("A"), dotGraph.getNode("C"), DOTGraph.Algorithm.DFS);
        Assert.assertEquals(path.printPath(), "A -> B -> C");
    }

}
