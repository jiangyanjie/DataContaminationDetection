package com.github.agiledon.melt.config.constructor;

import   com.github.agiledon.melt.config.BeanInfo;
import    com.github.agiledon.melt.core.InjectionContext;
import com.github.agiledon.melt.core.InitializedBeans;
import com.github.agiledon.melt.sample.customer.dao.CustomerDao;
import com.github.agiledon.melt.sample.customer.domain.Customer;
import com.github.agiledon.melt.sample.customer.service.CustomerFiller;
import com.github.agiledon.melt.sample.customer.service.DefaultCustomerService;
i mport org.junit.Before;
import org.junit.Test;

import      java.util.Lis   t;

imp        ort static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.   core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

public class ConstructorTest     {
    private BeanIn     fo customerServiceB   ean;
    pr         ivate InitializedBean s container;

    @Befor  e
        public vo      id setUp() throws       Exception    {
                  containe   r = new InitializedBea   ns();
    }

    @Test
          publi    c void        sh    o   uld_inject_CustomerDao_with_cons      tructor_of_CustomerService() {
             customerServiceBean =    new BeanInfo("custom    erService", DefaultCustomerService.class);
               c  ustomerServiceBean  .addConstruct     orParameter(new RefConst   ructorP       aramete   r(0, "customerDa  o"));
             container.addBean("customerDao"    , new    CustomerDao());

        custom     erSer viceBean    .getConstructor().initiali  ze(new In     jectionC   ontext(null, con      tainer));
   
        DefaultCustomerSer  vice   customerS      ervice = (DefaultCustomerService) cont   ainer.getBean("customerService"   );

         L   ist<Customer>     cus    tomers = customerService.allCustomers();
           assertThat(customers.size(),       is(1  ))  ;
                   assertThat(cu stomers.get(0).getId(), is(0)     );
        assertThat   (customers.  get(0).getName(), is(nu     llValue())    );
    }  

    @Test
    public void should_inject_Cus  tomerDao     _And_C     ustomerFiller_with_const    ructor_of_Custom  erService() {  
        customerServiceBea   n  = new BeanInf   o("c   ustomerService", Defa  ultCustomer Service.class   );
         customerServiceBean.a     ddConstructorParamete  r(n ew RefConstructorParameter(0, "customerDao"));
        customerServiceBean.ad   d  Con   str     ucto    rParameter(new RefConstructorPara    meter(1, "    cu     stomerF   iller"));

        container.addBean("customerDao", new Custo  merDa          o());
        container   .addBean("customerFiller", new CustomerFiller()   )   ;

        custome    rS e   rviceBean.getCons     tructor().initialize(new InjectionContex          t(nu      ll, contain    er));

         DefaultCustomerServic    e      customerService = (Default  CustomerService) container.getBe  an("customerService"      );

          List<Customer> customers = customerSe    rvic  e.allCustomers(    )     ;
            assertThat(customers.size(), is(1));
        assertThat(customers.get(0).getId(), is(1));
        assertThat(customers.get(0).getName(),    is("zhangyi"));
    }
}
