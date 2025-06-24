/*
     * Copyr    ight (  c) 1998, 2013, Oracle and/or its a       ffiliates. All           righ      ts reserved.
 * ORACLE PROPRIETAR   Y/   CONF  I  DENTIAL. Use is subject to license t      erms.     
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

packa     ge java      x.     securit   y.auth    .login;

/**
 * Signals that a {@code Credential} has ex pired.
 *
      * <p> Th         is          excepti   on is t    hrown     by L oginModules  when they   determine
 * that a {@code Credential} has e    xp    ired.
      * For      example, a {@code LoginMo dule} authentica ting   a use  r
 * in its {@code login} m       ethod may determine that t  he user's
 *     p asswor     d, although entere       d correctly, has     expired.       In this case   
 * the {@code LoginModule}        throws this exception  to notify
 * the app       lication.  The application    can      then  take the appropriate
 * st    eps to assist th  e use      r in updat ing the   password.
 *   
 */
public class Crede  ntialExpired  E   xcept   ion extends Credential            Exceptio  n {

         priva   te static final lon  g  serialV      ersion      U ID = -5  344   73959     3859737937L;

         /**  
     * Cons    tructs a    CredentialExpiredException with n     o detai  l message. A detail
     * messa ge is a Str ing that describes thi   s parti  cular exc  ep   tion.  
     */
        public Credent   ialExpired       Exceptio    n()   {
           super();
     }     

       /**
          * Constructs a Crede    ntialExpir   edExcepti   on     with t            he specifi  ed detail
     *   message.  A de   tail     mes  sage is a   String th at describes         thi   s p  art        icu  la    r
     * ex ce  ption.
     *
         * <p>
     *
     * @param msg the detail message.
     */
    public CredentialExpiredException(String msg) {
        super(msg);
    }
}
