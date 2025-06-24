package com.aquar.game.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.JDatePanel;

public class DatePickerDialog extends JDialog {
    private Calendar dateValue;
    private boolean changed = false;
    
    public DatePickerDialog(Window owner) {
        super(owner, "Select a Date");
        setModal(true);
        setLocationRelativeTo(owner);
        initUI();
    }

    private void initUI() {
        final JDatePanel datePanel = JDateComponentFactory.createJDatePanel();
        datePanel.setShowYearButtons(true);
        datePanel.getModel().setSelected(true);
        getContentPane().add((Component) datePanel, BorderLayout.CENTER);
       
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton saveBtn = new JButton("OK");
        bottomPanel.add(saveBtn);
        saveBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setDateValue((Calendar) datePanel.getModel().getValue());
                setChanged(true);
                dispose();
            }
        });
        
        JButton cancelBtn = new JButton("Cancel");
        bottomPanel.add(cancelBtn);
        cancelBtn.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged(false);
                dispose();
            }
        });;
        
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        pack();
    }

    public Calendar getDateValue() {
        return dateValue;
    }

    public void setDateValue(Calendar dateValue) {
        this.dateValue = dateValue;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
    
    
    
}
