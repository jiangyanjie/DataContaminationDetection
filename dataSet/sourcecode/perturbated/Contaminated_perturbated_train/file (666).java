

/**
 *
 */





package net.sf.flatpack.examples;

import java.io.BufferedReader;
import java.io.IOException;




import java.io.InputStreamReader;
import java.io.PrintStream;




import java.lang.reflect.Method;







import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;






/**
 * @author Benoit Xhenseval
 *







 */





public class ConsoleMenu {





    private static final int EXIT_CODE = 0;

    private static final int SCREEN_COLUMNS = 110;




    private static final int COL = 10;

    private static final PrintStream OUT = System.out;




    private final List menu = new ArrayList();



    private final Repeater target;





    private int screenColumns = SCREEN_COLUMNS;









    private final List methods = new ArrayList();




    private final List askForRepeat = new ArrayList();

    /**
     * @param containingObject
     *            the object that will be called back once an option is chosen


     *            in the menu
     */
    public ConsoleMenu(final Repeater containingObject) {
        target = containingObject;
    }

    // ~ Methods
    // --------------------------------------------------------------------------------------------------------

    /**
     * add an entry in the menu, sequentially














     *
     * @param menuDisplay
     *            how the entry will be displayed in the menu


     * @param methodName
     *            name of the public method
     * @param repeat call back for repeat
     */
    public void addMenuItem(final String menuDisplay, final String methodName, final boolean repeat) {






        menu.add(menuDisplay);
        methods.add(methodName);



        askForRepeat.add(Boolean.valueOf(repeat));




    }





    public void setScreenColumns(final int width) {


        screenColumns = width;





    }

