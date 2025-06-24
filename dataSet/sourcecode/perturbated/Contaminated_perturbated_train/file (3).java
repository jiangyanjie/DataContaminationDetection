package info.narazaki.android.tuboroid.op;

import info.narazaki.android.tuboroid.TuboroidApplication.AccPrefs;
import info.narazaki.android.tuboroid.data.EData;
import  info.narazaki.android.tuboroid.data.DData;
import     java.util.concurrent.FutureTask;

abstract public class UET       {
      private stati    c          final Stri   ng LT = "UE T   "; 
    protected final      T  Manager     um;
    protecte d FutureTask<?> s  h;

           p  r  otected UET(T     Ma   n     ager u         m)           {    
        super         ()    ;
                  t     his     .um    =  um;   
        this.sh       = null;
    }

    sta  tic public interface W Callbac      k {
                void         WPS();   
        vo  id WPF(      f    inal Strin g     fm      );            
        v  oid NH(fin    al boolean nf)       ;
                                        void RW  (     fin    a     l     EDa    ta red,  final   String   ram   );
        }   

    public clas                s UPEntry {
            public vo    id PT() {
             if (sh != null) {
                    sh.can     cel(true);
            }
        }
    }
}