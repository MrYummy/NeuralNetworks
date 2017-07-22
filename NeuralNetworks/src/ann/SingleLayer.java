package ann;

import java.util.Arrays;
import java.util.Random;

public class SingleLayer {

  public static void main(String[] args) {
  	Random rand = new Random();
    Neuron n1 = new Neuron(Arrays.asList(0, 0), Arrays.asList((rand.nextDouble()*4)-2, (rand.nextDouble()*4)-2), (rand.nextDouble()*4)-2);
    train(n1, 3);
  }

  public static void train(Neuron neuron, int operation) { //Operation: 1 = AND, 2 = OR, 3 = NAND, 4 = XOR
    int[] tests = null;
    if (operation == 3) {
      tests = new int[]{
        0, 0, 0,
        1, 0, 0,
        0, 1, 0,
        1, 1, 1
      };
    }
    int test_set = 0;
    for (int x = 0; x < 1000; x++) {
      double output = neuron.output();
      System.out.print(tests[test_set*3]+" "+tests[test_set*3+1]+" "+output);
      if (output > 0.5)
        System.out.println(" YES");
      else
      	System.out.println(" NO");
      //(delta)w_ji = (alpha)(t_j - y_j)g'(h_j)x_i
      double change_factor = (tests[test_set*3+2]-output)*(output*(1-output));
      neuron.setWeights(Arrays.asList(neuron.getWeights().get(0)+change_factor*neuron.getInputs().get(0), neuron.getWeights().get(1)+change_factor*neuron.getInputs().get(1)));
      neuron.setBias(neuron.getBias()+change_factor);
      test_set++;
      if (test_set == 4) {
        test_set = 0;
      }
      neuron.setInputs(Arrays.asList(tests[test_set*3], tests[test_set*3+1]));
    }
  }
}