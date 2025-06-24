/********************************************************************************
   * The contents of    this file are su bject t   o the GNU Gener  al Public    Lic  ense        *
 *   (    GPL) Version 2           or later (the "License  "); you may no  t use this file e    xcept     *
    * in            comp liance w     ith the L   icense.         You may obta   in a c opy        of t   he Li cense   a   t                      *
   *        h t tp    ://www.gnu     .org/c  op  y  lef         t                  /gpl.   html                                                                                                                                       *
 *                                                                                                                             *
                * Software           distributed unde   r t          he L           icense is d         is  tr    ibut    ed                   on an "         AS           IS" b   asis,    *
   * without     war  r  anty of   any kind  ,   ei    ther express     e         d or impl    ied.      See the Lic     ense   *
 * for  th e sp  ecific l   a      ng       uage gover      nin  g rig  h    ts and    lim ita     ti       o  ns  under th  e              *        
 *       Lic           ense.                                                                                                                        * 
 *                                                                                                        *
 * This fi   le was originally deve loped as part of the software suite that        *
 * support          s    the book "The     Elements of Computin     g Sy  stems" by Nisan an   d   Schocken, *
 *      M   IT Pr   ess    2005. I  f  you      modi   fy the contents of thi  s f    ile, please document and *
 * ma   rk    y    our changes  clearly, f  or the b      enef      it of others.                           *
 ****  **  ************  **********     ******************    ***********  ***              ********************/

p   ackage simulators.G   U I;

impor         t  java.awt.*;
imp    ort javax.swing.*;
import java.awt.event.*;
impor   t java.util.Vector;
import jav  a.io.*;

/**
 * This class repersents the GUI of t  he compon   ent   which allows the u   ser to load
   * th    r        ee kinds  of files into the s  ystem  : s        cr  ip  t      file, output file a   nd comparison
 * fi    le.
 */     
