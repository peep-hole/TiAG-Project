package Productions;

import GraphElements.Vertex;
import org.jgrapht.graph.SimpleGraph;
import java.util.List;
import static org.jgrapht.Graphs.neighborListOf;

public class P2 extends Production{

    public P2(SimpleGraph graph, Vertex S) {
        super(graph, S);
    }

    @Override
    public void apply() throws CannotApplyProductionException {
        SimpleGraph graph = getGraph();
        Vertex S = getSource();
        if (S.getLabel() != "X") throw new CannotApplyProductionException();

        List<Vertex> neighbours = neighborListOf(graph,S);
        int sourceId = S.getId();
        int hId = highestId();
        graph.removeVertex(S);

        Vertex newS = new Vertex(sourceId,"X");
        Vertex vb = new Vertex(hId+1,"a");
        Vertex vd = new Vertex(hId+2,"c");
        graph.addEdge(newS,vb);
        graph.addEdge(newS,vd);

        for(Vertex vertex : neighbours){
            switch (vertex.getLabel()){
                case "b":
                case "d":
                case "Y":
                    graph.addEdge(newS,vertex);
                    break;
                case "a":
                case "X":
                    graph.addEdge(vb,vertex);
                    break;
                case "c":
                    graph.addEdge(vd,vertex);
                    break;
            }

        }

    }
}
