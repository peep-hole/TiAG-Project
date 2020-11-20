package GraphReader;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.jgrapht.util.SupplierUtil;

import java.io.*;

public class Test {


    public static void main(String[] args) {

        File file = new File("test.gv");
        Graph<Vertex, DefaultEdge> graph1 = GraphTypeBuilder
                .undirected().weighted(false).allowingMultipleEdges(false).allowingSelfLoops(false)
                .vertexSupplier(new VertexSupplier())
                .edgeSupplier(SupplierUtil.createDefaultEdgeSupplier()).buildGraph();

        graphImporter importer = new graphImporter();
        importer.importIt(graph1, file);
        System.out.println(graph1.toString());

        graphExporter exporter = new graphExporter();
        exporter.exportIt(graph1, "testOutput.gv");

    }
}
