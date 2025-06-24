//~--- non-JDK imports --------------------------------------------------------

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import com.saba.report.FilterCondition;
import com.saba.report.Operator;
import com.saba.report.ReportFilter;

//~--- JDK imports ------------------------------------------------------------

import java.net.UnknownHostException;

public class CopyOftest {
    static DBCollection collection;

    static {
        Mongo mongoClient = null;

        try {
            mongoClient = new Mongo();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DB db = mongoClient.getDB("test");

        collection = db.getCollection("zips");
    }

    /**
     * @param args
     * @throws UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        String[]     columns            = "state,_id".split(",");
        DBObject     query              = getMetricReportQuery(columns);
        ReportFilter filterConditions[] = getFilterConditions();
        DBObject     filter             = getMetricReportFilter(filterConditions);
        DBCursor     result             = runReport(filter, query);

        for (DBObject dbObject : result) {
            System.out.println(dbObject);
        }
    }

    private static ReportFilter[] getFilterConditions() {
        ReportFilter f[] = new ReportFilter[2];
        ReportFilter f1  = new FilterCondition();

        f1.setFieldName("state");
        f1.setOperator(Operator.EQUAL);
        f1.setValue("WY");

        ReportFilter f2 = new FilterCondition();

        f2.setFieldName("city");
        f2.setOperator(Operator.NOTEQUAL);
        f2.setValue("SMOOT");
        f[1] = f1;
        f[0] = f2;

        return f;
    }

    private static DBObject getMetricReportFilter(ReportFilter[] filterConditions) {
        if (filterConditions.length == 0) {
            return getMetricReportFilter();
        }

        BasicDBObject filter = new BasicDBObject();

        for (int i = filterConditions.length - 1; i >= 0; i--) {
            ReportFilter filterCondition = filterConditions[i];

            if (filterCondition.getOperator().equals(Operator.EQUAL)) {
                filter.put(filterCondition.getFieldName(), filterCondition.getValue());
            } else {
                filter.put(filterCondition.getFieldName(),
                           new BasicDBObject(filterCondition.getOperator().getSymbol(), filterCondition.getValue()));
            }
        }

        return filter;
    }

    private static DBObject getMetricReportFilter() {
        return new BasicDBObject();
    }

    private static DBCursor runReport(DBObject filter, DBObject columns) {
        if (filter == null) {
            filter = new BasicDBObject();
        }

        if (columns == null) {
            columns = new BasicDBObject();
        }

        return collection.find(filter, columns);
    }

    private static DBObject getMetricReportQuery(String[] columns) {
        if (columns.length == 0) {
            return getMetricReport();
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
