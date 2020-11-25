package Productions;

public class ProductionSeriesElement {

    private final int vertexID;
    private final int productionNumber;

    public ProductionSeriesElement(int ID, int prodNo) {
        vertexID = ID;
        productionNumber = prodNo;
    }

    public int getVertexID() {
        return vertexID;
    }

    public int getProductionNumber() {
        return productionNumber;
    }
}
