package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P4 extends Production{

    public P4(SimpleGraph<Vertex, DefaultEdge> graph, Vertex S) {
        super(graph, S);
    }

    @Override
    public void apply() throws CannotApplyProductionException {
        SimpleGraph<Vertex,DefaultEdge> graph = getGraph();
        Vertex S = getSource();
        if (!S.getLabel().equals("Y")) throw new CannotApplyProductionException();

        int sourceId = S.getId();
        S.setLabel("c");
        }
}
