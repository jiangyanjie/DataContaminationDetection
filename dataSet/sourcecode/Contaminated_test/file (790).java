package autoer.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import autoer.Application;
import autoer.ui.actions.Delay;

import com.jeta.forms.components.panel.FormPanel;

public class DelayTab {

  private Delay delay;

  private FormUI form;
  private Application autoer;
  private JSpinner delaySpinner;
  private JTextArea descriptionTextField;
  private JButton addButton;
  private JButton clearButton;
  private JButton saveButton;

  private ActionListener buttonListener = new ActionListener() {
    public void actionPerformed(ActionEvent evt) {
      if (evt.getSource() == addButton) {
        addAction();
      } else if (evt.getSource() == clearButton) {
        clearFields();
      } else if (evt.getSource() == saveButton) {
        saveAction();
      }
    }
  };

  public DelayTab(FormUI formUI, FormPanel form, Application autoer) {
    this.autoer = autoer;
    this.form = formUI;
    fetchUIComponents(form);
    init();
  }

  private void init() {
    addButton.addActionListener(buttonListener);
    clearButton.addActionListener(buttonListener);
    saveButton.addActionListener(buttonListener);
  }

  private void fetchUIComponents(FormPanel form) {
    delaySpinner = (JSpinner) form.getComponentByName("Delay.delaySpinner");
    descriptionTextField = (JTextArea) form
        .getComponentByName("Delay.descriptionTextField");
    addButton = (JButton) form.getComponentByName("Delay.addButton");
    clearButton = (JButton) form.getComponentByName("Delay.clearButton");
    saveButton = (JButton) form.getComponentByName("Delay.saveButton");
  }

  public void loadAction(Delay delay) {
    this.delay = delay;
    delaySpinner.setValue(delay.getDelay());
    descriptionTextField.setText(delay.getDescription());
  }

  public void saveAction() {
    if (isValid()) {
      delay.setDelay((Integer) delaySpinner.getValue());
      delay.setDescription(descriptionTextField.getText());
      autoer.updateTree();
    } else {
      JOptionPane.showMessageDialog(null, "Error!");
    }
  }

  private void addAction() {
    if (isValid()) {
      int delay = (Integer) delaySpinner.getValue();
      String description = descriptionTextField.getText();

      autoer.addValue(new Delay(delay, description));
      clearFields();
    } else {
      JOptionPane.showMessageDialog(null, "Error!");
    }
  }

  private void clearFields() {
    delaySpinner.setValue(0);
    descriptionTextField.setText("");
  }

  public boolean isValid() {
    if (descriptionTextField.getText().trim().equals("")
        || (Integer) delaySpinner.getValue() < 0) {
      return false;
    }

    return true;
  }
}
