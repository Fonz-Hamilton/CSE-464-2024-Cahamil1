package org.hamilton.fonz;


import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;


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


        Assert.assertEquals(graphSize, dotGraph.getSize());
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


}
