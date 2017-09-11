package ann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MultiLayer {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

  public MultiLayer(ArrayList<Integer> design, double[][][] data) {
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
		int test = 0;
		for (int c = 0; c < 100000; c++) {
			for (int i = 0; i < data[test][0].length; i++)
				neurons.get(0).get(i).setInput(data[test][0][i]);
      for (int j = 0; j < neurons.get(1).size(); j++)
      	Neuron.train(neurons, data[test][1]);
      for (int k = 0; k < data[test][0].length; k++)
      	System.out.print(data[test][0][k] + " ");
      double[] outputs = Neuron.netoutput(neurons);
      for (int l = 0; l < outputs.length; l++)
      	System.out.print(" " + outputs[l]);
      System.out.println();
      if (test == data.length-1) {
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
