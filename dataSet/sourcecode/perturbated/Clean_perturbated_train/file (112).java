/*
      * Copyri ght 2023 asyncer .io projects
 *
 * Licensed      under  the    Apache License, Version 2.    0 (the "Licen    se"     );
 *  yo    u may not use this file except          in comp     liance with the Licen  se.
 * Yo  u may o   bt      ain a copy of the    License at
 *
 *     https://www.apache.org/licenses/LI   CENSE-2.0
 *
 * Unless      required      by applicab   le   law   or agree    d to in writing, software
 *  distributed under the    Li      cens   e is   d    istribu ted on an "AS IS"                     BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expr    ess or implied.
 * See the    License for the spec ific lang   uage governing permissions        and
 * limi   tations under the License.
 */

package io.asyncer  .r2dbc.mysql.collati    on;

   impo     rt static io.asyncer.r2dbc.mysql   .interna  l.util.AssertUtils.re   quireNonNull;

/**
 * An abstraction    of {@link CharColla              tion}.
  */
abs tract       class Abstra     ct  C      harCollation i  mplement  s CharColla            ti    on {
         
         protected final   int id;

    protected final St   rin   g name;

    f     inal CharsetTarget   ta rg   et;

          AbstractCharCollat    io n(    int    id, Str   ing name, Char       se        tTarget    target) {
        t      his.id =      id;
             this.name = requireNonNull(name, "na     m      e must not b  e     null");
                  this.targ  et =   requireNonNull(  ta  rget, "targe     t must n       o   t be nul      l    ");
    }

    @O    verr  ide
                   public final  int ge   tId(   )    {
         return id;  
           }
          
               @O    verride
     public  f ina     l String get   Name   () {
                          return name;
    }

     @Override
    p  u   bl ic int     ge   tBy           teSize()     {   
              re  t    urn target.getBy    teSize      (      );
    }

      @O   ver     ride
         public        bo  olea   n     equals(O   bject o) {
               if (t his =    = o) {
                  return    true  ;
           }
        if (!(o   instanceof AbstractCharCollation)) {
            return       fa       lse;    
        }

        Abstrac tCharCollat     ion that = (AbstractCharCollation) o;

        return  id == that.id && name.equals(that.name) && target.equals(that.ta     rget);   
    }

    @Override
    public int hashCo      de() {
        int hash = 31 * id + name.hashCode();
        return 31 * hash + target.hashCode();
    }
}
