package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P2 extends Production{

    public P2(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S) {
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

        Vertex newS = new Vertex(sourceId,"X");
        Vertex vb = new Vertex(hId+1,"b");
        Vertex vd = new Vertex(hId+2,"d");

        graph.addVertex(newS);
        graph.addVertex(vb);
        graph.addVertex(vd);

        graph.addEdge(newS,vb);
        graph.addEdge(newS,vd);

        for(Vertex vertex : neighbours){
            switch (vertex.getLabel()) {
                case "b", "d", "Y" -> graph.addEdge(newS, vertex);
                case "a", "X" -> graph.addEdge(vb, vertex);
                case "c" -> graph.addEdge(vd, vertex);
            }

        }

    }
}
