package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P1 extends Production{

    public P1(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S) {
        super(graph, S);
    }

    @Override
    public void apply() throws CannotApplyProductionException {
        SimpleGraph<Vertex,DefaultEdge> graph = getGraph();
        Vertex S = getSource();
        if (!S.getLabel().equals("X")) throw new CannotApplyProductionException();

        List<Vertex> neighbours = neighborListOf(graph,S);
        int sourceId = S.getId();
        int hId = highestId();
        graph.removeVertex(S);

        Vertex newS = new Vertex(sourceId,"Y");
        Vertex va = new Vertex(hId+1,"a");
        Vertex vc = new Vertex(hId+2,"c");

        graph.addVertex(newS);
        graph.addVertex(va);
        graph.addVertex(vc);

        graph.addEdge(newS,va);
        graph.addEdge(newS,vc);

        for(Vertex vertex : neighbours){
            switch (vertex.getLabel()) {
                case "a", "c", "Y" -> graph.addEdge(newS, vertex);
                case "b", "X" -> graph.addEdge(vc, vertex);
                case "d" -> graph.addEdge(va, vertex);
            }

        }

    }
}
