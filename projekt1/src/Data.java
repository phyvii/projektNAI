public class Data {
    private double[] arr;
    private int label;

    public Data(double[] arr, int label){
        this.arr = arr;
        this.label = label;
    }

    public double[] getArr() {
        return arr;
    }

    public void setArr(double[] arr) {
        this.arr = arr;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
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
