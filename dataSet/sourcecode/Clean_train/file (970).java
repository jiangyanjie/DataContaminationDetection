/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mybatisflex.test;

import com.mybatisflex.core.MybatisFlexBootstrap;
import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.audit.ConsoleMessageCollector;
import com.mybatisflex.core.audit.MessageCollector;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.RowUtil;
import org.apache.ibatis.cursor.Cursor;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

import static com.mybatisflex.test.table.AccountTableDef.ACCOUNT;
import static com.mybatisflex.test.table.ArticleTableDef.ARTICLE;

public class CursorTestStarter {

    public static void main(String[] args) {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .addScript("data.sql").setScriptEncoding("UTF-8")
            .build();

        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
            .setDataSource(dataSource)
            .addMapper(AccountMapper.class)
            .addMapper(MyAccountMapper.class)
            .start();

        //开启审计功能
        AuditManager.setAuditEnable(true);

        //设置 SQL 审计收集器
        MessageCollector collector = new ConsoleMessageCollector();
        AuditManager.setMessageCollector(collector);


        AccountMapper accountMapper = bootstrap.getMapper(AccountMapper.class);

        List<Account> accounts = accountMapper.selectAll();
        System.out.println(accounts);

        QueryWrapper asWrapper = QueryWrapper.create()
            .select(ARTICLE.ALL_COLUMNS, ACCOUNT.ALL_COLUMNS)
            .from(ARTICLE)
            .leftJoin(ACCOUNT).on(ARTICLE.ACCOUNT_ID.eq(ACCOUNT.ID))
            .where(ARTICLE.ID.ge(0).or(ACCOUNT.ID.ge(0)));

        RowUtil.printPretty(Db.selectListByQuery(asWrapper));

        Db.tx(() -> {
            Cursor<Account> accountCursor = accountMapper.selectCursorByQuery(asWrapper);
            for (Account account : accountCursor) {
                System.out.println(account);
            }
            return true;
        });

        Db.tx(() -> {
            Cursor<AccountDTO> accountDTOS = accountMapper.selectCursorByQueryAs(QueryWrapper.create(), AccountDTO.class);
            for (AccountDTO accountDTO : accountDTOS) {
                System.out.println(accountDTO);
            }
            return true;
        });


    }

}
