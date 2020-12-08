package GraphTransformationIO;

import GraphElements.Vertex;
import Productions.Production;
import Productions.ProductionSeriesElement;
import Statistics.Stats;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class TextParser {

    public static AtomicInteger numberOfProduction = new AtomicInteger(1);
    private final File file;

    public TextParser(File file) {
        this.file = file;
    }

    public void read(SimpleGraph<Vertex, DefaultEdge> graph, List<Production> productions,
                      List<ProductionSeriesElement> productionSeries) throws IOException {
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            switch(scanner.nextLine()) {
                case "--InitialGraph--" -> {
                    File init = new File("initialGraph.gv");

                    PrintWriter pw = new PrintWriter(init);
                    while(scanner.hasNext() && !scanner.hasNext("--END--")) {
                        pw.println(scanner.nextLine());
                    }
                    pw.close();
                    GraphImporter importer = new GraphImporter();
                    importer.importIt(graph, init);
                    scanner.nextLine();
                }

                case "--NewProduction--" -> {

                    int number = numberOfProduction.getAndIncrement();

                    scanner.nextLine();

                    String leftName = "leftSide" + number + ".gv";
                    File leftSide = new File(leftName);
                    if(!leftSide.createNewFile()) throw new IOException("File: " + leftName + " cannot be created");
                    PrintWriter pw1 = new PrintWriter(leftSide);

                    while(scanner.hasNext() && !scanner.hasNext("--RightSide--")) {
                        pw1.println(scanner.nextLine());
                    }

                    scanner.nextLine();
                    pw1.close();

                    String rightName = "rightSide" + number + ".gv";
                    File rightSide = new File(rightName);
                    if(!rightSide.createNewFile()) throw new IOException("File: " + rightName + " cannot be created");
                    PrintWriter pw2 = new PrintWriter(rightSide);

                    while(scanner.hasNext() && !scanner.hasNext("--Embedding--")) {
                        pw2.println(scanner.nextLine());
                    }

                    scanner.nextLine();
                    pw2.close();

                    String embName = "embedding" + number + ".gv";
                    File embedding = new File(embName);
                    if(!embedding.createNewFile()) throw new IOException("File: " + embName + " cannot be created");
                    PrintWriter pw3 = new PrintWriter(embedding);

                    while(scanner.hasNext() && !scanner.hasNext("--END--")) {
                        pw3.println(scanner.nextLine());
                    }

                    pw3.close();
                    scanner.nextLine();

                    productions.add(new Production());
                    productions.get(productions.size()-1).parseProduction(leftSide, rightSide, embedding);

                    if(!leftSide.delete()) throw new IllegalArgumentException("file: " + leftName + " cannot be deleted");
                    if(!rightSide.delete()) throw new IllegalArgumentException("file: " + rightName + " cannot be deleted");
                    if(!embedding.delete()) throw new IllegalArgumentException("file: " + embName + " cannot be deleted");
                }

                case "--ProductionSeries--" -> {
                    String[] prodSeries;
                    prodSeries = scanner.nextLine().split(";");
                    parseProductionSeries(prodSeries, productionSeries);
                    scanner.nextLine();
                }

                default -> scanner.nextLine();
            }
        }
    }

    private void parseProductionSeries(String[] prodSeries, List<ProductionSeriesElement> resultSeries) {
        for(String prod : prodSeries) {
            int[] split = Arrays.stream(prod.split(",")).mapToInt(Integer::parseInt).toArray();
            resultSeries.add(new ProductionSeriesElement(split[0], split[1]));

        }


    }

}