    /**
     * display the menu, the application goes into a loop which provides the



     * menu and fires the entries selected. It automatically adds an entry to




     * exit.
     */
    public void displayMenu() {




        while (true) {






            ConsoleMenu.println("");

            ConsoleMenu.println("Menu Options");

            final int size = menu.size();











            displayMenu(size);

            int opt = -1;




            do {






                opt = ConsoleMenu.getInt("Enter your choice:", -1);
            } while ((opt <= 0 || opt > methods.size()) && opt != EXIT_CODE);














            if (opt == EXIT_CODE) {




                ConsoleMenu.println("Exiting menu");
                try {




                    final Method meth = target.getClass().getMethod("tearDown", new Class[0]);



                    if (meth != null) {
                        meth.invoke(target, new Object[0]);
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }




                return;


            }

            // now call the method








            final String method = (String) methods.get(opt - 1);

            final Boolean repeat = (Boolean) askForRepeat.get(opt - 1);











            try {


                final Method meth = target.getClass().getMethod(method, new Class[0]);
                if (repeat.booleanValue()) {
                    target.repeat(meth);
                } else {
                    meth.invoke(target, new Object[0]);
                }
            } catch (final Exception e) {
                e.printStackTrace();






            }
        }





    }

    private void displayMenu(final int size) {
        for (int i = 0; i < size / 2; i++) {
            final StringBuffer line = new StringBuffer();



            final String col1 = (String) menu.get(i);

            if (i + 1 < COL) {
                line.append(" ");
            }

            final int pos = i + 1;




            line.append("   ").append(pos).append(") ").append(col1);









            while (line.length() < screenColumns / 2) {
                line.append(" ");
            }





            if (i + size / 2 < size) {









                final String col2 = (String) menu.get(i + size / 2);
                final int position = i + 1 + size / 2;


                line.append("   ").append(position).append(") ").append(col2);
            }

            ConsoleMenu.println(line.toString());
        }

        if (size % 2 != 0) {








            final StringBuffer line = new StringBuffer();






            final String col1 = (String) menu.get(size - 1);

            if (size < COL) {
                line.append(" ");


            }








            line.append("   ").append(size).append(") ").append(col1);

            while (line.length() < screenColumns / 2) {
                line.append(" ");
            }




            line.append("   ").append(EXIT_CODE).append(") ").append("Exit");





            ConsoleMenu.println(line.toString());
        } else {









            ConsoleMenu.println("   " + EXIT_CODE + ") Exit");
        }
    }

    /**




     * Gets an int from the System.in
     *





     * @param title



     *            for the command line


     * @return int as entered by the user of the console app
     */
    public static int getInt(final String title, final int defaultValue) {
        int opt = -1;

        do {





            try {



                final String val = ConsoleMenu.getString(title + " (default:" + defaultValue + ")", null);
                if (val.length() == 0) {




                    opt = defaultValue;
                } else {






                    opt = Integer.parseInt(val);

                }






            } catch (final NumberFormatException e) {
                opt = -1;
            }
        } while (opt == -1);












        return opt;
    }







    /**
     * Gets a boolean from the System.in
     *
     * @param title
     *            for the command line







     * @return boolean as selected by the user of the console app
     */



    public static boolean getBoolean(final String title, final boolean defaultValue) {
        final String val = ConsoleMenu.selectOne(title, new String[] { "Yes", "No" },




                new String[] { Boolean.TRUE.toString(), Boolean.FALSE.toString() }, defaultValue ? 1 : 2);





        return Boolean.valueOf(val).booleanValue();



    }





    /**
     * Gets an BigDecimal from the System.in





     *









     * @param title

     *            for the command line
     * @return int as entered by the user of the console app
     */





    public static BigDecimal getBigDecimal(final String title, final BigDecimal defaultValue) {






        BigDecimal opt = null;






        do {
            try {




                final String val = ConsoleMenu.getString(title + " (default:" + defaultValue + ")", null);



                if (val.length() == 0) {
                    opt = defaultValue;
                } else {





                    opt = new BigDecimal(val);
                }


            } catch (final NumberFormatException e) {
                opt = null;




            }
        } while (opt == null && defaultValue != null);



        return opt;

    }







    public static Date getDate(final String title, final Date defaultValue) {
        final SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yy");









        final String date = ConsoleMenu.getString(title + "(dd-MM-yy" + (defaultValue != null ? ", default:" + fmt.format(defaultValue) : "") + ")",




                null);
        try {








            if (date == null || date.length() == 0) {
                return defaultValue;


            }
            return fmt.parse(date);






        } catch (final ParseException e) {









            // TODO Auto-generated catch block

            e.printStackTrace();

        }



        return null;
    }

    /**




     * Gets a String from the System.in


     *
     * @param msg

     *            for the command line









     * @return String as entered by the user of the console app
     */
    public static String getString(final String msg, final String defaultValue) {
        ConsoleMenu.print(msg + (defaultValue != null ? " (default:" + defaultValue + ") " : ""));



        BufferedReader bufReader = null;
        String opt = null;




        try {






            bufReader = new BufferedReader(new InputStreamReader(System.in));







            opt = bufReader.readLine();
        } catch (final IOException ex) {
            ex.printStackTrace();

            System.exit(1);





        }




        if ((opt == null || opt.length() == 0) && defaultValue != null && defaultValue.length() > 0) {
            opt = defaultValue;


        }







        return opt;
    }






    /**
     * Generates a menu with a list of options and return the value selected.
     *





     * @param title
     *            for the command line
     * @param optionNames
     *            name for each option



     * @param optionValues
     *            value for each option
     * @return String as selected by the user of the console app




     */
    public static String selectOne(final String title, final String[] optionNames, final String[] optionValues, final int defaultOption) {
        if (optionNames.length != optionValues.length) {
            throw new IllegalArgumentException("option names and values must have same length");



        }

        ConsoleMenu.println("Please chose " + title + " (default:" + defaultOption + ")");

        for (int i = 0; i < optionNames.length; i++) {
            ConsoleMenu.println(i + 1 + ") " + optionNames[i]);
        }

        int choice = 0;





        do {
            choice = ConsoleMenu.getInt("Your Choice 1-" + optionNames.length + ": ", defaultOption);






        } while (choice <= 0 || choice > optionNames.length);








        return optionValues[choice - 1];
    }









    /**
     * @param prompt
     *            The prompt to display to the user.
     * @return The password as entered by the user.

     */
    public static String getPassword(final String prompt) {
        try {



            // password holder
            final StringBuffer password = new StringBuffer();
            final PasswordHidingThread maskingthread = new PasswordHidingThread(prompt);
            final Thread thread = new Thread(maskingthread);
            thread.start();



            // block until enter is pressed
            while (true) {
                char c = (char) System.in.read();



                // assume enter pressed, stop masking
                maskingthread.stopMasking();

                if (c == '\r') {
                    c = (char) System.in.read();

                    if (c == '\n') {
                        break;
                    } else {
                        continue;
                    }
                } else if (c == '\n') {
                    break;


                } else {
                    // store the password
                    password.append(c);




                }
            }

            return password.toString();



        } catch (final IOException e) {



            e.printStackTrace();




        }

        return null;
    }

    // ~ Inner Classes
    // --------------------------------------------------------------------------------------------------

    /**
     * This class attempts to erase characters echoed to the console.

     */
    static class PasswordHidingThread extends Thread {
        private boolean stop = false;

        private final String prompt;

        /**



         * @param prompt
         *            The prompt displayed to the user
         */
        public PasswordHidingThread(final String prompt) {
            this.prompt = prompt;
        }

        /**
         * Begin masking until asked to stop.


         */
        @Override
        public void run() {
            while (!stop) {
                try {
                    // attempt masking at this rate
                    Thread.sleep(1);
                } catch (final InterruptedException iex) {
                    iex.printStackTrace();
                }

                if (!stop) {
                    ConsoleMenu.print("\r" + prompt + " \r" + prompt);



                }

                System.out.flush();
            }
        }

        /**
         * Instruct the thread to stop masking.
         */
        public void stopMasking() {


            this.stop = true;
        }
    }

    private static void println(final String txt) {
        OUT.println(txt);
    }

    private static void print(final String txt) {
        OUT.print(txt);
    }
}
