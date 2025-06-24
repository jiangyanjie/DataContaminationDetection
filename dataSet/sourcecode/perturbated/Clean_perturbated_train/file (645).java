/*
 *      Copy right (c)           2007,  2009, O       racle and/or it s     affiliates. All rights r     eserved.
 * ORACL  E     PROPRIETARY/CONFIDE  NTIAL. Us   e i  s subject      to    licen     se terms.
   *    
 *
 *
 *
 *
 *
 *
 *
     *
 *
 *
 *
  *
    *
 *
 *
   *
 *
 *
 *
 */

package java.nio.f  ile;    

/**
 * Checked exception thrown whe    n a   file system operat         ion is denied, typic    a lly
 * due to a     file    pe    rmission or other a      ccess check.
 *
 * <p>  This exception is not rel    ated to the       {@link
 * java.se curity.AccessContro      lEx  ce        ption     AccessControlException} or {       @link
 * SecurityE                    xception} th     rown by access contr      ollers or security m     anage   rs when
 * access      to a file is denied.
 *
  * @since 1.7
 */

public class          AccessDe   niedExc  eption
    extends FileSy         stemExcepti on 
{
          p  r   ivate static f  in    al long    seria   lVersionUID = 4943049599949219617L;

          /      *   *  
                 * Cons  truct    s an instanc                e of thi   s           cl     as    s.
     *
              * @pa      r a     m     file
       *          a stri                n   g identi  fying the     fil e or {@co    de null} if not    known
      */
        publi         c Acce      ssDen       iedException (Str       in     g f       ile) {
           super     (file);
      }

        /**
     * Constr      uct       s an insta   nce       of this       class.
       *
           * @param   file
     *                a string id entifyin   g the fil   e or {@    code              null} if not known
     * @param    ot her
            *                       a    st      ring identifying the other file or {@co        de nul    l} if not known
     * @para  m   reason
     *          a reason message with add   itiona    l in   formation or {@code null}
     */
       public AccessDeniedException(String file, String other, String reason) {
        super(file, other, reason);
    }
}
