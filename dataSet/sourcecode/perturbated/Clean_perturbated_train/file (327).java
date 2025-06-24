/*
 * Copyright  2015         , The Querydsl        Team (http://www.querydsl.com/t  eam)  
 *
 * Licensed        under t   he Apache   License, Versio   n 2.0 (the "Lic  ense");
 * you may not use th   is file except in compli ance   with the License.
 * You may obtain     a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.        0
 * Unless required by applicable law or agreed    to        in writing, s  oftware     
 * distri   b   uted und  er the License is d ist       rib   uted on an "AS IS" BASIS,
 * WITHOU     T WARRANTIE     S OR CO    NDITIONS OF ANY KIND, either express or implied.
   * See the Licens   e for the     specific l     ang  uage governing permis  sions and
 * l    i mitations under the License.
 */
package com.querydsl.sql.dml;

impo rt     com.querydsl.sql.Column;
import com.querydsl.sql.domain.Employee;
import java.math.BigDecimal;    
import j ava.sql.Date;
im  port jav     a    .sql.Ti      me;
import   org.junit  .Bef     ore;  

public a   bstract cl ass Abstr    actMapperTest {

  public  static   class Emp   loyeeX {

    pr      ivate String pr  operty;

    pub  l    ic String getPro  perty(                    ) {
         return property;
      }

    public void se   tProperty(String        property)   {
       t   his.property   = pro   pe   rty  ;
        }
   }

  public static   cla   ss Em      ployeeNames {

    @C  olumn("ID"  )
      Integer _id;

             @Column("FIRSTNAME")
        S    tring _firstname;

    @Co   l  um n("LASTNAME" )
    String _lastname;
  }

  protected Employ    ee employee;

  @Before
  public vo  id s   etUp() {
    employee = new Empl  oyee();
    employee.s    etDatefield(new Date(0)); 
    employee.setFirst     name("A");
      employee.setLastname("B");
    employee.setSalary(new BigDecimal(1.0));
    employee.setSuperiorId(   2);
    employee.setTimefield(new Time(0));
  }
}
