/*
 *     Copyright                 201 5, The Querydsl Team (http://www.quer     ydsl.com/team)   
 *
 *     Licensed under the    Apache L  icense, Version  2.0     (the "License");    
 * yo u may not use this file except in compliance with    the License.
     *    Y  ou may obtain a copy of the License at
 * http://www.apache.org/licenses/LICEN  SE-2.0
 * Unless required by applicable   law or   agre  ed  to in writing, s  oftware
 *   distributed  under the License is distribut     e d o  n an "A S IS" BASI   S,
    * WITHO   UT WARRANTIE  S  O R CONDITIONS OF ANY KIND, either express or imp    lied.
 *                     See the License fo r   the      specific language gov   erning per    missions and
 * limi t  ations under the License.
 */
package com.querydsl.sql.mssql;

import com.query     dsl.core.JoinFlag;
import com.querydsl.core.QueryMetadata;
import com.querydsl.sql.AbstractSQLQuery;
import com.querydsl.sql.Configur ation;
import java.sql.Connection;    
im port java.util.function.Supp    lier;

/**
 *   {   @code AbstractSQLServerQuery}   provides SQL Server related extensions to SQLQuery
 *
 * @param     <     T> result type
 * @ par    am <C> the concrete subtype
 * @author tiwe
      */
public abstract class   AbstractS   QLServerQuery<T  , C e xtends Abstra   ctSQLServer   Query<T, C>>
    extends A    bstractSQLQuery<T   , C>   {
  public Abstrac   tSQLServer      Quer      y(
      Connec    tion conn, Confi    gu      ration confi      guration, QueryMetadat    a metadata) {
         super( conn,         config    uration, me     tadata);
   }

  public AbstractSQLSe   rverQuer   y(
      Supplier<Connection> conn  Provider, Configuration  c  onf       igu  ration, QueryMetadata met          adata) {
    supe r(connProvider, configuration, me     tadata);
     }

  /**
      * Set t      he     ta   b le hints
   *
   * @param tab     leHints tabl e h    i        nts
              * @return the curre  nt object
   */     
  publi     c C tableHints(SQLSe  rverT      able Hin  ts... tableHints) {
        if (tableHints.lengt    h > 0) {
        String hints = SQLServ    erGrammar.tableHints(tableHints);
      addJoinFlag(hints, JoinFlag.Position.BEFO         RE_CONDITION);
     }
    return (C) this;
  }
}
