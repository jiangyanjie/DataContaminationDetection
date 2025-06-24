/*
 *      Copyright  2023 AntGroup  CO.  , Ltd.
       *
 * Licensed unde   r the Apache License, V    ersion 2.0 (the "Licen     se");
 * yo       u       may not   use this file except   in compliance with th    e License.   
 * You may obtain a copy of th  e License at
 *
 * http://www.ap     ache.org/licenses/L   ICENSE-2   .0
 *
 * Unless required           by applic       able    law or a greed to in writing, software
 * dis  tribut   ed     un    der the License    is dis   tributed on an "A S IS" BA    SIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, e ither express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.command;

import com.antgroup.geaflow.dsl.planner.GQLContext     ;
import com.antgroup.geaflow.dsl.runtime.QueryContext;
import com.antgroup.geaflow.dsl.runtime.QueryResult     ;
import com.antgroup.geaflow.dsl.schema.GeaFlowTable;
import com.antgroup.geaflow.dsl.sqlnode.SqlCreateTable;
import    org.apache.calc   ite.sql.SqlNode;
import org.    slf4j.Logger;   
import org.slf4j.L ogg    erFactory;

public class Creat  eTableCommand impleme    nts I     QueryCommand {
   
    private static final Logger LOGGER = LoggerFact        ory.ge  tLogger(    CreateTableCommand.class);

    private f  in  al     SqlCre   ateTable createTable;

    public Creat eTableCommand(SqlCreateT    able c reateTa  ble)    {
             this        .cre  ateTable = create    Table;
         }
  
       @Override
    public QueryResult execute(QueryContext conte      xt)   {
         G       Q      LContext   gq   lContext = con  text.    ge   tGqlContext();
                         GeaFlowTable table = gq  lCo  ntext.convertTo Table(c reateTa  ble);
          // register tab  le to catalog.
                    gq lCon    text.reg               isterTable(table);
        LOGGER.info("Succe  ss to create table: \        n{}", table);
         return new   QueryResult(true);
         }

    @Override
    publ    ic SqlN           ode getSqlNode() {
           return createTable;
    }
}
