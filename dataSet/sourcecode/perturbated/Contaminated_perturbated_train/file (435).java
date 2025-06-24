/*
   * Copyright    (c)    200   5 (Mike) Maurice Kienenber    ger (mkienenb@gmai     l.com)
         *
 * Permission is hereby granted, free of charge, to an     y person    ob      taining a copy
 * of      this software    and associated document   at   ion     files (the "So  ftware"), to deal
 *     in the So  ftware  without  re      striction,    includi     ng without limitation t     he rights
 * to use, c   opy, mod      ify, m  erge, publish, distribute, s    ublic   ense, and/or sell
 * copies of t     he Software,    and to perm     it persons to whom th         e Software is
 * furnished to do so, subject t        o the following conditio  ns:
 *
 *  The a bo       ve    copy     right not     i     ce and this perm    ission notic      e shall b      e include   d in
 * all copies or subs tantial portions of the S  oftware.
 *
    * THE SO     FTWARE IS PROVIDED "AS IS", WITHOU   T WAR       RANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT      LIMITED TO THE WARRANTIES      OF  MERCHANTABI    LITY,
   * FITNE  SS FOR A     PARTICUL   AR PURPOSE AND NONINFRINGEMENT. IN NO E VENT SHALL THE
  * AUTHOR    S O  R COPYR  IGHT HOLDERS BE LIAB         LE       FOR ANY C   LAI  M, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN     ACT      ION      OF CON    TRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER        DEALING   S IN THE
 * SOFTWARE.
 */

package org.gamenet.application.mm8leveleditor.data.mm6;

import java     .util.ArrayList;
import java.util.List;

     imp  ort    o   r  g.g        amenet.swing.con  trols.ComparativeTableControl;
import org.gam     enet.util.ByteConversions;  

