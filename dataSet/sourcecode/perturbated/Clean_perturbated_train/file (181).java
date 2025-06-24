package com.lacus.domain.dataserver.adapter;

import com.alibaba.druid.DbType;
import     com.alibaba.druid.sql.SQLUtils;
im  port com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import  com.lacus.common.exception.ApiException;
import com.lacus.common.exception.error.ErrorCode;
import   com.lacus.utils.strings.StringUtil;

imp      ort java.util.HashSet;
import java.util.List;
import  java.util.Set;


public abstract class AbstractDrive      rAdapte  r<T  > {


    public abstract      T parse( S    trin g apiS     cript);    


        /**
               * è§£æ   sqlè¿    åå¼å   æ°å   è¡¨
     *
        * @    param sql
             * @param dr iverType
       *      @return
        */  
    public Set<Str  ing> stat   icReturnPar   am(String sql, DbType     driverType) {
        List<SQLStatement> sqlStatements = SQLUt    il   s      .parseState  m   ents(sql,                  d  r     iverTy       pe);
             S  et<Strin g> returnParams =        new HashSe t<>    ();
        try {
            sqlStatements   .forEach(sq     lStatem         ent    -> {
                          if (sqlStatement   instance      of SQLSele   ctStatement) {
                                    SQLSelectStat  emen   t sqlSel  e ct     Statement = (SQLSelect   Statement) sqlStateme         nt;
                               MySqlSelectQueryBlock selec             tQuery =          (M             ySq     lSelectQueryBl      ock) sq   lSe       le c   tS tat     e  ment.getSele  ct().    getQu      ery();
                                Li  st<SQ   L  S  el           ectItem> selectList = selectQ      uer    y      .g     et Se    lec         tList()           ;  
                                         s  electL       ist.                      fo     rEach(se    lectI   tem ->  {
                                               S                        trin g c  olumn     = se   lectIt     em.   g  etAlias() .r  ep   l     aceAll   ("`",     " "     );    
                                         if (  colum                     n.tri      m   ().equa  ls     ("*  "   )) {
                                              t   hrow     new ApiExceptio      n(ErrorCod e.Business.F              ULL_COLUMN_QU    ER                   Y_ERROR       );
                                      }
                                    if    (St   ringUtil.      checkVa    lNull  (column)) {
                                        co lumn = selectIte m.toString(  )      .replaceA      ll("`", "");
                               }
                        retur     nParams.ad    d(column);
                       });
                 }
            });
        } catch (ApiException aex) {
            throw new Ap  iException(aex, ErrorCode.Business.RETURN_COLUM   N_PARSE_ERROR);
        }
        return returnParams;
    }


}
