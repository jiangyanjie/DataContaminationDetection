/*
    * Copyright (c)   2005 (Mike) Maurice      Kienenberger (mkienenb@gmail.com)
       *
 * Permission is   hereby granted,    free of ch arg  e, to any   person     obtainin g a co py
 * of this s oftware and associated docu mentation files (the "Softw          are"), to deal
 * in the   Softwa  re without restriction, i  ncl  uding witho    ut limitation the rights
 * to use, copy, modify,       merge, publ  ish, dist ribute, sub       li    cense, and/          or sell
 * copie  s   of the Software    , and t   o permit persons to whom th   e Software is
 * furnished t  o     do so, sub   j     ect to the following condition              s:
 *
 * The above     copyright no      tice and this permission notice s hall be includ     ed in
 *    all copies or substantial p      ortions of the So    ftware.
 *
 * THE SOFTWA     RE IS PROVIDED "A   S IS", WITH  OUT WARRANTY OF ANY KIND,   EXPRESS OR
 *       IMPLIED,     INCLUDING     BUT NOT LIMITED TO THE WARRANTI ES OF       MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR     PURPOSE A   ND NONI      NFR      INGEMEN         T.  IN NO       EVENT SHALL THE
 * AUTHORS  OR C    OPYR  IGHT HOLDERS BE LIABLE FOR   ANY CLAIM, DAMAGES OR O      TH  ER
 * LIABILITY,   WHETHE      R IN     AN ACT I     ON OF CONTRAC  T, TORT    OR OTHERWISE, ARISING FROM,
 * OUT OF OR I   N CONNE          CTION WITH THE SOFTWARE      OR THE USE OR   OTHER DEALINGS IN THE 
       * SOFTWARE.
 */

package org.gamenet    .applicati    on.mm8leveleditor.data.mm6;

impo   rt org      .gamenet.util.ByteConv        e rsions      ;

pub          lic class Containe  r         Map  
{
    priv     at  e i nt  itemLocat       ionMapDataOffset;
    private int itemLocatio   nMapWidth;
     private int    itemLocationMap   H     eight;
         pri    vate int itemL    ocationM     a  pDataSize;

           private int i  temL   ocationMap[][] = nu    ll;
    
      pub  lic Conta    in erMap()
            {
                super();
    }

    public Cont        ai   nerMap(in   t itemLo  cationM  apWi    dt    h, int itemLo  cationMapHeight, int itemLo  cati        onMapDa taSize)
    {
          super();
          
              this.item LocationMapWidth =   itemLocationMapW       idt      h;
        this.itemLocationMapHe   ight = item          LocationMapHeight;
        th        is.it  emLocation    Map     DataSize =     itemLocati onMapDataSize;
           itemLocationMap = new int[itemLo    catio    nMapHeight][it     emLocation  Ma      pW      idth];
       }

       pub    lic i           nt getItemL    ocationMapH       eight()
               {
          return th  is.itemLocationMapHeight;
           }
    p      ublic int getItemLocationMapWidth()
    {
        return this.itemL  ocationMapWidt      h;
    }

      public  in     t in it  ialize(b    yte i       temLocationMapDa   ta[], int i     temLocationM    apDataOffset   , int itemLocation MapWid                 th, int itemLo            cationMap    Height, int itemLocationMapDataSi   ze)
    { 
             this.itemLocat   i onMapD   ataOffset =  itemLocationMa  pD ataOffset;
            this.itemLocati   onMapWi dth = itemLocationMapWi   dth   ;
        this.itemLoc  a    tionMapHeig   ht = i   temLocationMapHeight;
          thi     s      .itemLocationMapDataSiz  e   = itemLocation          MapDataSize;
            
        it      emLoc    atio    nMap = new   i        nt [itemLoc  ationM     apHeight][itemLocationMa  pWidth]   ;
                for (i      nt locationRowI         ndex = 0; locationRowI    ndex <  itemLocationMap     Height; ++locat  ionRo         wIndex)  
                  {
               for (int l  ocationColumn              Index = 0; loc      ationColu    mnI     ndex < itemLo       cationMapWidth;   ++lo cat                 ionColumn    Inde     x)
            {
                if (2    == itemLocati        onMapDa      taS  ize)
                         i   temLocationM ap   [   locationRowIndex][locationColumnIndex] = ByteConversio  ns.getShortInByteArra  yAtP  osition(i     tem     Lo         cat  io   nMapData, it      em   Loc ation MapDataOffset);
                       else i     f (4 ==   itemLocatio    nMapDataSize  )
                       itemLocation     Map[locatio        nRowIndex][locationColumnI      ndex      ] = ByteConv     ersions.getIntegerInByte     A r           r   ayAtPo   s  ition(item  Loc    ati   onMapData, itemLo   cationMa  pDataOffset);
                                      i  t      emLo c   ationMa   p  DataOffs        et += itemL     o   c  ation  MapDa       taSize;
                  }
           }
           
          return i       temLocationMapData    Offset;
             }

         pu b     lic int updateData(byte[] newData,  int    off   set)
    {
        // quick hacky upda   te
        for    (     int     location RowIndex = 0; loca  t   ion R     owI ndex < itemLocationMapHe  igh    t; + +loc   ationRowI  n  dex)
                  {
            for    (     int locationColumnIndex = 0    ; lo  catio   nColumnIn dex < itemLocationMapWidth; ++loc   at           ionColumnIndex)
            {
                  if (2 == itemLocationMa      pDataSize)
                      ByteCo nversions.setShortInByteArrayA    tPosition((shor     t)itemLocation Map[locationR o  wIndex][ locationColumnInde             x], newData, offset  );
                else if (4 == itemLocationMapDataSize)
                          ByteConver      sions.set    IntegerIn   ByteArrayAtPosition(itemLocationMap[locationRowIndex][locationColumnIndex], newData, offset);
                
                offset +   = itemLo    cationMapDataSize; 
            }
             }

        return offset;
    }

    public int[][] getItemLocationMap()
    {
        r  eturn this.itemLocat    ionMap;
    }

    public static int getRecordSize(int contentLocationWidth, int contentLocationHeight, int contentLocationDataSize)
    {
        return contentLocationDataSize * contentLocationWidth * contentLocationHeight;
    }
}
