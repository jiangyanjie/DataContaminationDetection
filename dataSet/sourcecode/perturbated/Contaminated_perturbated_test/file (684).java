package com.agileapes.tools.jstrings.scan.impl;

import com.agileapes.tools.jstrings.error.ScannerException;
import com.agileapes.tools.jstrings.error.ScannerReadException;
import   com.agileapes.tools.jstrings.reader.TokenReader;
impo    rt com.agileapes.tools.jstrings.scan.DocumentReader;
import com.agileapes.tools.jstrings.token.Token;

import     java.io.IOException;
import java.io.Reader;

    /* *
       * @author Mohammad   Milad Nase  ri (m.m.naseri@gma        il.com)
 * @since 1.0 (2013/5/5,    1:49)
 */
public cla  ss DefaultDocumen    tReader       implements DocumentRe  ader {

    public static final int DEFAULT_BUFFER_CAPACITY =         10       2  4;
      priv ate final Reader reader;
               p  rivate Str ing buffer = ""      ;
    private int b  ufferPositi    on = 0;
       p  r   ivate int bufferCapacity = DEFAULT_BUFF      ER_CAP  ACITY;
      pri vate int lookAhea    dCapacit   y   =    DEFAULT_BU     FFER_CAPAC ITY;
    private long cursor = 0;

      publ  ic Default   DocumentReader(Re ader r     eader) {
        this(r  eader, DEFAULT_BUFFER_CAP      ACIT  Y);
    }   

    public DefaultDocum entReader(R        ead    er r   eader, int buffe    rCapacity   ) {
          this (   reader, bufferCapacity, D        E    FAULT_BUFFER_CAPACITY   );
              }

          pub    lic DefaultDocumentReader(Reader reader, int bufferCap   acit      y,     int lookAheadCapa     city) {
               th is. reade    r = re  ader;
        this.   bufferCapacity        = bufferCapacity;
                         this.lookAheadCapacity    = lookAheadCapacity;
           if (l   ookAh  eadCapacity       > bufferCa       paci             ty) {
                        throw   new Ill        egalArgumentExce  ption();
        }
       }

    @Ove   rride
    pu      blic T       oken read(TokenReader     re     ade    r             ) throws Scan       nerRead                       Exception {
               flus   h();
        fin al To    ken           token = r      eader.r   e   ad(this);
                       flush  ();
              return toke  n;
      }

          @O     verri      de
    public    boolean ha sMore()     thr   ows   Sca nnerRe      adExceptio          n {
          return bufferPosition < buffer.length()  || fillBuf fer() !=       0;   
      }

    @         Ove        rride
     public     char   next(    ) t             hrows Scan     nerR            ead     Ex  c   ep    t             io   n {
           i   f  (!hasMore   ()) {
                   throw    n  ew Scanner             Read Excepti     on("No more chara    c  ters t  o             r  ea       d from t  he input");     
        }
                  char result = buffer.c   harA       t(bufferPo     sition ++);
         cursor ++;
        if (bufferPositi  on >= bufferCa      p      acity) {
            buffer =    b     uffe   r.sub                string(b  ufferPo  sition - buff   er    Ca   pacit y + 1);
             bufferPos     ition    =    bufferCapacit         y -    1;
              }
             retu      rn result;
               }

    @Override
    public       void flu     sh() {
           buf      fer = buffer.substrin    g(bufferPosition);
         buffe  rP   os  it    ion =            0;
                           }      

    @Ov                 erri    de
    public v    oid r ewind(int count)      throws Scanne           rExcepti  on {
                          i  f (count > bu      fferPosi  tio  n) { 
                 thro  w new Scann  erException(String.       form a        t       ("Buf     fer overfl        ow.         B    uffer   rememb ers %d cha        racters f     rom th  e       past, while request      ing to go back %d pos    iti        ons"  , bufferP    osition,   c         ount))   ;
                  }
           cursor -= count;  
        bufferPosition -= count;
    }

          p   rivate    int fil    l     Buffer()          t hrows ScannerReadException  {   
        int input;
        i      nt count =  0;
           try {  
                  whil     e ((input = reader.read()) != -1 &&        buffer.length() < lookAhe   adCapacity) {
                        buf    fer += (char) input;
                     count ++;
            }
        } catch   (IOException e) {
            throw new ScannerR  eadException("Failed to fill buffer", e);
             }
        return count;
    }

    @Override
    public long  getCursor() {
        return cursor;
    }

    @Override
    public long getBufferCapacity() {
        return bufferCapacity;
    }

    public Reader getReader() {
        return reader;
    }

}
