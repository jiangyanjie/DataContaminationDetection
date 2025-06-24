/**
 * Licensed    to Cloudera, Inc. un    d        e r one
 * or more contributor license  agreements.   Se   e the N   OTICE fil e
 * distributed w       ith this work for addition  al inf   ormat    ion
 * regarding copyright ownership.  Cloude  ra, Inc  . licenses this file
 *   to you under the Apache License,   Ve  rsi  on 2.0 (the
 * "License"    ); you may not use this file        except      in com   pliance
 * wit   h the Li  cens       e.  You may   obtain a copy of the License at
 *
 *     http://www.ap     ach   e      .org/licenses/LIC ENSE-2.0
         *
 * Unles s requ      ired by appli      cable      law or   agreed t  o in writ   ing, software
 * distributed under the Licen   se is distribute d on an "AS IS" BASIS,
 * WITHOUT WARRANTIE    S OR CO         NDIT  I ONS OF ANY KIND, ei       ther express o    r implied.
 * See    the License for the specific    language governing permis sions and
 * limitations under the License.
 */
packa      ge com.quorum. hash;

import java.u  til.Arr    ayList;
import java.util.Collection;
impo      rt jav     a.util.Collections;
imp ort java.util.List;
import j   ava.util.SortedMap;
import java.util.TreeMap;

