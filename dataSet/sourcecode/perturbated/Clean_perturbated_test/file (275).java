/*
 * Copyright  (c) 1998, 1999   , Oracl  e and/or its   affiliates. All r   ights reserve d.
 * ORACLE PROPRIETARY/CONFI   DENTIA   L. Use is su   bject to license t     e     rm     s.
 *     
             *
            *
   *   
 *
      *
 *    
 *
 *
 *
      *
 *
    *
 *
 *
 *
 *
 *
 *
 *
 */

pac   kage or     g.omg.          CORB        A;

/** Defines the      methods used to write primitive data types    to output s       treams
* for marsha    lling c ust         om value types.  This interface is used by user
* written custom marshalling code for custom value types.
* @see org   .omg.COR  BA.DataInputStream
*     @see org.omg.CORBA.Cust omMarsha     l
*/
pub    lic   i    nte   rfac  e DataOutputStream extends org.omg.CORBA.por     table.ValueBas  e
  {
          /**
    * Wr ites        the Any value to the   ou     tput s   tre am.
           * @par  am va         lue The value to be     writ te    n.
     */
    void write_any (org.omg.COR    BA .A    ny value)   ;

     /**
    * Write    s the       boolean v          alue to     the output st    ream.
    * @param value The      valu    e         to      be wri      tten.
           */
         void wr ite_boo   l  ean   (boolean v alue);   

       /**   
    * Write  s the IDL character value     to th    e output strea m.
       * @param value Th        e value t   o be wri             tten   .
    */
    void      wr   ite_cha    r     (cha  r value);

    /  **  
      *     Writ   es the IDL wide ch      ar acter v  alue to th  e   output stream.
    * @p aram val   ue Th  e        value         to be writte        n.
               */
    void       w   rite_wch   ar (     char   va    lue  );

       /**
    * Write s t      he IDL octe    t v alue (repres         ented as a Java      byte) to the      output stream.
                   * @   param value The value to          be w   ritten.
    */  
       voi d write_   o     ctet (byte     v          alue);

         /**
    *     W  rites th e IDL sh   ort value to the       out  p  ut strea m  .
    * @param val     ue The value to     be     written.
    */
    void write_short ( short    v           al  ue)  ;

         /  *  *
    *     Writes the          IDL unsig      ned s   hor    t value          (rep          r     esen     ted as a Java short
    * value   ) to     the output stream.
         * @par  am val      ue The v  al     ue to be      written.
    */
          void write_ushort (sh   ort value);

    /**
    * W r   ites the IDL long va l    u        e (r     epresented as a Java int) to the output stream.
    * @pa  ram val  ue The valu   e    t   o          be writt  e  n.
    */
         void wri   te_lon g (int va    lue);

        /** 
      *   Wri        tes t he IDL     u        nsi   gned                    l     ong valu     e (              r      epresent  ed as a Java int) to the output       stream.
     * @param value The val   ue to be wri    tten.
    */
    void wr     ite_ulo ng (int value);

    /**
    * Wr ites the IDL lon      g long value    ( rep  resent  e     d a  s a J    ava         l  ong   )    to t    he output stream.
    * @pa        ram     value    Th e val    u     e to       be     written.
        */
         void       wr i        te_longlong (l  ong value);

    /*      *
    * W   ri  tes the I  DL uns                 igned lo n    g lo    ng value (represented    as a Jav   a long)
    * to the               output st     ream.
         * @para  m   value The value     t  o be written.
    *      /
      vo     i      d wr  ite_ulong   lon   g (lo    ng value);

    /*    *
    *    Writes the  IDL flo    at valu              e to the output str ea  m.
    * @param value     The value to be written.
    */
    void write_float (floa               t value);  

      /**
    * Writes  the       IDL d    ouble valu     e to the output s     tream.
    * @   param val    ue The valu     e t  o       be     wr        itten.  
      */
    void write_do        u  ble (double value);

     // write_longdo  uble     not supp   orted    by IDL/Jav  a mapp   i    ng

    /  **
          * Writ  es th  e IDL string     value to the outp   ut stream.
    * @    param value The value to be     wri      tten.
    */
    voi   d w     rite_     string (Strin   g v         a      lue); 

    /*  *
    *           Writes the IDL       w      ide string va   lue (repr esent ed as     a Ja     va String)    to the o  utp      ut st    ream.
    * @param va   lu    e Th e v    alu         e      t o     be written.
    */
    voi    d wri  te_              ws       tring (String value  );

    /**
           *       Writes the IDL  CORBA::Object val        ue to the outp  ut stream  .
    *  @param val    ue The value to be     written    .
          */
       void write   _Object (org.  omg.CORBA.Obj ect value);
   
    /**
       * Writes t       he   I       DL Abstract interface type t o        the       output stream.
            * @param    value The  value        to be written.
        */
    vo        id write_Abstract   (java  .lang.Object value);

    /**
     * Writes the IDL value type value to the output str  eam.
    * @param va   lue The value     to be wri     tten.
       */
      void write_Value (java       .io.Serializable value);

    /**  
      * W              rites the type code to the output stream.
       * @   param valu    e The value to be w ritten.
            */
      void write_TypeCode (org.omg  .C      OR  BA.TypeCode val  ue);

      /**  
    * Write  s th  e array of IDL Anys from of      fset for le      ngth elements to      the
    *       outpu  t stre    am         .
    * @param seq The array to b     e wri tt  en  .    
         * @par      am o   ffset Th     e index into   seq of the f ir st      element to write to the
       * out  put str  eam.
    * @p    aram length The number    of element                   s  to write t    o the outp  ut      stream.
              */
    void write_      a     ny_array (o    r  g.omg.      C      OR    B      A.Any[       ] seq, in        t offse    t, int leng    th);

        /**
      * Writes the array of      IDL booleans from offset   for length eleme  nts to the
      * output    st   ream.
    * @par am       seq The array t    o   b      e written  .
    * @p    ara   m   offse   t       The     index in  to seq of the first element      to write  to the
        * outp ut s      tream.
         * @pa    ram l   ength T       he number of ele   ments   t     o write to the output strea m.
       */
       void write_boole  an_arr   ay (   boolean[]  seq,       int offset , i     nt length);

    /**
    * Writes       the        array of IDL character     s f  rom offs et for l     ength ele      me      nts to the  
     * outpu           t stream.
    * @param seq The    array        t   o be wri     tten.
    * @p    ar  am o       ff     se   t              The     index  into seq of the fir    st element to    write   to the
    * output stream.
    * @param    leng      th The nu mber of      elements t         o   w   rite t   o        the output strea       m. 
           */
    voi d wr  ite_char_array (char[] seq, int offse t ,         int length);

    /**
    * Writes th  e array of  IDL wide c hara    cte   rs from offset    for    length elements       to       the
                 * ou  tput stream.
    * @p   aram seq Th  e a         rray to be          written.
    * @p  a  ram   offset T   he index into seq of              the first    element to     write to the
    *   output stream.        
              * @pa   ram length T   he n umber of ele         ments to write to             the outpu  t stream.
    */
    v     oid write_wchar_array (char[     ] s  eq, int        offset, int length);

    /**
       * Writes the array of IDL octe   ts from o  ffset for   length elements to    the
    * out   put strea   m.
              * @pa   ram seq The array to be     writ     ten.
    * @   pa  ram offs   et The index int   o     se      q of t      he first element t     o writ     e to the
    *        ou   tput str      eam.
      * @param length  The n   umber of elements t    o wr  ite to t    he   output stream.
    */
        void w  rite_octet_array (byte [] seq,    in               t    of   fset, int    length);

    /       **
    * Writes the ar     ray   of IDL      shorts from     offset f        or le   ngth element   s     to th   e  
    * outpu   t str   eam.
       *  @param seq The     array         to    be            w   ritten  .
    * @param off          set The index   into        seq of th            e first elemen  t      to write to the
    * output stre       am.   
    * @par am    leng   th The      num ber of   elements to write to the ou   tput stream.
    */
    void writ e_short_array (short[] seq, int offset, int length);

                /**
           * Writes      the array   of      IDL     unsigned shorts     (represen  te    d as Java shorts)
    * fro    m offs    e     t for              l engt        h el        ements to          th  e output stream  .
    * @param seq The array to be writt    en   .
    *   @param offse   t The ind  ex into seq of the firs  t ele    ment to write  to t  he
    * output    stream.
    *    @param length T       he number of     elements to write to the output stream.
      */
    void                            write      _ushort_array   (short[] seq, int offset, int length);

    /  **
      * W     r   ites the    array of IDL longs from off  set for length elements       t            o the
        * out     put stream.
     * @param seq The array t     o be written.
             * @par am offset     The index  i nto seq of th    e f irst e    lement to write to the
            * output stream.
       * @para     m   length The   number  of elemen        ts     to w    rite to the output          stream.
               */
    void w  rite_long_array (int[] se  q, int offset, int l   ength    );

         /**
    *      Wr  ites the array of IDL    unsigned longs   (       represented as Java int  s)
    * from offset for  length ele        ments to the output stream.  
    * @par       am seq The array t     o be written.
            * @param o ff     set The      index into seq      of the first el   emen     t to write to the
    * output    stream.
    * @param length The number o   f elements       to write t   o the output stream.
             */
    void write_ulong_a  rray (int[] seq, int offset, int l      ength);

    /**
    *        Writes the array of IDL unsigned long longs (represented as Java longs)
    * from offset for length     elements to the ou tput stream.
    * @param seq The array to    be written.
      * @param offset The index into seq of   the first element to write to the
    * output stream.
    * @param le      ngth The numb  er of e lements to  wr      ite to the output stream.
    */
    void write        _ulonglong_array (long[] seq, int offs  et, int le        ngth);

    /**
    * Wr   ites the    array of IDL long longs from offset for length elements to the
    * output stream.
    * @param   seq The array to be written.
    * @param offset The index into seq of the first element to write to the
    * output stream.
    * @p  aram length Th    e number of elements to write to the     output stream.
    */
    void write   _longlong_array (   long[] seq, int offset, int length);

    /**
      * Wri  tes the array of IDL floats from offset for length ele        ments to the
      *     output stream.
    * @param seq The array to be written.
       * @param offset The index into seq of the first element to write to the
    * output stream.
    * @param length The number of elements to write to the output stream.
    */
    void write_float_array (fl    oat[] seq, int offset,  int length);

    /**
    * Writes  the array of IDL doubles from offset for length elements t   o the
    * output stream.
    * @param seq The array to be written.
    * @param offset The index into s     eq of the first element to write to the
    * output stream.
    * @param length The number of elements to write to the output stream.
    */
    void write_double_array (double[] seq, int offset, int length);
} // interface DataOutputStream
