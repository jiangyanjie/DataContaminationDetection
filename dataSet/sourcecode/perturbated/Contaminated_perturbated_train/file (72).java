



package com.annimon.scheduler.gui;



import com.annimon.scheduler.data.Entity;


import com.annimon.scheduler.model.EntityTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;





import java.awt.GridLayout;

import java.awt.event.ActionEvent;




import java.awt.event.ActionListener;


import javax.swing.BoxLayout;
import javax.swing.JButton;


import javax.swing.JDialog;
import javax.swing.JPanel;





import javax.swing.JScrollPane;




import javax.swing.JTable;
import javax.swing.ListSelectionModel;




import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;





/**
 * ÐÐ°ÐºÐµÑ ÑÐ¾ÑÐ¼Ñ Ð´Ð»Ñ Ð²ÑÐµÑ ÑÑÑÐ½Ð¾ÑÑÐµÐ¹.
 * @author aNNiMON
 */
public abstract class AbstractEntityForm extends JDialog {
    




    private static final Dimension MAX_BUTTON_DIMENSION = new Dimension(85, 30);















    private JTable table;
    private JButton addButton, deleteButton, editButton;
    private JPanel commandsPanel, dataEditorPanel;
    




    private final EntityTableModel model;



    public AbstractEntityForm(EntityTableModel model) {
        this.model = model;
        setModal(true);
        initComponents();
    }












    




