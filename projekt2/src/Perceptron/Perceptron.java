package Perceptron;


import java.util.Random;


public class Perceptron {
    private double[] ran;
    private double bias;
    private double learningRate;

    public Perceptron(int inNumber, double learningRate) {
        this.ran = new double[inNumber];
        this.bias = 0;
        this.learningRate = learningRate;
        Random rand = new Random();
        for (int i = 0; i < inNumber; i++) {
            this.ran[i] = rand.nextDouble() * 2 - 1;
        }
    }

    public int predict(double[] inputs) {
        double weight = 0;
        for (int i = 0; i < this.ran.length; i++) {
            weight += ran[i] * inputs[i];
        }
        weight += bias;
        if (weight >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public void train(Data[] train, int numEpochs, Data[] testInputs) {
        for (int epoch = 1; epoch <= numEpochs; epoch++) {

            int numCorrect = 0;
            for (int i = 0; i < testInputs.length; i++) {
                double[] input = testInputs[i].getArr();
                int label = testInputs[i].getLabel();
                int guess = predict(input);
                if (guess == label) {
                    numCorrect++;
                }
            }
            double accuracy = (double)(numCorrect)/testInputs.length;
            System.out.println("Epoch " + epoch + ": Test accuracy = " + accuracy);
            for (int i = 0; i < train.length; i++) {
                double[] input = train[i].getArr();
                int label = train[i].getLabel();
                int guess = predict(input);
                if (guess != label) {
                    update(input, guess, label);
                }
            }
        }
    }

    private void update(double[] input, int guess, int label) {
        double d = label - guess;
        for (int i = 0; i < this.ran.length; i++) {
            this.ran[i] += this.learningRate * d * input[i];
        }
        this.bias += this.learningRate * d;
    }

}