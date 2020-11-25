package GraphTransformationIO;

import GraphElements.Vertex;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GraphExporter {

    private final DOTExporter<Vertex, DefaultEdge> exporter;

    public GraphExporter() {
        exporter = new DOTExporter<>();
        exporter.setVertexIdProvider(v -> String.valueOf(v.getId()));
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> m = new HashMap<>();
            if (v.getLabel() != null) {
                m.put("label", DefaultAttribute.createAttribute(v.getLabel()));
            }
            return m;
        });
    }

    public void exportIt(SimpleGraph<Vertex,DefaultEdge> graph, String fileName) {
        exporter.exportGraph(graph, new File(fileName));
    }
}
