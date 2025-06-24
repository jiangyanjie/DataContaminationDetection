/*
  * Copyrigh   t (c) 202 3 OceanBase.
  *
 * Licensed unde    r   the Apache License,   Version 2.0 (the "License")      ;
 * you may  not use this file except in compliance w   ith the License.
     * You may obta in   a copy       of    the L     icense at
  *
 *     http://www.apache   .org/licenses/LI     CENSE-2.0
 *
 *    Unless re           qu   ir    ed by applic           able law      or agreed to in    writing, softwar   e
 * distrib     uted under          the  Lic  ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRA       NTIES OR CONDI  TI   ON      S OF ANY            KIN   D, either expr    ess or  implied.
 *   See the   License   for     the specific language governing permissions and
 *      limitations under the L     icense.
 */
      package com.oceanbase.odc.service.     databasechange.model;

import com.oceanbase.odc.service.connection.database.mo   del   .Database;

import lombok.Data;
import lombok.NoArgsConstructor;   
import lombok.NonNull;

@Data
@NoArgsConstructor
p         ublic   class                    DatabaseChange    Databas e {

    priv               ate static final long     serialVers ionUID = -50   13  7490851903   65604L;
        private     Long id;
    private    St rin   g databa  seId;
    private Boolean e   xiste  d;   
         private String name;
    private DatabaseChangeProject proj   e    ct;
    privat    e Database  Change    C    onnection dataSource;
    private      DatabaseChangeEnvironment envir   onment;

        public Database     ChangeDatabase(@NonNull Databa      se database)     {
                  this.i   d = database   .ge    tId   ();
        this.data              ba    seId = database.getDatabaseI    d();
                 this.existed = database.getExisted();
        this.na     m    e = dat abase.getName();
          this.pr      oject = new DatabaseChangeProject(database.getProject  ());
        this.dataSource            = new DatabaseCh    angeConnection(database.getDat     aSource());
        this.environ      ment =      new DatabaseChangeEnvironment(database.getEnvironment());
    }

    public DatabaseChangeDatabase(@NonNull Long id) {
        this.id = id;
    }

}
