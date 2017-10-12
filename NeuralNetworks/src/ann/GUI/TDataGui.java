package ann.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TDataGui extends JFrame {

	private static final long serialVersionUID = 1L;

	static double[][][] data;
	static double[][][] temp = null;

	public static double[][][] setupTableData() {
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setTitle("Frame");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JLabel setsLabel = new JLabel("# of sets");
		setsLabel.setBounds(320, 50, 110, 40);
		frame.add(setsLabel);
		JLabel inputsLabel = new JLabel("# of inputs");
		inputsLabel.setBounds(312, 100, 110, 40);
		frame.add(inputsLabel);
		JLabel outputsLabel = new JLabel("# of outputs");
		outputsLabel.setBounds(304, 150, 110, 40);
		frame.add(outputsLabel);

		JTextField sets = new JTextField();
		sets.setBounds(380, 50, 60, 40);
		frame.add(sets);
		JTextField inputs = new JTextField();
		inputs.setBounds(380, 100, 60, 40);
		frame.add(inputs);
		JTextField outputs = new JTextField();
		outputs.setBounds(380, 150, 60, 40);
		frame.add(outputs);
		
		JButton generateChart = new JButton("Generate Chart");
		generateChart.setBounds(300, 200, 140, 60);
		generateChart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		    new Thread(new Runnable() {
		      public void run() {
		      	data = DataInput.displayData(new int[]{Integer.parseInt(sets.getText()), Integer.parseInt(inputs.getText()), Integer.parseInt(outputs.getText())});
		      }
		    }).start();
			}
		});
		frame.add(generateChart);
		frame.setVisible(true);
  	while (data == null){
  		try {Thread.sleep(0);} catch (InterruptedException e1) {e1.printStackTrace();}
  	}
  	temp = data;
  	data = null;
  	frame.setVisible(false);
		return temp;
	}

}
