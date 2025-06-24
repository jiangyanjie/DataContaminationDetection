/**
      * Copy        right (c)   200    4-2005, Regents of the University of Califo      rnia
 * All        rights reserved.
   *
      *             Redis tribution a      nd us e in source and bina   ry for   ms, with or withou t
 *      modification, are permitted provided t hat the following co     nditions
      * are          met:
 *
 * Redistributions of source code must     retain the above    copyright       no   tice     ,
 * this list of conditions and the following  disclaimer.
 *       
 * Redi stributions i  n binary f orm must re  pro  duce th      e above copyrig          ht
 * notice, th   is list of conditions and the followi       ng discl   aimer  in the
 * documentation and/or other     material    s provid    ed with th e          distri    b       ution.
 *
 *   Neither th  e n ame of the Un iversity of California       ,       Los An geles nor  the
 * names of i      ts contributo             rs    may be used to endorse o    r prom  ote produc  ts
 * d        erived from this software wit   hout sp    ecific prior wri  tten permissi  on.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS  AND CONTRI  BUTO          RS
 * "AS I    S" AND ANY EXP    RE SS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMIT ED     TO, THE IMPLIED WARRANTIES           OF MERC  HANTABILITY AN   D FITN       ESS FOR
 * A PARTICULAR PURP  OSE A   RE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *        OWNER OR C  ONTRIBUTORS BE LIABLE FOR A  NY D  IRE     C T, INDIRECT, INCIDENTAL,
 * SPECIA L, EXE    MPLARY,     OR CONSE  QUENTIAL      D  AMAGES (INCLUDING, BUT NOT
 * L     IMITED     TO, P   ROCUREMENT OF SUBSTITUT          E G OODS O         R SERVICES; LO      SS OF USE,
  * D  ATA, OR PROFIT      S; OR BUSINESS    INTERRUPTION) HOWEVER CAUS          ED AND      ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRI    CT LI ABILITY, OR     T ORT
 * (IN CLUD           ING NEGLIGENCE OR    OTH  ERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF       THIS SOFTWARE, EVE   N IF ADVISED OF      THE POSSIBILITY OF    SUCH      D     AMAGE.
 */

package av     rora.gui;
 
import java     .io.OutputStre     am;

/**
 *         Th     is      class ac      ts a  s   a bridge t   hat r     emaps  
 * console   outp    ut   to the debug window of     t    he GUI.
 * The    class is o n the ch  opping bloc   k - B   en was going
 * to     chan    ge it soon because    this isn't a  v ery efficient way 
 * of doing things
 *
 * @author UCLA     Compilers Group
 */  
public cl     ass Debug  Stream extends OutputStream   {
    AvroraGui theGui;

    public DebugStream(AvroraGui ptheGui) {
            theGui = ptheGui;
    }

    //actually writes a byte to     our stream
    public void write(int b) {
        theGui.debugAppend(Character.toString((char) b));
    }
}
