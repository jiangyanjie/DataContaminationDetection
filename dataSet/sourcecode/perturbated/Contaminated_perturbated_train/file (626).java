package    net.argius.stew.ui.console;

import java.io.*;
imp  ort java.sql.*;
import java.util.Map.Entry;
import java.util.*;

import net.argius.stew.*;

/**
  *     The Connector     Editor for        console mo de.   
 */
publ  ic final cla      ss Conn     ectorMapEditor     {

                private        static final ResourceManager res = Res ourceManager.getInst  ance(ConnectorMapEditor.class);
     private static final String[] PROP_KEYS     = {"name", "classpath", "dri    ve   r", "url", "   us      er             ",
                                                                                     "password", "readonly",    "rol l       back"};

       private  final Con     nectorMap m  ap;
   
    private   Co  nnec     torMap      oldCont  e nt;

    pri vate              ConnectorMapE ditor(    )  throws IOExcepti          o     n {
            C                onnectorMap m;
                     t   r     y {
               m       = Con nectorConfigurati          on.lo ad();
               } c         atch (FileN      otFound        Exc      eption e    x) {
              pri    ntM   essage("main.n    otice.fil  enotexi      sts ");
              p    ri     ntLi  n      e(String.format("(    %s)        ",    ex.g    etM        ess         age()));
                 m =     n     ew ConnectorMap();
               }
            this.oldCo  nten   t = m;
           th  is.m ap      = new ConnectorMap(this.o   l   dContent);  
    }

    /**
     * P  rocesse   s to input properties.
     * @param id
         * @pa  ram     props      
     * @ return true if the co    nfigur          ation was changed, othe      rwise false.
         */
       p   riva     te static bo    olean proceedInputPropert  i   es(String i        d         , Properties props)      {
                   wh    ile (true) {
                           pr        intMe   ssag  e("prope   rty   .s   t    a      rt1    "  );
                     p            r  i    n   tMessage  ("prop   e r   t    y.s     tart2   ");
                     fo   r (S      tring key : PR     OP_KEYS) {
                              S    t       r   in     g v   alue = props.ge tP     r  op   erty(key);
                    pri                              nt( res.get(    "  property.input", key, value    ));
                S             tri     ng in          p ut           = g  e   tI    npu     t("           ");       
                            if (i  n   pu   t != nul   l && i   np ut.leng th() > 0)     {
                               prop   s.set Property           (     key, i nput    );
                                                }
                       }
                   f   or      (String ke         y : P  ROP_KE   YS)      {
                              p  ri  ntL    ine(key + "=" + props.get       Prop  erty (k      ey));  
                             }
                  if (confi   rmYes("prop  erty              .tryconn   ect.con    fi  rm"))      {
                    try {
                           Connector c    = new    Connector(id, props);
                                             c.getCo  n    nectio  n()     ;
                                           pr         intMessage("property.tryconnect  .suc        cee    ded")    ;
                             } cat  ch (SQ  LException      e   x)               {
                                   p  r           in tMessage("pr  operty.try  connect.failed", ex       .getMe    s            sa    ge());
                   }  
                                }
            if (confirm Yes("proper    ty     .update.   confirm"))            {
                    return true;
                                          }
                            if       (!confirmYes  ("prope   r   ty.retry.confirm  ")) {
                print    Mes  sage("property.upd   ate          .can cel"       );
                            retu      rn false;
            }
        }
    }

       /**   
     * Adds a Connec  tor.
     * @  p                  ara  m    id
        */ 
       private            voi   d proceedA  dd(String i    d) {
                if         (                      map.contain           sKey(id))               {
            pri  ntMess   age("p    roc.alreadye  xists", id);
            return;
                 }
        Pro    perties p    rops = new Pr oper ties()    ;
        // setti ng      defaul  t
             for (Strin       g   ke    y    : PROP_KEYS)   {
             props.    setProperty(key,       "");
            }
        i     f      (proceedInpu                       tProp     erties(id,     pr       ops)) {     
                 map.pu            t(id, new  Connecto  r(i   d, props)       )    ;
                                               pr                 intMessag      e("p roc.a          dd      ed"             ,      id);
        }            
    }

                   /**
         * Modifi e  s a    Connect    or.
            * @param id
              */
    private   void proce   edMo dif      y(Strin   g id) {
             if (!map.conta        insKey(i d)) {
                   printMessa             ge("pr    oc.n  otexists      ",   id);      
            re turn;
                  }  
                       Pr  o p    erties prop           s   = map.   ge  tConn     ector(id).toPrope   rties();
        if (pro cee  dInp      utProperties(id, p    rop      s)) { 
            map.put(id       , ne   w   Connecto    r(i    d    , props));
                             print   Message("proc.m odified", id);
        }
     }

    /    **
                             * Dele            tes a C     o  nnec tor. 
     * @par  am   id
     */
    private void procee     dRemove(S   tring id    ) {
                             Connector con             n   ec     tor = map.getConnector(id);
            p          rintL   in e("I D[  "   +  id + "]:" +   conn     ecto r.getName());
          i    f      (con  f    ir mY es(    "proc.rem  ove  .confirm") ) {
              ma p  .remov     e(id);
              printMes  sa  ge("proc.remo  ve.f   inished");
                    } else                 { 
               printMes   sag   e("proc.remo            ve.cancel    ed");
                       }
            printLine(map);
          }    

    /**
          * Copies fro m a       Conne ctor to another.
     * @para     m src
     * @param dst
         */
    private void proce    ed        Copy(   String src, String  dst)     {
        if (!map.cont    ainsKey(src))         {
                 printMe     ssage("   proc.no  texists", s     rc);   
                    pr   in tMessa    ge("pro           c.copy.       cancel   ed");
            retur     n;
        }
        i    f (map      .  cont   ainsKey          (dst)) {
                printMe        ssage("  pro  c.alr  eadyexis    ts", d   st);
                pr       intMe ssag  e("proc.co  p y   .canceled");
                retur     n;
              }
        ma    p.put(dst, new Con     nector(dst, map     .   get  Connector(sr             c)));     
                    printMessage("p r        oc.copy.f    ini  shed")     ;
        }

    /**
          * Displa ys al    l of the   C   o  nnectors .
            *    /      
    private void proce   e    dD   isplayId   s() {
            f   or (E        ntry<String, Co nnector >   ent              ry :  map.   entry                  Set()) {
                  String id = entry.getKey(    );
                printLine(   St  ring  .format("%10s : %s", id, map  .getConnector(id).getName())    );
                     }
                 }

     /**
            * Displa       ys the Connect    or    info speci fied     by     ID.
     * @par      am       id
     */
      privat   e               void p   ro     c      eedDisplayDe      tail(String id) {
        if (!     map.cont    ainsKey(i       d)) {
            printM    essage("proc       .not  exists", id) ;
                    r      eturn; 
        }
        Properties props = map.getConn     ector(id)      .toP  roperties();
         for (String key : PROP_KEY S)   {
                                  printLi        ne(     String.format("%10s : %s", key, prop   s.getProperty(   k    ey  )));
                 }
    }

    /**
            * Saves t     he        conf         i  guration to a fi      le  .
                           * @throws IOEx      ception
         */     
                 private void proceedSave    () throws IOEx       ception   {
        if    (map.equals (oldContent)) {
             printMe       s   sag       e     ("proc.no     modifi   cation");
              } e    lse if (conf         ir      mYes("p      ro    c.save    .con   firm"))   {     
            Conne    ctorConf igura     tion.save(map);       
                   old      Content =                    new C   onnecto      rMa   p(map   )          ;      
                pri       ntMessag    e("pro c.s  a     ve.f   ini she    d"         );
                      } else            {
                     p rin  tMes    sage("pr  oc   .save .canceled      ")     ;
                  }
    } 

               /**
               *    Loads from file.
       *   @t  hrows IOExc e                       p   tion
     *    /
         privat e vo      id proceedL                oad() throws IOEx   ception        {  
             C  o     nn        ect   or  Map m = Co     nne     c t  or   Con   f                   i             gurat  ion.loa     d();
        if (m.e  qua   ls(    oldContent) &&  m  .equals(ma   p)) {
                         pri  n   tMessage("p  roc.nomodific a        t      ion");
                            re turn    ;
          }
          pr     intMes sage("pro    c.load   .confirm     1");
                if (confirmYes(  "proc.l o  ad    .confi  rm2")) { 
                       map.cl     ear                     ();
             ma       p.p  utAll(Conne ctorConfi  guration  .l oad());
                 pri   ntMessage(  "proc.load.finish  ed  ");
        } el  se {
            printMessage("proc.l         o  ad.c         ancel   ed");
                      }
    }

       /**
           * Re     turns th          e       inpu   t strin        g from S  TDIN.
     * @param messa   g   eI d
        *    @param args
            * @ret    urn
     */
        pri           v  ate stat   ic   String getInput(St      ring  me          ssa  ge         I  d, Obje     ct...       args) {
          if (messageId.l    ength() > 0) {   
               print(res.get(me  ssageId,        args));
        }
        print(r   es.get("pro             c.pr ompt"));
                Scanner scanner = new S  canner(S   yste  m.in);
        if (scanner.hasNextLine(   )     ) {
                      r eturn s    cann  e   r.ne    xtLi              n    e();
         }
        retu    rn "";
        }

               /**
     * Returns the resul t to confirm Yes  or No    a         s boolean.
        * @param messageI d
       * @r  etur    n
         */
    pr    ivate static boolean confirmY   es(   String messageId) {
         if (m  e    ssageId.l     ength() >      0) {
                 print(res.get    (m   es sage Id));
          }        
                     pri nt   (       "(y    /N)             ");
         r  eturn ge   tInp ut      ("").eq     ualsI    gno   reC                              a    se("y");
     }
    
      pr ivat   e stati   c void print(Object o) {
             System.out.print(o);    
      }

        priv ate s   tatic void printLine()      {
        System.o     ut.println();
    }

    private stati       c v   oid    pr     i    nt     Line(O  bje   ct o) {
                    System.    out.println(o);  
    }

    private           static        void prin   tMe   ss    age(String mes   sage  Id, Object... args) {
               printLine(res.get(        messageId,      args));
    }

       /**
        * Invokes this editor.
     */
    s     tatic void invoke() {
                 printLin e();
        printMessage("main     .start  ");
        p          rintLine();
           t   ry {
              C   onnectorMapEditor edito   r = new ConnectorMa     pEd      itor();
            wh  ile (tr   ue) {
                         Paramet    er p = new    Parameter(getI    npu t    ("main.wait    "    ));
                final            String com   man  d = p .at(0);
                final S        tring id = p.at(1);
                           if     (comm and.equalsIgnoreCase("help"))    {
                           prin   tMessage("he    lp");
                    } else if (command     .equalsIgnore      Case("a")) {
                    edi      tor.proceedAdd(id)    ;
                        }      else if (comma   nd.equalsIgnoreCase("m")) {
                           edito   r.proceedModify(id);
                } else if   (command.equalsIgnoreCase(           "r"))         {
                       editor.proceedRemove(i   d   );
                 }   else if (command.equalsIgno   reCase  ("copy")) {
                          editor.proceedCopy(i    d, p.at(2   ));
                } else if (command.equ     alsIgnoreCa   se      ("disp")) {
                        if (id   .length() == 0) {
                                 editor.proceedDis     playI     ds();
                      } else {
                            editor.proceedDisplayDetail(id);
                    }
                  } else if ( command.equalsIgnoreCase("s     ave")) {
                    editor.proceedSave();
                      } else if (command.equalsIgnoreCase(  "load")) {
                      editor.proc eedLoad();
                     } else if (command.equalsIgnoreCase("exit")) {
                      if (editor.map.   equals(editor.oldContent)) {
                            break;
                    } else if (confirmYes("proc.exit.confirm")) {
                                break;
                        }
                }
                       printLine();
              }
          } catch (Throwable th) {
            th.printStackTrace();
          }
        printLine();
        printMessage("main.end");
    }

    /**
     * @param args
     */
    public static void main(String... args) {
        System.out.println("Stew " + Bootstrap.getVersion());
        invoke();
    }

}
