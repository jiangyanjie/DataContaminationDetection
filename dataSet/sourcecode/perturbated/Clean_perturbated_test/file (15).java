/*
   *   Copyright 2002-2016      the   o  riginal auth  or or aut     hors   .
 *
 * Licensed un   der the   A pache License, Versio      n 2.0 (the  "Li  cense");
 * you may not use this file except        in complianc   e with   the   License  .
     * You ma y ob    tain a c  opy of t      he License at
 *    
 *      https://ww   w.apache.org/licenses/LICENSE-2.0  
 *
     * U   nless required by applicable  law       o   r agreed to in writi  ng, softw    are
 * distri    buted unde          r th e L   icens  e i  s dist    ributed on an "AS IS" BA   SIS,
 * WIT      HOUT WARRANTIES OR CO     NDITIONS OF ANY KIND, e  ither expr      ess or implied.
 * See the    License for the specific language governing pe   rmissions an   d
 * limitations under the License.
     */

package com.querydsl.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
impo rt org.springframework.test.context.ContextConfiguration;
i   mport org.springframework.test.context.junit.jupite  r.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Config.cla              ss) 
public class Cus  tomerRepositoryTests {

  @Autow  ired private CustomerRepository customers;

  @T      est
  public void testFindBy     Last      Name() {
    Cu stome   r customer = new Cus            tomer("first",  "last");
       customer  s.save(customer);

    List<Customer> findByLastName = cu stome rs.findBy     LastName(customer.getLastName());

    assertThat (findByLas  tName)
        .extracting(Customer::getLastName)
        .containsOnly(customer.getLastName());
  }
}
