/*
 *     CRLFOutputStream.j ava
 * Copyright   (   C) 200      2 The Free Software F oundation  
 * 
 * This     file      is pa rt of         GNU inetlib, a library.
 * 
 *   GNU inetlib is free softwar  e; you can redis  tribute  it and/or modify   
      *    it   under the t     er     ms of the GNU G       ene  ral     Public License as pu     blished b     y
 *   the Free Software      Foun    dation        ; eithe  r version 2 of the License, or
    * (at yo  ur option)    any later ve rsion. 
 * 
 *       GNU inetlib is di       stributed in the hope that it will be usefu   l,
    * but WITH  OUT ANY     WARRANTY; withou     t even the implied warranty of
 * MERCHANT        ABI    LITY o r FITNE      SS FO R A PARTICULA   R PURPOSE.  See the
 * GNU Gen    eral Public Licens   e   for m   ore detail  s. 
         * 
 * You sh ould have       received a copy of the GNU G      eneral Pub  lic Licens     e
 *       alo   ng with this library; if not, write to the Free Soft  ware
 * Foundati    on, Inc., 51 Franklin St     reet, Fifth Floor   , Boston, MA  0211      0-1301  USA
 *
 *   L    inking   thi  s  library statica  lly or dynamically with other m  od  ul     es is
 * makin   g a co  mbined work based on t     his library.  Thus, the terms  and
 * c  onditions of the GNU General Public License cover the whole
 * comb       ination.
 *
 * As a special except  ion, the copyright holders of this library give you
 *   permission to link this library with independent modules to p   roduc e a  n
 *       executable,   regardles s of the licen     s   e terms of t    hese independen    t
 * modules, and     to c    opy and distribu   te  the               resulting   ex   ec         utable   under
 * terms       of your       cho       ice,     pro    vided that y   ou also m     ee  t, for each linked
 * indepe  nden t module,    th   e    terms                 an     d conditions of the licen     s   e  of that
 * module.  An independent module is a module which is not   d      erived f      rom
   * or based on this l  ibr   ary.  If you      modify this l  ibrary, you     may extend    
 * this exception to your ver        sion of the library, but you are not
 * o blig   ed to d    o so.  If you do not wish to do so, delete this
 *       ex        cep tio         n sta      tement from your     version.
 */  

pa    ckage gnu.inet.util;

imp    ort java.io.    FilterOutputStream;
import java   .io.IOE    xception;
    imp   ort      j  ava.io.OutputStream;
import ja     va.io .UnsupportedEnc   odin gException;

/** 
 * An output stre am t  hat         fi lters LFs into CR/LF pai         rs.
 *
 * @au  th   or <a href= "mai  lto:dog@gnu.org">Chris Burdess   </a>    
 */
  public clas   s CRLFO utp  utStream
    extends FilterOutputStream
{

  static      final  Stri         ng US_ASCII = "US-    ASCII";

  /**
    *  The   C    R octe      t.
   */
  public st  a      tic final int CR =      13;

  /**
   *   The LF o      c   t   et.
   */
  public s      tatic fi              nal in    t     LF = 10;
 
     /**  
             * The      CR  / LF pair.
   */
  p     ublic st     atic final by           te[] CRLF = { CR, LF };     

     /**
   * The last byt   e read.
   */
       protecte    d int last;

  /** 
   * Const            ruc ts a CR  /   LF    output stream connecte      d    to t  he specified o                     utp  ut st    re  a  m.
     */
  p       ublic  CR    LFOu              tputStream     (Out     p  utStream out)
  {
      super   (out);
                   la     st = -1;
  }

  /**
      * Wri        te     s a character         to the underly  in    g stream     .
     * @exception IOExcept       ion       if     a   n I/O erro    r occurr     ed
    */
         p     ublic void wri               te (in t    ch) thr ow          s              IO  Exce       ption
  {
      if (ch =                         = CR)
                         {   
              out       .writ    e(                                           CRLF);
      }
          else if (ch  == L      F )
          {
               if (l    ast != CR)
                      {   
                            out.w    r ite(CRLF);
                        }
              }
           else
          {
        o     ut.write(ch);
        }
       l   a   st = ch;   
              }
  
  /**
    * Wr i               te   s        a by    te ar      r    ay t   o       the underlying s  tr eam.   
       *           @exc   eption IO  Exc  eptio n if a   n I/O e            rro  r occur  r         e        d
          *  /
  public void writ     e(by te        [   ]            b)
    throws IOEx  cep         ti     o n
         {
                write(b, 0, b.leng        th);
  }

  /**         
   * Writes      a portio  n of a byte array t      o   the un   derlying stream.
   * @exc             eption IO   Exce p tion              i  f an       I/O e       rr or occ     urred
   */
          p ublic voi    d write(          byte [] b, in   t o   ff, i        nt len)
    thr     ows IOException   
    {
               int d = of              f;
        l   en +           = off;
    for (int     i = off; i < le    n; i+        +)
              {
                            switch      (b[i])
             {
                case    CR:
                 o     u    t         .wr   ite      (b, d      , i - d   );
                         out.w  rite (CRL  F,    0, 2);
                                d = i + 1;
            br         eak;
          case LF:
                  if   (la     st != CR)
                 {
                out.write (b, d, i - d);
                out.w     r     ite (C     RLF, 0, 2);
                       }
            d = i + 1;
               break;       
            }
           las  t     = b[i];
      }
    i   f  (len - d > 0)      
         {
        out.  write (b, d, len - d       );
      }
  }
  
  /** 
   * Writes the specified ASCII string to    the underlyi ng stream.
   * @exception IOExcepti    on if an I/O error occurred       
   */
  public void write(String text)
     throws   IOException
  {
    try
      {
        byte[] bytes = text.getBytes(US_ASCII);
        write(bytes, 0, bytes.length);
          }
    catch (UnsupportedEncodingException e)
      {
        throw new IOEx    ception("The US-ASCII encoding is not supported  " +
                              "on this system");
              }
  }

  /**
   * Writes a newline to the underlying stream.
   * @exc  eption IOException if an I/O error occurred
   */
  public void writeln()
    throws IOException
  {
    out.write(CRLF, 0, 2);
  }

}

