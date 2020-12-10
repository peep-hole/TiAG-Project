package Launcher;

import GraphElements.Vertex;
import GraphElements.VertexSupplier;
import GraphTransformationIO.TextParser;
import GraphTransformationIO.GraphExporter;
import Productions.Production;
import Productions.ProductionSeriesElement;
import Statistics.Stats;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.util.SupplierUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class LauncherAssistant {

    public static AtomicInteger numberOfEpoch = new AtomicInteger(1);

    public static ArrayList<String> images = new ArrayList<>();
    public static ArrayList<Stats> stats = new ArrayList<Stats>();
    public static ArrayList<Integer> productionsNumbers = new ArrayList<>();

    public static ArrayList<String> getImages() {
        return images;
    }
    public static ArrayList<Stats> getStats() {
        return stats;
    }
    public static ArrayList<Integer> getProductionsNumbers() { return productionsNumbers; }

    public LauncherAssistant(){}

    public void readAndRun(String pathName) throws IOException {

        File file = new File(pathName);

        SimpleGraph<Vertex, DefaultEdge> graph = new SimpleGraph<>(new VertexSupplier(),
                SupplierUtil.createDefaultEdgeSupplier(), false);

        List<Production> productions = new ArrayList<>();

        List<ProductionSeriesElement> productionSeries = new LinkedList<>();

        (new TextParser(file)).read(graph, productions, productionSeries);

        SimpleGraph init = (SimpleGraph) graph.clone();
        // dpdanie poczatkowego grafu do listy
        stats.add(new Stats(init));
        Graphviz.fromFile(new File("initialGraph.gv")).render(Format.PNG).toFile(new File("initialGraph.png"));
        images.add("initialGraph.png");

        // running productions

        for(ProductionSeriesElement seriesElement : productionSeries) {

            productions.get(seriesElement.getProductionNumber()-1).applyOn(graph,seriesElement.getVertexID());
            saveGraph(graph, "Epoch"+ numberOfEpoch.getAndIncrement());

            SimpleGraph temp = (SimpleGraph) graph.clone();
            stats.add(new Stats(temp));
            productionsNumbers.add(seriesElement.getProductionNumber());

        }

    }

    public void saveGraph(SimpleGraph<Vertex, DefaultEdge> graph, String pathName) throws IOException {
        GraphExporter exporter = new GraphExporter();
        exporter.exportIt(graph, pathName + ".gv");

        Graphviz.fromFile(new File(pathName + ".gv")).render(Format.PNG).toFile(new File(pathName + ".png"));
        images.add(pathName + ".png");
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