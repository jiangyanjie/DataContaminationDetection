package pippin;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class ControlPanel implements Observer {
	private Machine machine;
    private JButton stepButton = new JButton("Step");
    private JButton clearButton = new JButton("Clear");
    private JButton runButton = new JButton("Run/Pause");
    private JButton reloadButton = new JButton("Reload");


    public ControlPanel(Machine machine) {
        this.machine = machine;
        if (machine != null) {
            machine.addObserver(this);
        }
    }

    public void checkEnabledButtons() {
        runButton.setEnabled(machine.getState().getRunSuspendActive());
        stepButton.setEnabled(machine.getState().getStepActive());
        clearButton.setEnabled(machine.getState().getClearActive());
        reloadButton.setEnabled(machine.getState().getReloadActive());
    }

    private class StepListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            machine.step();
        }
    }
    
    private class ClearListener implements ActionListener{
    	  @Override
    	  public void actionPerformed(ActionEvent arg0) {
            machine.clearAll();
          }
    }
    
    private class ReloadListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			machine.reload();
			
		}
    	
    }

    private class RunPauseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (machine.isAutoStepOn()) {
                machine.setAutoStepOn(false); // add this void method to Machine
            } else {
                machine.setAutoStepOn(true);
            }
        }
    }
    
    public JComponent createControlDisplay() {
    	JComponent retVal = new JPanel();
    	retVal.setLayout(new GridLayout(1,0));
    	retVal.add(stepButton);
    	retVal.add(clearButton);
    	retVal.add(runButton);
    	retVal.add(reloadButton);
    	stepButton.addActionListener(new StepListener());
    	clearButton.addActionListener(new ClearListener());
    	runButton.addActionListener(new RunPauseListener());
    	reloadButton.addActionListener(new ReloadListener());
    	
    	
    	return retVal;
    	
    	
    	
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        checkEnabledButtons();
    }
}