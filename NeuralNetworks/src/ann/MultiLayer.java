package ann;

import java.util.Arrays;
import java.util.Random;

public class MultiLayer {

  public static void main(String[] args) {
    Random rand = new Random();
    Neuron[][] neurons = {
    		{new Neuron(1), new Neuron(-2)}, //input
    		{new Neuron(Arrays.asList(random(), random()), random()), new Neuron(Arrays.asList(random(), random()), random()), new Neuron(Arrays.asList(random(), random()), random()), new Neuron(Arrays.asList(random(), random()), random()), new Neuron(Arrays.asList(random(), random()), random())}, //hidden
    		{new Neuron(Arrays.asList(random(), random(), random(), random(), random()), random())} //output
    };
    //TODO: set all test data info (# of outputs/inputs, # of training sets) automatically with a GUI
		double[] tests = {
      1, 8, .9,
      5, 7, .12,
      4, 9, 0.13,
      2, 6, 0.8
		};
		int test = 0;
		for (int c = 0; c < 100000; c++) {
		  neurons[0][0].setInput(tests[test*3]);
		  neurons[0][1].setInput(tests[test*3+1]);
      for (int i = 0; i < neurons[1].length; i++) {
      	Neuron.calculate_error(neurons, tests[test*3+2]);
      }
      System.out.println(tests[test*3]+" "+tests[test*3+1]+" "+Neuron.netoutput(neurons)[0]);
      if (test == 3) {
        test = 0;
      } else {
        test++;
      }
		}
  }

  public static double random() {
    return (new Random()).nextDouble()*4-2;
  }
}
