package kmeans;

import java.util.*;

public class Means {
    private static int k;
    private static List<double[]> klaster;

    public Means(int k){
        this.k = k;
        this.klaster = new LinkedList<>();
    }
    public void setCluster(Data[] arr, int length, double count){
        int[] arrIndeks = new int[k];


        int countCopy = (int) count;
        int sum = 0;
        int tmp = 0;

        for (int i = 0; i < k; i++) {
            tmp = (int) (Math.random()*countCopy)/2;

            countCopy-=tmp;
            sum += tmp;
            System.out.println(tmp);
            arrIndeks[i] = tmp;
        }
        if(countCopy > 0){
            tmp+=countCopy;
        }
        arrIndeks[arrIndeks.length-1] = tmp;
        countCopy = 0;
        System.out.println("=====================");
        for (int i = 0; i < k; i++) {
            countCopy += arrIndeks[i];
            arrIndeks[i] = countCopy;
        }
        for(int a : arrIndeks)
            System.out.println(a);
        double[] arrValues = new double[length-1];
        int indexTrack = 0;
        for (int i = 0; i <arr.length ; i++) {
            for (int j = 0; j < length -1; j++) {
                arrValues[j]+= arr[i].getArr()[j];
            }
            if(i!=0){
                for (int j = 0; j < arrValues.length; j++) {
                    arrValues[j] = arrValues[j]/2;
                }
            }
            if(i == arrIndeks[indexTrack]){
                indexTrack++;
                klaster.add(arrValues);
                arrValues = new double[length-1];
            }
        }
        klaster.add(arrValues);
        System.out.println("========================");
        for(double[] d : klaster){
            for (double dou : d){
                System.out.print(dou+"   ");
            }
            System.out.println();
        }
        System.out.println(klaster.size());


        System.out.println("====================================");

        boolean isItOver = false;
        int iteration = 0;
        while(!isItOver){
            //struktura danych: lista klastrów ma w sobie liste danych które mają arr doubli
            List<List<Data>> klastry = new ArrayList<>();
            //szablon klastrów
            for (int i = 0; i < k; i++) {
                klastry.add(new ArrayList<>());
            }
            //następny proj
            //kategoryczny naiwny bayes

            //przypisywanie do najbliższych klastrów
            for(Data d : arr){
                int closestCluster = getClosestCluster(d.getArr());
                klastry.get(closestCluster).add(d);
            }

            isItOver = true;
            for(int i = 0; i < k;i++){
                double[] nowyKlaster = calculateNewCluster(klastry.get(i),length);
                if(!Arrays.equals(nowyKlaster, klaster.get(i))){
                    klaster.set(i, nowyKlaster);
                    isItOver = false;
                }
            }
            System.out.println("===========================================");
            System.out.println("===================="+iteration+"=====================");
            System.out.println("===========================================");
            iteration+=1;


            for (int i = 0; i < k; i++) {
                System.out.println("Cluster " + i + ": ");
                List<String> list = new ArrayList<>();
                double distance = 0;
                for (int j = 0; j < klastry.get(i).size(); j++) {
                    list.add(klastry.get(i).get(j).getLabel());
                    for(double d: klastry.get(i).get(j).getArr()){
                        distance+=d;
                    }

                }
                String formatted = String.format("%.2f", distance);
                System.out.println(formatted+"    "+"Cluster "+i);
                String mostCommon = "";
                if(list.size()!=0){
                    mostCommon = Collections.max(list, Comparator.comparingInt(s -> Collections.frequency(list, s)));
                }


                int counting = 0;

                for(String s : list){
                    if(s.equals(mostCommon))
                        counting++;
                }
                double accuracy = 0;
                if(list.size()!=0)
                    accuracy = (double)counting/(list.size()*klastry.get(i).size());
                accuracy*=100;
                String formatted = String.format("%.2f", accuracy);
                System.out.println("The most common string is: " + mostCommon+"    "+ formatted);
            }

        }



    }
    public static int getClosestCluster(double[] point) {
        int closest = 0;
        double closestDist = Double.MAX_VALUE;
        for (int i = 0; i < k; i++) {
            double dist = calculateDistance(point, klaster.get(i));
            if (dist < closestDist) {
                closest = i;
                closestDist = dist;
            }
        }
        return closest;
    }

    private double[] calculateNewCluster(List<Data> arr, int length) {
        double[] arrValues = new double[length-1];
        for (int i = 0; i <arr.size() ; i++) {
            for (int j = 0; j < length - 1; j++) {
                arrValues[j] += arr.get(i).getArr()[j];
            }
            if (i != 0) {
                for (int j = 0; j < arrValues.length; j++) {
                    arrValues[j] = arrValues[j] / 2;
                }
            }

        }
        return arrValues;
    }

    private static double calculateDistance(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += Math.pow(a[i] - b[i], 2);
        }
        return Math.sqrt(sum);
    }





}
