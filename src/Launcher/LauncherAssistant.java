package Launcher;

import GraphElements.Vertex;
import GraphElements.VertexSupplier;
import GraphTransformationIO.TextParser;
import GraphTransformationIO.GraphExporter;
import Productions.Production;
import Productions.ProductionSeriesElement;
import Statistics.Stats;
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

    public static ArrayList<String> images;
    public static ArrayList<Stats> stats = new ArrayList<Stats>();


    public static ArrayList<String> getImages() {
        return images;
    }

    public LauncherAssistant(){}

    public void readAndRun(String pathName) throws IOException {

        File file = new File(pathName);

        SimpleGraph<Vertex, DefaultEdge> graph = new SimpleGraph<>(new VertexSupplier(),
                SupplierUtil.createDefaultEdgeSupplier(), false);

        List<Production> productions = new ArrayList<>();

        List<ProductionSeriesElement> productionSeries = new LinkedList<>();

        (new TextParser(file)).read(graph, productions, productionSeries);

        // running productions

        for(ProductionSeriesElement seriesElement : productionSeries) {

            productions.get(seriesElement.getProductionNumber()-1).applyOn(graph,seriesElement.getVertexID());
            saveGraph(graph, "Epoch"+ numberOfEpoch.getAndIncrement()+".gv");
            // brzydko ale nie wiem jak inaczej
            SimpleGraph temp = (SimpleGraph) graph.clone();
            // lista statystyk dla kolejnych produkcji
            stats.add(new Stats(temp));

        }

    }

    public static ArrayList<Stats> getStats() {
        return stats;
    }

    public void saveGraph(SimpleGraph<Vertex, DefaultEdge> graph, String pathName) {
        GraphExporter exporter = new GraphExporter();
        exporter.exportIt(graph, pathName);
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