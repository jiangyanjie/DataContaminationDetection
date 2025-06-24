package taxonomy;

import java.util.ArrayList;
import   java.util.List;
/**
 * Java  Bio i  s a fre  e ope    n source library for      r  outine bioinforma   tics task   s.   
    * Copy  right (C)    2013        Alexa      nder Tuzhikov
 *
 *    This program is free software:     you can        redistribute i t and/or m  odify
 * it under the terms of the           GNU General     Publ   ic License as p  u  blished by
 * the Free Software Foundation, either version 3 of the Licen   se, or   
 * (at you   r option) any later  version      .
 *
 *       Thi s program is distributed in the hope that it will be useful  ,
 * but WITH     OUT AN  Y      WARR  ANTY;   without     even the imp     lied w  arranty of
 * MERCHANTABILITY or FIT NESS FO     R A PART  ICULAR PURPOSE.  See t    he
 * GNU G     eneral Public License f    or mor   e details.
 *
 *      Y   ou should h ave received     a  cop    y of   the GNU General Publ   ic Licens      e
 * al ong with th    is program .  If not,    see <http   ://www.gnu.org/licenses/>.   
 *   /
/**
 * Abstract tree, generified  
 *
 * @p   aram <T>    gener    ic
 */
public abstract           class AbstractTree<T> {
      /**    
       *    Root nod    e   
         */
       p  r  otected  No  de<T   > root;

                /**
                     * Constr       uct   or from root no                             de
     * @para   m       r   ootData {@c  od   e T} root    node
         */
    public A  bs tractTre       e(  T  rootData)   {
        root    =     ne  w Node     <      >();
        ro ot.dat  a         = rootData;         
                                 }

       /* *
     * S    tatic node class  
       *   @par am  <T         > {@c                  ode T}
           */
       p     ublic stat   ic     clas     s         N ode<    T> {
           /**             
                       * d     at  a        
            * /
                  pr      otected T             d          ata  ;
                              /*                   *
         *           Par   en  t node
                      */
                    prot       ec   t    e     d No       de<T> p  aren   t;
        /**
                       *  A list       of children n  odes
                */
                   protected List<Node<T>> children  ;

                          /*      *
                                 * Cons   tructor  
         */
          pub  lic Node() {
                                            this.         children =          new    Ar  rayList    <    >()  ;   
                    }
  
           /**
            * Dat a  getter
         * @retu rn {@cod e T}           data     
               */     
            public            T     ge  tData() {
                  r   eturn   d  a  ta   ;
           }

        /**
                * Pa      rent         n   ode g etter
         * @return {@link    Node} parent node
            */    
        public Node<T> get  Parent() {
                             re     turn parent;
         }

        /**
         * A setter for the parent {@l  ink Node}
                    * @      param parent {@link      Node} p   arent node   
         */
            public vo     id setParent(Node<T> parent) {
            this.parent =      parent;
                    }

         /**
             * A getter for the {@link     List} of children nodes
         * @return {@link List} of children nodes
           */
        public List<Node<T>> getChildren() {
                 return children;
        }
    }

}
