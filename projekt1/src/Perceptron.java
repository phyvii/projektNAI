import java.util.Random;


public class Perceptron {
    private double[] weights;
    private double learningRate;

    public Perceptron(int numInputs, double learningRate) {
        weights = new double[numInputs];
        this.learningRate = learningRate;
        // initialize weights randomly
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 2 - 1; // range -1 to 1
        }
    }

    public int predict(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return (sum >= 0) ? 1 : -1;
    }

    public void train(Data[] inputs, int maxEpochs) {
        int epoch = 0;
        while (epoch < maxEpochs) {
            int numCorrect = 0;
            for (int i = 0; i < inputs.length; i++) {
                double[] input = inputs[i].getArr();
                int label = inputs[i].getLabel();
                int guess = predict(input);
                if (guess == label) {
                    numCorrect++;
                }
            }
            double accuracy = (double)(numCorrect)/inputs.length;
            System.out.println(accuracy);
            boolean hasErrors = false;
            for (int i = 0; i < inputs.length; i++) {
                int predicted = predict(inputs[i].getArr());
                if (predicted != inputs[i].getLabel()) {
                    updateWeights(inputs[i].getArr(), predicted, inputs[i].getLabel());
                    hasErrors = true;
                }
            }
            epoch++;
            if (!hasErrors) {
                break;
            }
        }
    }

    private void updateWeights(double[] inputs, int predicted, int label) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * (label - predicted) * inputs[i];
        }
    }
}