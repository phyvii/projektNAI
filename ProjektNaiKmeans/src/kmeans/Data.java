package kmeans;

public class Data {
    private double[] arr;
    private String label;

    public Data(double[] arr, String label){
        this.arr = arr;
        this.label = label;
    }

    public double[] getArr() {
        return arr;
    }

    public void setArr(double[] arr) {
        this.arr = arr;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String arrString(){
        StringBuilder line = new StringBuilder();
        for(int i =0; i<arr.length;i++){
            line.append(arr[i]+"  ");
        }
        return line.toString();

    }

    @Override
    public String toString() {
        return "Data{" +
                "arr=" + arrString() +
                ", label=" + label +
                '}';
    }
}
