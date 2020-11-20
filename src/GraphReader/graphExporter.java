package GraphReader;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class graphExporter {

    private final DOTExporter<Vertex, DefaultEdge> exporter;

    public graphExporter() {
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

    public void exportIt(Graph<Vertex,DefaultEdge> graph, String fileName) {
        exporter.exportGraph(graph, new File(fileName));
    }
}