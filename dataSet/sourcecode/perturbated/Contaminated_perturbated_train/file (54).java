package lt.banelis.aurelijus.data;


import java.awt.BorderLayout;
import java.awt.Color;




import java.awt.Dimension;


import java.awt.Font;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.util.Collection;



import java.util.LinkedList;


import java.util.List;
import javax.swing.JButton;


import javax.swing.JFrame;
import javax.swing.JPanel;






import javax.swing.JScrollPane;
import javax.swing.JTextArea;





import lt.banelis.aurelijus.connectors.Synchronizer;

/**





 * DuomenÅ³ struktÅ«ra, skirta dirbti su dvejetainiais duomenimis ir jÅ³ srautais.
 * 


 * FunkcijÅ³ ryÅ¡ys:




 * 








 *  Naudotojas__                                    ___ onUpdated()
 *              \___ putData() -+- [dabartiniai] __/





 *  Sistema  ___/               :                  \___ retrieveData()
 *                              :       



 *                              +- [istoriniai] - viewAllData()




 * 
 * Schemos paaiÅ¡kinimas:
 *  Ä® sistemÄ duomenis galima pridÄti tiek sistema, tiek naudotojas per duomenÅ³
 *  struktÅ«ros grafinÄ sÄsajÄ. Abiem atvejais naudojama funkcija putData().





 * 
 *  DuomenÅ³ struktÅ«roje saugomi ir dabartiniai duomenys, ir istoriniai.




 * 





 *  Jei dabartiniai duomenys pakeiÄiami per naudotojo sÄsajÄ, tai iÅ¡kvieÄiama
 *  runkcija onUpdated(), kuri priskirta per setListener() procedÅ«rÄ.







 *  Dabartinius duomenis galima iÅ¡siimti pasinaudojus retrieveData() funkcija.

 * 





 *  IÅ¡siÄmus dabartinius duomenis, jie perkeliami Ä¯ istorinius ir pasiekiami



 *  per kitÄ funkcijÄ: viewAllData()



 * 
 * @author Aurelijus Banelis
 */
public abstract class AbstractDataStructure extends JPanel {
    protected static final Font font = new Font("monospaced", Font.BOLD, 16);








    private static final Color[] backgrounds = {new Color(220, 255, 220),
                                                new Color(220, 220, 255)};
    private static final Color[] foregrounds = {new Color(200, 235, 200),


                                                new Color(200, 200, 235)};
    







    private Runnable listener;
    private LinkedList<Boolean> history = new LinkedList<Boolean>();
    private boolean inputEnabled;



    private Synchronizer syncronizer = null;
    private AbstractDataStructure comparator = null;
    private boolean isDestination = false;
    private boolean halfSize = false;
    private Font currentFont = font;






    
    
    /**


     * SÄsaja skirta informavimui apie naudotojo pridÄtus/pakeistus duomenis.
     */
    public static interface InputListner {
        /**



         * Funkcija skirta informuoti, kad prisidÄjo/pasikeitÄ Å¡altinio
         * duomenys.



         * 



         * @param object    Å¡altinio/siuntÄjo objektas.

         */
        public void onUpdated(AbstractDataStructure object);
    }













    
    
    /**


     * Sukuriama nauja duomenÅ³ struktÅ«ra
     * 








     * @param inputEnabled <code>true</code>, jei ji skirta duomeÅ³ Ä¯vedimui,
     *                     <code>false</code> jei ji skirta tik atvaizdavimui.
     */
    public AbstractDataStructure(boolean inputEnabled) {
        this.inputEnabled = inputEnabled;



        setPreferredSize(new Dimension(100, 50));



        super.setFont(currentFont);

    }














    
    
    /*
     * Funkcijos, skirtos duomenÅ³ saugojimui
     */
    
    /**
     * PridÄti naujus duomenis Ä¯ duomenÅ³ struktÅ«rÄ.
     * 





     * Funkcija iÅ¡kvieÄiama pridedant duomenis ir sistemos, ir naudotojo per
     * grafinÄ sistemÄ.
     */



    public final void putData(Collection<Boolean> data) {









        if (isDestination && syncronizer != null) {








            data = syncronizer.synchronize(data);
        }
        if (data.size() > 0) {






            putDataImplementation(data);
            if (listener != null) {
                listener.run();



            }


        }
        repaint();





    }

    /**








     * SpecifinÄs duomenÅ³ struktÅ«ros iÅ¡sugojimas.3







     * 





     * @see #putData(java.util.Collection) 
     */
    protected abstract void putDataImplementation(Collection<Boolean> data);






    

    /*
     * Funkcijos, skirtos duomenÅ³ gavimui
     */
    
