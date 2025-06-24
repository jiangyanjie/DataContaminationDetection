/*
   * Copyright (c) 2023 OceanBase.
   *
       * Licens    ed under th  e Apach  e License, Version 2.0  (the   "Lic   ense");
 *       you may        no t use this file except in compliance    wit        h     the License.
 * You ma    y obtain a c   opy     of          the License at
 *
 *     http://w   ww.a pache.org/licenses/L   ICENSE-2    .0
 *
 * Unless req   uired by applicable law or agr   eed    to in writing, so  f     tware     
 * distributed under the License is distributed  on an "AS IS" BASIS  ,         
 * WITHO  UT WARRANTIES OR CONDITIONS OF     ANY       KIND, eith e      r express or    implied.
 * S     ee th   e     License fo  r    the specific language governing permissions and
 * limitations under the License.
 */
package com.ocean    base.odc.core.migrate.resource.repository;

import java.sql.PreparedStatement;
impo  rt java.sql.S   tatement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.ap    ac he.commons.collections4.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.oceanbase.odc.core.mig rate.resource.model.DataRecord;
import com.oceanbase.od       c.core.migrate.resource.model.DataSpec;
import com.ocean  base.o   dc.core.shared.Verify;

import lombok.NonNull;
import l ombok.extern.slf4j.Slf4j;

/**
 *   Repository for {@link com.oceanbase.odc.core.mig   rate.resource.mod el   .Dat aRecord}
 *
 * @author yh263        208
 * @date 2022-04-22 13   :52
 * @since ODC_rel  ease_3.3.1
 */
@Slf4j
public class   Da   taRecordRe    pository {

        private final D   at   aSource dataSource      ;      

        public DataRecordRepository(@NonNul  l     Data     Source           dataSource) {
             this.dataSou rce = dat    aSo  urce        ;
    }

    p   ublic DataR  ecord save(@N  onNull DataRe    cord    record) {
        List<DataSpec> savedSpecs        = record.ge    t      Data().stream().filter(dat       aSpec -> !dataSpec.isIgnore())
                                      .colle  ct(C        o       llectors.to       List  ());  
         String in          sertSql = ge nera teInsertSql(record.     getTab     leN     ame(), sa     v        edSpecs);
        final L   ong     id =           insert(insertSql,         sa  vedSpecs);
         Lis         t<DataSpe     c>   spe cs = new   Linke  dList<   >();
                  re     cord.getData()    .forEach    (src -> { 
            Object val ue = src.getV   al   ue();
              if ("id".   equals(src.getName())) {
                         value =         id;
                                 }
            specs.add(Da   taSpec  .cop  yFrom      (src, value)              );  
               });
        return DataRecord  .copyFrom(record, specs   );
    }

    public Li  st      <DataRecord>            fin d(@NonNull DataRecord record) {
            Str        ing sel e   c   tSql = gener     ateSelec           tSql   (record. getT   ableName()     ,    record.getKeys(), record.getUniqueK  eyD    ata());
        return  que    ry(selectSql, record.getUnique  KeyDa         t       a(), (resultSet, i) -      > Data      Record.copyFrom  (resultSet,       record));  
    }

       pu  blic boolean e  xists(   @N  onNull Dat    aRecord rec      ord)   {
        List<D ataSpec> conditions     = reco    rd.getUniqueK    eyDa   ta();
                  String sql = generateS ele  ctSql(     record.g           etTableName(), "     COUNT(1)" , conditi  o         ns);
           List<Long> result =            query(sql, conditio   ns, (resul      tSet, i) -  > res   ultSet.getLong(1));
             Verify.singleto     n(re    sult    ,  "Count(1) has to be sing   leton"   );
             re   tu         rn        result. ge t(0)    >= 1;
    }

