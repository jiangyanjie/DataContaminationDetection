/*
 *        To    change t   his template,   choose To ols | Temp lates
 * and open the t   emplate   in the  editor.
 */
package oms3.dsl;    

import java.awt.Desktop;
import java.awt.Image;
import  jav  a.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
impor  t java.util.Li       st;
impo rt java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIMana  ger;

import ngmf.ui.PEditor;
import ngmf.util.OutputStragegy;
import   oms3.ComponentAcces      s;
import oms3.   ComponentException   ;
i  mport oms3.io.CSProp  erties;
import oms3.i  o.DataIO;

/**
 *
 * @au thor od
 */
abst         r act public cl  ass AbstractSimulation implements         Buildable {

    prote   ct    ed static final Logger lo      g =           Logger.ge   tLogger("oms3.sim");
        Mod        el      model;
    String name;
       Resource re s = new Reso              urce();
    Ou     tputD     e    scrip    tor    outpu   t = ne   w O   utputDescr  i    pto   r()     ;
    List<Output> out = new ArrayL     ist<Out    pu  t>(    );
  // Chart   anal          y   sis;
    //
             Exec        buil    d;

    p     ublic void       setName( String name ) {    
                 this   .n        ame = name       ;    
      }

    protected String getName() {
                    return   name =     = nul    l ? getCla    ss().getSimpleName  ()   : n            a   me;      
                }

             public Mod        el g   etMo    de   l()        {
            return      model;
      }  

       protected Out       putDes  cripto  r ge        tO            utput() {
             ret    urn output;
          }

             pro  te       cted          List<Outp    ut> getOut(     )     {
        r  et    urn out;
    }
    
       @Override
           pub lic Bui   ld  abl    e create(Ob    ject name, Object value) {
          if     (name.  equa         ls("mod   el")) {
                       if (   model   != null                ) {
                                     thr     ow          new    Compon   entExcep   tion("O n  ly o    ne 'mo   del' element     allowed.")    ;
                              }
              model  = n      ew Model();
                model.setRes(res);
                       r   eturn model ;     
        } els     e if  (name.equal   s(   "resource")  )             {
                r es.       addRe source(value);
                    return LEAF;
                        } else if (name          .equals("        output          "))     {
                 Output    e       = new      Output();
                   out.add(e   );
                       retu    rn e            ;
      // } el      se if (na   me       .equals  ("ana  lysis"     )   ) {
        /      /     r             etu    rn analysis = new Chart(  );
        } else i f   (n       a        me.equals("output      strategy")) {
               return outpu   t;
        } else if (name.equals(     "build"))    {
            File  buildFi      l          e = ne      w File     (System. ge      tProperty("oms.pr  j") + File.separ  atorChar + "build.xml");
                                  if (!build   File.exists ()) {
                      throw    new Comp  onen      tExc    e       pt         ion(   "No      build file foun  d  : " + buildFil   e);     
                    }
                build = new Exe c        (Exec.Type.AN     T);
                bu          ild.se         tFile(buildFi         le.ge   tAbsol     utePath());
                        retur  n         bu ild;
              }
             throw new Com    ponentExcepti                 on("U   nknown              elemen     t  '" + name.toString() + "'");
    }

    static void n  ative            LF() {
                 try {
                 UI Manager.setLookAndFeel(UIManager.getSyst emLookAndFeelClassNa  me());   
                     String osNam    e              =     System .getProper ty("os   .name");
                             if  ((o    sN ame != n      u          ll)    &&  osNa        me.to        Lower       C  ase(). starts     With(        "lin")) {
                       UIMa     nager  .setLookAndFeel("com.sun.ja     va.swing.pl  af.gtk.GTKL  ookAndFeel");
                  }
        } catc  h      (Exc     epti    on E) {   
             log         .warni  ng("              Can  not        set native L  &F.");
                  }
    }

    pub    l  ic Ob ject      r un() throws Exceptio            n {
         throw new UnsupportedOperationExceptio  n("N       ot supp    orted.");
    }

     // publ     ic voi            d graph() throws Exception {
  // if     (an          alysi    s != null) {
  // Ou  tputStr   age    gy st =         g          etO    u  tput().getOutputStrategy(  getName());
  // nati veLF();
  //             analysis  .   run(      st, getName());
          // } else     {
  // th    r          o  w new Comp      onent        E      xception("         No analysis element d    efi     ned  .");
  // }
  //  }

    public voi   d d                 oc(    ) thro     ws Excep tio   n {
                  throw new Un    suppor           tedOperati    onException("Not s   upported.");     
    }

         p   ubl        ic void dig   () throws     Exce        ption {
            throw ne    w Unsupp  ort          edO   pe      ra    tio  nExceptio           n  ("Not support   e  d.");
    }

           /** E  d  i           t paramet  er   file content. Edit     o  nl              y th e 
     * 
     *     @th                 rows Exc eption
      */
    pu   bli   c v         oid edit() throws E    xcepti     on {
                        Li    s t<File> l = new    Arr  ay        List<File>();
                for (Par  ams p                : model.getParams       ()        )      {
                               if (p.getFile()      !=     nul    l) {
                                       l.add(new File(p.             g e      tFile())) ;
                   }      
         }
                  if           (l.isE mpt   y    ()) {
                    throw new     C           omponentException(  "   N     o parame  ter files to edit.");
                     }

            // initial Parameter    set generati                 on
        if (l  .size(           ) == 1    )    {
                              File f = l    .get(       0);
                      if (!f.exists()) {
                // create t         he de       fault p   arameter  a nd fill i  t.
                            CSProperties p = Da     taIO.properties(Com  pon             e ntAccess.crea  teDefault(model              .getComp  onent()));
                DataIO.save(p, f, "Parameter");
            }
        }

        //
          native      LF();
        PEditor p = new PEdito  r(l);
                // the fr ame
        Image im = Toolki  t.getDefaultToolkit().getImage(
                getClass().getResource("/ngmf/ui/table.png     "));
        JFrame     f  = new JFrame();
        f.setDefaultCloseOp  eration(JFrame.EXIT_ON_C  LOSE);
              f.get    ContentPane().add(p);
           f.setI    conI  mag e(    im);
                       f.setTitle("Pa        r  ameter "     + getName());         
            f.setSize(800, 600);
        f.setLocation(500, 200);
        f.setVisible(true    );
        f.toFront();
        System.out   .flush();
    }
     
       public void bui  ld() throws Exception     {
        if (build    !=   null) {
               build.run();
                } el    se {
             System.err.pr    in    tln("  No build file to run.");
        }
     }

    public void output() throws Exception {
        if (Desktop.isDesktopSupp    orted()) {
            Desktop desktop = Desktop.getDesktop();
                  if (desktop.isSupported(Desktop.Action.OPEN)) {
                OutputStragegy st = output.getOutputStrategy(getName());
                File l    astFold    e   r = st.lastOutputFolder();
                if (lastFolder.exists()) {
                    desktop.open(la   stFolder);
                } else {
                    log.warning("Folder does not exist (yet): " + lastFolder);
                }
            }
        }
    }
}
