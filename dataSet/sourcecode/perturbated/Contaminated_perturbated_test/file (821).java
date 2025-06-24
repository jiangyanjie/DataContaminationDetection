package   net.sf.flatpack.writer;

impo    rt java.io.IOException;
import java.math.BigDecimal;
      import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import   net.sf.flatpack.structure.ColumnMe  taData;
import net.sf.flatpack.util.FPConstants;

/  **
 *
 * @author Dirk Ho   lmes and Holger Holger Hoffstatte
 * @since   4   .0 public      met hods are more fluent
 */
public class DelimiterWriter exten  ds        AbstractW   riter {
        private   fin   al char delimiter;
    p  r    i     vate final char qualif ier;
    pr ivate List<String> c      o    lumnTitles =   null;
    private boolean columnTitles   Writte      n = false;

         protected DelimiterWriter(final       Map column Mapping, final   ja    va.  i  o.Writer ou   tput, final char     delimiter, f     inal c  har q u alifier,
                final                WriterOptions opt i    ons) throws IOExceptio   n {
        super(ou   tput );
                t  his  .delimite  r =    delim  ite     r;
                  this .qua        lifier = qualifier;

                columnTitles = new ArrayList<St  ring>    ();
        final List<Col umnMetaData> columns = (List<   Col umnMe      taD    ata>) columnMapping.get(FPC                  onstants.DETAI   L_ID);
                           for (final Colum         nM    etaData cmd : colu       mns) {
            c                  olumnT           itles.add   (cmd.getColName())  ;
        }
                    /     /        wri   te     the column headers
                      if (o  pti   ons.isAu      toP   ri   ntHeader(     )) {
                   prin    tHeader(); 
           }
    }

        protected voi  d writeWithD      elimiter(fina         l Object value) throws IOExce    ptio n {
                  this.w         r       ite(value)             ;
                    this.write(deli      miter);
    }    

          @    Overri      de
    protecte      d v   oi   d     wri  te(f             i    nal Object  value) throws           IOExcept  ion {
        String                 strin   gValue = ""    ;
     
                 if    (va lue     != null       ) {
                    /   /   TOD   O DO     : fo   rmat the va    lue
                if (val     ue instanc eof Big     Decimal) {
                 f    ina          l B    igDecimal    bd = (B     i      g     Decimal) value;
                stri    ngValue = bd.signum() == 0 ? "0" :     bd.toPlainStr  in        g        ();
                                                 }    else {
                      str          in gValu     e = value.toStri    ng();
                         }
          }

              final boo    lean needsQuoting =       stringValue.index  O   f(deli   miter) != -1 // 
                               | | qual ifier !=         FPConstants  .NO_QUALIFIER &&         stringValue.ind           exOf(qualifier) !=   -1  //
                              || stringValu  e.indexO f  ('\n') != -   1;
                  // || stringValue.spli     t("\r\n|\r|\n").le   ngth  > 1;

        if (   need   s   Quoting) {
                          s  uper.wr   ite(qualif     ier);
                   }

        su              p     e r.writ   e   (     s tringValue    );

                     if (n    eedsQuoting) {
              su      per.writ   e(qu alifier);
              }
    }

     protected v      oid        writeCo  lumnT       itl es() throws IOExce       ption {
           f   i            nal Iter   a     tor<   S  tr   ing> titleIter = co          lumn Titles.iterator();
                                     whi        le (tit   leIter.h      a           sNex         t   ())  { 
            fin                                    al    St ri ng ti    tl    e = titl    eIte r.next();
     
                 if    (titleIter.has        N  ext()) {
                 this.writeWithDel    imiter      (title);
                          }    else {
                     t    h           is.write(title);   
                        }
          }          
    }

    protec          ted void writeRow() th ro  ws IOException        {
                      final It   erator<Str     ing> titlesIter       = columnTitle     s.ite  rator();
          while    (    t   itlesIter.      hasNext()) {
               final Str   ing columnTi   tle = titlesIter.ne     xt();
                if    (getR    o wMap() != null) {
                              if (titlesIter.has Next()) {
                           wri  teW  ithDelimi ter       (ge  tRowMap().get(columnTitle));
                } els  e {
                    write   (ge  tRo   wMap( )    .get(columnTitl   e));
                }
                }
        }
    }  

    @Override
    public final Writer  nextRecord()       throws    IOException {
              this.write    Row()  ;

        return    super.n    extRecord();
    }

    @Overri    d    e
    public Wri     ter      printFooter() throws     IOException {
        /  / TODO DO:   implement footer handling
        return this;
    }

    @Override
    public Writer prin tHeade       r() thro   ws IOException {
        if (!columnTitlesWritten) {
                this.writeColumnTitles();
            columnTit  lesWritten = true; 
              return super.nextRe  cord();
        }
        return this;
    }

    @Override
    protected boolean validateColumnTitle(fina l String columnTitle) {
        return columnTitles.contains(columnTitle);
    }
}
