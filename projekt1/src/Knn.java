public class Knn {
    private String name;
    private double knn;
    private int id;

    public Knn(String name, double knn, int id) {
        this.name = name;
        this.knn = knn;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getKnn() {
        return this.knn;
    }

    public void setKnn(double knn) {
        this.knn = knn;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Knn{name='" + this.name + "', knn=" + this.knn + ", id=" + this.id + "}";
    }
}
