package ann.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class DataInput extends JFrame {

	private static final long serialVersionUID = 1L;

  static boolean pressed = false;

	public static void main(String[] args) {
		displayData(new int[]{4, 2, 1});
	}

	public static double[][][] displayData(int[] format) {
    List<ArrayList<ArrayList<Double>>> data = new ArrayList<ArrayList<ArrayList<Double>>>();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JLabel inputs = new JLabel("Inputs");
		inputs.setBounds(100, 50, 50, 50);
		panel.add(inputs);
		JLabel outputs = new JLabel("Outputs");
		outputs.setBounds(120 + format[1]*55, 50, 50, 50);
		panel.add(outputs);
		int xOffset = 100;
		for (int i = 0; i < format[0]; i++) {
			for (int j = 0; j < format[1] + format[2]; j++) {
				if (j >= format[1])
					xOffset = 120;
				JTextField cell = new JTextField();
				cell.setBounds(xOffset + j*55, 100 + i*55, 50, 50);
				panel.add(cell);
			}
			xOffset = 100;
		}
		JButton saveData = new JButton("Save as data file");
		saveData.setBounds(100, 120 + format[0]*55, 130, 50);
		saveData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(panel);
				System.out.println(returnVal);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
          File file = new File(fc.getSelectedFile().getPath().split("\\.")[0] + ".ydf");
          try {
						file.createNewFile();
				    PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
						for (int i = 0; i < format[0]; i++) {
							for (int j = 0; j < format[1] + format[2]; j++) {
								if (j >= format[1])
									writer.print("|");
								writer.print(((JTextField)panel.getComponent(i*(format[1]+format[2])+j+2)).getText());
							}
							writer.println();
						}
				    writer.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				} else {
					System.out.println("Action not permitted.");
				}
			}
		});
		panel.add(saveData);
		JButton confirm = new JButton("Confirm data inputs");
		confirm.setBounds(235, 120 + format[0]*55, 150, 50);
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				data.removeAll(data);
        ArrayList<ArrayList<Double>> set;
        ArrayList<Double> dataColumn;
				for (int i = 0; i < format[0]; i++) {
					set = new ArrayList<ArrayList<Double>>();
					dataColumn = new ArrayList<Double>();
					for (int j = 0; j < format[1] + format[2]; j++) {
						if (j == format[1]) {
							set.add(dataColumn);
							dataColumn = new ArrayList<Double>();
						}
						dataColumn.add(Double.parseDouble(((JTextField)panel.getComponent(i*(format[1]+format[2])+j+2)).getText()));
					}
					set.add(dataColumn);
					data.add(set);
				}
				pressed = true;
			}
		});
		panel.add(confirm);
		panel.setLayout(new LayoutManager(){
			@Override
			public void addLayoutComponent(String arg0, Component arg1) {
			}

			@Override
			public void layoutContainer(Container arg0) {
			}

			@Override
			public Dimension minimumLayoutSize(Container arg0) {
				return new Dimension(0, 0);
			}

			@Override
			public Dimension preferredLayoutSize(Container parent) {
				return new Dimension(120 + (format[1]+format[2])*55, 180 + format[0]*55);
			}

			@Override
			public void removeLayoutComponent(Component comp) {
			}
		});
		JScrollPane pane = new JScrollPane(panel);
		pane.setPreferredSize(new Dimension(500, 500));
		pane.setBounds(0, 0, 500, 500);
		frame.add(pane, BorderLayout.CENTER);
		frame.setVisible(true);
  	while (!pressed){
  		try {Thread.sleep(0);} catch (InterruptedException e1) {e1.printStackTrace();}
  	}
   	double[][][] array = new double[data.size()][][];
   	for (int i = 0; i < data.size(); i++) {
   		double[][] blankArray = new double[data.get(i).size()][];
   		for(int j=0; j < data.get(i).size(); j++) {
   			blankArray[j] = data.get(i).get(j).stream().mapToDouble(d -> d).toArray();
   		}
   		array[i] = blankArray;
   	}
   	frame.setVisible(false);
   	return array;
	}
}
