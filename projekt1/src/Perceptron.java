import java.util.Random;


public class Perceptron {
    private double[] weights;
    private double learningRate;

    public Perceptron(int count, double learningRate) {
        weights = new double[count];
        this.learningRate = learningRate;
        // initialize weights randomly
        for (int i = 0; i < count; i++) {
            weights[i] = Math.random() * 1 - 0;
        }
    }

    public int predict(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < inputs.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return (sum >= 0) ? 1 : 0;
    }

    public void train(Data[] inputs, int epochs) {
        int epoch = 0;
        while (epoch < epochs) {
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

            for (int i = 0; i < inputs.length; i++) {
                int guess = predict(inputs[i].getArr());
                if (guess != inputs[i].getLabel()) {
                    updateWeights(inputs[i].getArr(), guess, inputs[i].getLabel());
                }
            }
            epoch++;

        }
    }

    private void updateWeights(double[] arr, int guess, int label) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * (label - guess) * arr[i];
        }
    }
}