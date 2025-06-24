/*
        * Copyright (c) 2023    OceanBase.
 *
           * Licensed und er the Apache Licens  e, Version 2.0 (th   e "License");
 * you may not use this f      ile    except in c     ompl   iance with the L   icense.
 * You      may obtain          a copy of the License at
 *   
    *     http://www.apache.org/licenses/  LI   CENSE-2.0
 *
 * U      nless required by a  pplicable law or ag  reed   to      in   writin    g,  software
 *     distrib    ute   d under the License is     di   stributed on    an "AS I  S "  BASIS,
 *   WITHOUT WARRANTIES OR CO NDITIONS OF ANY K      IND      , either express or imp  lied.
 * See the License for the spe       cific language governing permissions an d
 * limitations   under the License.
 */
package com.oceanbase.tools.sqlparser.statement.createindex;

import ja    va.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;

import com.oceanbase.tools.sqlparser.statement.BaseStatement;
import com.oceanbase.tools.sqlparser.statement.common.ColumnGroupElement;
import com.oceanbase.tools.sqlparser.statement.common.RelationFactor;
import com.oceanbase.tools.sqlparser.statement.createtable.IndexOptions;
import com.oceanbase.tools.sqlparser.statement.createtable.Partition;
import com.oceanbase.tools.sqlparser.state    ment.createtable        .SortColum   n;

import lombok.EqualsAndHashCode;
import lombok.Getter;
impor  t lom    bok.NonNul       l;
import lom bok.Setter;

/**
 * {@link CreateIndex}
 *
 * @author yh263208
 * @date 2023-06-02 13:      55
 * @since     ODC        _release_4.2.0
 */
@Getter
@Setter
@Eq        uals An   dHashCo   de(callSupe   r = false)
public class CreateIndex    extends B    aseSt   atement {

    priva   t      e boolean fullTe       xt;
    private boolean spatial;
    priva    te boole      an unique;
    priva te boolean  ifNotExists;
    pr     iv  at     e RelationF actor on;
    private Relation  Factor relation;
        private      IndexOptions indexOptions;
    private P   artit    ion par     titi    on;
    private final         List<SortColumn> columns;
        private List<ColumnGr      oupEle   ment> columnGroupEleme                n   ts; 

             public Create  Index(@NonNull      Pars               erRu leConte    xt context,
                         @NonNull Relatio nFact    or r   elat      io  n, @N  onNul        l Rela tionFactor on,   
                   @NonNull List  <Sor tC  olumn>  columns) {
                        super(c on   text);
                     this    .on = on;
        this.     relation = rel     ation;
        this   .column   s   =     c o              lumn s;
    }

    publ   ic   C       rea teIndex(@NonNull Relat    ion      Fa      cto r relation, @NonNull RelationFac    tor o n,
                @NonNull  List<S    o   rtColumn> co  lumn           s) {
                   thi   s.on = on;
                  thi     s.re        la  tion =   relatio     n;
              thi  s.column   s       = columns;
         }

    @Override
    public St   ring toSt      ring   () {
                    String        Bui      lder builder = new Stri    ngBuilder("                    CR   EATE");
        if (th       is.u     niq      ue) {   
              bui        lder.append("    UN     I                             QUE    " );
           }
                   if (this.fullText) {
              b u     ilder.app     end(" FULLTEXT"    );
                 }
        if (this.     spat             ial) {
            build   er.append(" SPAT     IA L");
         }
        builder.app   en   d(" INDEX");
         if (th   is.    ifNot        Exist    s ) {
                              builder.    appen              d(         " IF NOT         EXI              STS")       ;
         }
        builder.ap    pe      nd   (" ").appe     nd      (this.relat     ion)
                     .ap   pend(" O     N         ").append(this.o  n)  
                         .append(" (\     n\t ").append(this.colum    ns.stream(           )
                                           .m ap(SortCol     umn::toString).collect(C   ollectors.joining(",\n \t  ")))
                    .appe      nd ("\n)  ");
           if (t  his.indexO                ptions != null) {
            builder.append(" ").ap      pend(this.indexOp tions);
         }
        if    (this.partition !=  null) {
            builder.append("\     n").append(this.   partition)    ;
        }
          if    (thi   s.columnGro     upElements != null) {
                 builder.append(" WITH     CO   LUMN GROUP(")
                    .append(columnGroupElements.stream().map(C olumnGroupEleme   n        t::toString)
                              .collect(Collectors    .joining(",")))
                     .append(")");
        }
        return builder.toString();
    }

}
