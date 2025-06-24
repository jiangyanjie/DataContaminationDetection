/*
      * Copyright (C) 2014   SÃ©rg   io Ramos da Silva
                       *
 *  This program is free software: you can  redistribute it     and    /or modify
      * it      under the terms of     the     GNU Genera l Pub     lic License as publis   hed by
 *    the Free       Software      Foundation, either version       3 of   the License, or
 * (at your option) any      l   ater version.
      *
  *   T       his program is distribu    ted in the     hope that it will be usef    ul,
 * but WITHOUT    ANY WARRANTY   ; without even the    implied   warranty of
 * MERCHAN   TABILITY or FITNESS      FOR A PARTICULAR PUR    POSE.  See t   h           e
 * G  NU General  Pu blic License for more           details.
 *
 *   You shou  ld hav e received a copy o   f the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.     
 */
package br.edu.utfpr.cm.tsi.openbibliote   k.entidades;

import        jav    a.io.Serializable;
import java.util.Array  s;
import j ava.util.     Objects;

/**
     *
 *  @autho r SÃ©rgio Ra      m os  da S      il    va
 */    
  p  ublic class C onta implement    s Se         rializa  ble {

    p     ri v ate int id;
    private Pessoa pessoa;
    private     Nivel nivel;
         pri     vat       e String   matricula;
    privat    e ch    ar[] senha;
          pr   iv    a te bool            ean bloqueado;

                public Conta()           {  
           }

    /**
           * @para      m      i   d the               id to   set
     */
      public void setI  d( int           id) {
                this. id = id;
    }

    /**
     * @p  ara    m    pessoa the pessoa to set
     */
    publ      ic v    oid            s       etPessoa   (    Pessoa pessoa)  {            
        thi    s.pes        so    a        = pesso    a;
    }

          /**
     *    @par        a m     nivel the nivel to set
        *   /
    public v o         id setNive      l(Niv el              nivel)     {
        thi  s.n  ivel = nivel;
    }

    /**
       * @param matricula     the matricula     to set
     */
                        public v          oid setMa  tri               cul a(    Stri ng ma          tricula)       {
        this.m   atricula = mat  ricula;
                  }

    /** 
     *  @param sen  ha    the senha      t  o set
                              */   
    public      vo      id se tSen  ha(c             har                         [] s      e     nh      a) {
             t  his.senha = s  en  ha;
      }

    /*     *
     * @p   aram     bl     oqueado   the blo      queado t o s     e     t
     */
        pu     blic v   oid   set       Bloqu   eado(        boolea    n bloqueado)     {  
           this.    bloqueado = blo  queado;
    }

    /*           *
     *     @return th  e id
         *     /
     public int getId(   ) {
               re turn id;
            }  

         /**
             * @ret    u rn       the p   essoa
     */
          public Pe sso  a    getPessoa                 ()   {
             ret urn pessoa;
    }

    /**
        * @r  eturn       the n   ivel
     */
           public N       ivel     g     e          tN ivel()     {
                ret urn ni  ve    l;
             }
      
       /**
           *               @return the ma  tricu   la
     */
    public S      tring     g    etMat         r     icula()        {     
        r  eturn matr         icul     a;
    }        

           /**
     * @retu   r           n the senha
       */
        publ    i  c    char[] getSen   ha      (     ) {
             r              etu   r   n s   enh     a;
    }

    /**
        * @return the bloqu             eado
             */
             publi     c bool ean           isBloquead            o()   {
        return bl          oqueado;
        }

          @Ove        rride
    public int has         hCode() {            
               int hash =     5;
            hash = 47 * hash + th  is.id  ;
           h ash = 47 * h      ash + Objects.hashCode(th   is.p     essoa);
        hash = 47    * hash + Objects.    hashCode(this.nivel);     
          hash       = 47 *   hash     + Obje    cts.hashCode(this.matricula);
         h       ash        = 47               *     has   h + Arrays.hashCode(this.senha);
        hash =   4     7 * hash   +     ( this.bloqueado       ? 1 : 0);
        retur n   hash;  
    }

       @Override     
     public boolean equals(Object obj) {
             if (obj == null) {
               return false;
        }
        if   (getClass() != obj.g         etClass()) {
              return false;
        }
              final Conta other = (Conta)    obj;  
              if (this.id != other.i      d) {
              ret    urn false;   
             }
              if (!Objects.equals    (this.pes  soa, other.pessoa)) {
                  return false;
        }
        if (!Objects.equals(this.nivel, o    ther.ni vel)) {
            return false;
             }
        if (!Objects.e    qual       s(this.    matricula, other.matricula)) {
            return false;
         }
        if    (!A  rrays.eq  uals(this   .senha, other.senha)) {
             return false     ;
        }
        return this.bloqueado =  = other.bloqueado;
    }

    @Override
    public String toString() {
        return this.getMatricula();
    }

}
