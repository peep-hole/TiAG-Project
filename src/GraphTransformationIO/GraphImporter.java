package GraphTransformationIO;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.dot.DOTImporter;
import java.io.File;


public class GraphImporter {

    private final DOTImporter<Vertex, DefaultEdge> importer;

    public GraphImporter() {

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



    public void importIt (SimpleGraph<Vertex, DefaultEdge> graph, File file) {
        importer.importGraph(graph, file);
    }
}
