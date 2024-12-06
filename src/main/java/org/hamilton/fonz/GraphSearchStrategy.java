package org.hamilton.fonz;

import guru.nidi.graphviz.model.MutableNode;

import java.util.ArrayList;

public interface GraphSearchStrategy {
    public Path search(MutableNode srcNode, MutableNode dst);
}
