package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.List;
import java.util.Set;

import static org.jgrapht.Graphs.neighborListOf;

abstract public class Production {
    private SimpleGraph graph;
    private Vertex source;

    public Production(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S){
        this.graph = graph;
        this.source = S;
    }

    public SimpleGraph getGraph() {
        return graph;
    }

    public Vertex getSource() {
        return source;
    }

    public abstract void apply() throws CannotApplyProductionException;

    public int highestId(){
        int hId = 0;
        Set<Vertex> verticles = graph.vertexSet();
        for (Vertex vertex : verticles){
            hId = Math.max(hId,vertex.getId());
        }
        return hId;
    }
}
