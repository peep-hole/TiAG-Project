package GraphElements;

public class Vertex {

    private Integer id;
    private String label;

    public Vertex(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    Vertex(Integer id) {
        this(id, null);
    }
    Vertex() {this(null,null);}

    @Override
    public int hashCode()
    {
        return id*7 ;
    }

    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        if(this == other)
            return true;
        if (!(other instanceof Vertex))
            return false;
        Vertex that = (Vertex) other;
        return (this.id.equals(that.id));
    }

    public String getLabel() {
        return this.label;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("(id: ").append(id);
        if (label != null) {
            sb.append(", label: \"").append(label).append("\"");
        }
        sb.append(")");
        return sb.toString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLabel(String label) {this.label = label; }


}
