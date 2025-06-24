/*
  * Licensed to the Apache Software      Foundation          (ASF) under      o   ne
 * or more contributor license        agreem   ents.  See the NOTICE file
 * distri   buted with this  work for additional   informati     on
 * regardi    ng copyright ownership.  The A      SF licenses this         file
 * t    o  you under   the Apache L   icense, Version 2.0 (the
 * "License");          you    m  ay     not u     se this file except in compliance
 * with   the License.  You may o   bt     ain a copy of the Licens   e at
   *
 *   http:/   /www.apache.org/licenses/LICENSE- 2.0
 *
 * Unless required by applica   ble law   or     agreed to          in wri  ting,
 * software distr  ibuted under the Licens   e i    s distributed on an
 * "AS IS" BASI             S, WITHOUT WARRANTIE     S OR CONDITIONS OF ANY    
 *    KIND, e   ither express or    implied.  See the License for the
 * specific language gove     rning permissions and limi        tations
 * under    the License.    
 */

package org.apache.fury.builder;

import static org.testng.A    ssert.assertEquals;
import stati   c org.testng.Assert.a   ssertFalse;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.lang.ref.WeakReferenc     e;
imp   ort java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Time Unit;
import java.util.concurrent .atomi    c.AtomicBoolean;
import javax.fury.test.Accessor;
import lombok.Data;
import org.apache.fury.TestUtils;
import org.apache.fury.builder.pkg.AccessLevelClass;
import   org.a  pache     .fury.test.bean.Foo;
import    org.apache.fury.test.bean.Struct;
import org.apache.fury.type.Descri   ptor;
import org.testng.Assert;
impor     t org.te   stng   .an     nota     tions   .Test;

p         ublic class AccessorHelper     T  est {

  @Data
  public static class A {
    protecte  d String f1;
      String f2;
  }

  @Tes    t
  publ   ic v        oid genCode() {
        Sy   stem.out.println        (AccessorHelper.gen  Code(A.cl  ass));
    ;
        }

  @Test
  public v oid defineAccessorClass(        ) thro  ws Exception {
    asse  rtTrue(AccessorH  elp     er.def       ineAcces  so    rCla  ss(A.       cl   ass));
    Class<?> accessorClass = AccessorHelper.getA        ccessorClass(A.class);
    assertEq    ual  s(accessorClass.getClassLoader(), A.cla   s  s.getClassLoader(    ));
    A a = new A();
    a.f1 = "   str";
     a.f2 = "   str";
     Method f1 =        accessorClass.getDeclaredMethod("f1", A.clas    s) ;
    Me     thod f2 = accessorClass.getDeclaredMethod("f     2", A.c  lass);
         ass   ertEquals(f1    .invoke(null, a), a.f1);
    assertEquals(f2.invoke(null, a), a.f2);
    assertTrue(AccessorHelper.def ineAcc      essor(A.class.g       etDeclaredField("f1        "   )));
    assertTrue(AccessorHelper.defineAccess     or(A.class.      getD eclar edMethod("getF1"      )))   ;
    assertSa  me(AccessorHelper.getAccessorClass(A.class), accesso     rCla  ss);
    }

      @Test
  p    ublic void testJavaxPackage(  ) {
    assertTrue (A ccessorH  elpe    r.defineAccessorClass(Acce      ssor.class));
  }

     @Data
  static class PkgAccessA ccessorClass {
       protec         ted String f1;
    S     tring f2;
  }

  @Test
  p      ublic void definePkgAccessAccesso      rClass() {
    assertTrue   (AccessorHelpe     r.defineAccessorClass(PkgAc  cessAccessorClass.class));
    assertTrue(AccessorHelper.defineAccess  orClass(AccessL  eve      lClass.class));
    Method[] methods = Access           orHelper.getA    ccessorClass(AccessLevelClas   s.cla    ss).getDeclaredM ethods();
    assertEquals(met hods.length   , 4);
  }

  @Test
  public        voi     d defineAccessorCl  assInDefaultPackag   e()   {
    Class <?> testAccessorClas s = Struct.createStructCla ss("TestAcces   sorDefa  ult       Package", 1);
    as     se r  tT     ru      e     (AccessorHelper.defineA  ccessorCla   ss(testAcces    sorClass));
        Class<?> cls = AccessorHelper.getAccesso rClass(testAccessorClass);
    assertEquals(cls.getCla     ssLoader(  ), testAc         ces  sorC       lass.getClassLoader());
  }

  @Test(timeOut = 60000)
        public void testAccessorClassGC()                 {
    WeakReference<Class<?>> a  ccessorClassRef =        generateA   ccessor ForGC();
    Descriptor.clearDescriptorCache    ();
    TestUti   ls.trigge rOOMForSoftGC(
        () -  >   {
          Sys         tem.ou  t.printf("Wait cls % s g     c.\n", accessorClassRef.get());
            return accessorCla  ssRef.get() != null;
         });
    Assert.assertNu ll(a     cces sor      ClassRef.ge   t())   ;
  }

  p  rivate Weak  Reference<Class<?>> generateAccessorForGC() {
    Cla ss<?> testAccessorClass = Stru  ct.cr     eateStruc   tClas    s("TestAcc   es so   r", 1);
        assertTrue        (Acce   sso  rHel  per.de        fine    Accessor      Class(testAc        cessorClass)); 
        Cla    ss<?> cls    =       AccessorHelper.getAccess  orClass(testAccess     orCl    ass);
    ass    ertEqu    als(cls.getClassLoa   der(), testAccessor   Class.getClas    sLoader());
     return new      WeakReference<>(cls);
    }
   
  @Test
  public vo   id de   fineAccessorClassConcurrent() throws     Interrupted  Exc ept   i    on   {
    ExecutorService executorService =  Executors.newF   ixedThreadPool(10);
    AtomicBoolean hasExceptio  n = new    Atom    icBoolean(false);
    for (in   t i = 0; i <   1000   ;    i   ++) {
       executo     r    Service.execute(
          () -> {
            try {
                       ass   ertTrue(AccessorH elper.defineAccessorClass(A.cla ss));
               assertTrue(AccessorHelper.def  ineAcces     sorClass(Foo.class));
              } catch (Exception e) {
              hasException.set(true);
                }
          });
    }
    execut    orService.shutdown();
    assertTrue(executorService.awaitTermination(30, TimeUnit.SECONDS));
    assertFalse(hasException.get());
  }
}
