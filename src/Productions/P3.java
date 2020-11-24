package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P3 extends Production{

    public P3(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S) {
        super(graph, S);
    }

    @Override
    public void apply() throws CannotApplyProductionException {
        SimpleGraph<Vertex,DefaultEdge> graph = getGraph();
        Vertex S = getSource();
        if (!S.getLabel().equals("Y")) throw new CannotApplyProductionException();

        List<Vertex> neighbours = neighborListOf(graph,S);
        int sourceId = S.getId();
        int hId = highestId();
        graph.removeVertex(S);

        Vertex newY = new Vertex(sourceId,"Y");
        Vertex newX = new Vertex(hId+1,"X");

        graph.addVertex(newY);
        graph.addVertex(newX);

        graph.addEdge(newX,newY);

        for(Vertex vertex : neighbours){
            switch (vertex.getLabel()) {
                case "a", "c", "X" -> graph.addEdge(newX, vertex);
                case "b", "d", "Y" -> graph.addEdge(newY, vertex);
            }

        }

    }
}
