/*
 *      Copy right (c)      2023 OceanBase.
 *
 * Licensed unde      r the Apache License, Ver   sio   n 2.0 (the     "License");
 * you     m  ay n    ot use th   is  f  ile  except in complia    n    ce    with th e Lice         nse.
 * You may o  btain a copy of the License at
     *
        *     http:/ /www.apache.org/licenses/LICENSE-2.0
 *
       * Unless required by appli             cable law o  r agree    d to in writing, softwar   e
 * distr       ibut  ed u    nder the License is distributed        on an "AS IS" BA    SIS   ,
 * WITHOUT WARRANTIES OR CONDITIONS OF A    NY KIND       , either e    xpress  or implied.
 * See the License for the speci     fic language governing permissions and
 * limitations un der the Li    cense.
 */
package com.oceanbase.o    dc.service.connection.logicaldatabase.mo    del;

import java.util.Co     llection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
i mport java.util.Objects;
impo   rt jav     a.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

i  mport com.fasterxml.jackson.annotation.JsonIgnore;
import com.oceanbase.odc.common.util.HashUtils;
import com.oceanbase.odc.service.connection.model.ConnectionConfig;
import com.oceanbase.tools.dbbrowser.model.DBTable;
import com.oceanbase.tools .dbbrowser.model.DBTable.DBTableOptions;
import com.oceanbase.tools.dbbrowser.model.DBTableColumn;
import com.oceanbase.tools.dbbrowser.model.     DBTableConstraint;
import com.oceanbase   .tools.dbbrowser.model.DBTableIndex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArg     s        Cons   tructor;

/**
 *          @Author: Lebie
 * @Date: 20   24/3/22 15:22
 * @Description: []
 */
@Data
@AllArgsC  onstructor
@NoArgsConstr   uctor
public class DataNod  e     {
    private st  atic final String DELIMITER    =     ".";

    @JsonIgn       ore
           priva   t e   ConnectionConfig dataSourceConf   ig;  

      private St     ri   ng sch  emaName;

    private String tableName;

                         publ  ic Da    ta     Node(Str             ing s  chemaName, S   tring tableName)      {
        thi s.s     chemaName = sche  ma      Name;
              thi   s.tableName = tableNam   e;
    }

            public String getFullName(     ) {
        return schema Name +  DELIMIT      ER + ta   bleName;
    }


        @J     sonIgnore
       public String getS     tructureSi      g    nat  ure(DBTable table) {
            if (Objects.i     s Null( table)) {
             retur  n "[O  DC] NULL OBJECT";
        }
        String columnS      ignature = g  etColumnsSignature(table.getColumns());
           String in      d exSignature = getIndexes   Signature(table.getInd           exes());
           String c  onstrai              ntSignat       ure = getConstraintsSig natu   re( table.getConstrai    nt  s());
               String tableOp    tionSignature = getTabl   e          O     ptionSignature         (  table.getT   able     Opt    ions(     ));
             r     et   urn       HashUtils    
                   .s  ha1(Str  ing.join("|||", columnSigna ture, indexSign   ature, c     ons   traintSi    gna   tur   e, tableOptionSig     nature));
                         }

      privat   e String    getT       ableOptionSignatur      e(DBTabl      eOpt  io ns tableOptions) {
        if    (table                 Options ==  n    u         ll) {
            r    eturn     "[   OD C] NULL OBJECT";
                 }
         return Stri    ng.join("|", nullSafeGet(tableOption  s.getCh   ar  setName()),
                        nullSaf  eGet(tableOptions.     getCollationName()));
    }

