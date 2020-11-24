package Launcher;

import GraphElements.Vertex;
import GraphElements.VertexSupplier;
import GraphExporter.graphExporter;
import GraphReader.graphImporter;
import Productions.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;

import java.io.File;

public class Launcher {





    public static void main (String[] args) {


        File file = new File("test.gv");
        SimpleGraph<Vertex, DefaultEdge> graph1 = new SimpleGraph<>(new VertexSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);

        graphImporter importer = new graphImporter();
        importer.importIt(graph1, file);
        System.out.println(graph1.toString());

        LauncherAssistant assistant = new LauncherAssistant();

        Production p2 = new P2(graph1, assistant.getFromSet(graph1.vertexSet(), 0));
        p2.apply();

        Production p1 = new P1(graph1, assistant.getFromSet(graph1.vertexSet(), 0));
        p1.apply();

        Production p3 = new P3(graph1, assistant.getFromSet(graph1.vertexSet(), 0));
        p3.apply();

        Production p4 = new P4(graph1, assistant.getFromSet(graph1.vertexSet(), 0));
        p4.apply();

        graphExporter exporter = new graphExporter();
        exporter.exportIt(graph1, "testOutputFinale.gv");
    }

}
