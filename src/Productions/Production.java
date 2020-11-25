package Productions;

import GraphElements.Vertex;
import GraphElements.VertexSupplier;
import GraphTransformationIO.GraphImporter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;
import static org.jgrapht.Graphs.neighborListOf;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Production {

    private SimpleGraph<Vertex, DefaultEdge> leftSideProductionGraph;
    private SimpleGraph<Vertex, DefaultEdge> rightSideProductionGraph;
    private List<EmbeddingTransformation> transformations;

    public Production(){}{

    }

    public void parseProduction(File leftSide, File rightSide, File embedding) {
        leftSideProductionGraph = new SimpleGraph<>(new VertexSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        rightSideProductionGraph = new SimpleGraph<>(new VertexSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        transformations = new LinkedList<>();

        GraphImporter importer = new GraphImporter();

        importer.importIt(leftSideProductionGraph, leftSide);
        importer.importIt(rightSideProductionGraph, rightSide);

        SimpleGraph<Vertex,DefaultEdge> embeddingGraph = new SimpleGraph<>(new VertexSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
        importer.importIt(embeddingGraph, embedding);

        parseEmbeddingTransformations(embeddingGraph);


    }

    public void applyOn(SimpleGraph<Vertex, DefaultEdge> graph, int id) {

        int maxID = -1;

        for(Vertex vertex: graph.vertexSet()) if(vertex.getId() > maxID) maxID = vertex.getId();

        Vertex leftSide = findSubGraph(graph, id);
        substituteWithRightSide(graph,leftSide, maxID);


    }

    private Vertex findSubGraph(SimpleGraph<Vertex, DefaultEdge> graph, int id) {

        String label = null;
        for(Vertex vertex : leftSideProductionGraph.vertexSet()) {
           label = vertex.getLabel();
        }

        Vertex result = null;

        for(Vertex vertex: graph.vertexSet()) {
            if(vertex.equals(new Vertex(id, label))) result = vertex;
        }

        return result;

    }

    public void substituteWithRightSide(SimpleGraph<Vertex, DefaultEdge> graph, Vertex leftSide, int maxID) {

        // missing id after deletion is going to be signed to first vertex from rightSideGraph

        int missingID = leftSide.getId();
        List<Vertex> neighbours = neighborListOf(graph, leftSide);
        graph.removeVertex(leftSide);
        List<Vertex> vertices = new ArrayList<>(rightSideProductionGraph.vertexSet());
        for(int i = 1; i <= vertices.size(); i++) {
            vertices.get(i-1).setId(maxID+i);
            graph.addVertex(vertices.get(i-1));
        }
        for(Vertex neighbour : neighbours) {
           for(EmbeddingTransformation transformation: transformations ) {
               if(transformation.from.equals(neighbour.getLabel())){
                   for(Vertex vertex : vertices) {
                       if(vertex.getLabel().equals(transformation.to)) {
                           graph.addEdge(neighbour, vertex);
                       }
                   }
               }
           }
        }
        vertices.get(0).setId(missingID);
    }

    private void parseEmbeddingTransformations(SimpleGraph<Vertex, DefaultEdge> graph) {
        Set<DefaultEdge> edgeSet = graph.edgeSet();
        for(DefaultEdge edge: edgeSet) {
            transformations.add(new EmbeddingTransformation(graph.getEdgeSource(edge).getLabel(),graph.getEdgeTarget(edge).getLabel()));
        }
    }
}
