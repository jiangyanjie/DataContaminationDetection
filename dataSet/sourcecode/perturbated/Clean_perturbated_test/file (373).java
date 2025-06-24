/*
     * Copyright      2009, M       ahmood Ali.
 * All           r   ights reserved  .   
 *
 * Redi  stribution  and u         se i  n source and   binary      forms, with or without
 * modifica tion  , are pe   rmitt  ed     provided that the fo llowing conditions are
 * met:
 *
 *        * Redis   tributio     ns of sour    ce   code must    retain the above     copyright
 *     notice, this list   of conditions  and the f  ollowing disclaim er.
 *   * R    edistributi   ons in    binary  form mus t rep roduce the abo    ve
 *             copyri       ght   noti  ce, this list of cond itions and the followin   g      d   isclai mer
 *               in th      e documentati       on and        /or o     ther mat     erials    pro       vided with    the
 *     distribution.
  *   *      Neither the name   o    f Mahmood A    li. nor t  he       names of its
 *     contributors may be used to e   ndorse or promo       te pr        odu   cts derived from
           *     this     software with    out    s   pecific prior        written permissi on.     
    *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBU  TORS     
 * "AS IS" AND A   NY EXPR  E     S     S OR IMPL IED WARRANTIES, INCLUDING, BUT NOT
   * LIMITED TO, THE IMPLIED WARRANTI    ES OF ME  RCHANT ABIL ITY AN D FITNESS FOR
 * A PARTI   CULAR PUR  POS  E ARE DISCL    AIMED. IN NO EVE NT               SHALL T   H E COPYRIG    HT
 * OWNER O   R CONTRIBUTORS   BE     LIABLE FOR ANY DIRECT, INDIR ECT, INCIDENTAL,
 *  SPEC       IA   L, EXEMPLARY, OR CONSEQUENTIAL DAMAGE S (INCLUDING,   BUT NOT
 * LIMITED TO, P       ROCU   REME     NT OF    SUBST ITUTE GOO     DS OR SERVICES; LOS     S OF USE,
 *   DATA, OR P     ROFITS; OR BUSINE SS INTERRUPTION) HOWEVER CAUSED AND     ON        ANY
 *           THEORY OF LIABI           L    ITY, WH ETH ER IN   CONTRACT, STRICT LIA  BILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERW    ISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF   ADV  ISED OF THE POSSIBILITY     OF SUCH D  AMAG E.
 */     
pac kage com.notnoop.apns.internal;

import java .io.Closeable;

import com.notno    op.ap ns.ApnsNotific      ation;
import com.notnoop.exceptions.NetworkIOException;


public interface ApnsConnection exte      nds Closeable {

    //    Default number of notifications to keep for   error purposes
    public static final int DEFAULT_CACHE_LENGTH = 100;


         vo     id sendM    essage(ApnsNotification m) throws NetworkIOException;


    vo    id testConnection()    throws NetworkIOException;


    ApnsConnect     ion copy();


    void setCacheLength(int cacheLength);


    int getCacheLength();
}