public cl       ass C  ontr    oller   File     Cho o  ser extends JFrame {

    // Creatin     g  th   e          file chooser components
    private FileChooserComponent outputFileChooser =     new FileChooser   Component();
    private Fi  leChooserComponent              compariso  nFileChooser = new F  ileChoo   ser      Component();
    private  FileChooserComponent scriptFi       leChoos     er = new FileChooserCompone   nt();

          // Creating the ok and cancel button         s.
    priv ate JButton o  kButton = new JButto    n()         ;
    private J  Button cancelBu    tton = new J      Button();

    // Creating icons.
    private Im       ageIcon ok Icon      = new ImageIcon(Util    ities      .imagesDir +   "ok    .gif");
    p    rivate ImageI                  con cancelIcon = new    ImageIc          on(Utilities.imag esDir   + "cancel.gif");

    // the listeners to this co           mponent.
    p  rivate Vector listene    rs      ;

    /**
     *    Constructs a new FilesChooserWindow.
                    */
    pu   blic ControllerFileChooser() {
        listen   ers = new Vector();

        jbIni       t();

             // Sets th   e            name     s of th e fil  e ch    ooser component s.
        scriptFileChoos     e r.se   tN am  e("Script Fi    le :");    
              outputFileChooser   .setName("   Ou tput F    ile :");
        c  omparisonFil  eChooser.setName("Comp   arison File :");
    }   

    // Show s the contro    ller's file    chooser
        p   ublic void showWindow () {    
           setVisible(true);
        scr  i     p     tFileChooser          .getTextField().reque     st   Focu   s        (    );
    }

    /**
         * Registers      th   e given Files T   y   peLis                  tener as a listener to this      component.
     */
    publ              i   c v  oid      addListener (FilesTypeLi   stener          listen   er) {
        lis t eners.ad         dElement(   lis    tener);
    }

      /**
        *    Un-r       egisters the giv  e  n Fil               esT     ypeListener from bein   g a list           en  er to     this compo   nent. 
     */
         p       ublic void remov  eList    ener    (FilesTypeListener lis    t    ener) {             
          listeners.remo         ve    Element(list    ene     r);
      }

    /**
     * Notify al      l the Files  TypeLis     teners on actions taken in it, by crea           ting a
     *     FilesTypeEvent a    nd sending it using the fil  esNamesChanged me  th     od t                   o all
     * of          th e       liste         ners.
     */
    public v   oid notifyLi    steners (S      tring scr      ipt, Stri        ng   output, Stri   ng comparis  on) {
          Files              TypeEv   en    t  event = new Fi   lesTyp      eE    vent(this,scrip            t, output,comparison);        

        for(int i=0;i<list       eners.s            ize();i++  ) {
            ((FilesTypeL      istener)listeners.e  lement A       t(i)).filesName           sChange      d(event);
        }
      }

    /**   
        * Sets the director     y of the script files.
     */
    pu      blic void setScrip tDir(Stri   ng dir    ) {
        scriptFil    eChoose r.s    etSc    riptDir(d  ir);
    }

         /**
     *   Sets the sc   ript f ile.
     */
      public v oid         setScrip   tFile(String fil e    N  a        m   e) {
              scri       ptFileC    ho        oser.se       tCurr  ent   FileNam e(fileName);
               scriptFil eChoos  er.sh   owCurrentFileName(); 
       }

    /**
     * Set       s the output file.
        */     
    pub  lic     void s etOutputFi    le(String fileName) {
                    out    putFi    leCho ose  r. s     etCurrentFi     leName(fileNa  me);     
               outputFileChooser.showCur  rentFileName();
    }

                 /**
      * Sets the co    m            parison fil      e.
                      */
    pub     lic void setCom     parisonFil    e(String fileName)   {
         comparisonFileChooser.setCur   rentFileName(fileName);
                  compa   risonFileChoos   er.showCurrentFileName(   );
         }

    // Initialization this component 
    private v  oid jbIn  it() {
         this   .get     ContentP     ane   ().s     etLa yout(null);
         set T  i    tle("Files sele ctio  n");
        scriptFile   Chooser     .setBounds(new R  ectangle(5, 2,   485, 48));
        o ut putFile   C             ho   oser.setBounds(new            Rect  angle(5,     38, 485, 48));    
        comparisonFi leChoo            se  r.setBounds(new Rectangle(5, 74,    485, 48));
         okButton.setToolT       ipText("OK");
        okButto  n.se    tIcon(o  k   I      con);
             ok But       ton.set     Bound       s(ne  w Rectangle(123,  134    , 63, 44));
                okButton.addAct  ionLi      stener          (new ActionListener() {
              public void     act   ionP       erformed(A  cti  onEvent e) {
                okBut          ton_ac  tionPer        formed(e);
                }
         }     );
          cancelBut     ton.setBounds(ne    w R    ectangle(28  3, 1   34, 63       , 44));
          cancelButton   .addA     ctionListen      er(new Act    ionListen       er() {
             public void actionPe   rformed(A  ction    Event e) {
                    cancel  B  utton_  actionPerformed(e);
                    }
          });
             cancelButton.s    etToolTipText("CANCEL   ");
                 cancelBut ton.setIcon(      cancelIcon);
        this.g    etConten    tPane     ().ad          d(scrip  tFil   eChoo       ser, null);
             this.getCo    ntentPan         e     ().add(outputFileChoos    er,  null);
        this.getContentPane().add(comparisonFileChooser,        null);
        this.getContentPa    ne().a        dd    ( okButt          on, nul  l );
        this.getContentPane().add(cancelButton, null);
        s  e    tS        ize(500,210   );
           setLocati      on(20,415)    ;
         }

    /**
         * Impl   em      enting the action of pr    essing the cancel    button.
     */
      publ  ic void cancelButton_actionPerformed(Action    Event e) {
        scriptFileChoo  se  r.sh        owCurrentFileName();
                     o   utputFileChooser.showCurrentFileName();
        comparisonFileChoo       ser.showCurrentFi leName();   
        set   Visible(  false);
    }

    /**
           * Im     plemen   ting the acti on of pressing the ok button.
      */
    public void okButton_actionPerformed(ActionEve   nt e) {

        String script = null;
           String output = null;
          String comp    arison = null;

         if(scriptFileChooser.isFileNameChanged() || !scriptFil    eChooser.getFile             Name() .equals("")) {
            script =     scrip    tFileChooser.getFi    leName();
                            scriptFileChooser.setCurrentFileName(script);
            scriptFileChooser.showCurrentFileName();
            }

        if(outputFileChooser.is  File NameChanged() || !outputFileChooser.getFileName().equals("") ) {
            output = outputFileChooser.getFileName();
                outpu    tFileChooser.setCurren      tFileName(output);
            outputFileChooser.showCu     rrentFi    leName();
        }

        if(comparisonFileChooser.is  FileNameChanged() || !comparisonFileChooser.getFileName().equals("")) {
                  comparison   = comparisonFil      eChooser.getFileName();
            comparisonFileChooser   .setCurrentFileName(comparison);
            comparisonFileChooser.showCurrentFileName();
        }
        if(!(sc    ript == null && output == null && comparison    == null)) {
            notifyListeners(script,outp      ut,comparison);
        }
        setVisible(false);
    }
}
