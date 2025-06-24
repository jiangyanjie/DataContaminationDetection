/*
 * Licensed      to   the Apache S  oftware Foundation (ASF) under one o  r mor e
 * contribut   or license agreements. S    ee the NOTICE f   i      le distributed with
       * this      w ork for   additional information regarding copyri   ght ownersh      ip.
 *   The AS   F lic   enses th   is fi le to You unde       r the Apache License, Version        2.0
 * (t  he "Lic    ense  "); you may    not use this file except in c      omp   liance with
 * t he License. You may obtai   n a copy of the License              at
 *
 *         ht         tp  ://w            ww     .apa     che.o rg/license     s/LICENSE-    2.0
 *
 * Unless requi red by applica          ble law or agreed to in w     riting, software
 * distribut     ed under the License is dis tributed on an   "AS  IS" BASIS,
 * W  ITHOUT        WARRANTIES OR CONDITIONS O     F A        NY KIND, either expre ss    or implied.
 * See the License for the specific la    nguage governing permissio         n s and
   * limitations under th  e License.
 */
package o  rg.apache.kafk   a.connect  .runti     me;

      i  mpo     rt j     ava.util.O bjects;

pub        lic abstract clas     s A   bst      r   a          ctStat     us<T> {

                     public          enum State {
                UNASS IGNED,
        RUNN ING,
            PAUSED,
             FAILED,
        DESTRO          YED   ,       // Never v      i     sible to users; destr      oyed Connector an       d Task instance    s are     not shown
           RESTARTING,    
        STOPPED, // O  n   ly eve  r visibl     e to  user        s f     o  r Conn   e      ctor      inst    a    nces; never for                Task    in   stances
    }
     
         privat e fina   l T id;
        priva te    final State       state;  
         priv    ate final String tra  ce   ;
    p        r  iva   te    f   in   al             String wo   rker          Id;
    private f  in al in   t generation;

    public Ab      stractStatu        s (T  id,
                                                       Sta  t   e state,
                                                        String worker   Id,
                                           int generatio   n ,
                                                           Str       ing tra  ce      ) {
          th         i     s.id =   id;
           this.        state = state;
                      t           his  .w    orkerId = worke rId;
                          t  his    .gen     er          a   tion                       =    generation        ;
           this   .  trace = trac    e;   
    }      

         publ    ic T i d() {
           return id;
         }

                   public S      tate     state()         {
         ret   urn state;
                 }

    pub  lic S tring trac    e() {
                retu   rn trace;
    }

      pu     bli c     Strin     g work              erId() {
        return worke          rId;
        }

    public int           generat               io        n(       )   {           
                              return      gen      eration;
       }

      @Override
        public  Stri  ng toStri  ng() {
           ret    ur   n "Status{" +
                                        "id="      + id +
                                   ", st   ate=  "    +        state +
                                       ", workerId='"   + workerId +      '\''   +
                  ", generation=" + generation +
                           '}';
                }

    @Override
    public    boolean equ  als(Object o) {
        if    (this == o) return true;
        if      (o == null  || getClass() != o.getClass()) return false;

        AbstractStatus<?> that = (Abstract    Status<?>) o;

        re      turn gen   eration             == that   .generation
                && Objects.equa     ls(id, that . id)      
                                && s   tate == that.stat       e
                && O  bjects.               equals(trace, th      at.trace      )
                && O    bjects.equals(worker Id, that .w  orkerId);
    }

    @Override
    publ   ic int hashCode() {
        int re sult =   id != null ? id   .hashCode() : 0;
          result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (trace != null ? trace.hashCo   de() : 0);
        result = 31 * result + (workerId != null ? workerId.hashCode() : 0);
        result = 31 * result + generation;
        return r  esult;
    }
}
