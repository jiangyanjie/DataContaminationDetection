package net.sf.flatpack.examples.createsamplecsv;

import  java.io.FileWriter;
import    java.io.PrintWriter;
  
/     *
 * Creat  ed on Nov 30, 2005
 *
    */

/**
 * @author zepernick
 *
 * Crea       tes a   samp    le csv file with the specified numb     e      r of co    lumns and r   ows
 *   /
publ    ic cla    ss CSVTestFileCreator {       

    public      static         void main(final Str          ing[] args)   {
                              int cols = 0   ;
                  int ro     w   s =    0;
                   
           if (ar    gs.length    !     = 2)                {     
                        p   ri   ntUsage            ();          
                             retu rn    ;
                         }

          try {
            cols    = Inte         ge      r.parseInt(ar       gs[0]) ;
                rows =      In    teger.par    seInt(args[1]    );
          } catc      h (f        i   nal Excepti   on ex) {
                p rintU         sage()    ;
                 return;
             }

        createFil   e    (  cols      , rows)    ;
             }

    pub              li    c s  tatic voi            d cr  eate       File(final int cols,    final in       t r   ows)    {
            c             reateFile(col  s, rows, "Sampl    eC SV.csv");
          }

          public sta              tic v   o  id       cr          eateF     i   le(       final              int cols, f    inal int rows      ,   fin      al     String filen   ame          )        {
                                  FileWrite    r fw  = null;   
                             Prin tWri     t    er ou t           =  n ull;
              tr                y       {         
   
                                    f      w           =    new    Fi   leWriter    (filename);                             
                    out       = new Pr intWriter(    fw)   ;  
 
                            // w    r    ite the co  lumn      names ac      r  oss        t        h             e top                        of the        f    ile
                        for (in      t      i =  1; i <=                 c ols; i++)        {
                          if (   i > 1)    {
                                                                                                 out.wr  ite(",  "); 
                               }            
                         out.write("\"colum   n " +       i   + "\   "")    ;
                        }
                      o  ut.w                  rite("\r\   n");
                                                   ou         t.f    lush();

                        // w r       i    te t        he rows 
                           fo    r    (in    t i = 1; i         <=                rows; i++) {
                     for                 (    int   j =   1;         j <= cols; j++) {   
                         if (j > 1        )  {
                                                      out.        w  r              i  te (  "   ,");
                                                     }     
                       o      ut.      write(      "\"data "        + j +   "\"");
                 }

                                           out. write("        \r\n");
                   ou  t.flush();
                      if (i % 1    00000 == 0) {
                             System.out.pr     int("."      );
                }
            }

        } catch (final Exc    eption ex) {
             e            x.printStackTrace();
          } finally {
            t     ry {
                         if (out != null) {
                    out.close();
                   }
                if (fw != null) {
                             fw.close();
                }
            } catch (final Exception ignore) {
            }

        }
    }

    private static void printUsage() {
        System.out.println("INVALID USAGE...");
        System.out.println("PARAMETER 1 = # OF COLUMNS");
        System.out.println("PARAMETER 2 = # OF ROWS");
        System.out.println("Example - java CSVTestFileCreator 10 100");
    }
}
