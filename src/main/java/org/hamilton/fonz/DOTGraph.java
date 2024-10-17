package org.hamilton.fonz;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Factory;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import guru.nidi.graphviz.model.MutableGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class DOTGraph {
    private MutableGraph graph;

    /**
     * Parse a DOT file and output a graph object
     * @param filepath: filepath of the DOT file
     */
    public void parseGraph(String filepath) {
        try {
            // This is super redundant probably, but I want the file path in the method and dont
            // want to change the parameters given in the pdf. just want user to enter name
            // of file with no path
            URL locator = getClass().getClassLoader().getResource(filepath);

            if (locator == null) {
                throw new IOException("File not found: " + filepath);
            }

            // Get the absolute path
            String file = Paths.get(locator.toURI()).toString();

            graph = new Parser().read(new File(file));

            System.out.println("Graph parsed");
        }
        catch(IOException | URISyntaxException e) {
            e.printStackTrace();
            graph = null;
            System.out.println("Could not parse graph");

        }
    }
    @Override
    public String toString() {
        return graph.toString();
    }

    /**
     * Add node and check duplicate labels
     * @param label: name of node
     */
    public void addNode(String label) {

        boolean dupLabel = false;
        Collection<MutableNode> nodes = graph.nodes();

        MutableNode[] nodeArray = nodes.toArray(new MutableNode[0]);
        for(int i = 0; i < nodeArray.length; i++) {

            MutableNode node = nodeArray[i];
            if(node.name().toString().equals(label)) {
                dupLabel = true;
                break;
            }
        }
        if(!dupLabel) {
            graph.add(Factory.mutNode(label));
            System.out.println("Added node: " + label);
        }
        else {
            System.out.println("Duplicate node: " + label);
        }

        graph.add(Factory.mutNode(label));
    }

    /**
     * Add a list of nodes
     * @param label: array of nodes
     */
    public void addNodes(String[] label) {

        Collection<MutableNode> currentNodes = graph.nodes();
        MutableNode[] nodeArray = currentNodes.toArray(new MutableNode[0]);

        for (int i = 0; i < label.length; i++) {
            boolean dupLabel = false;

            for(int j = 0; j < nodeArray.length; j++) {
                if(nodeArray[j].name().toString().equals(label[i])) {
                    dupLabel = true;
                    break;
                }
            }
            if(!dupLabel) {
                graph.add(Factory.mutNode(label[i]));
                System.out.println("Added node: " + label[i]);
            }
            else {

                System.out.println("Duplicate node: " + label[i]);
            }

        }
    }

    // For test method
    public int getSize() {
        return graph.nodes().size();
    }
    // for test method
    public MutableGraph getGraph() {
        return graph;
    }
}
