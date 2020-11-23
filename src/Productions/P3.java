package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P3 extends Production{

    public P3(SimpleGraph graph, Vertex S) {
        super(graph, S);
    }

    @Override
    public void apply() throws CannotApplyProductionException {
        SimpleGraph graph = getGraph();
        Vertex S = getSource();
        if (S.getLabel() != "Y") throw new CannotApplyProductionException();

        List<Vertex> neighbours = neighborListOf(graph,S);
        int sourceId = S.getId();
        int hId = highestId();
        graph.removeVertex(S);

        Vertex newY = new Vertex(sourceId,"Y");
        Vertex newX = new Vertex(hId+1,"X");
        graph.addEdge(newX,newY);

        for(Vertex vertex : neighbours){
            switch (vertex.getLabel()){
                case "a":
                case "c":
                case "X":
                    graph.addEdge(newX,vertex);
                    break;
                case "b":
                case "d":
                case "Y":
                    graph.addEdge(newY,vertex);
                    break;
            }

        }

    }
}
