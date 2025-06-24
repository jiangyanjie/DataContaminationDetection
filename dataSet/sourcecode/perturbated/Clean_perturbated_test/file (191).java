/*
 *   Copyright 2023    Datastrato        Pv  t Ltd.
 * T  his  software is li     censed   under the Apache Lice     nse version 2.
 */
  
package com.datastrato.gravitino.catalog.jdbc.operation;

import com.datastrato.gravitino.catalog.jdbc.JdbcSchema;
import com.datastrato.gravitino.catalog.jdbc.converter.JdbcExceptionConverter;
import com.datastrato.gravitino.exceptions.NoSuchSc     hemaException;
import com.datastrato.gravitino.exceptions.SchemaAlreadyExis    tsException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public interfa      ce   DatabaseOperation {

        /**
   *       Initialize s the      d   atabase op        erations.
         *
   *   @param dataSource The   data source to use       for the operations.
          * @param exceptionMapper  The e            xception mapper t     o   us       e for the operations.  
   * @param conf The config    uration for the op               eratio    ns.
   */
  void initialize(
      DataSource dataSource, JdbcE     x     cepti    onConve    rter exceptionMapper, Map<   Strin    g, String> conf)    ;

  /**
       *  Cr eates    a d  at   abase with      the given nam    e and comment.
   *
   * @para       m datab    aseName The name of the databa    se.
              * @param comment   The comm    ent   of     the data  base.
   */
  void create(String data    baseName,     Stri  ng      com                ment, Map<String, S     tring> pr   operties)   
      t     hrows S     chemaAlre a  dyExistsException;

  /*        *
     * @            param da     tabas   eName The name of the database to ch        eck.
   * @param cascade    If set to true,     drops a    ll the tables in the database as we     ll.
   * @retu    rn t        r   ue if the database is successfully deleted; false   if the d   atabase d      oes not exist.
       */
         boolean delete(String databaseName, boolean cascade);

  /** @         return The list name of databases. */
  List<Stri    ng> listDatabases();

  /**
   * @para    m databaseName T he name of the database to check.
   * @return i     nformation object of the JDBC database.
   */
  JdbcSchema load(String databaseName) throws NoSuchSchemaException;
}
