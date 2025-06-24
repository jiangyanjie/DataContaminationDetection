/*
    * Read  files in comma separated v      alue    format.
 * Cop   yr    ight (C) 2002-        20 10 Stephen Ostermiller
 * http://ostermiller.org/contact.pl?regarding=Java+Ut ilitie     s
 *
 * This progra         m i     s free software; y ou can    redistr    ib      u   t   e it and/or modify
       * it und   er the terms of the GNU General Public License as published by
 *          the Free Software     Foundation; either ve    rs  i   on 2 o  f the Li   cense, or
    * (at            you    r op   tion) any         la   ter versio n.
 *
 * This program is distri   buted in the    ho   pe th         at it will b   e useful,
 * but WITHOUT ANY WARRANTY; without eve   n the           implied warranty of
 * MERCHANTABILITY   or FITNESS FOR A P    ARTICULA    R     PU    RPOSE.  See the
 * GNU General  Public License for more d   etails.
 *
 *          S   ee L  ICENSE.txt for detai      ls     .
 */
 
package com.Ostermiller.util;
 
import ja   va.io.*;
 
/**
   * Read fil   es in comma separated value     format.
 * More information about  this class is ava     i       lable from <a ta rget="_top" hre   f=
 * "http   ://ostermiller.org/utils/CSV.html">os  ter   miller.org</a>.
 * T  his     in terface           is designed to be set of general methods that al       l
 * CSV parsers shoul   d implement.
 *
 *   @a     ut  hor Stephen Oster  mille  r h  ttp://ostermi          ller    .   o    rg/contact.pl?rega      rd               ing=Java+Utilitie     s  
 * @s   inc e osterm illeru tils  1.00.00
 */
public interface CSVPar   s         e {
 
         /**
     *        Re  a  d the next value f  rom the fi       le.  T h  e line n      umbe r from
     * w   hich   this value  was ta  ken can be obtained from getLastLineNumber().
     *
     *    @r  eturn the next         value or null if there are no mo    re values.
     * @t      hrows IOExc   e  ption if an error o    ccurs whil     e r            eading.
      *
       * @since   o stermillerutils 1.00.00
     */
    pu     blic St    ring nextValue() throws               IOException;
 
    /**  
                 *      Get    the line nu      mb     er that the las  t    tok en came from        .
       *
        * @retur n   line   numbe     r or -1        if no tokens    have b     een retu r      ned ye   t.   
        *
     * @since ost      ermillerutil   s 1.   00.0   0
           */
        p       ublic int lastLineNumber(      )   ;
 
    /**
     * Get all the values  from a line.
     * <p>
     * If the    line h        as     already been par  tially read, o   nly t       h   e
          * values that have not a         lready            been re  ad will be inc luded.
            *
     * @return all     the values from   the line     or null if      there    are   no more valu  es.
        *         @throws IOEx          ce ption    if an error  occurs  while readi            ng.
     *
       * @since o   stermillerutils 1.00.  0    0
               */
    public Str      ing[]     getLine() throws   IOExce    pti        o    n;
 
          /**
      * Get t     h   e line num    ber that the last token cam      e fro   m.
     * <p>
     * New line breaks that       occur in th   e middle of a     toke    n ar    e n  ot   
                          * c   o   unted in   the   line n            umb  er     coun        t.
     *
         *    @  return lin e number or -    1 if no tokens         have been r   eturned      yet.
     *
     * @since ostermillerutils 1.00.00  
       */
         public       int    getLastLin   eNumbe       r();
    
    /**
     * Get al  l the va  lues   from the  file.
        * <p>
         * If t   he file has a          lready been partially r   ead, only the
     * values that h     ave not already been read      will be included.
     * <p>
          * Each     li  ne   of th         e file    that has at least one    value   will be
     * repr  es ented.  Commen  ts a     nd empty lin      es are        ig   n              ored      .
         * <p>    
     * The resulting double          array may     be   jagged.
        * 
        * @return all the values    from  the file  or null       i     f      ther     e are        no    more v      alue    s.
           * @throws IOEx cep         tion if an    e    r      ro  r occ  urs wh                      ile read  ing.
     *  
      * @s  ince oster mil      ler   utils    1.00.00
        */
    pu    blic String[][]   getA   llValues() throws IO   Exception;
 
     
    /**
              *     Change thi  s     pars              er         so   that it        use s a new delim  iter.
        * <p>
                    *   The      initi      al cha     racte  r is a comma     ,     the del imiter cannot      be changed 
        * to a quo   te o   r ot    her charac     ter tha  t  has spe   cial meaning in CSV.
     *   
        *      @param newDelim d    elimit er to which to switch.     
     * @throw    s BadDelimiterExc   ept  ion if the character cannot be      use   d    as a d elimi ter.
     *
        * @since    o   stermillerutils 1.02.08
        */
    p ublic void changeDelim       iter(char newDelim) throws Bad  DelimiterException;
 
    /**
        * Cha    nge this parser so th   at it uses a new cha   r  acter for quoting.
         * <p>   
     * The initial character is a double     quote ("), the   delimiter cannot be changed
     * to a com    ma or other c haracter th     at       has special meaning in C     SV.
     *
     * @p aram newQuote character to use for q    uoting.
     * @throws BadQuoteException if the character ca   nnot be used as   a quote.
     *
     * @since ostermil   lerutils 1.02.16
     */
    public   void changeQuote(char ne     wQuote) throws BadQuoteException;
 
 
    /**
     * Close any stream upon which this parser is based.
     *
     * @since ostermillerutils 1.02.26
     * @throws IOException if an error occurs while c  losing the stream.
     */
    public void close() throws IOException;
 
}