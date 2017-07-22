package ann;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Neuron {

	double input;
	List<Double> weights;
	double bias;

	public Neuron(List<Double> weights, double bias) {
		this.weights = weights;
		this.bias = bias;
	}

	public Neuron(double input) {
		this.input = input;
	}

	public double getInput() {
		return input;
	}

	public void setInput(double input) {
		this.input = input;
	}

	public List<Double> getWeights() {
		return weights;
	}

	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public static double[] netoutput(Neuron[][] neurons) {
		int o_layer = neurons.length-1;
		double[] outputs = new double[neurons[o_layer].length];
    for (int i = 0; i < outputs.length; i++) {
			outputs[i] = (o_layer == 0) ? neurons[o_layer][i].getInput() : neurons[o_layer][i].output(neurons, o_layer);
		}
		return outputs;
	}

	public double output(Neuron[][] neurons, int current_layer) {
		double output = 0;
		if (current_layer == 1) {
			for (int i = 0; i < neurons[0].length; i++) {
				output += neurons[0][i].getInput()*this.getWeights().get(i);
			}
		} else {
			for (int i = 0; i < neurons[current_layer-1].length; i++) {
				output += neurons[current_layer-1][i].output(neurons, current_layer-1)*this.getWeights().get(i);
			}
		}
		return 1/(1+(Math.exp(-(output-this.getBias()))));
	}

  public static double calculate_error(Neuron[][] neurons, double target) {
    List<Double> errors = null;
    List<Double> errors_temp;
    List<Double> updated_weights;
    for (int current_layer = neurons.length-1; current_layer > 0; current_layer--) {
      errors_temp = new ArrayList<Double>();
  		double[] outputs = netoutput(Arrays.copyOfRange(neurons, 0, current_layer+1));
      for (int j = 0; j < neurons[current_layer].length; j++) {
        double output = outputs[j];
        double[] inputs = netoutput(Arrays.copyOfRange(neurons, 0, current_layer));
        if (current_layer == neurons.length-1) {
          errors_temp.add(3*output*(1-output)*(target-output));
        } else {
          double e = 0;
          for (int i = 0; i < errors.size(); i++) {
            e += Math.pow(errors.get(i)*neurons[current_layer+1][i].getWeights().get(j), 2);
          }
          errors_temp.add(3*output*(1-output)*0.5*e);
        }
        neurons[current_layer][j].setBias(neurons[current_layer][j].getBias()+errors_temp.get(errors_temp.size()-1));
        updated_weights = new ArrayList<Double>();
        for (int i = 0; i < neurons[current_layer][j].getWeights().size(); i++) {
          updated_weights.add(neurons[current_layer][j].getWeights().get(i)+(errors_temp.get(errors_temp.size()-1)*inputs[i]));
        }
        neurons[current_layer][j].setWeights(updated_weights);
      }
      errors = errors_temp;
    }
    return 0;
  }

	public static double error(Neuron[][] neurons, int cl, int pos, double target) {
	calculate_error(neurons, target);
  return 0;
	}
	public static void print(Object x) {
	  System.out.println(x);
	}
}