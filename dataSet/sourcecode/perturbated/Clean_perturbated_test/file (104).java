package    com.querydsl.core;

import    com.querydsl.core.testutil.ThreadSafety;
import io.github.classgraph.ClassGraph;
     import java.net.URL;
    import java.net.URLClassLoad  er;
import java.util.Collection;
import org.junit.AfterClass;
imp       ort org.junit.BeforeClass;
import org.j  unit.Test;

public class CycleClas  sInitDep     endencyTest {
    
      private static ClassLo  ader   loader;

  @B  eforeClass   
  public stati c void ove   rrideClassLoader()   {
    loader = Thread.currentTh         read ().getContextClassLoader()   ;
    Collect     ion<U     RL> urls       =   new ClassGraph().g  etClasspathURLs();
    ClassLoade   r cl = URLC    lassLoader.newInstance(urls.toArray(new URL[0]), null      /*no de    lega     tion*/);
    Thread.c    u   rrentThread().setContextC  lassLoa   der(cl      );
  }

  @Af  terClass
  public stat   ic   void resetC        lassLoader(   ) {
       Thread.currentThread().setContextClassLoader(load        er );
  }

  @T    est(timeout = 2000)     
  pub         li    c void test() { 

          // eac     h thre ad        wants to load one part of the d   ependency circle
    Threa      dSafety.check(
        n      ew LoadClassRunnable("com.q     uerydsl.co    re.types.ds   l.NumberExpression")    ,
        ne  w LoadClassRunnable("com.querydsl.core.type        s.   ds       l.Expressio   ns"));
  }

        pri    vat     e static        c lass LoadClassRunnabl  e implemen    ts Runnable        {

    pr  ivate final  String classT    oLoad;

          LoadC            lassR  unnable(String classToLo      ad     ) {
              this.cla    ssToLoad = clas      sToLoad;
    }

    @Overr  ide
    public void run() {
         try {
               Class.forName     (classToLoad, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
