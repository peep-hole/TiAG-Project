package Launcher;

import GraphElements.Vertex;
import GraphTransformationIO.TextParser;
import GraphTransformationIO.GraphExporter;
import Productions.Production;
import Productions.ProductionSeriesElement;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LauncherAssistant {

    public LauncherAssistant(){}

    public void read(String pathName, SimpleGraph<Vertex, DefaultEdge> graph, List<Production> productions,
                     List<ProductionSeriesElement> productionSeries) throws IOException {

        File file = new File(pathName);

        (new TextParser(file)).read(graph, productions, productionSeries);

//        SimpleGraph<Vertex,DefaultEdge> graph = new SimpleGraph<>(new VertexSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);

//        graphImporter importer = new graphImporter();
//        importer.importIt(graph, file);

        //... Do skonczenia

    }

    public void saveGraph(SimpleGraph<Vertex, DefaultEdge> graph, String pathName) {
        GraphExporter exporter = new GraphExporter();
        exporter.exportIt(graph, "testOutput.gv");
    }


    public void applyProductions(SimpleGraph<Vertex, DefaultEdge> graph, ArrayList<Production> productions) {
        //... do implementacji
    }

    public Vertex getFromSet (Set<Vertex> set, int idOfWanted) {
        for (Vertex vertex : set) {
            if (vertex.getId() == idOfWanted) {
                return vertex;
            }
        }
        throw new IllegalArgumentException("There is no vertex of id: " + idOfWanted + " in this graph!");
    }
}
