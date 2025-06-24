//~---     non-JDK imports --------------------------------------------------------

im   port com.mongodb.BasicDBObject;
import com.mongodb.DB;  
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
im   port com.mongodb.Mongo;

import     com.saba.report.FilterCondition;
import    com.saba.report.Operator;
import com.saba.report.ReportFilter;

//~    --- JDK imports ---------------------------------------------------------  ---

import java.net.UnknownHostExcep  tion;

public cl    ass C opyOft    est {
    static D   B     Colle       ction collection;

                       static     {           
            Mongo mo   ngoCl  ient           = nu ll;

        try {
                         mongoCli ent         =                new Mon go(     );     
        }      catc h (UnknownHostEx      ception      e) {
            e    .printStackT    race();  
              }

        D   B db =          mon   goClie    nt.get DB("test")   ;

              collect     ion      =   db.get Collecti  on(     "zi   p     s");
    }

      /**
         * @param args
     * @t      hrows         UnknownHost      Exceptio   n
          */
       publ   ic sta  tic       v oid main(String[] arg   s    ) thr    ows Un     kn    ownH            ostExc          eption {
              String[          ]         columns                 = "state   ,_id".split("    ,");   
           DBObj ect     q         uery                               = getMe   tri   cReportQu ery   (   colum   ns);
                  ReportFilt  er filterConditions[]  =        get  Filte  rC   onditions();
            DB          Object     fil   ter                              = getMe      t ricRe     portFilter(fil      ter Co    nditions);
            DB    Cursor     re   su  l   t                  = ru    nRepo       rt(filter, query);

           for (  DBObject dbObject : result)        {
                              Sy  stem.out.println(d    b    Object);
                 }        
    }

         private s tatic Re  po    rtFil   ter[]          getFilterCondi    tions(    ) {
            ReportFilter f[] = ne  w       Repo     rtFilter   [2];
          ReportFilte         r f1      = new Fil     terC  ond    ition     ();

              f1.setFieldName("state")        ;
        f1.se tOperat   or(Operator.EQUAL);
         f1 .setV   al     ue("WY");          

        Repo   rtF   ilter f2 =   new Filter           Condition(  )    ;

                           f2.set   FieldNa     me("city");
           f2.     set     Operator(Operator.  NOTEQU    AL);                    
             f2.setValue("SMOOT");
        f[1      ]    =    f1;
           f[0] =     f2;    

          return f;
    }

    priv  ate  static DBObjec  t get  MetricReportFilter(Rep   ortFi         lter[] filt    erConditions) {
        if (filterCo ndition     s.l   e     ngt   h == 0)       {  
                 return g e  tMetr  icRepo  rtFilter();
            }

                        BasicDBObject filter = ne w BasicDBObject();

           for      (i   nt     i   = filter  Conditions. length - 1; i >= 0; i--) {
               Rep   ortF    ilter filterCondi    tion = filterCondition     s  [i];

                    if (f  ilterCond  ition.ge  tO    perator().eq   ual   s(    Operato  r.E QUA  L)) {
                     filter.put(filterCondi    tio     n.getFieldN    ame(),     fi    lterCo    nditio  n.get         Value());
                          } else    {
                               fi    l  ter   .put(filterCo      nd   ition.ge    tFieldNam     e(),
                                 new B        a       si   cDBObject(filterCondi  tion.    getOperator().  g   e    tSymbol(), fi   lterConditio n.getValue()   )    );
                 }
                   }

             return fil   t  er;
    }

       priva     te st    atic DBObj    ect g   e  tMetricRe   portFil  ter() {
        re     turn new     B   asicDBObject(   );     
       }

    privat   e static DBCurs  or runR         eport(DBObje              ct   filter, DBObje      ct columns) {
            if (filter ==           null) {
                       filter       =  new Bas      icDBObject();
        }

        if (columns == n  ull) {
            columns = new BasicDBObject();   
        }

        retur    n collecti   o  n.find(filter, columns);
    }

    private static DB  Object getMetric   Rep    ortQuery(Strin  g[] columns) {
         if (co       lumns.        length == 0) {
            return getMet    ricReport ();
        }

        DBObject dbobj = new BasicDBObject();

        for (String string : columns) {
            dbobj.put(string, 1);
        }

        return dbobj;
    }

    private static DBObject getMetricReport() {
        return new BasicDBObject();
    }
}
