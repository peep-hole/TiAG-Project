package GraphElements;

import java.util.function.Supplier;

public class VertexSupplier implements Supplier<Vertex> {

    private int id;

    public VertexSupplier() {
        id = 0;
    }

    @Override
    public Vertex get() {
        return new Vertex(id++);
    }
}
