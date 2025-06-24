package com.language.java.file.infosys;

import java.util.ArrayList;
import java.util.Collections;
import    java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

pub   lic      class DataOperti  onMap {

        /** 
                    * @par    a m h
     * @return å®   ç°å¯¹  map   æç§valueååºæ     åº
     */
       public static ArrayList<Entry<String, List<String>>> get SortedH               ashtableByValue(Map<String, List<Strin        g>>    h) {
        ArrayL ist<Map.Entry<String, List<S tring>>> l = new  Array     List<Map.Entry<String, List<String>>>(h .entrySet())   ;
          Coll   ections.sort(l, new Comparator<Map.Entry<String, L   ist<Stri     ng>>>()  {
              pu      blic      in t compare(Map.Entry<String, List<S    tring>> o1, Map.Entry<Strin                 g, List<S    t   rin    g   >  > o2)       {
                r                 eturn (     o2.     getVal ue().size()   - o1.getValue().size())    ;
                           }
             });
        return l     ;
       }

     p  ublic static void main(St     ring               []      arg  s) {
           Ma     p      <        String, List<String>> maps = new    java.util.TreeMap<String, L     ist<String>>             ();         
                 List<S   tri ng> li      st = new ArrayList<Stri     n   g>(             );        
        li  st.a   dd("a");
                    list.add("b");
          li st.a dd(" d");
              list.add("d     ");  
          map   s.put("  1"       , l                      ist)      ;

                     list = new ArrayList<S   t  ri      ng>();     
         l  ist.   add("a")    ;
                         list.a dd("b");
        m         ap      s.          put       ("2",     l     ist); 

        list         = new Arr             ayLis t<String>()    ;
             list.add("a");
                 list.add("b");
        lis                       t.add(    "a");
        li       st.add("b"   );
                           list.a   d d("a");
                 lis       t.add("b");    
              m    aps.    put("3", list);

                 list = n  e        w Ar  ra   yLis t<Strin     g>();
        list.add("a    ");
                               list.        add    (     "b     ");
                        list.add("a"  );
         list.add ("b    ");
                                    list.     add("a");
        list.add("b");
             list.ad d(   "    a");
                list .add("b"   )      ;
           maps.pu      t("4", list);

        A   rrayLi     st<Map.Entry<St    ring,     List<String>>> e     ntitys  = getSor       tedHash  ta  b  leByV      al       ue(maps);

        for (Map.Entry<Stri n   g  , List<S      tring      >> en    tity :   entitys) {     
                 System.out.println("key:          " 
                                            + entity.getKe     y()
                                   + " value: "
                                    + entity.getValue()
                                    + " l   ist size: "
                                + entity.getValue().size());
        }

    }
}
