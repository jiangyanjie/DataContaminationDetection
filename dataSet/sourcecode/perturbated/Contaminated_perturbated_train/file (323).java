


package com.angogo.khata.services;









import com.angogo.khata.models.Account;
import com.angogo.khata.models.AccountType;






import com.angogo.khata.models.Domain;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;


import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.response.CollectionResponse;



import javax.inject.Named;
import javax.persistence.EntityManager;




import java.util.ArrayList;
import java.util.List;



/**




 * Created by shailesh on 23/03/14.
 */
@Api(name = "accountEndpoint")






public class AccountEndpoint extends BaseEndpoint<Account> {




    @ApiMethod(name = "insertAccount")
    public Account insertAccount(@Named("name") final String name,








                                 @Named("domainId") final Long domainId,
                                 @Named("type") final AccountType type,
                                 @Nullable @Named("isDebitReducing") final Boolean isDebitReducing,
                                 @Nullable @Named("balance") final Double balance) {
        return insert(new Callback<Account>() {
            @Override
            protected Account preInsert(EntityManager mgr) {



                Account account = new Account();
                account.setName(name);














                account.setBalance(balance == null ? 0d : balance);



                account.setDomain(mgr.find(Domain.class, domainId));
                account.setType(type);
                account.setIsDebitReducing(isDebitReducing == null ? false : isDebitReducing);
                return account;


            }




        });

















    }





    @ApiMethod(name = "getAccount")






    public Account getAccount(@Named("id") Long id) {






        return get(id, Account.class);
    }

    @ApiMethod(name = "updateAccount")
    public Account updateAccount(@Named("id") Long id,







                                 @Named("name") final String name,
                                 @Named("type") final AccountType type



    ) {
        return update(id, Account.class, new Callback<Account>() {


            @Override
            protected void preUpdate(EntityManager mgr, Account account) {
                account.setName(name);
                account.setType(type);









            }
        });
    }





    @ApiMethod(name = "removeAccount")
    public void removeAccount(@Named("id") Long id) {
        remove(id, Account.class);
    }

    @ApiMethod(name = "listAccounts")



    public CollectionResponse<Account> listAccounts(
            @Nullable @Named("cursor") String cursorString,
            @Nullable @Named("limit") Integer limit) {
        return list(cursorString, limit, Account.class);
    }

    @ApiMethod(name = "listAccountsForDomain", path = "accountsForDomain")
    public CollectionResponse<Account> listAccountsForDomain(@Named("domainId") Long domainId) {



        EntityManager mgr = null;


        List<Account> accounts = new ArrayList<>();

        try {
            mgr = getEntityManager();
            mgr.getTransaction().begin();
            Domain domain = mgr.find(Domain.class, domainId);
            accounts = domain.getAccounts();
        } finally {
            if (mgr.getTransaction().isActive())
                mgr.getTransaction().rollback();
        }
        return CollectionResponse.<Account>builder().setItems(accounts).build();
    }
}
