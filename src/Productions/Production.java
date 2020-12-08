package Productions;

import GraphElements.Vertex;
import GraphElements.VertexSupplier;
import GraphTransformationIO.*;
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
        leftSideProductionGraph = new SimpleGraph<>(new VertexSupplier(),
                SupplierUtil.createDefaultEdgeSupplier(), false);
        rightSideProductionGraph = new SimpleGraph<>(new VertexSupplier(),
                SupplierUtil.createDefaultEdgeSupplier(), false);
        transformations = new LinkedList<>();

        GraphImporter importer = new GraphImporter();

        importer.importIt(leftSideProductionGraph, leftSide);
        importer.importIt(rightSideProductionGraph, rightSide);

        SimpleGraph<Vertex,DefaultEdge> embeddingGraph = new SimpleGraph<>(new VertexSupplier(),
                SupplierUtil.createDefaultEdgeSupplier(), false);
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
            if((vertex.getId() == id)&&(vertex.getLabel().equals(label))) result = vertex;
        }

        return result;

    }

    public void substituteWithRightSide(SimpleGraph<Vertex, DefaultEdge> graph, Vertex leftSide, int maxID) {

        // missing id after deletion is going to be signed to first vertex from rightSideGraph

        int missingID = leftSide.getId();
        Vertex likeLeft = null;
        for(Vertex vertex: graph.vertexSet()) {
            if((vertex.getId() == leftSide.getId())&&(vertex.getLabel().equals(leftSide.getLabel()))) likeLeft = vertex;
        }
        List<Vertex> neighbours = neighborListOf(graph, likeLeft);
        graph.removeVertex(likeLeft);

        List<Vertex> vertices = new ArrayList<>(rightSideProductionGraph.vertexSet());

        vertices.get(0).setId(missingID);
        graph.addVertex(vertices.get(0));
        Vertex forSubs = vertices.get(0);

        for(int i = 1; i < vertices.size(); i++) {
            vertices.get(i).setId(maxID+i);
            graph.addVertex(vertices.get(i));
        }


        for(DefaultEdge edge : rightSideProductionGraph.edgeSet()) {
            graph.addEdge(rightSideProductionGraph.getEdgeSource(edge),rightSideProductionGraph.getEdgeTarget(edge));
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
    }

    private void parseEmbeddingTransformations(SimpleGraph<Vertex, DefaultEdge> graph) {
        Set<DefaultEdge> edgeSet = graph.edgeSet();
        for(DefaultEdge edge: edgeSet) {
            transformations.add(new EmbeddingTransformation(graph.getEdgeSource(edge).getLabel(),graph.getEdgeTarget(edge).getLabel()));
        }
    }

    @Override
    public String toString() {
        String s1;
        if(leftSideProductionGraph == null) s1 = "|L:Null|";
        else s1 ="|L:" + leftSideProductionGraph.toString() + "|";
        String s2;
        if(rightSideProductionGraph == null) s2 = "|R:Null|";
        else s2 ="|R:" + rightSideProductionGraph.toString() + "|";

        String embS ="EmbS: ";

        for(EmbeddingTransformation emb : transformations) {
            if(emb.from == null) embS = embS + "Null";
            else embS = embS + emb.from;
            if(emb.to == null) embS = embS + "Null";
            else embS = embS + emb.to;
            embS = embS + "--";
        }
        return s1 + s2 + embS;
    }
}
