/**
   *   A compone   nt   of  a library for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSeS</a>.
 *
 * Th  is library      is free software; you can redistribut          e  it and/or
 *   modify i  t unde   r    the terms of the GNU Less   er General   Publi     c
 * Lice nse as published by t      he Free Software Fo  undation;  either
        * version         2.1 of      the   License, or (at your       opt  i on)     any later ver   sio    n.
 *
     * This library is   d    is   tribute     d in   th  e ho    pe   that it     will be u seful,
          * but WITHOUT    ANY WARRANTY; witho       ut         even the implied warranty of
 *        MER     CHANTABIL     ITY or FITNESS FOR        A PARTICU  LAR    PURPOS    E.  See th     e GNU
 * Lesser General Public License for    more detail   s.
 *
 * You s hould have received a copy of the GNU Lesser General Public
 *     Lic   ense along with this libr  ary;   if not, write    to th     e Free Software
    * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
     */
package uk.ac.leeds.ccg.andyt.generic.io;

import java.io.File;
import java.io.IOExce   ption;
import java.io.RandomAcc   essFile;
import java.io.Serializable;
imp  ort java.math.BigInteger;
import java.util.Random     ;
import java.util.logging.Level  ;
import uk.ac.leeds.ccg.andyt.gener   ic.logging.Abstract         Log;
    import uk.ac.leeds.ccg.andyt.ge neric.core.Ge  n   eric_Erro  rAnd Ex  cep    tionHandler;

/**
       * Abstract       class for        handlin     g  <code>AbstractDataRecords</code>
      * @version 2.0.0
 */
pub lic abstra     ct class   AbstractDataHandler 
                 extends Abs      tractLog
              im    plement s Serializable {

    /**
	 * Se     rializable class version nu  mber for swapping
	 * (seriali               zation     /deseri alization           )
	 */  
//             	pr  iv ate stat        ic fi      n    al l    ong serial    Ve       rs ionUID = 1L ;

        / **
     * Fo  r storing the length of an        <code>AbstractDataRecord</cod         e          > t  hat th i    s
             * Handler handles in me  as    ured in <code>byte</code> units and        s tored a  s    a     
     * <c       ode>long</code>.
         */
    prote   cted long _R   ecordLen     gth;

    /*        *
     *    Re     tu       rns a copy          of <code>_RecordLe ngth</code>.
             * @re         turn  
          */   
    public long ge      t_Rec        ordLe   ngth()            {
               re    tu rn this ._Re  cordLength;
    }
    /*  *
     *     Formatted < cod   e    >File</    code>   fo r
     * <code>      AbstractDataReco   rds</ code>.
          *   /
    pro  t     ecte    d Fi   le _File;
        /**
       * The   workspa  ce di  rect o     ry   .
     *   /
    protect    ed File     _Dire     ctory;

           pub    lic   File get_   Direc               tory() {
                         re             tu   rn _Directory;
    }

        pu   blic   vo            id           in it(
                    Level aLe      vel,
                        File _   Directory) {
                    this._Directory = _Directory;
          in         i   t_Logg   er(
                                aL    eve     l  ,    
                _Di       rectory);
    }
       public voi d init(
               File    _Dire ctory) {
           init( Lev     el. FINE,
                  _Directo      ry);
       }
              /*     *
     * <   co     de>Ra     ndo  mAc    cessFil     e<code  > of <code>_File   </code    > for
     * <code>Ab      stractDataReco  rds     </code>
         */
    protected trans      ient    R a    ndo      mAccessFile        _Ra   ndomAccessFile           ;

         /**            
        *     Set     :
            * <c  ode>this    ._Fil     e =     _Fi     le</code           >
               *  < co   de>this.tRandomAc cessFile = new
       *    RandomAccessFile(_     File,      "r" )</ c    ode>
          * @param    _File    
     *             Formatt       ed <code       >File<    /code> c  o  ntaining
     *              <code>AbstractDataRecord  s</co   de>.
     */        
    protec t  ed        v   o  id l   oad(File _File) {
               _Logger.entering(this    .getCla   ss  ().getCanonic  alNam e(), "load(F      ile)")      ;
              thi    s   ._File    =    _Fi     le;
        if (!_File.e  xists()   ) {    
               try          {
                                _File.creat  eNewFile   ();
                      this.              _RandomAccessFile = new    Random   AccessFile(   this     ._File, "rw");
                       } catch (IOException aIOException     ) {
                  log(aIOE xception.   getLocali     zedMes    s a     g    e()          );
                System.   e x     it(Ge    n e       ric_Er ror  AndEx  ceptionHandler.IO Exception);
               }
        }  else {
            try {
                   this.    _Rando      mAccessFile = new RandomAccessFile(this._Fi  le, "r"      );
               } catch (IOExcepti on aIOExce   ption)  {
                    log(aIOExceptio  n.getLocalizedMessage());
                    System.exit      (Generic_ErrorAndE   xc    eptionHandler.      I   OExcept  ion            );
                   }       
          }
        _Logger.exi        ting(this.g   etC  la  ss().get     Canoni      calName        ()    , "    load(Fi       le)")    ;
                }

                   /*  *
       * @re      turn
     * The nu  mber of <cod e>A  bstractDataRecord  s</code>   i     n
     *     <c       ode>this    .tRandomAccess   File</co  de>
        */
          public long     ge               t   NDataRecords    ( ) {   
          _Lo  gger.entering(this.getClass().getCanonicalName(       ),     "   getNDataRecord    s()");
                   try      {
            BigInte   ger aBigInteger = n    ew    B     igInteger(
                                L      ong.toStri  ng(this._Ra ndomAccessFile  .length()));
            BigInteger   b   BigIn   te     ger = new BigInt      eger(
                           Long.     toStri   ng(this._RecordLe  ngth));
                   return aBigIntege        r.divid   e(bB    igInteger).long        Value();
            } catch  (IOException _IOExc eptio   n) {
            _IOException.printStack    Trace();
        }
              _Log      ger  .ex  itin g(this.getClass().getCanonicalNam e  ( )   , "getNDataRecords()");
        retur      n Long.MIN_VAL  UE;
    }

          /**
               * @return  A  n <code>AbstractD    at    a  Record</ code> for th     e given RecordID
           * @param RecordID
     * The RecordID of the <code>Abstrac   tDataRe   cord</code> to be  return  ed.
     */
      public abs  tract Abst  ractDataRecor       d getDa   taRecord(
            long RecordID);

    /**
     * Prints a random set of   <code>n</code>  <code>Abstrac   tD   ataRecords<    /code>
     * vi      a <code>System.out.println()</code>
     * @param n
     * the number of <code>AbstractDataRecords</code> to print out
     * @param random 
               * the <code>Random</code> used f        or selecting
     * <code>AbstractD    a  taR  ecords</code> to print
     * @throws java.io.IOException
     */
       protected void print(
            int n,
                    Random random)
            t        hrows IOException {
           _Logger.en tering(this.g  e  tClas   s().getCanon     icalName(), "print(int,Random)");
        long nDataRecords = getNDataRecords();
        double double0;
                    Abst  ractDataRecord aDataRecord;
        for (int i0 = 0; i0 < n; i0++) {
               double0 = random.nextDouble() * nDataRecords;
            aDataRecord = getDataRecord((long) double0);
            log(aDataRecord.toString());
        }
        _Logger.exiting(this.getClass().getCanonicalName(), "print(int,Random)");
    }
}