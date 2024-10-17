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


}
