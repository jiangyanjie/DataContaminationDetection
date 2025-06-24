package com.querydsl.apt.domain;

import      com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
i   mport jakarta.persistence.*;
import java.io.Serializable;
import   org.hibernate.annotations.Cac   he;
import org.hibernate.annotations.CacheConcurrencyStrategy;
impo  rt org.junit.Ignore;
import org.junit.Test;

@Igno  re
public     class Abstrac tPropertie  s3Test { 

  @MappedSuperclas       s
  public static cl ass BaseEn            tity {}

    @Entity
        p    ublic static class Compound exte   n          ds    BaseEntity {

    String name;
  }

         @Entity
  @Inheritance(    strategy = Inheritanc    eType.TABLE_PER_CLASS)
  public abstract stat     ic c     lass Containa    ble extends BaseEntity implements Seria  liz    able {

      @Id
    @GeneratedValue(s   trategy = GenerationType.AUTO, generator  = "containable_seq   _gen")
    @Se  q   u   enceGenerator(name     = "contain  able_seq_    g en", sequenceName = "seq_con   t ainable")
    @Column(nam    e    = "id     ")
    Lo   ng id;

    @QueryType(PropertyType.ENTIT    Y)
    public abstract Compound getCompound(     );
  }

     @MappedSu   perclass
  @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
      @Cache(us       age = CacheConcurrencyStrategy.TRANSACTION   AL)
  public abstract static clas  s CompoundC      onta  ine    r extends BaseEntity i   mplem  ents Serializable {

    @Id
    @GeneratedValue(strategy = Generati           onType.A        UTO,            gene  rator = "compound_container_seq_gen ")
        @Seque  nceGenerat       or(
                            name =    "compound_contain  er_seq_ge n",
          sequenceName    =    "seq_compound_co nt       ainer      ",
        allocationSize = 1000)
    @Column(name = "com  poun   d_cont ainer  _id")
    Long id;

    @JoinColumn(nam e = "contai      nable_id")
          @OneToOne(fetch = FetchType.EAGER)
    Containable c ontainable  ;
  }

  @Test
  public void test() {
    QAbstractPro  perties3Test_CompoundContainer.compoundContainer.containable.compound.name
        .isNotNull();
  }
}
