package Perceptron;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\glusz\\projektNAI\\projekt2\\src\\Perceptron\\train.txt");


        File testFile = new File("C:\\Users\\glusz\\projektNAI\\projekt2\\src\\Perceptron\\test.txt");

        int length = length(file);
        int count = (int) count(file);


        int testLength = length(testFile);
        int testCount = (int) count(testFile);
        System.out.println(length+"   "+count);

        Data[] trainData = createTrainArray(file,count,length);

        Data[] testData = createTrainArray(testFile,testCount,testLength);


        Scanner userInput = new Scanner(System.in);
        String user = "";
        double learningRate = 0.1;
        int epoch = 4;
        while (!user.equals("0")) {
            try {
                System.out.println("Enter Learning Rate (type l): ");
                System.out.println("Enter Train Path (type t): ");
                System.out.println("Enter Test Path (type p): ");
                System.out.println("Enter Epoch number (type e): ");
                user = userInput.nextLine().toLowerCase();
                switch (user) {
                    case "l":
                        System.out.println("Enter learning rate: ");
                        user = userInput.nextLine().toLowerCase();
                        learningRate = Double.parseDouble(user);
                        break;
                    case "t":
                        file = new File(user);
                        break;
                    case "p":
                        testFile = new File(user);
                        break;
                    case "e":
                        System.out.println("Enter epoch number: ");
                        user = userInput.nextLine().toLowerCase();
                        epoch = Integer.parseInt(user);
                        break;
                }

                Perceptron perceptron = new Perceptron(2, learningRate);
                perceptron.train(trainData, epoch, testData);


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Something went wrong, try again");
            }
        }


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

        return count-1;
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
            Data data = new Data(doubleArr, Integer.parseInt(arr2[arr2.length-1]));
            arr[index] = data;
        }


        return arr;
    }
}
