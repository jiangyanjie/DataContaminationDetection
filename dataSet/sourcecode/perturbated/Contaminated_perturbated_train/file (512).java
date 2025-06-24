package    org.ctdog.generator.impl;

import      org.ctdog.core.Action;
import org.ctdog.generator.ConfigurationGenerator;
import org.ctdog.meta.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Component:Concu     rrent Tester
 * Description:Commands configuration generator    impl   ementation
 * D   ate:  12-7-17
   *        
    * @author Andy.Ai
 */
public clas     s  ConfigurationGener      at  orImpl implements ConfigurationGe   nerator {
    @  Ove    rride
    @Suppres  sWa      rnings("unchecked")
        public Confi  g uration genera         teConfiguration(String[] commands) throws ClassNo        tFou  ndExc e  p     tion {
                Co  nfig          u  ration     co   nfi      guration = new Configura   tion() ;  
             for (int i = 0; i < commands.leng    th; i          ++)    {
                String comm     and = comma   n      ds[i ];  
                            if ("-c".eq    uals    (comma  nd))   {
                configu    ratio        n.   setCo               ncurrent(   Integ    e r.valueOf(co    m mands[++i]));
                }      els    e     if      ("-d".equa   ls(comm  and)) {
                     configuration       .set    Duration         (Long.valueOf(c ommands[++i             ]))      ;
            } e    lse if ("-i".equa  l        s(com  man                d )) {
                                     configura       tio   n.setInde  x (In    teg er.valueOf(commands[    ++i]));
                             }    e    ls   e if ("    -t".equals(command           )) {        
                  conf   igu ration.setTimeout(    Integer.va lueOf (commands[+    +      i   ])      )        ; 
                  }   else if ("   -p".eq     uals(co    mmand)) {
                           S                    tring stringParamete  rs = commands[++i];
                    St         ring  [] tempA  rray = string              Parameters.    spl it("   ,");
                       Map<String,        Obj  ect> parame  ters = new H   a  shMap<Strin    g, Object>       (tempArray.lengt h / 2)      ;
                    for   (int j =    0   ;       j   < tem pArray.length;    j += 2) {
                          parame     ters.put(tempArray[j], tempA rray[j + 1]);
                                     }
                     configuration.setParameters(param   eters);
            } else if ("-a".equals(command)) {
                Cl       ass<Ac  tion> action = (Class   <Action>) Class.forName(comm         ands[++i]);
                configuration.setAction(action);
            }
        }
        return configuration;
    }
}