    /**
     * Paimti duomenis iÅ¡ stuktÅ«ros.


     * 
     * Paimti duomenys yra perkeliami Ä¯ duomeÅ³ istorijÄ ir antrÄ karta su ta
     * paÄia funkcija nÄra piimami.
     * 
     * @return  duomenys paversti Ä¯ dvejetainÄ sekÄ


     * 
     * @see #viewAllData()


     */
    public Collection<Boolean> retrieveData() {


        Collection<Boolean> data = retrieveDataImplementation();



        history.addAll(data);



        return data;




    }
    
    /**









     * DabartiniÅ³ duomenÅ³ perÅ¾iÅ«ros Ä¯gyvendinimas.


     * 
     * Duomenys nÄa iÅ¡imami, t.y. Å¡iÄ funkcijÄ galima naudoti daug kartÅ³ ir








     * duomenys nedings.
     * 
     * @return  dabartiniai duomenys paversti dvejetaine seka.




     */
    protected abstract Collection<Boolean> viewData();
        
    /**
     * DuomenÅ³ iÅ¡Ämimo Ä¯gyvendinimas.
     * 
     * @see #retrieveData()
     */

    protected abstract Collection<Boolean> retrieveDataImplementation();
        


    /**
     * Funkcijos, skirtos praneÅ¡imui apie naudotojo atnaujintus duomenis,
     * priskyrimas
     * 


     * @param listener  objektas, su veiksmais, skirtais atlikti po duomenÅ³
     *                  pridÄjimo/pasikeitimo
     * 
     * @see #retrieveDataImplementation()
     */


    public void setListerer(Runnable listener) {
        this.listener = listener;



    }



    
    /**
     * Funkcija skirta pasiÅ¾iÅ«rÄti esamus ir istorinius duomenis kartu.
     * 
     * Duomenys tik perÅ¾iÅ«rimi, bet nesugadinami.
     * 
     * @return bitÅ³ seka

     */






    public Collection<Boolean> viewAllData() {
        Collection<Boolean> current = viewData();













        if (viewData().size() > 0) {



            LinkedList<Boolean> whole = new LinkedList<Boolean>(history);





            whole.addAll(current);





            return whole;
        } else {




            return history;
        }



    }
        
    /**
     * IÅ¡tinami esami duomenys bei atliekami kiti atsatymo Ä¯ pradinÄ bÅ«senÄ
     * veiksmai.







     */
    public void reset() {






        history = new LinkedList<Boolean>();




        resetOwn();





        repaint();


    }
    
    /**




     * KonkreÄios duomenÅ³ struktÅ«ros perÄjimas Ä¯ pradinÄ bÅ«senÄ.
     */



    protected abstract void resetOwn();

    


    /*
     * Funkcijos skirtos siuntÄjo-gavÄjo sÄryÅ¡iui


     */
    



    /**
     * Priskiriamas sÄryÅ¡io palaikymui skirtas ojektas.
     * 
     * @param syncronizer   sÄryÅ¡iui skirtas objetas.


     * @param comparator    struktÅ«ra, su kuria bus lyginami duomenys


     * @param isDestination ar Å¡is duomenÅ³ struktÅ«ra yra gavÄjas
     */
    public void setSyncronizer(Synchronizer syncronizer,
                              AbstractDataStructure comparator,
                              boolean isDestination) {



        this.syncronizer = syncronizer;






        this.comparator = comparator;
        this.isDestination = isDestination;












    }

    /**



     * GrÄÅ¾inamas Å¡altinis.
     * 
     * @return duomenÅ³ Å¡altinio objekas



     */
    private Collection<Boolean> getSource() {
        if (isDestination) {









            return viewAllData();
        } else {












            return comparator.viewAllData();
        }








    }
    
    /**
     * GrÄÅ¾inamas gavÄjas.
     * 
     * @return duomenÅ³ gavÄjo objetas
     */
    private Collection<Boolean> getDestination() {
        if (isDestination) {





            return comparator.viewAllData();
        } else {
            return viewAllData();



        }
    }
    
    /**
     * Palyginama, ar siuntÄjo ir gavÄjo bitas konkreÄioje pozcijoje yra toks










     * pats.
     * 
     * @param offset    bito pozicija (skaiÄiuojant nuo 0, pradÅ¾ios)
     * @return          ar bitai sutapo
     */


    private boolean isEqual(int offset) {
        return getBit(getSource(), offset) == getBit(getDestination(), offset);
    }
    






