package pippin;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class DataViewPanel implements Observer{
	//The private fields are JTextField[] intFields and JTextField[] binaryFields and Machine machine
	private JTextField[] intFields = new JTextField[Memory.DATA_SIZE];
	private JTextField[] binaryFields = new JTextField[Memory.DATA_SIZE];
	private Machine machine;
	
	public DataViewPanel(Machine machine) {
        this.machine = machine;
        machine.addObserver(this);
    }
	
	 public JComponent createDataDisplay() {
		 JPanel returnPanel = new JPanel(new BorderLayout());
		 returnPanel.add(new JLabel("Data Memory View", JLabel.CENTER), BorderLayout.PAGE_START);
		 JPanel panel = new JPanel(new BorderLayout());
		 JPanel numPanel = new JPanel(new GridLayout(0,1));
		 JPanel intPanel = new JPanel(new GridLayout(0,1));
		 JPanel binaryPanel = new JPanel(new GridLayout(0,1));
		 panel.add(numPanel, BorderLayout.LINE_START);
		 panel.add(intPanel, BorderLayout.CENTER);
		 panel.add(binaryPanel, BorderLayout.LINE_END);
		 for(int i = 0; i < Memory.DATA_SIZE; i++) {
			 numPanel.add(new JLabel(" "+i+": ", JLabel.RIGHT));
			 intFields[i] = new JTextField(10);
			 intPanel.add(intFields[i]);
			 binaryFields[i] = new JTextField(30);
			 binaryPanel.add(binaryFields[i]);
		 }
		 returnPanel.add(new JScrollPane(panel));
		 return returnPanel;
		 
	 }
	 
	    @Override
        public void update(Observable o, Object msg) {
            // this first part is for later use
            if ("Clear".equals(msg)) {
                for(int i = 0; i < Memory.DATA_SIZE; i++) {
                    intFields[i].setText("");
                    binaryFields[i].setText("");               
                }           
            } else {
                for(int i = 0; i < Memory.DATA_SIZE; i++) {
                    try {
                        int val = machine.getData(i);
                        String binary = machine.getDataBinary(i);
                        if(binary.length() > 0) {
                            intFields[i].setText(""+val);
                            binaryFields[i].setText(binary);
                        }
                    } catch (DataAccessException e) {
                        e.printStackTrace(); // i is always in bounds here
                    }
                }
            }
        }
    }
