package kmeans;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        File file = new File("C:\\Users\\s25586\\projektNAI\\ProjektNaiKmeans\\src\\kmeans\\train.txt");




        int length = length(file);
        int count = (int) count(file);

        System.out.println(length+"   "+count);

        Data[] trainData = createTrainArray(file,count,length);

        System.out.println(trainData.length);
        System.out.println("Enter k");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();

        int  k = i;
        Means means = new Means(k);
        means.setCluster(trainData,length,count);






    }
    static int length(File file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException var5) {
            throw new RuntimeException(var5);
        }

        int length = 0;
        String line = scanner.nextLine();
        String[] arr = line.split("\\,");
        length = arr.length;
        scanner.close();
        return length;
    }

    static double count(File file) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException var5) {
            throw new RuntimeException(var5);
        }

        double count;
        for (count = 0.0; scanner.hasNextLine(); ++count) {
            String line = scanner.nextLine();
        }
        scanner.close();

        return count;
    }

    static Data[] createTrainArray(File file, double count, int lenght) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException var9) {
            throw new RuntimeException(var9);
        }

        Data[] arr = new Data[(int)count];


        for (int index = 0; index < count; ++index) {
            String line = scanner.nextLine();
            String[] arr2 = line.split("\\,");
            double doubleArr[] = new double[arr2.length-1];
            for(int i = 0; i<doubleArr.length;i++){
                doubleArr[i] = Double.valueOf(arr2[i]);
            }
            Data data = new Data(doubleArr, arr2[arr2.length-1]);
            arr[index] = data;
        }



        return arr;
    }
}