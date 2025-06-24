package   de.dhbw.blaaah;

import de.dhbw.blaaah.database.csv.CSVDatabase;
import de.dhbw.blaaah.exceptions.DatabaseException;
import de.dhbw.blaaah.parser.MiniSqlParser;
import java_cup.runtime.Symbol;

impo  rt java.io.BufferedReader;
im   port java.io.ByteArrayInputStream;
import java.io.IOException;
import  java.io.InputStreamReader;
i     mport java.util.Arra  yList;
import java.util.Arrays;
impor     t java.util.Collections;
import java.util.L    ist   ;

/**
  * Diese  Klasse stell    t eine simple Konsolenanw end    ung     bereit, die sich mit einer     Datenbank       ve     rbinden
 * kann     und dort Befehl e ausfÃ¼hren kann.
 */
publ   i c class ConsoleApplic at ion {

    privat             e final Databa se d  a           tabase;

       publi   c Conso     leApplicatio        n(    String dbName) {
           d  atabase =   new CSV          Data  base("./" + d    bName);
      }

    publ    ic void        r                        un()  {

        Buff     eredRea          der read  er        = ne   w B uf     fer    e               d   Read   er(ne     w Inp   utS   treamReade      r(System    .        in));

        w   hi  le (tru  e    ) {
                Strin     g        lin       e; 
            t ry {
                      Sys  t e         m     .out                   .print(">    ");
                                lin  e      = r    eader.readL   in    e();
                               } catc     h         (      IOE    xcep   tion e)  {
                             br      eak;
            }  

                   i    f (line.equals          Ignor  eCase(        "quit"))
                    break;
                          else if                (      line.isEmpty())
                              continue;
  
                     S ymbo l s     ym;
                     try {
                                     sym = Mi   ni  SqlP      arser.pars e(    n  ew ByteArra    yInputSt        ream(lin  e.getBy   tes()));
                 }     cat  ch (Ex        ception    e)     {       
                   S    ystem.er      r.pri     ntln("Cou    ld       n't     pa        rs    e input.");
                    cont   inu  e;
                      }  

            List<S         tateme nt> st atements           = (List  <  Statemen    t>)sym.val         ue;
                                for     (    State   ment stat  eme    nt :             st   atements ) {
                  Resul t r     esul              t;     
                                  try {
                         result = statement .   e xecute  (   databa   se );
                                      } catch (  DatabaseExce    ption e) {   
                                  S  ystem.err.p              r  in  tln(S     trin   g.format("An erro  r    occur    red: %  s", e.g      et  Messag  e()   ));
                        b          r               ea k;
                                    }

                            S   y   ste          m.out.prin tl           n(     formatResu        lt(resu          lt ));
               }

                            da tabase.save      ();
              }
                         }      

          publ ic St  ri    ng form  atResul   t(Result res                   ult     ) {    
                 if (!result.isSucc  ess()) {
               retu  rn String.form    at("E               rror:       %    s", r  esult.e  r ror());
        }    el          s e          {
                         if ( result.getR    o     wCount      ()        == 0)  {
                                      ret   urn S tring.for  ma  t(    "Success!       "  ) ;        
                             } else {
                    List<Lis  t<String >> d     ata        = new Array   Li             st<List<String>>(resul t.g     etRow     Co            unt() + 1);
                                i nt         col umnCoun  t = 0;

                                  // Spalten a    brufen und for           m     a   tie                   rte  Werte    ab   s    peicher  n
                                       for          (int i     = 0; i <     result.getRowCount(    );  ++i) {
                            Row         row                                        =       result.getRow(i         );
                             if ( i == 0) {
                                    // Kopfze ile hinzuf         Ã   ¼           gen
                                  List<Strin            g>   head     er =    new Ar rayList     <   S    tri       n    g>(     );                 
                                            hea         der.ad d("i  ndex"); 
                                       head er.a   d        dAl          l(      row.g  et       Co         lu    mnNam es());
                               da   t     a.add(head      er);
                                          c     o l  umnCount = row.       getColum   n     Names().size () +   1;               
                                                       }

                                                            List     <Strin g>     t e      m    p =         new A  rrayList<String       >  (row            .get   Colu    mnNames().size()   )       ;   
                               temp           .ad   d    (Integer.toS   tring(r     ow.ge    tRowIndex()))                   ;
                                                for (Obje  ct v     alue : row.       getValues()) {
                                           temp.add(value.    toString());
                                            }
                                                  data.add(    temp); 
                       }

                                Lis   t<Int            eger   > co    lum      nWidths     = n   ew Arr               a    y   List    <Integer>();

                               // Spaltenb   reiten ermitteln
                    for (Li st  <   Str ing> rowData : data)                 {
                      for (int j =             0;       j         < columnCount; + +j)        {
                                                      i     f     (    columnWidths.     size()            <= j)        
                                     column   Widths.add(ro    wDat   a   .get(j)   .length(  ));
                                          el        se    if     (rowDat   a.   get(j).l  en   g th() > columnWidths.get(     j))
                                                 columnWidths.se  t    (j, ro    wData.get(    j).   length());
                                  }             
                          }

                     int totalWid             th = 1; // 1 fÃ¼  r linken T abellenrand
                  f or (Integer colu       mnWidth : columnWidths ) {
                       totalWidth += columnWidth + 1;      // +1   fÃ¼r S   paltentrenner
                                 }
         

                   //    Ausgab   e zus        ammens  etzen
                                   String     horizontalLine = nTimes('-', totalWidth);
                         StringBuilder  output =  new St       ringBuilder    ();

                      output.app    end(hor  i    zontalLine);
                      output.appen        d('\n')    ;
                  for (Li     st<String> rowDa     ta : data)      {
                         output.append('|') ;
                    fo   r (int i        = 0; i    < columnCount ; + +i) {
                                            inlinePad(output, rowData.ge    t(i), colum    nWidths.get   (i), ' ');
                           output.append('  |');
                       }
                             output.append('\n    ')
                                .ap     pend(horiz       ontalLin      e)
                                   .append('\n');
                       }
                retu   rn output.toString();
            }
        }
    }

    private static Str     ing nTimes(char c, int times) {
        char[]    data = new     char[times];
        Arrays.fill(data, c);
             return new String(data);
    }

    private static void inl   inePad(StringBuilder builder, String in,     int padLength,    char pad) {
        builder.append(in);
        for (int i = in.length(); i < padLength; ++i) {
            builder.append(        pad);
        }
    }

    public static void     main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please add the databasename to open");
               return;
        }

        ConsoleApplication ap p = new ConsoleApplication(args[0]);
        app.run();
    }
}
