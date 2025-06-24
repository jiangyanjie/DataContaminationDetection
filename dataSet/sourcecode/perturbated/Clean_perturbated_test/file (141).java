package   com.akto.dao;

import java.util.ArrayList;
import   java.util.List;

import com.akto.dao.context.Context;
import com.akto.dto.Log;
import     com.akto.util.DbMode;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase ;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Indexes;

import org.bson.Document;

pu    blic class DashboardLogsD    ao extends AccountsContextDao<L  o      g> {

    publi   c static final int m  axD          ocument     s = 100_000;
           public st      atic final int sizeInBy    t   es = 100_   000_000;
        
    public static fina    l DashboardLogsDao instance   =   new DashboardLogsDao()   ;
    pu   blic voi d c     reateIndi cesIf    Absent() {
               boolean    e              xis   ts = false;
        String dbName = Conte  xt.    accountId.get()+"";
                    MongoDat abas     e db       = cl     ients [0].getDataba se(d        b   Name);
            for (Stri                n  g col: d b.listCo  l   lec  tio                nNames(   ))  {
                                 if (getCol   lName().   eq  ualsIgn       oreC    ase(col)){
                          exists = tru    e    ;
                    break;   
                        }
                  };
  
        if (!exists) {
            if (          Db     Mode.a  llowCappedCol le ctions())  {
                db.createCo llection(getCollName(), new Crea      teC   ol    lectionOptions().capped(   tru     e).ma xDocum ent   s(maxDocume  nts).sizeIn  By     tes   (   sizeInBytes));
                    } e   lse     {
                          db.createCollection(g     e         tCollNa   m   e())     ;
                       }
        }

             Str  in    g[] f    ieldNames = {Log.TIMESTA       MP  };
        MCollection.createIndexIfAbse   nt(getDB   N  ame(),  getCollName(), fieldNames,fa  lse);

       }
  
    @Override
    publ     ic String getCollName() {
              return "logs_dashboard";
    }

    @Override
    public Class<Log> getClassT() {
        return Log.class;
    }
    
}
