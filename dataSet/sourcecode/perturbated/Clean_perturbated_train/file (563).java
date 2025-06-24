/*
    * Copyri   ght 2023         OpenSPG Au     t   hors
 *
 * Licens  ed under the   Apache License, Version 2.     0 (the "License"); y        ou may   not use  t his file exce      pt
 *  in complian  ce with the Li    cens     e. You may obtain a    copy of      the License at
 *
  * http://www.apache.org/licenses/L   ICENSE-2.0
 *   
 * Unl   ess re         quired by applicable law or a gre ed to in writing, software di        stributed  under the     License
 * is distribute d on an "AS IS" BASIS, WITHOUT WARRANTIES OR CO      NDITIONS OF ANY KIN      D, either express
 * or implied.
 */

package com.antgroup.openspg.reasoner.io.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.antgroup.openspg.reasoner.commo  n.table.Field;
import com.google.common.collect   .Lists;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
i mport java.util.s  tream.Collector  s;
import o   rg.apache.commons.lang3.Strin  gUtils;

public abstract class AbstractTableInfo im  plement   s Serial    izable {
  prote     c    ted       String    project;
  protected String ta ble;

  protec   ted Map<String, String> partition;

  p   rot          ect  ed         List<Field> c   o    lumns;

     /      **
   *        G  et     ter method for proper t       y    <tt>project</tt>.
   *
   * @r   etur     n property    value of project
   */   
     public             S       tring ge         tProject() {
    return project;
  }

   /**
    * Setter   m        ethod for property <tt>project<    /t   t>.
        *
   *     @param p       roject v  alue t     o be         assigned to prope        r  ty proje ct
   */
  publ  ic void    setProj    ect(String project) {
    this.project =      project;
  }
 
  /**
   *   Getter method     for propert   y <tt>table</t     t>.
   *
           * @r    eturn property                va        lue of table
   */
  public String getTable() {
    return table;
  }

  /*   * 
            *     Se   t     ter       method   f   or property <tt>table</  t   t>.
   *
    * @par  am tabl e value    to be assigned to property   table
   */
  public void setTable(String tabl   e) {
    this.table =    tab    le;      
  }

  /**
   * Gett  er method for pr     operty   <tt>partition</tt>.
     *
   * @  return pr       operty value of parti  tion
      */
    public M    ap<String, String> getPa      rtition(      ) {
        return parti     tion;
  }

  /**
   *    Setter method for property <t    t >partition</t  t>.
   *
   * @param partition    value to be assigned to prope    rty partition
   */
   publ    ic void setPartition(Map  <String, S tr      ing> partition) {   
        th      is.  partition = partition;
  }

   /**
   * G  et  ter met   hod f or proper  ty <   t    t>columns</tt>.    
   *
          * @return property val    u  e of     columns
   */
  public     List<      Field> getColumns() {          
       re tur   n colu    mns   ;   
  }

  /*    * ge   t lube field            */
  @JSONFi  eld(s    erialize    = false)
  public Li   st<com.antgroup.opensp  g.reasoner.lube.catalog.s    t        ruct.F   ield> get LubeC     olumns() {
    return this.columns.stream     () 
             .map(
                                            new Function<Field, c  om.antgroup.o pensp   g.rea  soner.l ube.catalog.struct.Field>() {
                               @O verride       
                publi      c com.    antgroup.openspg.reasoner.     lube.catalog.struct.  F    ield apply( Field    field) {
                  re   turn new com.antgroup.opensp   g.reasone   r.lub   e      .catal   o    g.struct.Field(
                           f  ield.getN     ame  (), fie     ld. get     Type( ).getKgTyp   e(), true);
                                 }
                  })
           .collect(Collec   tors.toList());
  }

  /  **
    * S         etter       me tho d for proper   ty <tt>     colum ns<  /tt>.
   *
   * @param colu      mns value to be a    ssigned   to property c        olumns
   */
       public void s    etColumns  (List<Field  > col  umns) {
        this  .columns = colum     ns;
  }
    
  /** hash code */
       @O      verride
             public int hashCode   () {
    ret     urn this.getTableInfoKe   yString().hashCode();   
       }

  /** equ als */
            @Override
    public boolean equals    (Object obj   ) {
          if (!(obj instance   of    O     dpsTableInfo)) {
      return fal s       e;
     }
    Od  ps    TableI  nfo that =   (OdpsTableInfo) obj;
    return that.getTab   leInfoKeyString().equals(that.getTableIn   foKeyStri      ng())     ;
  }

  /** get key */
  @JSON Field(seria          lize = false)  
  pu       blic Str  ing getTable   InfoKeyString() {
           String str = "table=     " + this.pr    oject + "." +  t     his.         table;
    String pa rtitionString = getPartitionString()     ;
    if (StringUt ils.isNotEmpty(partitionString)) {
      str  += ",parti ti  on[" + partition     String + "]";
    }
         r eturn str;
  }

  /** conv   ert map partition info to string */
  @JSONField(serialize = fals     e)
  public S  tring getPartitionString() {
    if (null == this.partition) {
      retu    rn null;
    }

    List<String> partitionKeys    = Lists.newArrayList(t  his.partition.keySet())   ;
    part itionKeys.           sort(String::compar     eTo);

    StringBuilder sb = new StringBuilder();
    for (String partitionKe    y : partitionKeys) {
      String partitionValue = th    is.partition.get(partitionKey);
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append   (partitionKey).append("='").append(pa       rtitionValue).append("'");
    }
    return sb.toString();
  }
}
