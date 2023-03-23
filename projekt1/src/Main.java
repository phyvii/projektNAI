import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\train.txt");
        List<Example> trainList = createList(file);

        File testFile = new File("src\\test.txt");
        List<Example> testList = createList(testFile);

        Scanner userInput = new Scanner(System.in);
        String user = "";
        int k =4;
        while (!user.equals("0")) {
            try {
                System.out.println("Enter K (type k): ");
                System.out.println("Enter your own observation (type o): ");
                System.out.println("Start program (type s): ");
                user = userInput.nextLine().toLowerCase();
                switch (user) {
                    case "k":
                        System.out.println("enter the number");
                        user = userInput.nextLine();
                        k = Integer.parseInt(String.valueOf(user));
                        break;
                    case "o":
                        System.out.println("enter values separated by ','");
                        String line = userInput.nextLine();
                        String[] arr = line.split("\\,");
                        testList.clear();
                        List<Double> lista = new ArrayList<>();

                        for (int i = 0; i < arr.length - 1; i++) {
                            lista.add(Double.valueOf(String.valueOf(arr[i])));
                        }
                        Example example = new Example(arr[arr.length - 1], lista);
                        testList.add(example);
                        break;



                }
                if (!user.equals("o")) {
                    testList = createList(testFile);
                }


                int lenght = length(file);


                List<Example> output = compare(trainList, testList, k, lenght);
//        for(Example e : output){
//            System.out.println(e.toString());
//        }
                double correct = 0;


                for (int i = 0; i < output.size(); i++) {
                    if (output.get(i).getName().equals(testList.get(i).getName())) {
                        correct++;
                    }
                }

                System.out.println((correct / (double) testList.size()) * 100 + "%");

        } catch (Exception e){
                e.printStackTrace();
                System.out.println("Something went wrong, try again");
            }
            }



    }

    static List<Example> compare(List<Example> trainList, List<Example> testList, int k,int length) {

        List<Example> outputList = new LinkedList<>();
        new ArrayList();
        new LinkedList();


        for(int i = 0; i < testList.size(); ++i) {
            double value = 0.0;
            List<Knn> list = new ArrayList();
            Map<Double,Integer> mapa = new HashMap<>();
            int id = 0;

            for(int j = 0; j < trainList.size(); ++j) {
                value = 0.0;

                for(int z = 0; z < length-1; ++z) {
                    value += Math.pow((testList.get(i)).getValueList().get(z) -trainList.get(j).getValueList().get(z), 2.0);
                }
//                System.out.println(trainList.get(id).getName());
                list.add(new Knn(trainList.get(id).getName(), value, id));
//                System.out.println(list.get(id).getName()+" "+list.get(id).getKnn());
                ++id;
            }

            for(Knn knn : list){
                mapa.put(knn.getKnn(), knn.getId());
            }

            Map<Double, Integer> sortedMap = new TreeMap<Double, Integer>(mapa);
            int count = 0;
            String stringArr[] = new String[k];

            int[] indexArr = new int[k];
             for(Map.Entry entry :sortedMap.entrySet()){
                if (count==k)
                    break;
//                System.out.println(entry.getKey()+"    "+entry.getValue());
                indexArr[count] = (int) entry.getValue();
                stringArr[count] = list.get((int) entry.getValue()).getName();
//                 System.out.println(stringArr[count]);
                count++;
            }
            HashMap<String, Integer> map = new HashMap<>();
            for (String s : stringArr) {
                if (map.containsKey(s)) {
                    map.put(s, map.get(s) + 1);
                } else {
                    map.put(s, 1);
                }
            }


            String mostCommonString = null;
            int highestCount = 0;
            for (String s : map.keySet()) {
                int counting = map.get(s);
                if (counting > highestCount) {
                    mostCommonString = s;
                    highestCount = counting;
                }
            }

//            System.out.println("The most common string is: " + mostCommonString);

//            Collection<Integer> smallest= sortedMap.values();
//            Integer[] smallestArr = smallest.toArray(new Integer[0]);
//            Arrays.sort(smallestArr);
//
//            for(Integer entry : smallestArr)
//                System.out.println(entry);





//            for(int p =0; p < k;p++){
////                System.out.println(list.get(indexArr[p]).toString());
//            }
            System.out.println(testList.get(i).toString()+":  Predicted:   "+mostCommonString);
//            testList.get(i).setName(mostCommonString);
            Example temporary = new Example(testList.get(i).getName(), testList.get(i).getValueList());
            temporary.setName(mostCommonString);
            outputList.add(temporary);           ;
//            System.out.println(list.get(24).toString());

//            System.out.println(list.size());
//            System.out.println("==================================");
        }

        return outputList;
    }





    static List<Example> createList(double count, int length, String[][] arr) {
        List<Example> list = new ArrayList();

        for(int i = 0; (double)i < count; ++i) {
            list.add(new Example(arr[i][length - 1], createList(arr, i, length)));
        }

        return list;
    }

    static List<Example> createList(File file) {
        double count = count(file);
        int length = length(file);

        try {
            new Scanner(file);
        } catch (FileNotFoundException var6) {
            throw new RuntimeException(var6);
        }

        String[][] arr = createArray(file, count, length);
        List<Example> trainList = createList(count, length, arr);
        return trainList;
    }

    static List<Double> createList(String[][] arr, int index, int length) {
        List<Double> temp = new ArrayList();

        for(int i = 0; i < length - 1; ++i) {
            temp.add(Double.valueOf(arr[index][i]));
        }

        return temp;
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
        for(count = 0.0; scanner.hasNextLine(); ++count) {
            String line = scanner.nextLine();
        }

        return count;
    }

    static String[][] createArray(File file, double count, int lenght) {
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException var9) {
            throw new RuntimeException(var9);
        }

        String[][] arr = new String[(int)count][lenght];

        for(int index = 0; scanner.hasNextLine(); ++index) {
            String line = scanner.nextLine();
            String[] arr2 = line.split("\\,");
            arr[index] = arr2;
        }

        return arr;
    }
}
