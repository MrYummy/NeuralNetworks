package ann;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultiLayer extends JFrame {

 private static final long serialVersionUID = 1L;

 int test = 0;
 String lines = "";
 Thread thread = null;

 int pauseLevel = 1; // Level 1: running normally, not paused. Level 2: pause requested. Level 3: Pause completed. Ready for resumption.
 boolean stopped = false;

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
  JFrame frame = new JFrame("Output");
  frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  frame.setSize(500, 500);
  JTextArea textArea = new JTextArea();
  textArea.setEditable(false);
  textArea.setFont(textArea.getFont().deriveFont(18f));
  JScrollPane scrollPane = new JScrollPane(textArea);
  frame.add(scrollPane, BorderLayout.NORTH);
  JPanel panel = new JPanel();
  JButton pause = new JButton("Pause");
  pause.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    if (pauseLevel != 3 && !stopped)
     pauseLevel = 2;
   }
  });
  panel.add(pause);
  JButton resume = new JButton("Resume");
  resume.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    if (pauseLevel == 3 && !stopped) {
     thread.interrupt();
     pauseLevel = 1;
    }
   }
  });
  panel.add(resume);
  JButton stop = new JButton("Stop");
  stop.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent arg0) {
    stopped = true;
   }
  });
  panel.add(stop);
  frame.add(panel, BorderLayout.CENTER);

  thread = new Thread() {
   public void run() {
    int count = 0;
    test = data.length - 1;
    while (true) {
     if (stopped == true) {
      thread.interrupt();
     }
     if (pauseLevel == 2) {
      pauseLevel = 3;
      synchronized (thread) {
       try {
        thread.wait();
       } catch (InterruptedException e) {}
      }
     }
     for (int i = 0; i < data[test][0].length; i++) {
      neurons.get(0).get(i).setInput(data[test][0][i]);
      lines += data[test][0][i] + " ";
     }
     for (int j = 0; j < neurons.get(1).size(); j++) {
      Neuron.train(neurons, data[test][1]);
     }
     double[] outputs = Neuron.netoutput(neurons);
     for (int k = 0; k < outputs.length; k++) {
      lines += " " + outputs[k];
     }
     lines += "\n";
     if (count%data.length == 0) {

      setText(textArea, lines);
      lines = "";
     }
     if (test == data.length-1) {
      test = 0;
     } else {
      test++;
     }
     count++;
    }
   }
  };
  thread.start();
  frame.setVisible(true);
 }

 public static double random() {
  return (new Random()).nextDouble()*4-2;
 }
 public static void setText(JTextArea textArea, String lines) {
  if (!Thread.currentThread().isInterrupted())
   textArea.setText(lines);
 }
}
