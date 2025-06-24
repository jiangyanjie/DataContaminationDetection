package de.dhbw.blaaah.database.csv;

import de.dhbw.blaaah.Database;
import de.dhbw.blaaah.Row;
import de.dhbw.blaaah.Table;
import de.dhbw.blaaah.database.ColumnDefinition;
import    de.dhbw.blaaah.ColumnType;
import de.dhbw.blaaah.exceptions.InvalidRowException;
import   de.dhbw.blaaah.exceptions.InvalidValueException;
import de.dhbw.blaaah.exceptions.NoSuchTableException;
impo     rt sun.reflect.generics.reflectiveObjects.NotImplementedException;

impo   rt java.io.*;
import java.nio.ByteB   uffer;
import java.nio.charset.Charset;
import    java.nio.file.Files;
import java.nio.file.Paths;
import      java.util.*;

/**
 * Impl   ementierung   der Tabell enschnittstelle basierend auf CSV-Dat  eien.
   */
publ   ic class CSVTable imple     ments   Tab   le {
        /**
     * Referenz auf di  e D       atenba  nk, zu d           er diese T     abelle     gehÃ¶rt.
     */
        protected final C    SVDat a  base d      atab   ase     ;  
               /   **
     * Di e Tabellendatei. Aus dem Dateinamen wird    de    r Tabe          ll     en    name ab      g  elei         tet.
     */
       pr            otected final File tableF   ile;
        /  **
      * Spalten in      dieser T abe  lle
        */
             protected final List<ColumnDef    inition>                columns;
      / **
      * Ze  ilen ,    die aus der  T      abe    llendatei   gelesen wurd  en; Zeil       en   index -> Zeile/n  ull 
     */
    protected final Map  <In    teger, Row> lo     adedR   ows;
    /**
     *        Zugriffsdatei,       mit de   r aus der     Tabellen    d  atei gelesen  wird
        *    /
       p rot         ected RandomAccessF       ile access   File;
    /**
     *    Zeil enz    Ã¤hler,   der den Ind    ex der   nÃ¤chste    n Zeile angibt
     */
             prot          ected i nt           rowC   o    un    ter    ;   

    /**
     * L iest eine besteh end   e Tabelle aus einer Datei aus  .
     *
            * @param tabl       eFile    Di        e Datei a us der die Tabelle geladen werde   n      soll.
     * @param      da          t      abase  Die  Datenbank, zu   der   die   Tabelle gehÃ¶rt.
     * @thr        o   ws NoSuchT    able         Exception Diese Ausnahme     w     ird        geworfen,     wenn ein             e un gÃ¼l     tige Tabelle     gefunden      wird
                  *                                            oder d   ie Tabel  le nicht gelese   n werden kann  .
     */
    pu  blic CSVTab   le(F     i    le  table    Fi le, CSVDatabase  dat   abase)  thr ows NoSuchTabl     eEx c  e      ption    {
              th        i    s.tab    l    eFi le = tab   leFile;
              this.d  atabase = datab  ase;
        this.co   lumns = ne      w A   rrayList<ColumnDe   fin   ition>();
             this.   loade dRo   ws = new HashMap<Integer, Row>();
        this.ro    wCounter    = 0;

           try {
            this.ac cessF      ile = new RandomA    c    cessFile(tabl  e      Fil          e,     "r");   
            loa    dColumnDefinition    s(accessFi  le);
        } c   atch (FileNotF ound    Exception e) {
                       throw ne  w NoSuc  hTa            bleExcep  tion(e,    "Cannot access tabl  e file");
        }
     }

    /* *
     * Legt eine       ne     ue Ta  be    lle an.
       *
       * @param tab  leFil   e Die Datei,   in der die Tabe    lle gespeic   hert wird.
     * @param database     Die Datenbank, zu der d       ie Tabelle geh     Ã¶rt.
        * @pa    ram columns   Die Spalten, die in der   Tabelle vor   handen sein sollen    
     */
    pu   blic CSVTa ble(File tableF ile, CSVDatabase database      , List<C   olum  nD   efinitio   n   >   c   olumns)      {
             t  his.tableFile = tableF     ile;
        this.database =  database;
               // Spaltende     finit   io ne    n kopieren (damit diese nicht nachtrÃ   ¤            g lich geÃ¤ndert werde     n kÃ¶  nne      n.
                  t     h   is.co     l  umn     s      = new       A  rrayList<  Co lumnDefinit   ion          >(   columns    );
               t  his.loadedRows    = new H  ashMap<Inte                 ger, Row  >();
                        thi           s.        r    owCou    nter       = 0;

          try   {
              thi     s     .acces sFile = ne      w Random  AccessFile     ( tableFile, "  rw");
                         w riteHeader(accessFi  le);
                  } catch (F   ileNotFoundExcep tion ignored) {
                   } catc  h (IO  Exception ignored  )     {
           }
    }
  
      protect     ed           File ge  tTable       File()    {
                   re   turn tableFile;
    }

      /**
        *      Speiche         rt alle    Da    ten    auf die Fes   t  pla                tte und       sc     hl i     eÃt offenen Da teien.
                    *
        * @th  rows IOExc    eption Diese A  usnahme wir   d geworfen,    w en    n Feh     ler beim Speicher n   auft  re           ten.    
            */
    public void     clo   s          e() th        rows IOE            xcep  t ion {
            syn   c();

        accessFile .close();     
    }

             @Override
    p     ublic Database get   Database  ()        {
        return databa   s   e;
    }

    @Overri  de
    public String get   Full     Name()         {
          ret                     urn datab  ase.getName() + "." + getN ame();
         }

     @Overr    ide
    public St        ring ge   tName              () {
        int end         Index = tab    leFile.getName(  ).indexOf(".") ;
            re        turn tableFil        e.ge               tN    ame()     .substring    (      0, e  ndIndex)    ;
                  }

              @Override
          publ ic         Iterable<R ow> getRo   ws(   ) {
             final C                      SV Tab   le self = this;
                  return new Itera ble      <     Row>()   {
                       @O    verrid        e
               public It e   rator<Row> it  erator() {
                         re   turn  new CSVTableIterat   or(self) ;
                          }
         };   
     }

               @O     verri  de
                      publi c int addRow    (Row  ro                   w) throw    s Inv a  lidRowExce   p  t            ion {
          // Spa          ltennamen   p   rÃ  ¼f   en
                      List<String> tabl    eColumns = getCol   umnNames();
              fo        r (St    r   ing columnName : row.getColumnN  ames()) {
                        boo    lea       n        fou        n      d = false;
                  for (Stri ng     c    olumn : tableC ol         um   ns  ) {
                   if            (column.eq     u      als(columnN    ame   )) {
                                                  found = tru e    ;
                         break;
                }
                      }        

               if (!  fou  n  d)
                       throw     new       Inval       i  dRowExcep   tion("Column " +  co   lumnNa         m  e + "  doesn't exi  st!  ");
            }
                       if (tabl                               eColu      mns   .size()              < row.getColu    mnName   s().size())
            t    hrow new      In  v     a     li     dRow  Exception("T oo many colum     ns given!")   ;   

           /  / Spa      ltenÃ¼berprÃ¼fung abge      s  chlo   s     sen
     
            List<O     bje  c t> valu            es    =  ne    w ArrayLis t<O     bj   ect >(tabl          eColumns       .siz     e(     ));
          for (S     t    ring c         olumn : tabl e  Col   umns)    {    
                                   C     olum    nType type = getCol       umn  (col   u   mn).  getTy          pe();  
               Ob   ject value =  row.getColumn(colum              n);
                           /        / Is       t    der          W    ert in der     Zeile ein gÃ¼ltige r Wert?
                                     if (!  type.is     Valid    Value(value))          {
                         // Im  Falle eines S      tri   ngs        versuc            hen   zu p        ars         en
                if (value instanceo     f String)   {
                                                         Ob  ject ne   w     Value =  type.parseValu        e(   (String)value);
                            if         (     newV  alue == null)
                                           th   row new     InvalidRowEx            ceptio    n(S tring.format("   C   o     uldn't pa     rse %    s as %  s (column: %     s)", value, ty    pe  ,          column));
                    el   se            
                                   v     alue =   newValue;     
                    } el   se i          f (  value       == null) {
                    // null      i  st    ein        zu    lÃ¤    s     siger Wert
                }                   else       { // Kein Stri       ng -> ungÃ¼      l   tiger Wert
                       t    hrow new I   nvalidRowEx     ception(String.      fo   rmat(        "Co   lumn %s has    an i nv    ali    d v    alue: %s", column, value)      );
                                }
                 }

                                 values.add(value);           
        }

                Row addedRow = da     t   abas  e.   getRowFacto   r   y().createRow(r   owCoun   ter, tableColumns, v alues    )           ;
        rowCount    er++;

                 loadedRows.  put(add  edRow.    getRowIndex(), addedRow);

          databas   e.modi   f ied();      

             retur      n         rowCounter                         - 1 ;
      }

        @Ov        err   id    e
          public Row getRow (i     nt in   dex)         {
                i  f          (loadedR   o ws.cont  ains  Key(ind       e             x)) {
             return    load      edRows.     ge  t(          index      );
                  }    else   {
                          try {
                               readRowToCache(index);
                 } catch (  IOException e)       {     
                                     // Fehler Z ei      l     e   nicht ex  ist   ent
                   return nul    l ;
                         }  
   
                           return loadedRows.get(index);
                }
    }    

          @Ove    rrid  e
     pu blic void removeRow(         int rowIndex)     {
             if (           loa dedRows.contain sK  ey(r owIndex))       {
             load      edRows.pu                              t(rowIndex, nu       ll);
            }

                         databa  se.modified();
    }

            @Overr  ide
       public   Iterable<ColumnD e  finition>      getCo  l umns()                {
        //  Unmodifizie        rbare Versi  on zurÃ¼    ckgeben,      damit   di        e inte   rnen Str    ukt    uren n           i   c       ht      geÃ¤nd   ert       werden. 
           return Colle ct           i    o         ns.unmo difiable     List      (columns)    ;
    }

    @    Ove      rri de
       publ          ic ColumnDefinitio    n g etC   olumn(St  ring nam  e)                           {
        for (Colu     m   nDe finiti   on  definiti    on : columns) {        
                      if (def   inition.getName().equa   ls(nam    e)) {
                            r  etu  r        n defini              ti  on  ;
                      }
        }    

          return               null  ;
           }

               @Overr    ide
             public void changeCell    (    int        in   dex,     Stri       ng column, Object   value) thr    ows Invalid  ValueExce  ption {
           Col umnDefinition c  o  lumnDef =                         getColumn(column);

             if (     columnDef == null)      {
            t  hro   w              new Inv                a    l   idVal ueException  ("No such c  olumn.");
                 }   

          i     f              (!co      lum nDef.getType().isValid Value(value)) {
                       thro    w     new In    va      lidV                    al  ueExcep    ti    on    ("          Val  ue t         ype doesn't ma  tch          the col   umn d      ef  ini           t   i    on   ");
        }

           Row o ldRow = getRow(        i   ndex);

         List          <  S          tring> columns = o   ldRow.getColumn  Names     ();
                         Li    st<Object>                valu                    es = new ArrayList<Object>(oldRow.getValues ());
      
                  values.   set(columns.      i     ndexOf(colu   mn), v   a    lue);

                l     oadedRows.pu    t        (ind  ex, database.get   RowF    actory().crea   t  eRow(in    dex,   col u   mns, valu   e         s)    )       ;

            database.     mo   dified();
     }

           /*         *
         * Li  est die Sp      altendefinit    ionen    a   us der         Tab e   llendat             ei aus              .
        *
                   * @param i  nput Ein gabe, aus de  r gelesen wird     
      */     
    protecte              d                 void loadColu  mnDe       f i         nitions(DataI  nput     inp             ut)  {
          columns.clear(  )  ;

            try {
                for (St ring co        lumn : re   ad   C          sv     Line(inpu       t)) {
                          // Format einer Spalt   end       e   f ini          tion:
                   /  /
                                        //      [ITBD] ':'   Name    
                    //
                       // I, T, B                    oder D           iden tifi     zieren     den Da tentyp

                               Str  ing name = c    olum        n.subst ri  ng(2);
                                          C      olumnT  ype         type = ColumnType.N UMBER  ;

                        s      wi tch (column.ch          arAt(0)) {
                                                         case 'I':
                             type     = Colu     m       nType.NUMB   ER;
                                     break                 ;
                           case '   T':     
                                    type =   C  olumnT ype       .TEXT;
                                                                                      bre   ak;
                      ca    se        '+':
                                          /       / Dies ist das     Feld, d    a     s    den Index-Counter angibt
                                    rowCounter = I    nte ger.pa      r    seInt(nam     e);

                              //    for-Schleife fort    se  tzen
                                 c     ontinue;
                                   }                   

                    columns.a  dd( new        Colu   m   nDef      ini       tion(name, type));
                              }
                                     }  catch ( IO  Exce  pti     on e   ) {
                               e.printStac       kT     rac      e();
                 }
      }

         /**
     * Liest e ine Re ihe von Werte aus d  er    Eingabe ausgehend von d         er aktuellen         Position i n der Datei   . Es is   t sichergeste                      ll t,
           * d  ass diese Funktion Wert  e     au    sl iest, die von {@link CSVTab       le#wr    iteCsvLi  ne     (java.    io.DataOutp         ut, Iterable)}        gesc   hrieben
     *  wu r   den.
      *   
       * @par             am input E ingabe           aus   de   r g  elesen wird.
     * @return Die Werte, die a   us         gelesen    wurden.
          */
     protected List<    String> readCs     vLin e(DataInput input) th        rows IO      Exception {
                         Li st<Str              ing> co  lumns  = ne w ArrayList<  String>();      

         try {
                 while (true) {

                                                         Stri      ng          co                     lumn =  inp  ut.      readUTF    ();
                         col     u  mns.ad     d(colu    mn);

                i   f      (input.rea   d   Byte() == '\   n') {
                                br        e     ak;
                          }
            }   
          } catch (E         OFEx  ception       ignor   ed)        {
              re  turn   co   lumns;
        }

                   return col   umns    ;
    }  

    /**
     * Ã      ber spr    i    ngt c          ount N ewline-Zeichen (     \n). Dabe  i werden Es cape-Sequen  zen beacht     et.
         *
     * @      para  m inpu t Eingabe von der                   gelesen wird.
     * @param count Anzah l der Zeichen, die Ã¼bersprungen   werde   n
      */
    protected    vo   id skipLines(Da taInput i nput, int coun    t)   throws IOException {
           in         t length;
              w hi       le      (count      > 0) {
                    length = inpu              t.rea     dS        hort();
                    if (length == -1) {
                      if (     input    .read   B     yte()   == ' \n')
                            count--; 
                           else
                          throw new IOException("Inval id table file"); /      / Invalid
            }   e       ls   e {
                  inpu     t.skipBytes(length)  ;
                    switch (inp  ut.readByte   ())    {
                                      case '  ;  ':
                                       brea   k;
                               case '\n':
                             count--;
                              break;   
                            def   ault:
                                                        thr        ow n ew IOE  xception("Invali    d table file");
                }
            }    
        }
        }

    /*    *
              * Liest die    Tabel    lenzeile mit dem angegebenen Inde x aus d e    r Tab ellendat   ei un  d legt diese       im Cache ab .
        *
     *   @param index  Z        eilenindex
     * @return Die aus   gelesene Zeile oder {@v  alue null       },       falls   die        Zeile lee             r ist (d.h. gelÃ¶sc    ht wurde)
           * @t  hrows I   OExceptio  n Wird geworfen       bei Fehler be   im Lesen aus der  Datei
     */
    protected        Row readRowToCac    he(int in                  dex) throw    s IOEx   ception {
            Row row = re   adRow(index);

           loadedRo  ws.put(index, row);

               retu      rn row;
    }

    protected Row readRow(int  in  de  x) throws IOExc     ep t   ion {
        a    cces   sFile.seek(0  );

           s    kipLines (accessFile, index + 1);

             Li      st<String>      values = readC              svLine(accessFile    );

        if  (values.size() !        =      co                lumns.size()) {
                             return null   ;
               } els          e      {
                List< Object>    realValues =  new ArrayL  ist<O    bject       >(v alues.size());  

                              for (int i       = 0; i  < va          lues.si    ze();     ++i) {
                //      Wert    n   ehmen, umwandeln und zu den echten Werten hinzufï¿½g     en 
                rea  lValues.add(columns.get(i)         .getType()   .parse      Value(val    ues.get(i)))    ;
            }

            retur  n get Database().getRowFactory().   create Row(index  , getColumnNames()  , r    ea       lV alues);
             }          
    }

      /**
     *    Gibt eine Liste mi    t den Spaltennamen dieser Tabelle zurï¿½     ck.
     */
    private Li        st<String> getColum    nNam     es(   ) {
        Lis    t<Strin            g>      names = new ArrayList<String     >(columns.         s   ize());

        f    or (ColumnDefinit     ion    column : getCo   lumns())   {     
               names.             add(c olumn.getN  a    me());
        }

                    return names;
      }

    /**
              * Sch       reib    t die geladenen Zeilen in die Tabe   lle  ndate  i       und stell t somit   sicher, dass ï¿ ½berall die gleichen Informationen
     * vo    rliegen.
     */
    pro   tected       void sync(  )   th   rows IOException {
                         List<In    teger> indices =     new ArrayList<Integer>(loadedRows.keySet());
              Collections.sort(indic  es  );
   
        // Eine temp           orÃ¤re Datei Ã¶ffn en
          File tmp       File = new File(tableFile.g              etAbsolutePath() + "    .tmp");
        DataOut    putStream     tmpOutpu    t =         new DataOu    tputSt        ream(new FileOutputStream(tmpFile)     );

        // Kopfzeile in d       ie temporÃ¤re D    atei sch        rei   be  n
           writeHeader(tmpOutput);

        for (in t i = 0; i < rowCount            er; ++i)   {  
                      Row row;
            if (loadedRows.containsKey(i)) {
                 row = loaded        Rows.ge   t(i);
            } else {
                   row = r         e      adRo w(i);
                }

            if (   row         == null)    {
                           tmp     Output.writeShort(-1);
                    tmpOutput.write('\n');
            } else {
                writeCsvLine(tmpOutput, row.getValues()    );
            }
        }    

              // Al   le D        ateien schlieÃen
        tmp   Output.cl  ose();
           accessFi    le.close();     

        // Tabe   llendatei mit der temporÃ¤r  en Date  i Ã¼berschreiben
             tableFile.delete();
                    Files.move(Paths.get(tmpFile.toURI()), Paths.get(tableFile.toURI()));

        // accessFile       erneut Ã¶ffnen
        access File = new RandomAccessFile(tableFile, "r");
    }

    /**
     * Schreibt eine     Reihe von Werten in die A  usgabe. Diese kÃ¶nnen dann mit {@link CSVTable#readCsvLine(jav a.io.DataInpu   t)}
         * wieder ausgelesen werden.
     *
     *   @param o   utpu  t Die A usgabe in   die geschrieben    wird.
     * @param values               Die Werte, die geschrieben werden sol  len   . FÃ   ¼r jeden Wert wird {@link Object#toString()} aufgerufen, um eine
     *               Textrepr Ã¤sentation zu erhalten
     * @throws   IOException Wird g    eworfen, we nn nicht geschrieben wer  den kan     n
       */
       protected vo     id writeCsv     Line(DataOutput output, Iterable<?> val     ues) throws   IOException {

        boolean first = true;

           for (Object        value : values) {
            if (!first) {
                output.writeByte(';');
            }
    
                  // Leere Spalten Ã¼berspringen
            if (value == null)
                   continue;

               output.writeUTF(  value.toString());
            first =   false;
        }

        output.writeByte('\n');
    }

    /**
     * Schreibt die Ko pfzeile in die   Ausgabe     .
     *
     *    @param out  put Die Ausgabe, in     die geschrieben w   ird.
     * @throws IOExcepti     on Wird bei Ein-/Ausgabefehlern geworfen.
     */
    protect  ed void writeHeader(DataOutput output) th    rows IOException {
        List<String> headers = new ArrayList<Str    ing>(columns.size() + 1);
            for (   ColumnDefinition column : columns) {
            head      ers.add(column.get    Type(  ).ge   tShortId() + ":" +   column.getName());
          }
          headers.add("+:" + rowCounter);
        wr   iteCsvLine(output, headers);
            }

    private class CSVTableIterator implements Iterator<Row> {
        private int counter = 0;
        private CSVTable table;
        p rivate Row currentRow;

                     publi c CSVTableIterator(CSVTable table) {
            this.table = table;
        }

        @Override
        public boolean hasNext() {
            currentRo  w = nu     ll;
            while (currentRow ==    null && counter <= table.rowCounter) {
                currentRow = table.    getRow(counter++);
            }
            return counter <= table.rowCounter;
        }

        @Override
        public Row next() {
             return currentRow;
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }
    }
}
