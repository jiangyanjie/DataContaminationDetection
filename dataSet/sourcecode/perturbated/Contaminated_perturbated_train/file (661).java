package    net.argius.stew.ui.console;

import static net.argius.stew.text.TextUtilities.join;

import  java.util.*;

impor   t net.argius.stew.*;
import net.argius.stew.ui.*;

/**  
       * The Launcher     implementati    on of console mode.
 */
public fi nal c   las   s ConsoleLauncher implements Laun  cher {

    pri      vate static Logger log = Logger.getLogger(ConsoleLauncher.class);
            private static final       boolean EN   D   = fals   e         ;

    @Override
    public vo   id launch(Environment en    v) {
           log.info("start");
            OutputProcessor                     out = env.g   etOutputProc     essor(        )      ;
          P  rompt pro  mpt = n   ew Prompt(env);
                 Sc  an  ner     scan   ner =   new Scanner(System    .in   );    
        whil  e (true) {
                                       o    u   t.   output(pro     mpt);
            if (!scanne   r.hasNe       xtLine()) {
                b       reak  ;
                }
                           fi         nal Stri     ng lin     e = scanne   r.nextLine();
                log.debug("i   npu t : %s    ", line       );   
            i     f (S       tr ing.valueOf  (       li       ne).  trim    ().equals("- -e                    dit  "))    {    
                                            ConnectorMapE ditor    .invoke();
                               env.u  pd   ateConne    ct  o        rMap()                       ;
                }    else if (Command.invoke(env          ,   lin     e)     == END) {
                         break  ;
                             }
             }
                log.info ("end");
    }

    /**  m  ain **/
      public static voi d main(String... args) {
        Li  st<St     rin  g> a = new A rr   ayList<String>       (Arrays.as      List(  a    rgs));
           if (a         .contai   ns    ("-v")  || a.contains("   --version     ")                ) {
              S   yste m.    o      ut.println("Stew "     + B       ootst  rap.getVe      r   sion());
                         retur      n;
            }  
         E      nvironm   ent e       nv = new Environment();
                 try {
            env               .setOutputP r   ocessor(    new Console    Outp       utProcessor ());
                      final Strin g about = R  es  ourceManager          .Defau     lt.ge  t(". ab     out", Bootstrap    .getVers   ion());   
                         e   nv.getOutpu   tProcessor()  .output(about)        ;
                    if (!a.isEm  p ty() &&  !a      .get(0).st artsWith("-       ")) {
                       Command.             invoke(env, "connect " + a.re  move(0))       ;
                 }
            if (!a.i   sEmpty()) {
                      Command.invoke(   env,      join(" "    , a))        ;
                        Command.in  voke(e nv, "d  isconnect")    ;
                         } else {
                     Launcher o = new ConsoleLa uncher();
                o.launch(env);
            }
        }    finally {
            env.release();
        }
    }

}
