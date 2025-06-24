
package     CreateXML;
import java.util.ArrayList;
import java.util.Scanner;
import    sqlite.*;
imp  ort java.io.*;
import   java.util.Arrays;
i    mport CreateXML.*;

public class      Create     Xm   l {
    //íàçâàíèå xml ôàéëà
       private  Strin   g nameXml;
   //èìÿ àòðèáóòà ïî ãîðèçîíòàëè                   è åãî çíà÷åíèÿ(êëþ÷è)
   private A    rray  List<String> rowNameAnd  Value;
    //èìÿ àòðèáóòà ïî âåðòèêàëè è åãî çíà÷åíè     ÿ(êëþ÷è)
       privat   e ArrayList<   String> c olu     mnNameA   ndVa               lue;
   priv     ate ArrayL   ist<String> section;
   //     ìàññèâ ñîäåðæàùèé íàçâàíèÿ âñåõ èçìåðåíèé
   pri   vate ArrayList<String> d    i       mensio  n     ; 
   //êîíòðîëü ôèêñèðóåìûõ è   çìåðåíèé
    private ArrayLi st<   S tring> control;
    
    public      C     reateX ml(){
        String[] array = {"Apple", "Store", "Time"};
          this.    dime nsion = new A  rrayList<S  tring>( Arrays.as   List(ar    ray)) ;
        this.control     = new Ar  rayList<Str   ing>(Arrays.a       sList(arr   a   y));
             this.section = new ArrayList<String>();
              this.rowNameAndValue =       new ArrayList<String>();
            thi    s.colum          nName  An    dV    al      ue = new ArrayLi     s    t<String>()   ;
                  }
      
          void getDimension(Ar rayList<     Strin  g> al){
        S canner sc = new Sc      anner(   System .in);
        Sql  ite.g  etResultSet(new File(    "").getAb s     olutePath()+    "\        \Ap    ple","Select *  from "+al     .get    (0));
                                     System.   out.print   ln("Ââåäèòå            ê    ë   þ÷   è : ");   
                       al.add(sc.nextLine())       ;
    }
    p   ublic void  buildXml(){ 
        Sc    a       nner   sc      = n            ew Scanner(Sy    stem.in);
        Strin          g[] arra    y = {" Apple"  , "Store" , "T       ime"};     
        for(   int i = 0;   i < 3; i++){
                      this.sec   tion.add(this.dimension.g    et(i));
                         System.out  .pr     in    tln("Ô    èêñèðóå  ì      îå  è  çìåðåí      èå : "+thi    s.sec          t    ion.get     (0));
               this.getDimen   sion(this.section);
            t  his.   control.remove(i);  
            Sys        tem.out.print         ln("Èçìåðå   íèå ïî                âåðòèêàëè    : ");
               Sy    stem.ou t.     pr    in  tln("1."  +this.c  ontrol.g     et   (0   ));
                 System.out.println("2."   +th  is.control  .get  (1));
            int column = Integer. valueOf( sc.nextLine())-1;
                  this   .c   olumnNameAndValue.add   (thi s.contro   l.get(column));
                     this.get  Dimension(this.   co lumnNameA  ndVal  ue);
                  this.control.rem  ove(column);
            
            Syste  m.out.println("Èçì åðåíèå ïî ãîðèçîíòàë  è :         "+this.control.get(0));
               this.rowNameAndValue   .add(this.contr  ol.get(0));
            this.control        .   remove(0);
            this.     ge     tDimension(this.rowNameAndValue);
            this.contro    l = new ArrayList  <Str  ing>(Arr   ays.asList(array));
            BuildXML.b     uildXML(this.rowNameAndValue, this.c    olumnNameAndValue, this.section);
            this.section.clear();this.columnNameAndVal   ue.clear();this.rowNameAndValue.clear();
        }
            
    }
}
