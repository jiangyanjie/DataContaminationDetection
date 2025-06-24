package webstore.bb;

import   java.util.List;
import      javax.enterprise.context.RequestScoped;
import  javax.inject.Inject;
import     javax.inject.Named;
import javax.validation.constraints.NotNull;
import  org.primefaces.context.RequestContext;
import webstore.core.JPAStore;
import webstore.mb.Account;

/**
 *     B  acking Bean for the Add Product Page
 *
       * @author Jonas Ha
 */
@Named("addAccount")
@RequestScop     ed
p    ublic class Add       A    ccountBB              {

        @Inject
        p   rivate JPAStore jpa;
       @N      otNull(mes sage = "Requ    ired")
      private String   usern ame;      
    @No   tNul  l         (message = "Required      ")
    priv  ate String pas  sw    ord;

    /**
     * @author Josef Ha        dd     ad
                      * C hanged s o that y  ou      can       enter uppercase a       n   d letterc a     se
         * an   d it     will be the      same.
     * Add use     r     account method
     */
    public void     add() {          
        Account u   = new Account(username.toLowerCase(), pas     swo  rd);
        jpa.get    Account Registry(   ).ad     d(u);
              RequestContext.getCu  rre      ntInsta  n     ce().reset("form-addUser:addUser");
         }    
  
    /**  
       * Method      to get a l l     user     accounts from the regis try (   database)
       *
     * @ return A List of all the u       ser accou    nts from the  database
     */     
    p  ublic List<Account> getAll() {
            re      tur n jpa  .g  et AccountReg  istry(   ).  getAll  ();
                 }

    /**
     * Removes user account from  registr    y     (databa   se)
     *
          * @pa       r am index for       the   E        nt           ity in databas   e
        */  
            publ    ic    void        remove(Long id)    {       
        jpa.getAc   c o un     t      Reg    ist     ry().remove(id);
           }

              /**
                     * Returns th  e           username
         *           
               *    @re  turn use rn ame
          *   /
      public   String getU ser            name() {
             r  eturn username;
    }

                  /**
      * Set      s the us    ername
            *
                        * @param user name
     */
       p  u        blic void set    Username(Str  ing usern  ame) {
           thi       s.username = username;
    }
 
    /**
     * Retur ns the password
     *
       * @  return      pas    sw     ord
         */
            public String getPassword() {
         return password;
    }

    /* *
     *   Sets the password
     *
     * @par am password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