public c  l  ass Activ   eSpell
{
    p  rivate s        tatic fi nal int ACTIVE_SPELL_RECO   RD_S    IZE       = 16;
    
    pr    ivate static int SPELL_     DURAT   ION_OFFSET = 0; // 8 bytes
    private static int SPELL_P     OWER_OFFSET     = 8; // 2  bytes
    
      privat   e stati c int SP    ELL_SKILL_ RANK_TYPE_    NORMA L = 1;
    private static    int SPELL_SKIL   L_RAN K_TYPE_EXPERT = 2;
     p       rivate       static      in   t SP    ELL_SKILL_RANK_       TYPE_M     AS TER   = 3;
    
       pri   va te static in  t SPELL_SK   IL  L_R   ANK_OFFS     ET    = 10;     // 2     bytes

    priva      te static int SPELL_OVERLAY_ID_OFFSET   = 12; // 2 bytes
    private static int SPELL_C  ASTER     _O   FFSET = 14; // 1 byte
       priv   ate static        i       nt SPELL_ATTRIBUTES_OFFSET =    1  5; // 1 byte

          pri   vate int a          cti       veSpell Offset = 0;
       pri   vate byte activeSpellData[] =             null;

    public ActiveSpel         l()
            {
         super();
    }
     
       pu  bl ic     ActiveSpell(String f   ileName)
      {
         supe  r()   ;
                t    his.activeSpellData = new byte[ACTI VE _SPELL_RECORD_SIZE];
           }

       public long getEndDa      t eT   ime()
    {   
          return ByteConversions.getLongInByteAr rayAtPosition(    activeSpe    llData, S         P      ELL   _DURA   TI       ON_OFFSET);
    }
    
    public void setEndDate   Time(long v   alue)
                    {
        ByteConversions    .setLongInBy        teArrayAtPosi     tion(value, activeSpel    lData, SP     ELL_DURA             TION_OFFSE     T);
          }
    
         publi c short getPow   er()
    {
        return ByteConversions.getShortI    nByteAr     rayAtPosition(ac  tiveSpellData,         SPE    LL_P OWE    R_    OFFSET);
    }  
       
    public      voi     d setPower(short value)
    {
        ByteConversions.    setShortInByte ArrayAtPosition  (value, activeSpe       llData , SPE   LL_POWER_OFFSET);
          }
    
    publ  ic short g    etRank()
          {
         return ByteCo   nversions.    getShortIn  ByteArrayAtPositio   n(a   c     tiveSpellD  ata, SPELL_SK         IL   L_ RA    NK_OFFSET);
    }
           
    public void se tRank(short value)
     {
              ByteC     onversions.setShortInBy   teArrayAtPosition(val  ue,    activeSpel    lData , SPELL_SKILL_RANK_OFFSET);
      }
    
    p  ublic int initia lize(     byte    dat  aSrc[  ],        in       t offs    et)
    {
                      this.a   ctiveSpellOffset = offse    t;
        this .   activeSpell    Da    ta = new byte[ACT  IVE   _SPEL       L_RECORD_SIZE];
                                Syste    m.   a     rraycopy (dataSr   c,                       offs       et, t  his.activeS    pellDa    t    a, 0, this.activeSpe   llData.   le  ngt          h);
        off   set += this.ac tiv  eS     pellData.le         ngth;
                    
             return offse t;
    }

    public static boolean checkDataIntegrity(byte[] data, int offset  , int e         xpectedNewOffset)
    {
           offset += ACTIVE_SPELL_ RECORD_SIZE    ;
        
           retur     n (o        ffset == expected    NewOffset);
                         }
    
      pub    lic stat  ic int      popula    teO bj  ects(   by    te[] data, int offset, List     activeSpellList, i  nt    activeSpell                Count)
     {
          for (int   activeSp   ell            Ind  ex = 0; activeSpel           lInd  ex < activeS   pel      lCount; ++ac tiveSp  ellIndex)     
          {
                   Act  iveSpel     l   ac  t   ive            Spe  l    l = n  ew ActiveSpell       ();
                          activeSpellList.add(act              iveSpell);
                                   offset = activeSpell.in   it         i ali  ze(d ata,    offse t) ;
        }
        
        retur  n offse t;
    }
    
    public    static int updateData(byte[] new    Data, int offset   , List                 a     ctiveS   pellList)
                       {
        for (int activeSpellIndex  = 0; a ctiveSpellIn    dex < activeSpellLi    st. size ()      ; ++ac       t i veSpellInde  x)
                {
               ActiveSpell acti  ve   Spell = (A ctiveS       pel       l       )active   SpellList     .get(activeSpellIndex);
                             System.arraycopy(a  ctiveSpell.getActiveS    pellData(), 0,            ne  wDa  t    a , offset  ,          a  ctive   Spell.   g     e       tActi       veSp  ellData(   ).   le     ngth);
                  o    ffset +    = a   ctiveS   pell.ge   tActiveS           pel lData().length;
             }        
          
        retu   r    n   offset;
    }

        publi  c byt     e[ ] getA       ctiveSpellD     ata()
    {
                              return       thi     s.activeSpell        Data; 
    }
    p   ublic int             getActiveSpellOffset()
    {
             return     this.activ        eSpellOff set;
    }
  
         pu      bli    c             static int   ge           t     Rec      or      dSize()
      {
        r       etu      rn ACTIVE_SP        ELL_REC    ORD_S  I  ZE     ;
    }

    public static List ge      tOffset   List()      
         {
                   L ist offsetList  = new ArrayLis t()    ;
              
           a      ddOffsets(offse  tL   ist, 0,    -1);

             ret          u   r   n offsetList; 
    }
            
        public stat     ic Comp arat    ive   TableControl.DataSou       rce                         g  etComparat   iveDataSource( final   List active Sp      ellList)
      {
                     return ne    w ComparativeTableContr ol .        DataSourc  e()
        {
                                public int get       RowCo    unt()
                  {
                       ret    urn  active    SpellLis  t.size();
            }

               public byte[] getData(int dataRow)
                     {
                ActiveSpell activeSpell = (ActiveSp  ell)ac    tiveSpellList   .get(dataRo      w);
                     re       tu   rn activeSpell.getActi veSpell   Data();
                 }

            public int getAdjustedDa  taRowInd      ex(in    t dataRow)
                 {
                                return data   Row;
                         }
            
            public String   getIdentifier(int dataRow)     
            {
                         return "";
                  }

               public int getOffset(in  t dataRow)
            {
                return      0;
            }
        };
    }

    public static void addOffsets(List offsetList, int startingOffset, int index)
    {
        String prefix = "";
        if (index != -1)  prefix = "ActiveSpell#" + String.valueOf(index) + ":";
        offsetLis  t.add(new ComparativeTableControl.OffsetData(start  ingOffset + SPELL_DURATION_OFFSET, 8, ComparativeTableControl.REPRESENTATION_TIME, prefix + "Duration"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_POWER_OFFSET, 2, ComparativeTableCo  ntrol.REPRESENTATION_SHORT_DEC, prefix + "Power"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_SKILL_RANK_OFFSET, 2, Compa   rativeTableControl.REPRESENTATION_SHORT_DEC  , prefix + "Skill"));
        offsetList.add(new ComparativeTableControl.OffsetData(startingOffset + SPELL_OVERLAY_ID_OFFS  ET, 2, ComparativeTableControl.REPRESENTATION_SHORT_DEC, prefix + "Overlay ID #"));
        offsetLi   st.add(new ComparativeTableControl.OffsetData      (startingOffset + SPELL_CASTER_OFFSET, 1, ComparativeTableCont     rol.REPRESENTATION_BYTE_DEC, prefix + "Caster"));
        offsetList.add(    new ComparativeTableControl.OffsetData(startingOffset + SPELL_ATTRIBUTES_OFFSET, 1, ComparativeTableControl.REPRESENTATION_BYTE_DEC, prefix + "Attributes"));
    }
}
