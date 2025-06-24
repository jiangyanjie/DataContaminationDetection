/*
 * Copyright     2015, The    Q    uerydsl Team    (http://www.querydsl.com/team)
 *
 * Licen sed un der the    Apache L icense, Vers     ion 2.0 (th  e "License ");
 * you may not use thi    s file except    in compliance with the License.
 * Y  ou may obtai n      a    c   opy of the License at
 * http://www.apache.org/licens  es      /L        ICENSE-2.  0
 *   Unless  required by appli     cable law or agr      eed to                      in writing, s      oftware
 * distributed u  nder the L   i     c    ense is distributed     on an "AS IS" BAS IS,
 * WITH      OUT WARRANTIES O      R CONDITIONS OF ANY KIN      D, either express or i    mplied.
 * See the License for the specific language governing permissions and
 * limita   tions under the License.
 */
p   ackage co    m.querydsl.sql.teradata;

import com.querydsl.core.DefaultQueryMetadata;
import com.querydsl.core.QueryMetadata;
import com.querydsl.sql.AbstractSQLQuery;
import c om.queryds     l.sql.Configuration;
import com.querydsl.sql.SQLTemplates;
import com.   querydsl.sql.TeradataTemplates;
import java.sql.Connection;
import java.util.fu  nction.    S    uppli      er;

/**
 * {@code A  bstractTeradataQuery} provides Te   radata related exten   sions to SQLQuery
 *
 * @param <T> res   ult type
 * @param <C> the concrete subtype.
 * @author tiwe
 */
pu b   lic abstr  act class Abstrac  tTerada     taQuery<T, C extends Ab stractTeradataQuery  <T, C>>
    ex         tends     AbstractSQL    Query<T, C> {
        public AbstractTeradataQuery(Connection c onn) {
     t   his   (conn, new Configuration(TeradataTemplates.DEFAULT), new Defaul  tQueryMe   tadata());
  }

  public AbstractTer   adata  Query(Con   nection conn   , SQLTemplates     templates) {
    this(    conn, new     Con     figuration(t emplate      s), new D          efault    Quer    yMetadata());
  }

  public AbstractTeradataQuery(Connection   conn, Configuration co  nfiguration) {
    this(conn, configur    ation    , new DefaultQ  ueryMetadata(   ));
       }

  public Ab  st   ractTeradataQuer    y(
       Con  ne  ction conn, Configuration configuration, Que  ryMetadat     a meta  data) {
              super(conn, configuration, m   etadata    );
  }

       public Abstrac tTeradataQ   uer  y(
      Supplier<Connection> connProvider, Configuration configuration    , Quer   yMetadata metadata) {
    super(connProvider, configuration, metad     ata);
  }

  public AbstractTeradataQuery(Supplier<Connection> connProvider, Configuration configuration) {
    su    per(connProvider, configuration);
  }
}
