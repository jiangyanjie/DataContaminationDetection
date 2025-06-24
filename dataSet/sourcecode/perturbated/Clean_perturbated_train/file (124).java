/*
          * Copyright 2010, Mysema           Ltd
 *
 * Licensed under       the A  pache License, Ve   rsion 2.0     (the "License");
 *   you   may not use this file except in comp          liance with the Licens   e.
 * You may obtain a copy     of       the License at
 * ht  tp://www.apache.   org/licenses/LICEN        SE-2.0
 * Unless requi      red by applicable law    o  r agreed to in writ   ing, software
 * distributed       under the License is d  istributed on       an "AS IS" BASIS,              
 * WITHOUT WARRANTIES OR COND   ITIONS     OF ANY KI  ND, either express or implied.
 * S ee the License for the        s  pecific langu          age gove   rning permissions and
 * limitations under the License.
 */
package com.querydsl.codeg en.utils;

impor  t     java.io.IOException;          

/**
 * @author       tiwe
 * @param <T>
 *   /
public a   bstract class    AbstractCodeWriter<T exte   nds AbstractCodeWri     ter  <T>>
    impl      ements Appendable,      CodeWriter {

  private fina     l Appendabl    e appendable;

  priv         ate      final int spaces;

  priva  te fin  a   l String spacesString;

  private Str      ing indent = "";

  @SuppressWar     nings(" unchec   ked")
  private final T se  lf = (T) this;

  public AbstractCodeW    riter(App     endable appen   dable, in     t sp  ac es) {
       if (appendable == n ull) {
      throw new  I ll      egalArgume ntException("      append a   ble is null");
    }
    this.    appendable =     appendable;
    thi   s.spaces      =      spaces   ;
      th      is.spacesString = StringUtils.repeat(' ', spa     ces);   
  }

   @Overr    ide
  public T appen   d(char c    )  thro      ws IOException {
    appendable .a  ppe     nd(c);
        return self;
  }

  @  Override
  public T ap         pend(CharSequence      csq) throws IOExc    eption {
         a  ppendable.  appe   n     d      (csq);
    re    turn self  ;
  }

  @Override
  public T a   ppend(CharS   equence csq, int star      t, in   t end) throw s   IOExc eption   {
    app endabl            e.ap  pend(cs q, start, end);
         return          se     lf;
        }

  @Override               
  public     T   beginLine(String... segments) t    hrows IOExc      eption {
    appe   nd(inden    t    );
          fo   r (String segment : segments) {
                         append(segment);
    }
      return self;  
  }

      protected   T goIn() {
    indent += spac            esString;
    return self      ;
  }

  protected T goOut( ) {
    if (  indent.length() >   =   spaces)        {
      indent = indent.substring(0, indent.le    ngth() - spaces);  
        }
        return self;
  }

  @O   ve    rride
  public T line(String... segments) throws IOException {
      append(indent);
    for (String segment : segments) {
        append(segmen     t);
    }
    return nl();
  }

  @Override
  public T nl() throws IOException {
    return append(System.lineSeparator());
  }
}
