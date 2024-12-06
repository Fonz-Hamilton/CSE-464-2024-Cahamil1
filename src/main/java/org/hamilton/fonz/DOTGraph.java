package org.hamilton.fonz;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.*;
import guru.nidi.graphviz.parse.Parser;
import guru.nidi.graphviz.model.MutableGraph;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

/**
 * DOTGraph modifies and outputs a graph in DOT format
 */
public class DOTGraph {
    private MutableGraph graph;
    private static int time;            // for the DFS search that doesn't really need to be used
    private boolean destinationFound = false; // A boolean used for DFSVisit. Scope needs to be outside the method

	public enum Algorithm {
    BFS, DFS, RANDOM_WALK
    }

    /**
     * Parse a DOT file and output a graph object
     * @param filepath: filepath of the DOT file
     */
    public void parseGraph(String filepath) {
        try {
            // This is super redundant probably, but I want the file path in the method and don't
            // want to change the parameters given in the pdf. just want user to enter name
            // of file with no path. file should be in resources
            URL locator = getClass().getClassLoader().getResource(filepath);

            if (locator == null) {
                throw new IOException("File not found: " + filepath);
            }

            // Get the absolute path
            String file = Paths.get(locator.toURI()).toString();

            graph = new Parser().read(new File(file));

            System.out.println("Graph parsed");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            graph = null;
            System.out.println("Could not parse graph");

        }
    }

    /**
     * A toString method that turns the DOT graph into a String object
     * @return String
     */
    @Override
    public String toString() {
        return graph.toString();
    }

    /**
     * Adds a node and checks for duplicate labels
     * @param label: name of node
     */
    public void addNode(String label) {

        boolean dupLabel = false;
        Collection<MutableNode> nodes = graph.nodes();

        // to search through the nodes
        MutableNode[] nodeArray = nodes.toArray(new MutableNode[0]);
        for (int i = 0; i < nodeArray.length; i++) {

            MutableNode node = nodeArray[i];
            if (node.name().toString().equals(label)) {
                dupLabel = true;
                break;
            }
        }
        if (!dupLabel) {
            graph.add(Factory.mutNode(label));
            System.out.println("Added node: " + label);
        } else {
            System.out.println("Duplicate node: " + label);
        }

        graph.add(Factory.mutNode(label));
    }

    /**
     * Add a list of nodes to the graph
     * @param label: array of nodes
     */
    public void addNodes(String[] label) {

        Collection<MutableNode> currentNodes = graph.nodes();
        MutableNode[] nodeArray = currentNodes.toArray(new MutableNode[0]);

        for (int i = 0; i < label.length; i++) {
            boolean dupLabel = false;

            for (int j = 0; j < nodeArray.length; j++) {
                if (nodeArray[j].name().toString().equals(label[i])) {
                    dupLabel = true;
                    break;
                }
            }
            if (!dupLabel) {
                graph.add(Factory.mutNode(label[i]));
                System.out.println("Added node: " + label[i]);
            } else {

                System.out.println("Duplicate node: " + label[i]);
            }

        }
    }

    /**
     * Add edges to imported graph
     * @param srcLabel the name of the source edge
     * @param dstLabel the name of the destination edge
     */
    public void addEdge(String srcLabel, String dstLabel) {
        MutableNode srcNode = Factory.mutNode(srcLabel);
        MutableNode dstNode = Factory.mutNode(dstLabel);

        srcNode.addLink(dstNode);
        graph.add(srcNode);
    }

