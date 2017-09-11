package ann;

import java.util.ArrayList;
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

	public static double[] netoutput(List<ArrayList<Neuron>> neurons) {
		int o_layer = neurons.size()-1;
		double[] outputs = new double[neurons.get(o_layer).size()];
    for (int i = 0; i < outputs.length; i++) {
			outputs[i] = (o_layer == 0) ? neurons.get(o_layer).get(i).getInput() : neurons.get(o_layer).get(i).output(neurons, o_layer);
		}
		return outputs;
	}

	public double output(List<ArrayList<Neuron>> neurons, int current_layer) {
		double output = 0;
		if (current_layer == 1) {
			for (int i = 0; i < neurons.get(0).size(); i++) {
				output += neurons.get(0).get(i).getInput()*this.getWeights().get(i);
			}
		} else {
			for (int i = 0; i < neurons.get(current_layer-1).size(); i++) {
				output += neurons.get(current_layer-1).get(i).output(neurons, current_layer-1)*this.getWeights().get(i);
			}
		}
		return 1/(1+(Math.exp(this.getBias()-output)));
	}

  public static void train(List<ArrayList<Neuron>> neurons, double[] targets) {
    List<Double> errors = null;
    List<Double> errors_temp;
    List<Double> updated_weights;
    for (int current_layer = neurons.size()-1; current_layer > 0; current_layer--) {
      errors_temp = new ArrayList<Double>();
  		double[] outputs = netoutput(neurons.subList(0, current_layer+1));
      for (int j = 0; j < neurons.get(current_layer).size(); j++) {
        double output = outputs[j];
        double[] inputs = netoutput(neurons.subList(0, current_layer));
        if (current_layer == neurons.size()-1) {
          errors_temp.add(3*output*(1-output)*(targets[j]-output));
        } else {
          double e = 0;
          for (int i = 0; i < errors.size(); i++) {
            e += Math.pow(errors.get(i)*neurons.get(current_layer+1).get(i).getWeights().get(j), 2);
          }
          errors_temp.add(1.5*output*(1-output)*e);
        }
        neurons.get(current_layer).get(j).setBias(neurons.get(current_layer).get(j).getBias()-errors_temp.get(errors_temp.size()-1));
        updated_weights = new ArrayList<Double>();
        for (int i = 0; i < neurons.get(current_layer).get(j).getWeights().size(); i++) {
          updated_weights.add(neurons.get(current_layer).get(j).getWeights().get(i)+(errors_temp.get(errors_temp.size()-1)*inputs[i]));
        }
        neurons.get(current_layer).get(j).setWeights(updated_weights);
      }
      errors = errors_temp;
    }
  }
}