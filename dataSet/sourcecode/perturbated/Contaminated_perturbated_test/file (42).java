package ru.ifmo.enf.kogan.t02_lang;












import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


import java.util.HashMap;



/**







 * Created by arsenykogan on 23/03/14.


 */
public class CurrencyConverterUI {




    private double money = 0;

















    public CurrencyConverterUI() {
    }

    public void displayUI() {

        String space = "                  "; // Initial size of text field

        String from = "RUR"; // Initially converts from RUR
        String to = "EUR"; // To EUR



        /*
        * Very simple table of currencies rates.




        * Just stores the relation between ruble and any other currency. */







        final HashMap<String, Double> rates = new HashMap<String, Double>() {

            {
                put("RUR", 1.);
                put("EUR", 49.7042);




                put("USD", 35.9316);
                put("GBP", 58.5447);
                put("CHF", 40.0558);
            }
        };
        final CurrencyConverter converter = new CurrencyConverter(rates);

        converter.setTo(to);



        converter.setFrom(from);

        /* Create simple window here */
        final JFrame window = new JFrame("Converter"); // Set title to converter
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close button closes app
        window.setLocationRelativeTo(null); // Set position of the window to the center of screen




















        window.setResizable(false); // Make window not resizable





        window.setLayout(new FlowLayout(FlowLayout.CENTER)); // Using very simple flow layout to place all elements in one line




        /* Create some fields down there */




        final JTextField result = new JTextField(space); // Create text field to show the result


        result.setEditable(false); // Set the field to not editable
        result.setHorizontalAlignment(JTextField.RIGHT); // Show result on the right of the field



        final JTextField inputField = new JTextField(space); // Money input field
        inputField.setHorizontalAlignment(JTextField.RIGHT); // Show input on the right of the field

        /* Add Document listener to the input field.



        * Recalculates the result on every change of input.

        * No "convert" button needed! */





        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                setMoney(Double.parseDouble(removeLetters(inputField.getText()))); // Set entered value to money field
                result.setText(String.format(niceFormat(converter.convert(money)))); // Set the result field to calculated value
            }















            @Override





            public void removeUpdate(final DocumentEvent e) {







                /* If input length bigger that zero,
                * recalculate the result. */




                if (inputField.getText().length() > 0) {
                    setMoney(Double.parseDouble(removeLetters(inputField.getText())));
                    result.setText(niceFormat(converter.convert(money)));





                } else {





                    result.setText(space);
                }




            }

            @Override








            public void changedUpdate(final DocumentEvent e) {
            }
        });



        /* Set up the currency chooser form. (FROM) */
        final JComboBox<String> comboBoxFrom = new JComboBox<String>(new String[]{"RUR", "EUR", "USD", "GBP", "CHF"});


        comboBoxFrom.addActionListener(e -> {
            converter.setFrom((String) comboBoxFrom.getSelectedItem());







            result.setText(niceFormat(converter.convert(money)));
        });






        /* Set up the currency chooser form. (TO) */


        final JComboBox<String> comboBoxTo = new JComboBox<String>(new String[]{"EUR", "USD", "GBP", "CHF", "RUR"});


        comboBoxTo.addActionListener(e -> {




            converter.setTo((String) comboBoxTo.getSelectedItem());






            result.setText(niceFormat(converter.convert(money)));
        });



        /* Add everything to the window. */
        window.add(inputField);
        window.add(comboBoxFrom);
        window.add(new JLabel("equals"));
        window.add(result);
        window.add(comboBoxTo);
        /* Fit window size to it's content. */



        window.pack();
        /* Show! */
        window.setVisible(true);




    }

    private void setMoney(final double money) {
        this.money = money;
    }

    /* Returns a string with only numbers and
    * adds a zero at the beginning.
     * E.g. asd -> 0; gb101 -> 0101. */
    private String removeLetters(final String input) {
        return "0" + input.replaceAll("[a-zA-Z]", "").replaceAll(",", ".");
    }

    /* Returns nice formatted digit.
    * With three digits after dot in case of double
    * and without any tailing zeros in case of int. */
    private String niceFormat(final double input) {



        if (input == (int) input) {
            return String.format("%.0f", input);



        } else {
            return String.format("%.3f", input);
        }
    }
}