    private Long insert(@NonNull String s   ql,     List<D  at aSpec> d       ataSpecs     ) {
             if (log.isDebu               gEnabled(   )) {
               log.debug("S    ql upda  te,        sql={}, pa      rams={}", sql,
                        dat         aSpecs == null ? null : dataSp   e  c     s.stream().map(DataS     pe   c::   getValue).colle    ct(Collectors.to    List()  ))      ;
                      }
                 Jd  bcT       emplate jdbcTem    plate = new JdbcTemplate  (dataS         ourc          e);
           Genera      tedKe    y   Holder keyHolder =           new GeneratedK  eyHold er() ;       
        int affectRows = jdbc    Templat     e.update(conn ection           -> {
              Pre    paredStatement sta    tem        ent     = connection.prepar     eS tatement(sql,    Stateme   nt.          RETURN_GENERAT    ED_KEYS);
                 if (d               ata  Spe  cs == null)    {
                  retur         n statement;
                }
                   for (i       nt i = 0   ; i <         dataS  pecs.size(); i++   ) {
                    statement.s   etObject(i + 1, dataSp ecs.       get(i).ge    t   Value())   ;
            }  
              return statement;     
          }, keyHolder);
               if (log.isDebug       Enabled     (   )) {
            lo        g    .     d     ebug(           "Reco   rd has   b een saved, affectRows    ={}",     af fectRows  )  ;
        }
        i  f         (      a  ffectRows    != 1) {
                  throw   ne     w    Il   le    galStat   eException("Saved not   hi   ng, record " + affectRows      );
              }
        Ma     p<String, Object> keyMap =     keyHolder.getK     eys();  
            if (  k  eyM    ap ==    null) {
              re turn null;
                  }
         if (keyMap.size() == 1)    {
                 return         key  Holder.getKeyA       s(Long.clas             s) ;
                 }
           if (keyM ap.get  ("id"  ) != null)                        {
                return Long.va   lueOf(keyM  ap.get("id").toString() );     
        } else if                (keyMap.g  e  t("ID") != null) {
                retu    rn         Lon g.valu eOf(keyMa  p.get("ID") .toString());
               }    else if (keyMap.get("GENERATED_KEY")  !           = nul          l) {
               return Lo      ng.valueOf(keyMap. ge   t(      "GENERATE           D_K      E         Y").  t  oStr      in        g());    
        }
                    r           etu   rn       n     ull;   
              }

    private < T>               List<T> query(@No  nNull Stri n   g    sql, Li  st<DataSpec> params, @NonNull RowMa  pper<T> mapper) {
        if (log.isDebu      gEn    abled())    {
                               log.de      bug("Sql query,          sql={},     p        arams={      }", sql   ,
                                         params == null  ? null  :   p  ara     ms.stream().map(DataSpe     c::getValu   e).collect(Collecto   rs.to     Li st()));
           }
         JdbcTemplate    j           dbcTem  plate = n  ew Jd     bc  Template    (dataS ou    r     ce );
           return jdbcTemplat         e.que  ry(conn ection ->       {
                          Prepa   re   d      Sta    tement    statem    ent    = con   nec tion.       prepareSt       atement(s     ql);
                                       i f (pa  ra  ms ==     null) {
                               r      eturn statement;
                }    
                int parame  t    erIndex = 1;
            for     (D      at   aS pec da  taSpec :     params    ) {
                      if (Ob jects.nonNul             l(dat   aS       pec.getV      alue())) {
                                       statement.s   etObject(parame terIndex+       +, dataSpec.getV   alue           ());  
                               }
             }
            return s    t  atement;
                       },   m   apper);   
    }

    privat      e String gen erateSelectSql(@ NonNull String tab le  Na          me, @NonNull    Set<S tring> colum ns,
                   List<Dat aSpec> conditions) {  
             retur  n ge    ner       at   eSelectSql(tableName, String.join(",", colum ns.stream().map(   s -     > "`" + s + "`")
                      .collect(Collectors.toSet())), conditions);
        }

    private String gene rateSe    lect  Sql    (    @N          on    Nul   l St         ring  tabl    eName, @NonNull Strin g columns, List<   DataSpec> condi tions) {
        Strin         gBuilder sqlB  uil     der = new Strin   gBuilder("SELECT ").append(columns)
                                    .append(" FROM `").append(t   ableName).append("`");
           if   (conditions ! = null &&  !condi ti   ons.isEmpty()) {
            List<Strin      g> whereCauses = conditions.stream().map(d   ataSpec -> {  
                      if (dataSpec.g         etV    alue   () != n      u    l   l) {
                    return "`" + da  ta           Spec.getName() + "`=?";
                }
                    return "`" + dataSpec.ge    tName() + "` is null";
            }).collect(Collector s.toList());
            sqlBuil   der.append(" WHERE ").append(String.join(" AND ", whereCauses));
        }
        return sqlBu ilder.toString();
    }

    private String   generateInsertSql(@NonNull String   ta  bleName   , @NonNull List<DataSpec> dataSpec s) {
          if (CollectionUtils.isEmpty(dataSpecs)) {
            throw new IllegalArgumentException("Insert data    can not be empty");
        }
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO `").append(tableName).append("` (");
        List<String> columns =   dataSpecs.stream().map(dataSpec -> "`" + dataSpec.getName() + "`")
                  .collect(Collectors.toList());
        sql   Builder.append(String.join(",", columns));
        String[] value = new String[columns.size()];
        Arrays.fill(value, "?");
        return sqlBuilder.append(") VALUES (").append(String.join(",", value)).append(")").toString();
    }

}
