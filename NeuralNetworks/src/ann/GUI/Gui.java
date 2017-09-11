package ann.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import ann.MultiLayer;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;

	static JFrame frame = new JFrame();
  static JTextField layers = new JTextField();
  double[][][] testingData = null;
  double[][][] temp = null;
	public static void main(String[] args) {
		new Gui();
	}
	
	public Gui() {
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setTitle("Neural Network Setup");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    AbstractDocument document = (AbstractDocument) layers.getDocument();
    document.setDocumentFilter(new DocumentFilter() {
        public void replace(FilterBypass fb, int offs, int length,
                String str, AttributeSet a) throws BadLocationException {

            String text = fb.getDocument().getText(0,
                    fb.getDocument().getLength());
            text += str;
            if (text.matches("[0-9]*")) {
                super.replace(fb, offs, length, str, a);
            }
        }

        public void insertString(FilterBypass fb, int offs, String str,
                AttributeSet a) throws BadLocationException {

            String text = fb.getDocument().getText(0,
                    fb.getDocument().getLength());
            text += str;
            if (text.matches("[0-9]*")) {
                super.insertString(fb, offs, str, a);
            }
        }
    });
		List<JTextField> neuronCount = new ArrayList<JTextField>();
    layers.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent key) {
						}

			@Override
			public void keyReleased(KeyEvent key) {
				int num = layers.getText().equals("") ? 0 : Integer.parseInt(layers.getText());
				if (num < neuronCount.size()) {
					for (int i = neuronCount.size()-1; i >= num; i--) {
					  frame.remove(neuronCount.get(i));
					  neuronCount.remove(i);
					}
				} else {
					for (int i = neuronCount.size(); i < num; i++) {
						JTextField field = new JTextField();
						field.setBounds(50*(neuronCount.size()), 200, 50, 50);
						neuronCount.add(field);
						frame.add(field);
						field = null;
					}
				}
				frame.validate();
				frame.repaint();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
    
    });
		JLabel layerInfo = new JLabel("Number of layers");
		layerInfo.setBounds(49, 10, 105, 50);
		frame.add(layerInfo);
		layers.setBounds(50, 50, 100, 50);
		frame.add(layers);
		JLabel neuronInfo = new JLabel("Number of neurons on each layer");
		neuronInfo.setBounds(40, 160, 200, 50);
		frame.add(neuronInfo);
		JButton data = new JButton("Make Training Data");
		data.setBounds(250, 50, 150, 50);
		data.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
		    new Thread(new Runnable() {
		      public void run() {
		      	testingData = TDataGui.setupTableData();
		      }
		    }).start();
			}
		});
		frame.add(data);
		JButton loadData = new JButton("Load Training Data");
		loadData.setBounds(250, 100, 150, 50);
		loadData.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				TDataGui.setupTableData();
			}
		});
		frame.add(loadData);
		JButton go = new JButton("Go!");
		go.setBounds(50, 260, 60, 60);
		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> design = new ArrayList<Integer>();
				for (JTextField layer : neuronCount) {
					design.add(Integer.parseInt(layer.getText()));
				}
				temp = testingData;
				new MultiLayer(design, temp);
				testingData = null;
			}
		});
		frame.add(go);
		go.setEnabled(false);
		frame.setVisible(true);
  	while (testingData == null){
  		try {Thread.sleep(0);} catch (InterruptedException e1) {e1.printStackTrace();}
  	}
  	go.setEnabled(true);
  	frame.revalidate();
	}
}
