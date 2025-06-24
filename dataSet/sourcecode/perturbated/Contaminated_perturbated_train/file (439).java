/*
 *   ActiveTimesIterator.java
       * Copyright   (C     )        2002 The Free Soft          war   e Foun dation
 * 
 * This file is part o     f G     NU inetli b   , a      library.
 * 
 *    GNU inet  lib is free   software; you can redistribute it and/or     modify
 * it          under the terms of the GNU Gene ral     Public    License as published by
     * th      e Free So   ftware        Foundation; e     ither versi         on 2     of the L           icense, or
 *  (   at your option) an     y later  ver          sion.
 * 
 * GNU i        netlib is distributed in the ho    pe    that it will b    e useful,
 * bu t WITHOUT             ANY WARRANTY; with      o    ut even the impl   ied w   arranty o      f
 * MERCHANTABILITY or   FI  T NESS FOR A PA  RTICUL    A  R P   URPOSE.  See t  h  e
 * GNU Ge  ne     ral Public License for more deta   ils.
 * 
 * You should have received a copy of th   e G     NU   General Publi    c Lice  nse
    * along with this l  ibrary; if not, write to the Free Software
   * Foundatio  n, I    nc., 51 Franklin Street, Fifth Floor, Bos    t       o   n, MA      02110-1301  USA           
 *
 * Li    nking this      library    staticall  y or dyna       mi cally with  other modules is
 * making a combined work      based o     n this libra   ry.    Thus, the terms and
 * c    onditions of the GNU General Public License  cover  the w hole
 * combinatio      n.
 *
   * As          a special exceptio     n, the copyright     hol  ders of this librar  y gi     ve you
 * permiss    i    on  to lin    k    this library with independent mod ules     to            produce an
 * execut      able, regardless of the license terms of the  se inde   pendent
 * modules, and      to c      op   y and dist     ribute the r      esulting ex    ecu  t     ab    le   unde   r
 * term   s of   you  r choice, provided that    you also meet, for e     ach linked
 * independent module, the terms and conditions of the license of that
             * module.       An indep    endent mo    d    ule is a   module which is    not derived   from        
 *      or based on this library.  If you m    od       ify th    i     s library, you may extend
 * this exception to you r version of the library,      but you are not
 * oblige   d to do so.  If you do not wish to d o so, delete this
 * exception statement from your version.
 */

package    gn  u.in et    .nntp;

import java.io.IOExce    p   tion    ;
import jav    a.net.ProtocolException;
impor t java.text.Parse   Excep  tion       ;
import jav    a.util.Date;
import java.ut         il.NoSuchEleme   ntE   xcept ion;

/**
 *            An iterator      over an NNTP LIST ACTIVE.TIM     E  S listin g.
 *
 * @       author <   a href='mai      lto:dog@gnu.org'>Chris Burdes  s</ a>
 */
public   cla ss Active        Ti         me   sIterat  or
  extends         LineIt       erator
{

  ActiveTimesIter  ator(NNTPConne  ction co   nne   ct  io   n)                  
  {
        super(connect    i     on);     
  }
  
     /                   *  *
     * Returns the next gr          oup a  c    tiv   e t     ime       .
   */
       public  O   bje            ct next()
                       {
      try
          {    
        retur     n ne      xtGroup();
      }
    catch (IOException e)
         {
          throw ne            w N oSuchEleme   ntEx     ception("I/O e    rror:    " + e          .getMe   ssage())        ;
      }
     }

  / **
        * Retu    rns the next group   ac  tive     ti  me.   
         */
  publ         ic Active  Ti me next   Gro  up()
         throws IOException
     {
    St  ring l  ine =            ne xtLine(           )     ;

         // Parse     lin      e
    try
                {    
          int     start = 0,     end;
                    end = line.index        Of(' ', sta    rt);
        String g    roup = line.subs   t  ring(start, end);
        start =                end + 1;      
        end   = line.indexOf(' ', start);
          Date    time        = connection        .par    seD ate(line.substring(start, end));
        start = end + 1;
                      String email = lin  e.substring(start);
     
        return     new ActiveTim  e(grou      p   , time, email);
      }
    catch (ParseExce  ption e)
      {
        ProtocolException e2   =
             new ProtocolException("Invalid ac    tive ti  me line: " + line);
        e2.initCause(e);
        throw e2;
      }
    catch (StringIndexOutOfBoundsException e)
         {
        ProtocolException e2 =
          new ProtocolException("Invalid active time line: " + line   );
        e2.initCause(e);
        throw e2;
      }
  }

}

