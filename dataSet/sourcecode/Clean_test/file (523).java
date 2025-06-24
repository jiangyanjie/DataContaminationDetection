package FIT_0204_Shelestova.Common;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Hashtable;
//TODO: поправить слушатели на спиннере 98сомлв987
public class ControlGroup extends MyPanel {
    private Controller controller;
    private JLabel label;
    private JSlider slider;
    private JSpinner spinner;
    private int step = 1;
    private int min;
    private int max;
    protected int currentValue;

    public int getCurrentValue() {
        return currentValue;
    }

    public ControlGroup(CommonGUI gui, String name, final int min, final int max, final int init) {
        super(gui);
        this.min = min;
        this.max = max;

        label = new JLabel(name);
        createSpinner(init, min, max);
        createSlider(min, max, init);
        this.currentValue = init;
        addWidgetsToPanel();
    }

    private void addWidgetsToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;

        gbc.ipadx = 100;
        add(label, gbc);

        gbc.gridy = 1;
        add(spinner, gbc);

        gbc.gridy = 2;
        add(slider, gbc);

    }

    private void createSpinner(int init, int min, int max) {
        spinner = new JSpinner(new SpinnerNumberModel(init, min, max, step));
        addSpinnerListeners();
    }

    private void createSlider(Integer min, Integer max, Integer init) {
        slider = new JSlider(JSlider.HORIZONTAL, min,max, init);
        slider.setMajorTickSpacing(0);
        slider.setMinorTickSpacing(step * 100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( min), new JLabel(min.toString()) );
        labelTable.put( new Integer( max ), new JLabel(max.toString()) );
        slider.setLabelTable( labelTable );

        slider.setPaintLabels(true);

        addSliderListener();
    }

    public int getHeight(){
        return gui.getFullHeight(label) +
                gui.getFullHeight(spinner) +
                gui.getFullHeight(slider);

    }



    protected void addSpinnerListeners(){
        final JFormattedTextField textField = getTextField( spinner );
        final int minValue = this.min;
        final int maxValue = this.max;
        textField.addKeyListener( new KeyAdapter() {
            @Override
            public void keyReleased( final KeyEvent e ) {
                if ( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                    String input = textField.getText();

                    try{

                        Integer intInput = Integer.parseInt(input);

                        if(intInput > maxValue || intInput < minValue){
                            showWarning();
                            textField.setValue(slider.getValue());
                        }
                        else{
                            if(canChange(intInput)) {
                                slider.setValue(intInput);
                                changed();
                            }
                            else {
                                textField.setValue(slider.getValue());
                            }
                        }
                    }catch (NumberFormatException ex){
                        showWarning();
                        textField.setValue(slider.getValue());
                    }
                }
            }
        } );


        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spinner = (JSpinner)(e.getSource());
                SpinnerNumberModel model = (SpinnerNumberModel)(spinner.getModel());
                int value = model.getNumber().intValue();
                if(canChange(value)){
                    slider.setValue(value);
                    changed();
                }
                else {
                    spinner.setValue(slider.getValue());
                }
            }

        });

    }

    private void showWarning(){
        JOptionPane.showMessageDialog(this,
                "Bad value. Input number in [" +min +", "+ max + "]",
                "Value warning",
                JOptionPane.WARNING_MESSAGE);
    }
    private JFormattedTextField getTextField( JSpinner spinner )
    {
        JComponent editor = spinner.getEditor();

        if ( editor instanceof JSpinner.DefaultEditor )
        {
            return ( ( JSpinner.DefaultEditor )editor ).getTextField();
        }
        else
        {
            System.err.println( "Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor" );
            return null;
        }
    }
    private  void  addSliderListener(){
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // синхронизируем состояние спиннера и слайдера.
                JSlider slider = (JSlider)e.getSource();
                int value = slider.getValue();
                if(canChange(value)){
                    spinner.setValue(value);
                    changed();
                }
                else {
                    slider.setValue(currentValue);
                }
            }
        });
    }
    @Override
    public void changed(){
        currentValue = slider.getValue();
        super.changed();
    }
    protected boolean canChange(int value){

        return true;
    }

}
