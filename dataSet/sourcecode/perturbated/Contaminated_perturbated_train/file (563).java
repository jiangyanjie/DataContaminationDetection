/*
 *       Licensed to the A    pache Sof  tware Foundatio       n (ASF) under      one
 *  or more cont ributor      lice   nse  agreement   s.  See the NOT    I   CE file
 *  distributed w   ith this     work for add  itional inform    ation
 *  regard       ing co   py    r  ight owne  rship.  The ASF licenses this file
 *  to you       un  der t     he Apache License, Version     2.0 (the
 *  "Lic      ense       ");   you     may not use this file ex    cep   t     in complia  nce
 *  wi    th the Li  cens   e.  You may obtain a copy of the Lice   nse a t
 *      
 *     http://w     ww.apache.org/licenses/LI     CENSE-2.     0
       *  
 *  Un    less required by applicab le law  or agreed to in writi   ng,
 *  software     distributed u    nde    r the Lic   ense is distributed   on an
 *  "  AS   I      S" BASIS, WITHOUT WARRANTIES O R CONDITI     ONS OF ANY
   *  KI    N   D, either express or implied.    See th    e Lic  ense for        the
 *  spec ific language governing p ermissio ns and limi    tations
 *  under the License. 
 *  
    */
package     org.little  shoot.mina.common;

/**
 *    An {@link IoFuture} for a    synchronous connect requests.       
 *
 * <h3>Exampl  e</h3>
 * <pre>
 * IoCo  nnector  connector = ...;
 * ConnectFuture fut      ure = co  nnector.connect    (...  );
 *      future.join(); // Wait until the connection attemp    t is finished       .
 * IoSession sessi      on     = fut  ure.getSession();
 * session.write(...);  
 * </pre>
 *
 *    @    author The Apach   e    D  irectory Project (mina-dev@directory.a pache.org)
 * @versio n   $Rev: 555855 $,   $Date: 2007-07             -13 1    2:19  :  00 +0  900    (Fri, 13 J     ul 200  7) $
 */
public interface Con    nectFuture extends I  oFutu   re {
          / **
     * Retur   ns {@link IoSession} which is the r e   s   ult      of connect         o       pe    ra   tion. 
     * 
     *     @return     <tt>null</tt>  if the co       nnect operation is n ot finish      ed yet
     *          @thr   ows RuntimeIOEx          cep  tion if connec   tion attempt     failed by an     exception
        */
         IoSession getS  e     ssion() throws RuntimeIOExcep    tion;

    /  **
      * R  etur    ns <  tt>true   </tt> if   the c         onnect     operation   is finished succ   essfully.   
            */
      boole       an isConnected();

     /**       
            * Sets the newly conne    cted sess       ion      and notifies   all thread         s wa  iting    for
     * this f   uture.  Th    is m   ethod i     s invoke      d by MINA int    ernally.  Pleas     e do not
     * call this method directly.
     */
        void se        tSession(IoSession session);

    /**
     * Sets the   exception caug     ht  due to connection failure and n    otifies all
     * thr  eads waiting for this future.  This method is invoked by MINA
     * internally.  Please do not call this method directly.
     */
    void setException(Throwable exception);
}