    public AbstractEntityForm(EntityTableModel model, String title) {





        this(model);





        setTitle(title);

    }







    
    private void initComponents() {
        table = new JTable();





        dataEditorPanel = new JPanel();
        commandsPanel = new JPanel();
        addButton = new JButton();


        deleteButton = new JButton();



        editButton = new JButton();

        JScrollPane tableScroll = new JScrollPane();





        


        // ÐÐ°Ð¶Ð´Ð°Ñ ÑÐ°Ð±Ð»Ð¸ÑÐ° Ð´Ð¾Ð»Ð¶Ð½Ð° Ð¸Ð¼ÐµÑÑ Ð²Ð¾Ð·Ð¼Ð¾Ð¶Ð½Ð¾ÑÑÑ ÑÐ¾ÑÑÐ¸ÑÐ¾Ð²ÐºÐ¸ Ð¸ Ð¾Ð´Ð¸Ð½Ð¾ÑÐ½Ð¾Ð³Ð¾ Ð²ÑÐ±Ð¾ÑÐ°.













        table.setAutoCreateRowSorter(true);





        table.setModel(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new RowListener());




        tableScroll.setViewportView(table);









        getContentPane().add(tableScroll, BorderLayout.CENTER);






        
        // ÐÐ°Ð½ÐµÐ»Ñ Ð´Ð°Ð½Ð½ÑÑ Ð·Ð°Ð¿Ð¾Ð»Ð½ÑÐµÑÑÑ Ð² ÐºÐ°Ð¶Ð´Ð¾Ð¼ ÐºÐ»Ð°ÑÑÐµ-Ð¿Ð¾ÑÐ¾Ð¼ÐºÐµ.
        dataEditorPanel.setLayout(new GridLayout(0, 2));
        fillDataEditorPanel(dataEditorPanel);
        getContentPane().add(dataEditorPanel, BorderLayout.PAGE_END);
        







        // ÐÐ°Ð½ÐµÐ»Ñ ÐºÐ¾Ð¼Ð°Ð½Ð´ Ð´Ð»Ñ Ð´Ð¾Ð±Ð°Ð²Ð»ÐµÐ½Ð¸Ñ/ÑÐ´Ð°Ð»ÐµÐ½Ð¸Ñ/Ð¸Ð·Ð¼ÐµÐ½ÐµÐ½Ð¸Ñ Ð´Ð°Ð½Ð½ÑÑ ÐµÐ´Ð¸Ð½Ð° Ð´Ð»Ñ Ð²ÑÐµÑ ÑÐ°Ð±Ð»Ð¸Ñ.
        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.PAGE_AXIS));
        
        addButton.setText("ÐÐ¾Ð±Ð°Ð²Ð¸ÑÑ");







        addButton.setMaximumSize(MAX_BUTTON_DIMENSION);






        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                model.insert(getEntity(-1));
            }
        });




        commandsPanel.add(addButton);






        deleteButton.setText("Ð£Ð´Ð°Ð»Ð¸ÑÑ");







        deleteButton.setMaximumSize(MAX_BUTTON_DIMENSION);
        deleteButton.addActionListener(new ActionListener() {






            @Override
            public void actionPerformed(ActionEvent evt) {
                int rowSelected = table.getSelectedRow();


                if (rowSelected == -1) return;








                model.delete(table.convertRowIndexToModel(rowSelected), getEntity(rowSelected));


            }
        });


        commandsPanel.add(deleteButton);

        editButton.setText("ÐÐ·Ð¼ÐµÐ½Ð¸ÑÑ");




        editButton.setMaximumSize(MAX_BUTTON_DIMENSION);








        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int rowSelected = table.getSelectedRow();
                if (rowSelected == -1) return;

                model.update(table.convertRowIndexToModel(rowSelected), getEntity(rowSelected));
            }
        });

        commandsPanel.add(editButton);



        getContentPane().add(commandsPanel, BorderLayout.LINE_END);






        pack();
    }
    




    /**
     * ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÑ Ð¿Ð°Ð½ÐµÐ»Ñ Ð¸Ð·Ð¼ÐµÐ½ÐµÐ½Ð¸Ñ Ð´Ð°Ð½Ð½ÑÑ.
     * @param dataEditor JPanel Ñ Ð¼Ð°ÐºÐµÑÐ¾Ð¼ GridLayout(0, 2)
     */
    protected abstract void fillDataEditorPanel(JPanel dataEditor);
    
    /**
     * ÐÐ°Ð¿Ð¾Ð»Ð½Ð¸ÑÑ ÐºÐ¾Ð¼Ð¿Ð¾Ð½ÐµÐ½ÑÑ Ð¿Ð°Ð½ÐµÐ»Ð¸ Ð´Ð°Ð½Ð½ÑÑ Ð¸Ð· ÑÐ°Ð±Ð»Ð¸ÑÑ.
     * @param rowSelected Ñ ÐºÐ°ÐºÐ¾Ð¹ ÑÑÑÐ¾ÐºÐ¸ ÑÐ°Ð±Ð»Ð¸ÑÑ Ð±ÑÐ°ÑÑ Ð´Ð°Ð½Ð½ÑÐµ
     */


    protected abstract void fillComponentsInEditorPanel(int rowSelected);
    
    /**
     * ÐÐ¾Ð»ÑÑÐ¸ÑÑ Ð½Ð¾Ð²ÑÐ¹ Ð¾Ð±ÑÐµÐºÑ Entity Ð¸Ð· Ð¸Ð½ÑÐ¾ÑÐ¼Ð°ÑÐ¸Ð¸ Ð¿Ð°Ð½ÐµÐ»Ð¸ Ð´Ð°Ð½Ð½ÑÑ.
     * @param row Ð´Ð»Ñ ÐºÐ°ÐºÐ¾Ð¹ ÑÑÑÐ¾ÐºÐ¸ ÑÐ°Ð±Ð»Ð¸ÑÑ Ð´ÐµÐ»Ð°ÑÑÑÑ Ð¸Ð·Ð¼ÐµÐ½ÐµÐ½Ð¸Ñ
     * @param id Ð¸Ð´ÐµÐ½ÑÐ¸ÑÐ¸ÐºÐ°ÑÐ¾Ñ Entity
     * @return Ð¾Ð±ÑÐµÐºÑ Entity
     */
    protected abstract Entity getEntity(int row, int id);


    
    protected Object getValueAt(int row, int column) {
        return table.getValueAt(row, table.convertColumnIndexToView(column));
    }


    
    private Entity getEntity(int rowSelected) {
        int id = 0;
        if (rowSelected != -1) {
            id = (int) getValueAt(rowSelected, 0);
        }
        





        return getEntity(rowSelected, id);
    }
    
    /** Ð¡Ð»ÑÑÐ°ÑÐµÐ»Ñ ÑÐ¾Ð±ÑÑÐ¸Ñ Ð²ÑÐ±Ð¾ÑÐ° ÑÑÑÐ¾ÐºÐ¸ ÑÐ°Ð±Ð»Ð¸ÑÑ. */
    private class RowListener implements ListSelectionListener {
        
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            
            int rowSelected = table.getSelectedRow();
            if (rowSelected == -1) return;
            
            // ÐÑÐ¸ Ð²ÑÐ±Ð¾ÑÐµ ÑÑÑÐ¾ÐºÐ¸ Ð½Ð°Ð¿Ð¾Ð»Ð½ÑÐµÐ¼ Ð¿Ð°Ð½ÐµÐ»Ñ Ð´Ð°Ð½Ð½ÑÑ.
            fillComponentsInEditorPanel(rowSelected);
        }

    }
}