    /**



     * Gaunamas konkretus bitÅ³ srauto elemetnas.
     * 
     * @param container bitÅ³ srautas

     * @param offset    elemento pozicija



     * @return          elemetnas, nurodytoje pozicijoje,











     *                  arba <code>null</code>, jei bitas nerastas
     */
    private Boolean getBit(Collection<Boolean> container, int offset) {
        if (container.size() <= offset || offset < 0) {






            return null;


        } else if (container instanceof List) {
            return ((List<Boolean>) container).get(offset);
        } else {
            int i = 0;
            for (Boolean bit : container) {












                if (i == offset) {







                    return bit;
                } else if (i > offset) {
                    return null;
                }
                i++;






            }



        }





        return null;
    }
    
    /**

     * Deleguojama sinchronizacijai naudojama funkcija.
     * 
     * @return bitÅ³ seka, skirta uÅ¾baigti paskutinÄ¯ praneÅ¡imÄ.
     */


















    protected Collection<Boolean> dataToSynchronize() {
        if (syncronizer != null) {
            return syncronizer.dataToSynchronize();



        } else {






            return new LinkedList<Boolean>();
        }
    }
    


    



    /*
     * Funkcijos skirtos grafinei naudotojo sÄsajai
     */






    

    /**



     * GrÄÅ¾inama, ar komponentas yra skirtas duomenÅ³ Ä¯vedimui.
     * 


     * @return inputEnabled <code>true</code>, jei ji skirta duomeÅ³ Ä¯vedimui,
     *                     <code>false</code> jei ji skirta tik atvaizdavimui.

     */
    protected final boolean isInputEnabled() {
        return inputEnabled;
    }





    /**



     * Nustatoma, ar komponentas yra skirtas duomenÅ³ Ä¯vedimui.
     * 
     * Ä®prastai komponentai nekeiÄia galimybÄs Ä¯vesti ar tik rodyti duomenis.



     * 
     * @param inputEnabled <code>true</code>, jei ji skirta duomeÅ³ Ä¯vedimui,
     *                     <code>false</code> jei ji skirta tik atvaizdavimui.





     */
    protected void setInputEnabled(boolean inputEnabled) {


        this.inputEnabled = inputEnabled;
    }
    
    



    /*
     * Funkcijos skirtos paiÅ¡yti bitÅ³ sekÄ




     */


    
    /**
     * Bendriausia bitÅ³ paiÅ¡ymo funkcija.
     * 
     * @param g paiÅ¡ymui skirtas objetas.
     */





    protected void paintBuffer(Graphics g) {






        if (halfSize) {




            paintBuffer(g, currentFont.getSize(), font.getSize(), 8,
                        viewAllData());
        } else {
            paintBuffer(g, font.getSize(), font.getSize(), 4, viewAllData());
        }
    }
    
    /**




     * GrÄÅ¾inamas paslinkimas ekrane, kad siuntÄjo ir gavÄjo bitÅ³ sekos
     * susiligiuotÅ³
     * 












     * @param width vaizduojamo bito plotis
     * 
     * @return      poslinkio atstumas taÅ¡kais nuo kairio kraÅ¡to
     */


    protected final int getBufferPadding(int width) {
        if (isDestination && syncronizer != null) {












            return syncronizer.getSynchronisation() * width;
        } else {
            return 0;
        }
    }


    
    /**
     * BitÅ³ sekos paiÅ¡ymo funkcija.
     * 



     * Lengvesniam duomenÅ³ palyginimui naudojamas grupavimas, paremntas
     * skirtingu fonu.
     * 












     * @param g         paiÅ¡ymui skirtas objetas





     * @param width     vaizduojamo bito plotis (taÅ¡akis)
     * @param height    vaizduojamo bito aukÅ¡tis (taÅ¡akis)
     * @param step      bitÅ³ kiekis vienoje grupÄje
     * @param data      duomenÅ³ seka
     */






    protected void paintBuffer(Graphics g, int width, int height,
                              int step, final Collection<Boolean> data) {
        int padding = getBufferPadding(width);
        final int length = data.size() - 1;



        int i = length;        
        Color background = backgrounds[0];
        Color foreground = foregrounds[0];







        for (Boolean bit : data) {



            /* Bito vieta ir reikÅ¡mÄ */
            int x = padding + width * i;
            int symbol = bit ? 1 : 0;



            /* Bito spalva */
            if ((length - i) % step == 0) {





                int colorIndex = ((length - i) % (step * 2) == 0) ? 0 : 1;
                background = backgrounds[colorIndex];
                foreground = foregrounds[colorIndex];
            }







            /* PaiÅ¡ymas */
            if (x + width < getWidth()) {
                g.setColor(background);
                g.fillRect(x, 0, width, height);



                if (bit) {
                    g.setColor(foreground);
                    g.drawRect(x - 1, 1, width - 2, height - 2);





                }
                int offsetFromEnd = data.size() - i - 1;




                if (isDestination && !isEqual(offsetFromEnd)) {
                    /* Nesutampatis bitas */
                    paintError(g, x, width, height);
                } else {
                    /* Sutampatnis bitas */
                    g.setColor(Color.BLACK);










                }
                g.drawString(symbol + "", x, height);
            }
            i--;
        }
    }
    
