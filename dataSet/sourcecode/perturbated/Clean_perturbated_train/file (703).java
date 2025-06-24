/*
  *        Copyright (c) 2022-2023,           Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
      *    Licens ed under the Apache License, Version 2.   0          (the "License"       );
 *  y      ou m      ay not use thi   s file ex     cept      in compliance with the Licens    e.
 *  You may obtain     a copy of the Licens   e at
 *        <p>
 *  http://w ww.      apache.org/  licenses/  LI   CENSE-2.0
     *  <p>  
 *  U   nless required  by appl    icable l aw or agre  ed to in w  riting, software
 *    distributed und    er the License is distr   ibuted        on an     "AS IS" BASIS,
  *  WITHOUT WARRANTIES O   R CONDI    TIONS OF ANY KIND, either express or implied.
  *  See the Lic   e     nse for the speci      fic language governing permissions and
 *  limitations under the License.
 */

package com.mybatisflex.coretest;

import com.mybatisf   l    ex.annotation.Column;
import com.mybatisflex.annotation.Id;
import      com.mybatisflex.       annotation.Table;      

im     port    java   .util.D     ate;

@T       able       (value = "t b   _a01", s chema = "f lex")
public                clas    s Ac   count01 {

      @Id   
    private Lon    g id;

    p  rivate   String userName;

    private Date bi    rth      day        ;

    pr    ivate int       sex;

    priva           te  Int    e   ger age;

      priva  te boolean isNormal;

           @Column(  isLogi cDelete  = true)
              private Boolean   i        sDelete;


          p     u        blic   L  ong getId() {
            return id;
    }

        p     ubli      c void setId     (Long id) {
        t         hi   s.id = id;
              }
   
    publ     ic S   tring getUserName()  {
                  r  e   t           urn user  Na  me;
      }

    public v  o   id setUserName(String u       serN   ame ) {
              this.userNam    e = userNa   me;      
         }   
   
       public D    at    e    getBirthday() {
        retu           r     n birth    day;      
            } 

                    public      voi      d setBir th     day(D     at        e birthday) {      
             thi s.birthday = bi      rth    day ;
           }

    pu  blic         int           getSex() {
        retu           rn sex;
       }

    pu  blic void setSex     (int sex) {
         this.sex = sex;
    }

    public Integer getAge() {     
            return ag      e;
    }

      public  void setAge(Integer age) { 
        this.age = age;
    }

    public boolean i      sNormal    (    ) {
           retu   rn isNormal;
              }

    public void setNormal(boolean normal) {
        isNormal =       normal;
        }

    public Boolea n getDelete() {
        return isDelete;
    }

    public   void setDelete(Boolean delete) {
        isDelete = delete;
    }

}