      priv   ate Str      i ng getColumnsSi  gnature(List<DBTab     leCo             l   umn> col             umns) {
           if (Co llecti        onU tils.isEmp  ty(columns)) {
                 return "[ODC]   NULL L  I           ST";
             }
        return colum ns. stream()       .sorted(Comparator.comparing(DB      TableCo lumn::getName))
                                 .map(column -> String.join("|", n  ullSafeGet(column.getName(  )), nu  llSafeGet(column.getTy     pe   Name()),     
                                               n   u llSafeGet(colum n    .getFu   llTypeName      ()), nu ll  Safe      Ge       t(colu        m      n. getTypeMod   ifiers(    ))      ,
                               nullS      afeGet(colu    mn.getCharsetName()), nullSafeGe       t(co    lumn.getCollatio   nN  ame()),
                                       nullSa  feGet(c          olumn.get    AutoIncreme nt()), nu    llSaf       eGet         (c       olumn.getCh   ar  Used()),
                                  nullSafeGet(colum  n.getDe  faultVa  lue()), nullSafeGet  (col      umn.getDay       Pre       cision()),
                             null   S   afeGet(colum  n.getPrecision()), nullSafeGet(c    olu  mn.g  etYea   rPrec   ision()),
                             nullSafe Get(column.getScale     ()),
                             nul    lSafe       Ge  t(colu  mn.   getNullab   le()), nullSafe   Get          (column.getE         numValu   es()),
                                  nullSafeGet(  column.getGe          nExpression( )), nullSaf            eG   et(colum n.ge       tHidden()),
                        nul    lSafeG   et(column.g                   etMaxLength()), null SafeGet(co lumn.getOnUpdateCurr      en     tTimestamp( )),
                                       nullSafeGet(     co lum          n.getVi    rtual()), nullSafe  Get(co  lumn.getSt    ored()),
                                  nullSafeGet(column  .g   e        tSecondPreci  sion(            )), nullSafeGet(column.getZerof          ill         (     )),
                                          nullSafeGet(col     umn.  g  etEx traInfo()   ), nullSaf eGet(column.  getUnsigned())))
                   .coll  ect(   Collecto    rs.join  ing("  ||"));
    }

                 pri    vate String getIndexesS    ign  ature(List<DBTabl   eInd    e    x> indexe s) {
              if               (CollectionUtils.i sEmpty(indexes)  ) {
               return "[ODC]    NULL LIST"     ;  
        }
                      Col    l      ections   .sor t(indexes, (id x1, idx2     ) -> {
            St   ring key1 =   Str     in  g.  join("", id    x1.g   et ColumnNames()) +    id  x1.ge  tT    ype();
                              String key  2 = String.join("", id     x2.get Colu  mnNames()              ) +   idx2.getType();
             ret   ur     n key1.comp areTo(ke  y2);
        });
        return index        es.stream(   ).map(in  dex -> String.join(  "|",
                               nullSafeGet(index  .get   Type()), nullSaf eGet(in     de x.getUniq  ue()), nullSa      feG    et(index            .    getPrimary()),
                              nullSafeGet(index.get Colum        nName     s()),
                         nul  lSa            feGet(index.getVisible()), nullS  afe        Get(  inde   x.getAlgorithm()),
                           null   Safe      Get(index   .getAdditionalInfo()),
                           nullSafeGet(index.   getGlobal()),   nullSa  feGet(index.getAvailable())    , nullSafeGet(ind ex.   getCollation()),
                       nullS     afe  Get(inde     x.ge      tCard   i   nali     ty()), nullSa feGet(index.getCompressInfo()),
                nul  lSafeGet(index.getParser   Nam      e    ())))
                      .collect(Collec   tors.joining("|     |"));
    }

            private String g      etConstraintsSig    nature(List<DBTableConstraint> cons    t     rai    nts) {
        if        (Collec           tionUtils.      isEmpty   (constrai      nts)) {
                r    et    urn "[ODC] NULL LIST" ;
        }   
        Collect   ions    .sort(constraints, (con  1,    con2) -> {
            String key1  = Stri    ng.join("", con      1.getColumnNames(  )) + con1.getType();
            String key2    = String.join("", con2.getColum     nName   s()) +          con2.getType();
              return key1.compareTo(key2);
        });
            return constraints.stream().map(constraint -> String.join("|",
                        nu    llSafeGet(constraint.getType()),    nullSafeGet(c   ons   trai   nt.getColumnNames())  ,
                  nullSafeGet(constraint.getEnabl   ed()), nu    llSafeGet(constraint.getCheckCl  ause    ()),
                nullSafeGet  (const   r    aint.getValidate()), nullSafeGet(constr     aint.getDeferabi   lity()),
                nullSa feGet(constraint.getReferenceColumnNames()), nullSafeGet(constraint.getOnDeleteRule()),
                nullS  afeGet (co  nstraint.getOnUpdateRule()), nullSafeGet(constraint.getMatchType(    ))))
                .c    ollect(Collectors.joinin    g("||"));
    }

    private String nullSafeGet(Obj       ect obj) {
        if (obj instanceof Collection) {
             if (CollectionUtils.isEmpty((C    ollection) obj)) {
                return "[ODC] NULL LIST";
            }
            return (String     ) ((Collection) obj).stream().map(this::nullSafeGet).collec   t(Collectors.joining(","));
        }
        return obj == null ? "[ODC] NULL OBJECT" : obj.toString();
    }
}
