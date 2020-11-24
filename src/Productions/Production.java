package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.Set;


abstract public class Production {
    private final SimpleGraph<Vertex,DefaultEdge> graph;
    private final Vertex source;

    public Production(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S){
        this.graph = graph;
        this.source = S;
    }

    public SimpleGraph<Vertex,DefaultEdge> getGraph() {
        return graph;
    }

    public Vertex getSource() {
        return source;
    }

    public abstract void apply() throws CannotApplyProductionException;

    public int highestId(){
        int hId = 0;
        Set<Vertex> vertices = graph.vertexSet();
        for (Vertex vertex : vertices){
            hId = Math.max(hId,vertex.getId());
        }
        return hId;
    }

}
