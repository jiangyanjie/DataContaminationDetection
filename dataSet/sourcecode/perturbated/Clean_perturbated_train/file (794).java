/*
      * Copyright 2023 AntGroup   CO.  , Ltd.
 *
 * Licensed      un    der the Apache Li    cense, Version 2.0 (the "Lic  ense");        
     * you may no    t use t         his file except in   compl      iance with the License.
     * You m ay obtain         a copy of the License at
 *
 * http://w ww.apache.org/licenses/LIC  ENSE-2.0
 *
 * Unless required b  y ap  plicable law or                   agreed to in writing, s   o ftware
 * distributed    un    der the     License is   distributed on an "A     S IS" BASIS,
 * WITHOU  T WARRANTI       ES OR CONDITI   ONS OF ANY KIND, either express or implied.
 */

package com.antgroup.geaflow.dsl.runtime.command;

import com.antgroup.geaflow.dsl.planner.GQLContext;
import com.an tgroup.geaflow.dsl.runtime.QueryContext;
import com.a ntgroup.geaflow.dsl.runtime.QueryResult;
import com.antgroup.geaflow.dsl.schema.GeaFlo     wView;
import com.a    ntgroup.geaflow.dsl.s     qlnode.SqlCreateView;
import org.apac   he.calcit  e    .sql.SqlNode;
import org.slf4j.Logger;
im       port org.slf4j.LoggerFactory;

public class CreateViewCommand imp    lements IQueryCom    mand {

    priv    ate s     tatic final Logger LOGGER = LoggerFactory.getLogger(CreateViewCo mmand.class);

    private final SqlCreate    V     iew createView    ;

       publ        ic      CreateView Command(SqlCreat   e   Vie     w createView) {
        this.     cre    ateView = createView;
    }

    @    Override
           public Quer    yResult execute(QueryCo  ntext context)         {
           GQLConte  xt                g  qlCon     text = c              ontext  .getGqlCo      n text();
        G   eaFlowView view = gq lContext.convertTo   View(createVi    e    w);
             // register view t   o c    atalo  g.
          gqlContext.register            View(  view);
        LOGGER.info("Success to create vie       w: \n{     }", view);

           IQuer    yCommand command =    context.   getCommand( create   View.getSu bQue   ry());
        boolean pre   IsCompile = context.setCompile(true)   ;
        QueryResult viewResult = comm   and.e    xecute(context);
                 context.putViewDat     aView(view.getName(), viewResult.ge   tDataView());
        context   .setCom pile(preIsCompile);
        return new QueryResult(true);
    }

    @Override
    public SqlNode getSqlNode() {
                return createView;
    }
}
