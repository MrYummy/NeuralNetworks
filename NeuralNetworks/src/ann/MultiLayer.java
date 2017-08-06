package ann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MultiLayer {

  public MultiLayer(ArrayList<Integer> design) {
    Random rand = new Random();
    
    List<ArrayList<Neuron>> neurons = new ArrayList<ArrayList<Neuron>>();
    
    for (int i = 0; i < design.size(); i++) {
    	ArrayList<Neuron> layer = new ArrayList<Neuron>();
    	if (i == 0) {
    		for (int j = 0; j < design.get(0); j++) {
    		  layer.add(new Neuron(0));
    		}
    	} else {
    		List<Double> inputs = new ArrayList<Double>();
    		for (int j = 0; j < design.get(i-1); j++) {
    			inputs.add(random());
    		}
    		for (int j = 0; j < design.get(i); j++) {
    			layer.add(new Neuron(inputs, random()));
    		}
    	}
    	neurons.add(layer);
    }
    for (int i = 0; i < neurons.size(); i++) {
    	System.out.println(i+": "+neurons.get(i).size());
    }
    //TODO: set all test data info (# of outputs/inputs, # of training sets) automatically with a GUI
		double[] tests = {
      0, 0, 0,
      1, 0, 1,
      0, 1, 1,
      1, 1, 0
		};
		int test = 0;
		for (int c = 0; c < 100000; c++) {
		  neurons.get(0).get(0).setInput(tests[test*3]);
		  neurons.get(0).get(1).setInput(tests[test*3+1]);
      for (int i = 0; i < neurons.get(1).size(); i++) {
      	Neuron.train(neurons, tests[test*3+2]);
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
