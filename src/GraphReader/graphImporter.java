package GraphReader;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.nio.dot.DOTImporter;

import java.io.File;
import java.io.Reader;

public class graphImporter {

    private final DOTImporter<Vertex, DefaultEdge> importer;

    graphImporter() {

        importer = new DOTImporter<>();
        importer.addVertexAttributeConsumer((p, attrValue) -> {
            Vertex v = p.getFirst();
            String attrName = p.getSecond();
            if (attrName.equals("label")) {
                String label = attrValue.getValue();
                v.setLabel(label);
                }
    });
    }



    public void importIt (Graph<Vertex, DefaultEdge> graph, File file) {
        importer.importGraph(graph, file);
    }
}
