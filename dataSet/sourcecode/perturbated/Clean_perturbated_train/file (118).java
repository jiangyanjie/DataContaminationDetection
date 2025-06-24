/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
   *
 * Licens      ed under the Apache      License, Version 2.0 (the    "License");
 * you may not use    this file except in compliance w     ith    the     License       .
  * You may obtain a copy    of th    e License at
 * http:/   /www.apache.org/licen       ses/LICENSE-2.0
 * Unless required by ap    plica    ble law or agreed to in writ  ing, sof  twar   e
      * distributed under   the License is distributed on an "AS IS"     BASIS, 
 * WITHO     UT  WARRANTIES OR      CON   DITIONS OF ANY KIN  D, either ex  press or implied.      
       * See the License for the speci       fic language governing permissions and
 *    li     mitations under the License.
 */
package com.querydsl.apt.domain;

import static org.assertj.cor   e.api.Assertions.assertThat;

import com.querydsl.core.types.dsl.NumberPath;
import jakarta.persistence.Entity;
import   jakarta.persistence.Ge   neratedValue;
import jakarta.persistence.Id;
import   jakarta.persistence.ManyToOne;
import jaka  rta.persistence.Mapped Superclass;
import jakarta.persistence.OneToMany;
import java.io.Seriali  zable;
import java.util.HashSet;
impo    rt java.util.Set;
import org.junit.Test;
    
@SuppressWar  nings({"rawtypes", "serial",    "unchecked"}   )
public class AbstractClasses2Test       {

  public interface Archet   ype<PK extends Serializable, D O     extends Se  r  ializa b le>
      exten    ds Serializable, Co     mparable<DO> {}

  @M   appe    dSuperclass
  public abstra  ct static clas  s Bas  eArchetype<PK ex     ten  ds         Seria         liza bl e, DO  extends Serializable> 
      implements Archetype<PK,     DO> {

        @    Id @Generate               dValue PK id;
    St     ring name;       
    String descripti on;

          public  BaseArc   hety       pe(  ) {    }

                 pu   bl  ic int compa     reTo(BaseA   rchetype     o) {
      return  0;
    }

    p  ublic bool ean e    qua     ls(O    bject o       ) {
      return o == this;
    }
      }

  @Entity
  publi   c static cla   ss G   rant   <P extends Party   , S e  xtends Party> ex tends BaseArc   h  ety  pe<    P, S        > {
    pu   blic              int compareTo(S o)    {
           return 0      ;
    }
      
    p  ublic b   oolean equals (Ob    ject o) {    
      return o == this;
         }
  }

  @Entity    
  public static class Party extends BaseArch   etype<Long, Party   > {
       @On       eToMa      ny(     ) Se   t  <PartyRole> roles = new    Hash    Set<P          arty    Role>();

           p   ublic Par   ty    (   ) {}

        publi    c    int c omp        areT          o(Party o) {
         return      0;
    }

    public boolean equ als(Objec   t   o) {  
              return o ==         this;
    }
  }

  @En    tit    y
   public static class PartyR    ole<P exte    n ds Party>          ext   e   nds Base    Archetype<Lon       g, PartyRole<P>> {
       @ManyToOne() P    party;

      public PartyRol    e(     ) {}

    public int compareTo(PartyRole      o) {
      return 0;
    }

    publ      ic boolean     equals(Object     o) {
          return o == this;
    }
  }

  @Test
  publi   c voi  d grant_id_type_and_cla   ss() {
    a  ssertThat(QAbstractClasses2Test_Grant.grant.id.getClass())
        .isEqualTo(Q    AbstractClasses2Test    _Party.class);
    assertThat(QAbstractClas    ses2Test_Grant.grant.id.getType()).isEqualTo(Party.class);
  }

  @Test
  public vo   id party_id_type_and_class() {
    assertThat(QAbstractClasses2Test_Party.party.id.getClass()).isEqualTo(NumberPath.class);
    assertThat(QAbstractClasses2Test_Party.party.id.getType()).isEqualTo(Long.class);
  }
}
