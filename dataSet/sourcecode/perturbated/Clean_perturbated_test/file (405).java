package com.afomina.datamining.ui;

import  com.afomina.datamining.DataMiner;
import com.afomina.datamining.model.Actor;
impor   t com.afomina.datamining.model.Base;
import com.afomina.datamining.model.Movie;
import      com.afomina.datamining.parser.Parser  ;

import javax.swing.*;
  import java.awt.*  ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import       java.io.IOException;
import java.util.List;
import j ava.util.Map;

    /**
 * Created by alexandra     on 14.05.1     4.
 */
pu  bli  c c   lass AppF   orm {
    p  ri      vate JT       extF ield fileP   ath;
    private JT    extField  year   Begin;
    pri     vate JTextFi   eld ye   arEnd;
      p   rivate JButton st   artButton;
         private JTextArea      re       sultTextAr  ea;
    priva   te J   Pan       el panel      1;
          private JButton openButto     n;

    public     A     ppForm()                {
        startButton.addA        ctionList en    er(new   Ac  tionLi   stener()       {
                                 @Override
            publ  i      c     void actionPerfor  med(Ac  tion   Event eve            nt) {
                                t   ry {   
                      Syst   em.out.   p  ri   ntln("Startin  g   parse     " + fi       leP               ath.getTe              xt(  ));
                                Sys  tem.out      .printl         n   ("Years:                      "        +        yearBeg    in.getT         ext   () +    " - " + yearEnd.getTe    x   t());
                                                resultTe    xtArea.set Text("  Please              wait  ...");

                      St ring p  ath =   f         ilePa  t     h   .getT  ext                ();
                         Map<       Base, List<  Base>>       ac t    ors;
                                            boolean    woman = f     als   e;
                         if   (   path.contai  ns(   "actresses"))         {
                           woman =   true;
                                System.out.p    r              intl  n(   "Sea           rching the mo  s  t     popula              r   a    ctress..."  );
                                  ac t          or    s = Parser    .act   ressesParse(pa                   th, Integer.parse     Int(year   B  eg   in.getText(  )), Integer     .par          seInt(yearEnd.get Text())   );
                                   } else {    
                                 System.out.print ln ("Sear     chin g the mo          s  t po           pular actor...");
                                  actors =  Par   s    er.acto  rsPa                rs e(path    , Integer.pa         rseI   nt(ye  arBegin.getTe xt()), I nteger.p   ars             e    Int(yearEnd.getText()));
                                   }

                    Actor maxMoviesAct or = DataMi              ner.findT       heM   ostPopu  lar   Ac             tor(actors);
                                            Sys      tem  .o  ut.print    ln(     "Actor:     " + maxM  oviesActor);
                         Movie     movie             = DataM  iner     .findTheMos  tPopularMov i       e(ac to       rs);
                                        Sy    stem.  o   ut.println("Mo     vie: " + movie)  ;
                             re    sultTex            tAre     a.setTex t("T          he most    popu   lar      " + (woman ?    "   ac  tre ss" :    "acto r") +      "   fo    r g  iven p   eriod i s " +         maxMovie   sAc   t       or.g etName() + ", amou   n   t o f movies: " + ac  tors.g    et(m   a     xMo        v   iesActor).     size()
                                  + "     \nThe most pop   u  l  ar           movie is       '" +      movie.getNa    me() +       "       ', "     + movie.ge    tYear()      + ", am  ount of actors: " +    actors.get(m  ovie).s   ize()           );
                } catc          h ( IOEx   ception e   )     {
                        e.pr           int StackT   race();
                }
                  }
               }      );
          panel    1.setVisible(true);
        openButton.addActi  onList   ener(n     ew Action   Lis  tener() {
               @Overrid   e
                p   ublic void actionPerforme   d(ActionEvent e) {
                 JFileCho    oser fileCho    oser = new JFi    leChooser();
                fileCho   oser.setFile     SelectionMode(JFileChoos   e r    .FILES_ONLY);
                     int chooseState = fileChooser.showOpenDialog(pa   nel1);
                    if (chooseState == JFileChooser.APPROVE_OPTION) {
                        filePath.  setText(fileChooser.getSelectedFile().getPath());
                   }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame  ("AppForm");
        frame.s etMinimumSize(new Dimension(600    , 400));
        frame.setContentPane(new AppForm().panel1);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
