package Statistics;

import GraphElements.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.event.GraphVertexChangeEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;

import java.util.List;
import java.util.Set;


public class Stats {
    public SimpleGraph<Vertex, DefaultEdge> graph;
    public ConnectivityInspector<Vertex,DefaultEdge> ins;

    public Stats(SimpleGraph<Vertex, DefaultEdge> graph) {
        this.graph = graph;
        this.ins = new ConnectivityInspector<>(graph);
    }


    public int vertexCounter() {
        return graph.vertexSet().size();
    }

    public int edgeCounter() {
        return graph.edgeSet().size();
    }

    public int stronglyConnectedComponents() {
        return ins.connectedSets().size();
    }

    public float averageDegree(){
        float sum = 0;
        for (Vertex v : graph.vertexSet()){
            sum+= graph.degreeOf(v);
        }
        return sum/vertexCounter();
    }

    public float averageVDegree(){
//        V = {a,b,c,d}
        float sum = 0;
        int counter = 0;
        for (Vertex v : graph.vertexSet()){
            if (!v.getLabel().equals("X") && !v.getLabel().equals("Y")) {
                sum += graph.degreeOf(v);
                counter += 1;
            }
        }
        return sum/counter;
    }

    public float averageNumberSCC(){
        float count = 0;
        int setCounter = ins.connectedSets().size();
        for(Set SCC : ins.connectedSets()){
            count += SCC.size();
        }
        return count/setCounter;
    }
}