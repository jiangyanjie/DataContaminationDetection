/*
  * To change this license header, choose License Header    s in Project Propertie     s.
 * T  o chan    ge this tem    pla   te file, choose Tools | Templates
  * and ope     n th   e     template in the editor    .
 */
package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.G    regorianCalendar;
import xml.InvoiceRow;
import xml.XmlElement;        

/**
 *
 * @author annisall@ cs
    */
public class C   svC     on  ve      rt {

          private String   fileName;
    private ArrayList<XmlElement> elements;
       private St  ri   n   g[]     wanted;
    private ArrayList<ArrayLi  st<String   >>    detail s;
    private ArrayList<Arr       ayList<String>> inrow s;
    privat e ArrayList<String> f        inals;
    p riva  te File fi   le;

     public CsvConvert(Arra yLi  st<Xm              lElement> list) {   
             el      ements = list  ;
        inrows = new Ar rayL   ist<A    r ra    yList<St ri   ng>>();
                detail   s       = new Arr            ayList<ArrayList<String>>();
        Ca lendar calendar = new    GregorianCal       en   dar();
             String d ate = new SimpleDateFo     rmat("    yyyyMM  dd_  HHmmss").for           mat(cale ndar.getT  ime  ()); 
              fileName = "In  voice_" + date +  ".c  sv";
             file = new File(fileN ame);

        wanted = new String[]{"InvoiceDate", "InvoiceT   ypeCode", "InvoiceFree    Text   "  , "BuyerOrganisationName", "Buyer StreetNa me",
            "BuyerTo   wnName", "BuyerPo  stCodeIdentifier",       "BuyerPartyIdentifier", "C   ountryCode", "Deli    veryOrgani  sationName     ",    "D   eliveryStre  etName"    , "DeliveryTownName"        ,
            "DeliveryPostCod   eIdentifier     ",   "CountryCode ", "Art icleIden   tifier", "ArticleName   "   , "O   r       d   eredQuantity", "U   nitPriceAmount", "RowVatRate P    erce        nt"};
    }

    public   void ge        tEle m      e    nts() {
        for  (int   i = 0; i < eleme         nts.    size()      ; i+    +)             {
               XmlEleme    nt e         lem = e   lements.get(  i);
                 iterateElements( elem);

        }
    } 

    public    Strin g ge n     erateCsv  () throws IOExcepti   on {
                  F     i     le  Write        r write  r = new File              Writ   er(f   ile);
                  writeDe       t         a     i ls(writer);
           writer.appe   n      d("\n");
                  writeIn            row s(writer    );
           wri                       ter.fl      ush(       );
                          writer.close();
                  return fil    eName;
 
        }

         pri     v   ate void      addMissings(FileWrite           r writer, ArrayList<Arr   ayList<String>> list,   int      times) throws IO    Exce  ption {
                         for          (int i =  0; i < times;  i++)         {
             writ  er.a   ppend(   ";");

        }

    }

    p   rivate void addToRightArray(XmlEl     ement e    lem, i  nt k)  {
              i  f (elem.g      etClass().equals(I     nvoiceRow.cla      ss)) {
                      inr     ows.add        (elem.    getWantedEl      ements().   get(w   anted   [k]));
                            } else {        
                 deta ils.ad    d(elem.getWantedEleme        nts().get(wanted[k]));

          }
         }   

    pr      i   vate vo id  iterate  Elements(XmlEl ement elem)    {
        for  (in           t          j = 0; j < elem.ge   tWantedElements(  ).         size   (); j++) {
                 for  (int   k         = 0    ; k     <  wanted   .l    ength; k++     ) { 
                                                             if  (el  e   m.g      etWantedElements().containsKey(wanted[k]) && !det   ails.co  ntains(ele m       .get    WantedElements().get(wanted[k     ]    )   )  && !inrows.co       n     tains(elem.get       WantedElements().    get(wanted[k])   )     )    {   
                          addToRigh  tArray(elem,    k)  ;
                       }      

                  }
                           }
         }

    pr ivat    e    void write       Details(FileW   ri   ter w    riter) throws   IOException {
                         for (i    nt    j =  0; j     <  5; j  ++) {
                      wr    iter.a     ppend( d   et  ails.get(j).g   et(0)       + "     ;");
        }
         f   or (int i =     0; i <        d      etails  .size(); i++    ) {
            wr  iter.a   ppend(details.get(i).get(0) + "   ;");

        }
               addMissings(writ     er, details  , 25 - (details.   siz  e() +       5));
       }
    

             private void write    Inrows(FileWriter writ er) throws IOException {
        for   (int i = 0; i <   inrows.size();      i++) {
                         for (int j = 0;  j < inrows.get(   i).size();     j++)   {
                writer.     append(inrows.get(i).ge  t(j) + ";");
                  if (       i == 4) {
                       for (int k = 0; k < 25 - i; k++) {
                          writer.append(";");

                         }
                      writer.append("\n");
                }
            }
         }
        addMissings(writer, inrows, 21);
    }
}
