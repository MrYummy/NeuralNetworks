package ann;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;

	static JFrame frame = new JFrame();
  static JTextField layers = new JTextField();

	public static void main(String[] args) {
		new Gui();
	}
	
	public Gui() {
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setTitle("Frame");
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
				if (key.getKeyCode() == KeyEvent.VK_ENTER) {
					frame.requestFocus();
					if (Integer.parseInt(layers.getText()) < neuronCount.size()) {
						for (int i = neuronCount.size()-1; i >= Integer.parseInt(layers.getText()); i--) {
						  frame.remove(neuronCount.get(i));
						  neuronCount.remove(i);
						}
					} else {
						for (int i = neuronCount.size(); i < Integer.parseInt(layers.getText()); i++) {
							JTextField field = new JTextField();
							field.setBounds(50*(neuronCount.size()), 200, 50, 50);
							neuronCount.add(field);
							frame.add(field);
						}
					}
					frame.validate();
					frame.repaint();
				}
			}

			@Override
			public void keyReleased(KeyEvent key) {
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
		JButton go = new JButton("Go!");
		go.setBounds(40, 240, 60, 60);
		frame.add(go);

		go.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
			}
		
		});

		frame.setVisible(true);
	}
}
