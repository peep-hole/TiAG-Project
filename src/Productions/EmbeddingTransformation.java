package Productions;

public class EmbeddingTransformation {
    /* We assume that graph of left side of the production contains
      only one element */
    public final String from;
    public final String to;

    public EmbeddingTransformation(String from, String to) {
        this.from = from;
        this.to = to;
    }

}