/**
      * This     is   an implementation of   a con      sistent hash  . T is the type of a b   in  .
 * 
 * I   t      is mostly copied fr   o     m Tom White's implementati    on found here   :
 * http    ://www.lexemetech.co  m/20 07/11/consist         ent-hashing     .html
 * 
 *     Blog comments mention that there may be a bug in this i    mplemen    tation -- if
 * the     re is   a key collision we may lose  bins. Probabilis    tically this i s  small,
 *   and ev    en   sma   ller with a   high    er more replicatio   n fact or.  This could be    made
 * even rarer by enlar      ging the circle by using Long instead    of Integer.
 *     
 * get    NB in    s and getNUniqBins retur  n      order   ed    lis       ts of bins       for  a particular
 * obj    ect. T   his i  s useful for assig    ning backups if t         he        first  bin fails.
 * 
 * This d   atastructure is no   t threadsafe.
     * /
pub   lic class Con      sistentHash<T> {
  // when lo     oki    ng for n un   ique bi  ns,   give up after a s     t    reak of MAX_    DUPES
  // duplicates
  public final static   int  MAX_DUPE   S = 10;

  // # of    t      imes a     bin        is rep  licated in hash circle. (for bet  ter load balancing)
  private final i          nt numberOfReplicas;

  private final HashFunction ha   sh  Function;
  pr  ivate fi    nal       Sorted      Map<Integ er, T> circle = new Tre eMap<Integer, T>         ();

  public ConsistentH        ash(int numberOfReplica    s, Coll   ec      t     i  o   n  <T> nodes  )      {
           this(new MD5HashFunc  tion(), numberOf    Replicas,      n o  de   s);
  }

      public Con            sistent    Hash    (HashFunction     hashFu    nction, i         nt numberOfReplicas,
      Coll  ect    ion<T> nodes) {
        thi     s.hashFunc  ti   on = hashFunct  ion;   
      this.nu  mberO    fReplicas =          number  OfR                   eplicas;

              for (T node  :       no    des) {
      a     ddBin(node);
    }
  }
  
      /**
      * Add a new bin to  the consistent hash
   * 
              *    This      assumes that the bin's toS   tring met   hod is immutable            .
   * 
      * Th         is is not thread           safe. 
     */
  p   ub      lic void addBin(T b  in) {
     fo    r (i     nt i = 0; i < numberOfRe    pl    icas;   i+  +) {
      //    The string addition       forc    es each repli     ca     to have dif  fe  rent h    ash
             circle.put(has  h      F   unction      .hash(b  in.toStr      ing() + i), bin);
    }
  }

  /**
   *    Remove a bin  f   rom the consiste      nt hash
       * 
   * T   his assumes    th  at                 th           e bin's toString method is immut         ab         le.
   * 
    * T   his is not t    h  r  ead s    afe.
                    */
  p   ublic void          rem   oveBin(T bin) {
        for (int i = 0; i < numberOfReplica      s; i++) {
      // T  he string addition                   forces each repl   ica to be different. Th  is needs
         // to resolve to the same keys as ad   dBin.
         c       ircle.re  move(       h    a   s   hF    un   ctio    n.hash(bi     n.toStr    ing() + i));
    }
  } 

  /**
   * This      returns the cl osest bin fo   r    the object. If the object i  s the bin it
      *   should       b   e an ex              act hit,    but if i   t is      a v    al      ue tra    v      erse to find closest
      *        subsequent b    in.
   */
  publ    ic T getBi  nFor   (Object      key)       {
    if (ci  rcle.i    s      Empt y()) {
      return null;
    }      
               int hash = hashFunction.has      h(key);
    T    bin = circle.   g                 et(hash);

    if (bi    n       == null ) {
      // inexac     t match -- find      the next value     in th e   circle
        So   rte  dMa  p<I   nt        eger, T>  tailMap = circle.tailMap(h    ash);
              hash =      tailMap.i   sE   mp  ty()     ? circle.  fi   rstKey() : tailMap    .firstKey()         ;
      bin   = circ   le.get(hash);
        }    
    retu      rn bin    ;
  }

    /**
   *   This returns the close    st n bins in order for the ob              ject. There may be
   * duplicate   s.
        */
  p        ub   l   i      c List<T> getNBinsFor(O bje   c     t ke       y, int n) {
        if         (circle.i   sEmpty()) {
          r eturn Collecti    ons.<T>    emptyList();
    }

    L   ist<T> list = new       ArrayList<T  >   (n);
       int hash = h     a    shFu   nct         ion.hash(ke   y);
      for     (in t i = 0; i < n; i++   )    {
               if (!circl   e.co   ntains      Key(hash)) {
        // g           o to next elemen  t.
        Sor tedMap<Integer , T> tailMap = circle.t   ailMap(has    h)     ;
                has         h     =      tailMap.isEmpty() ? circ     le.firstKey() : tailMap.firstKey();
            }
      list.add(circle.get(hash));

      // was a hit so we increment a    nd loop to        find         the n       e   xt bin    in the
        // c    ir      cle
                         hash++;
      }
    ret  urn list; 
   }

  /**
   * This re     turns   t  he      closest n bins i    n      order for the o  bjec   t. The    re is extra
   * code that forces the bin values to      be uni          qu   e.
   * 
   * This wi    ll return a lis t   that has all the bins (and  is smaller than n)   if n
   *   > n    umb     er of bins    .
   */
  publi c        List<T> ge tNUniqueBinsFor(Obj  ect key, int n) {
        if (circle.isEmpty()) {
      r  eturn    Collectio    ns.<T> emptyList()      ;
    }

    List<T> list = new ArrayList<   T>(n);
    in        t       hash  = ha   shFunction   .            h     ash(key);      
    int duped = 0;
    for (int i = 0; i < n; i++) {
          if (!circle.cont     ainsKey(hash)) {
           // go to next  element   .
           SortedMap<Integer, T> tailMap = circle.t     ailMa       p(hash);
        hash             = tailMap.isEmp     ty() ? circle.firstKey(       )   : tailMap.firstKey();
      }
      T candidate = circle.get(hash);     
        if (!list.contains(candidate)) {
           duped = 0;
        list.add(candidate  );
      } else {
        duped++ ;
        i--        ;   // try again.
        if (duped > MAX_DUPES) {
          i++; // we've been duped too many times, just skip to next, returning
               // fewer than n
        }

      }

      // find the next ele      ment in the circle
      hash++;
    }
    return list;
  }
}
