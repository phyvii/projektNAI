import java.util.Iterator;
import java.util.List;

public class Example {
    private String name;
    private double knn;
    private List<Double> valueList;

    public Example(String name, List<Double> valueList) {
        this.name = name;
        this.valueList = valueList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public List<Double> getValueList() {
        return this.valueList;
    }

    public String iterateList() {
        String text = "";

        Double element;
        for(Iterator var2 = this.valueList.iterator(); var2.hasNext(); text = text + element) {
            element = (Double)var2.next();
        }

        return text;
    }

    public String toString() {
        String var10000 = this.name;
        return "name='" + var10000 + ", valueList=" + this.iterateList();
    }
}
