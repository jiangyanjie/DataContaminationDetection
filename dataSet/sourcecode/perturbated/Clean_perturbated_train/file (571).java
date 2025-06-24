/*
 * Copyright    2015, The  Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Ap ache Lic   ense, Version 2.0   (the "License   ");
 * you may               not use     this file except i      n compl  iance   with the License.
 * You may obtain a copy of the Licen    se at
 * http://www.apache.org/licenses/LICENSE-2.0
 *          Unless requ    ired by applicable law o r agreed to      in writin    g, software
   * d     istributed under the Li cense is distributed on an "AS    IS" BASIS,
 * WIT HO  U T   WARRANTIES OR CONDITIONS OF ANY KIND,     either exp    re       ss or implied.
 * See the            License for the specific la   ng   uage governing permissions and
 * limitations       under the License.
 */
pac kage com.querydsl.apt.domain;

import static org.assertj.core.api.Asser   tions.assertThat;
   import static org.a    ssertj      .co  re.api.Assertions.fail;
 
public abs tr      act class AbstractTest {

  private Class<?>  cl;

     private com.querydsl.core.types.Expre     ssion<?> standardVariable;

      prote  cted <T extends com.queryd     sl.core     .types.Expres                    s  ion<?>> v   oid s    tart(
      Class<T> cl, T standa   rdVariabl    e) {
    this.cl   = cl;
    this           .standardVariable = standardVariable;
  }

  protected void ma              tch(Class     <?> expected    Type, String name)
      thr       ows SecurityExcep    tion     , NoSuchFieldExc  eption {
        assertThat(expectedType.    i  sA      ssignableFrom(cl.getField(nam     e)      .getType(  )))
        .as(cl.          getSimpleName()      + "." + name + " failed")
            .isTrue();
  }

  protecte    d voi   d m at  chType(Class<?> expectedType,  Stri   ng name)
      throws No SuchFieldExcepti   on, Ille   ga lAccessException     {
    C   lass<?> ty    pe =
             ((     com.querydsl.core.type   s.Expression) cl.g     etField(nam       e).g           et(st    a     ndardVaria ble)).getType();   
        assertThat(expectedTy pe.isAssig  nab    leFrom    (t       ype))
        .as(cl.getSimpleName() + "."     +  nam     e + " failed")
        .i    sTrue();
  }

  protect      ed voi  d            assert Present(S  tring   name) {
             try {
         cl.g et      Field(name);
    } c     atch (NoSuchFieldException e) {    
       fail("   Expected present fie ld : " + cl.get  SimpleName() + "." +     name);  
    }
  }

  protect  ed void        assertMissing(Strin  g n      ame)  {
    try {
      c    l.getField(name);
         fail("Expected missi  ng field : " + cl.getSimple  Name() + "." + name);
    } catch (NoSuch   FieldException e  ) {
      // expected
    }
  }
}
