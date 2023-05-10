package bayes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BayesClassifier {

    private Map<String, Integer> labelCounts;
    private Map<String, Map<String, Integer>> featureCounts;
    private int numExamples;

    public BayesClassifier() {
        labelCounts = new HashMap<>();
        featureCounts = new HashMap<>();
        numExamples = 0;
    }

    public void train(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] tokens = line.split(",");
            String label = tokens[tokens.length - 1];
            if (!labelCounts.containsKey(label)) {
                labelCounts.put(label, 0);
                featureCounts.put(label, new HashMap<>());
            }
            labelCounts.put(label, labelCounts.get(label) + 1);
            Map<String, Integer> counts = featureCounts.get(label);
            for (int i = 0; i < tokens.length - 1; i++) {
                String feature = i + ":" + tokens[i];
                if (!counts.containsKey(feature)) {
                    counts.put(feature, 0);
                }
                counts.put(feature, counts.get(feature) + 1);
            }
            numExamples++;
        }
        reader.close();
    }

    public String[] classify(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        String[] predictedLabels = new String[0];
        int numInstances = 0;
        while ((line = reader.readLine()) != null) {
            numInstances++;
            String[] tokens = line.split(",");
            Map<String, Double> labelProbs = new HashMap<>();
            for (String label : labelCounts.keySet()) {
                double labelProb = (double) labelCounts.get(label) / numExamples;
                Map<String, Integer> featureCountsForLabel = featureCounts.get(label);
                double logProb = 0.0;
                for (int i = 0; i < tokens.length - 1; i++) {
                    String feature = i + ":" + tokens[i];
                    int count = featureCountsForLabel.containsKey(feature) ? featureCountsForLabel.get(feature) : 0;
                    if(count==0)
                        logProb += Math.log((double) (count + 1) / (labelCounts.get(label)));
                    logProb += Math.log((double) (count) / (labelCounts.get(label)));
                }
                labelProbs.put(label, Math.log(labelProb) + logProb);
            }
            double maxProb = Double.NEGATIVE_INFINITY;
            String maxLabel = null;
            for (String label : labelProbs.keySet()) {
                double prob = Math.exp(labelProbs.get(label));
                if (prob > maxProb) {
                    maxProb = prob;
                    maxLabel = label;
                }
            }
            predictedLabels = expandArray(predictedLabels, numInstances);
            predictedLabels[numInstances - 1] = maxLabel;
        }
        reader.close();
        return predictedLabels;
    }

    private String[] expandArray(String[] array, int length) {
        String[] newArray = new String[length];
        System.arraycopy(array, 0, newArray, 0, Math.min(array.length, length));
        return newArray;
    }
}