    /**
     * Output imported graph into DOT file
     * @param path: name of the new file
     */
    public void outputDOTGraph(String path) {
        try {
            Graphviz.fromGraph(graph).render(Format.DOT).toFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Output imported graph into graphic (PNG)
     * @param path:   name of the new file
     * @param format: format of the file
     */
    public void outputGraphics(String path, String format) {
        try {
            if (format.equalsIgnoreCase("png")) {
                Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(path));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes a node from the graph
     * @param label the name of the node
     */
    public void removeNode(String label) {
        List<MutableNode> nodeList = new ArrayList<>(graph.nodes());
        boolean nodeExists = false;

        for (int i = 0; i < nodeList.size(); i++) {
            MutableNode node = nodeList.get(i);
            if (node.name().toString().equals(label)) {
                nodeExists = true;
                break;
            }
        }

        // If the node doesn't exist, throw an exception
        if (!nodeExists) {
            throw new NoSuchElementException("Node with label \"" + label + "\" does not exist in the graph.");
        }

        String graphString = graph.toString();
        graphString = removeNodeRegEx(graphString, label);

        graph = rebuild(graphString);
    }

    /**
     * removes a list of nodes from the graph
     * @param label a list of names of nodes that the graph needs to remove
     */
    public void removeNodes(String[] label) {
        String graphString = graph.toString();

        for (int i = 0; i < label.length; i++) {
            graphString = removeNodeRegEx(graphString, label[i]);
        }
        graph = rebuild(graphString);
    }

    /**
     * Removes an edge from the graph
     * @param srcLabel the name of the source edge
     * @param dstLabel the name of the destination edge
     */
    public void removeEdge(String srcLabel, String dstLabel) {
        boolean edgeExists = false;
        List<MutableNode> nodes = new ArrayList<>(graph.nodes());

        for (int i = 0; i < nodes.size(); i++) {
            MutableNode node = nodes.get(i);

            if (node.name().toString().equals(srcLabel)) {
                List<Link> links = new ArrayList<>(node.links());

                for (int j = 0; j < links.size(); j++) {
                    Link link = links.get(j);

                    if (link.to().name().toString().equals(dstLabel)) {
                        edgeExists = true;
                        break;
                    }
                }
            }
            if (edgeExists) break;
        }
        if (!edgeExists) {
            throw new IllegalArgumentException("Edge from " + srcLabel + " to " + dstLabel + " does not exist.");
        }

        String graphString = graph.toString();

        graphString = removeEdgeRegEx(graphString, srcLabel, dstLabel);
        graph = rebuild(graphString);

    }

    /**
     * Graph search that uses either BFS or DFS. Busted out my CSE310 book for this one
     * @param src the source node
     * @param dst the destination node
     * @return Path
     */
    public Path graphSearch(MutableNode src, MutableNode dst, Algorithm algo) {
        GraphSearchTemplate strategy;

        if (src != null && dst != null) {
            switch (algo) {
                case BFS:
                    strategy = new BFS(graph);
                    return strategy.search(src, dst);

                case DFS:
                    strategy = new DFS(graph);
                    return strategy.search(src, dst);

                case RANDOM_WALK:
                    strategy = new RandomWalk(graph);
                    return strategy.search(src, dst);

                default:
                    throw new IllegalArgumentException("Unsupported algorithm: " + algo);

            }
        }
        return new Path(null, null);
    }

    /**
     * Method for testing
     * @return int size of the graph
     */
    protected int getSize() {
        return graph.nodes().size();
    }

    /**
     * Method for testing
     * @return MutableGraph
     */
    protected MutableGraph getGraph() {
        return graph;
    }

    /**
     * Rebuild the graph after edge or node is removed
     * @param graphString the String representation of the graph
     * @return MutableGraph
     */
    private MutableGraph rebuild(String graphString) {

        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(graphString.getBytes(StandardCharsets.UTF_8));
            graph = new Parser().read(inputStream);  // Rebuild the graph from the modified string
            System.out.println("Graph rebuilt successfully.");
        }
        catch(IOException e) {
            System.out.println("It should just be a string");
        }

        return graph;
    }

    /**
     * Helper method. Regular expressions for formatting the string so the nodes can be removed
     * @param graphString the String representation of the graph
     * @param label the name of the node to be removed
     * @return String
     */
    private String removeNodeRegEx(String graphString, String label) {

        // I hate regex
        // remove edges where the node is the destination ( -> "B")
        String edgeDestRegex = "(?m)\"[^\"]*\"\\s*->\\s*\"" + label + "\"\\s*;?\\s*";
        graphString = graphString.replaceAll(edgeDestRegex, "");

        // remove edges where the node is the source ("A" -> )
        String edgeSourceRegex = "(?m)\"" + label + "\"\\s*->\\s*\"[^\"]*\"\\s*;?\\s*";
        graphString = graphString.replaceAll(edgeSourceRegex, "");

        // Remove the node definition line
        String nodeDefRegex = "(?m)^\\s*\"" + label + "\"\\s*;?\\s*$";
        graphString = graphString.replaceAll(nodeDefRegex, "");

        // Clean up extra whitespace and newlines left over after removal
        String cleanupRegex = "(?m)\\n\\s*\\n";  // Match consecutive newlines or newlines with spaces

        graphString = graphString.replaceAll(cleanupRegex, "\n");  // Replace with a single newline

        // test for the modified graphString
        System.out.println("Modified graphString:\n" + graphString);

        return graphString;
    }

    /**
     * Helper method: Regular expressions for formatting the string so the edges can be removed
     * @param graphString
     * @param src, dst
     * @return String
     */
    private String removeEdgeRegEx(String graphString, String src, String dst) {

        String edgeRegex = "(?m)\"" + src + "\"\\s*->\\s*\"" + dst + "\"\\s*;?\\s*";
        graphString = graphString.replaceAll(edgeRegex, "");

        // Clean up extra whitespace and newlines left over after removal
        String cleanupRegex = "(?m)\\n\\s*\\n";
        graphString = graphString.replaceAll(cleanupRegex, "");
        return graphString;
    }

    /**
     * Gets a node based on name
     * @param label the name of the label
     * @return MutableNode
     */
    public MutableNode getNode(String label) {
        for(MutableNode node : graph.nodes()) {
            if(node.name().toString().equals(label)) {
                return node;
            }
        }
        return null;
    }
}