    /**




     * PaÅ¾ymimas (uÅ¾pieÅ¡iamas) klaidingas bitas.
     * 
     * @param g         pieÅ¡imui skirtas objektas


     * @param x         bito pozicija ekrane (taÅ¡kais)




     * @param width     bito plotis ekrane (taÅ¡kais)









     * @param height    bito aukÅ¡tis ekrane (taÅ¡kais)
     */
    protected final void paintError(Graphics g, int x, int width, int height) {


        g.setColor(Color.RED);




        g.drawRect(x, height, width, 2);
    }




    /**
     * IndividualiÅ³ bitÅ³ dydÅ¾io nustatymas.


     * 





     * Funkcija naudojama, kai reikia vaizdÅ¾iai susieti 1:2 bitais.
     * 


     * @param halfSize  ar sumaÅ¾initi bitÅ³ plotÅ¡ per pusÄ




     */
    public void setHalfSize(boolean halfSize) {
        this.halfSize = halfSize;
        if (halfSize) {
            currentFont = font.deriveFont(8.f);
        } else {
            currentFont = font;
        }
        repaint();





    }
    





    /**
     * Sugeneruojama bitÅ³ sekos vaizdavimui pritaikytas skydelis.
     * 
     * @return  sugeneruotas sludelis.
     */
    protected JPanel getStreamPanel() {

        JPanel binary = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {


                paintBuffer(g);
            }
        };
        addExternalViever(binary);



        binary.setFont(font);
        binary.setPreferredSize(new Dimension(100, font.getSize()));
        return binary;
    }


    
    



    /*
     * Funkcijos skirtos perÅ¾iÅ«rÄti bitÅ³ sekÄ naujame lange
     */
    
    /**
     * Skydeliui priskiriamas iÅ¡Å¡okanÄio lango su pilna bitÅ³ seka
     * funkcionalumas.
     * 
     * @param panel skydelis, kuriam reikia priskirti Å¡Ä¯ funkcionalumÄ.
     * 
     * @see #showBinaryTextExternally() 
     */
    protected void addExternalViever(JPanel panel) {






        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    showBinaryTextExternally();
                }
            }
        });
        panel.setToolTipText("Dvigubas bakstelÄjimas visiems vektoriams " +
                              "parodyti");
    }
    
    /**
     * Sukuriamas ir parodomas langas su pilna duomenÅ³ struktÅ«ros bitÅ³ seka.
     */
    private void showBinaryTextExternally() {
        JFrame frame = new JFrame("VektoriÅ³ seka");



        frame.setLayout(new BorderLayout());


        frame.setSize(400, 400);
        frame.setLocation(100, 100);
        JScrollPane pane = new JScrollPane();
        final JTextArea text = new JTextArea();
        updateBinnaryTextExternal(text);
        pane.add(text);

        pane.setViewportView(text);
        JButton button = new JButton("Atnaujinti");
        button.addActionListener(new ActionListener() {



            public void actionPerformed(ActionEvent e) {
                updateBinnaryTextExternal(text);
            }
        });
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.getContentPane().add(button, BorderLayout.SOUTH);
        frame.setVisible(true);
    }



    
    /**
     * Atnaujinamas visÅ³ bitÅ³ sekÄ vaizduojantis langas.
     * 
     * @param text  naujas tekstas.
     */
    private void updateBinnaryTextExternal(JTextArea text) {
        text.setText(allDataToText());
    }
    
    /**

     * BitÅ³ seka paverÄiama Å¾mogui lengviau skaitomu formatu.
     * 
     * @return  bitÅ³ sekos tekstinÄ iÅ¡raiÅ¡ka.
     */
    private String allDataToText() {
        Collection<Boolean> data = viewAllData();
        StringBuilder builder = new StringBuilder(data.size());
        int i = 0;
        for (Boolean bit : data) {
            if (i % 32 == 0 && i != 0) {
                builder.append("\n");
            } else if (i % 16 == 0 && i != 0) {
                builder.append("  ");
            } else if (i % 4 == 0 && i != 0) {
                builder.append(" ");
            }
            if (bit) {
                builder.append("1");
            } else {
                builder.append("0");
            }
            i++;
        }
        return builder.toString();
    }
}
