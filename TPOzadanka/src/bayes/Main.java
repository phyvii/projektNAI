package bayes;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        BayesClassifier classifier = new BayesClassifier();
        classifier.train("C:\\Users\\glusz\\OneDrive\\Pulpit\\TPOzadanka\\src\\bayes\\train");
        String[] predictedLabel = classifier.classify("C:\\Users\\glusz\\OneDrive\\Pulpit\\TPOzadanka\\src\\bayes\\test");
        for (String label : predictedLabel) {
            System.out.println(label);
        }
    }
}