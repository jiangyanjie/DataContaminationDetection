package schema_differ;

import datalayer.SchemaDL;
import entities.*;
import java.sql.SQLException;
import java.util.ArrayList;
import sql.Connector;
import util.DifferUtils;

/**
 * The DB Layer is responsible for all the processing
 *
 * @author ahughes
 */
public class DBLayer {

    private Connector c1;
    private Connector c2;
    private Schema s1;
    private Schema s2;
    private SchemaDL schemaDL;
    private ArrayList<Diff> diffs;

    /**
     * Constructor for DBLayer Builds the two schema objects
     *
     * @param aConnector1
     * @param aConnector2
     * @throws SQLException
     */
    public DBLayer(Connector aConnector1, Connector aConnector2) throws SQLException {
        c1 = aConnector1;
        c2 = aConnector2;

        schemaDL = new SchemaDL(c1);
        s1 = schemaDL.buildSchema();

        schemaDL = new SchemaDL(c2);
        s2 = schemaDL.buildSchema();
    }

    /**
     * Compares the two schema objects and populates the ArrayList with their
     * differences
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<Diff> compare() throws SQLException {
        diffs = new ArrayList();
        boolean tables, columns;

        //first fetching the tables
        ArrayList<Table> tables1 = s1.getTables();
        ArrayList<Table> tables2 = s2.getTables();

        //quickchecking whether the schemas contain the same tables
        if (tables1.containsAll(tables2) && tables2.containsAll(tables1)) {
            tables = true;
        } else {
            tables = false;
        }

        //if the tables are not the same, creating a report
        if (!tables) {
            for (Table t1 : tables1) {
                if (!tables2.contains(t1)) {
                    diffs.add(new Diff("table", t1.getName(), 1));
                }
            }
            for (Table t2 : tables2) {
                if (!tables1.contains(t2)) {
                    diffs.add(new Diff("table", t2.getName(), 2));
                }
            }
        }

        //checking the columns of the tables
        int t2index;
        Table t2;
        for (Table t1 : tables1) {
            //fetching the index of the table in the other schema
            t2index = tables2.indexOf(t1);

            //if the table does not exist in the other schema, we skip the column check (there's a bigger problem)
            if (t2index != -1) {
                t2 = tables2.get(t2index);

                //quickchecking whether the table contains the same columns
                if (t1.getColumns().containsAll(t2.getColumns()) && t2.getColumns().containsAll(t1.getColumns())) {
                    columns = true;
                } else {
                    columns = false;
                }

                //if the columns are not the same, creating a report
                if (!columns) {
                    for (Column col1 : t1.getColumns()) {
                        if (!t2.getColumns().contains(col1)) {
                            diffs.add(new Diff("column", t1.getName() + "/"
                                    + col1.getName(), 1));
                        }
                    }
                    for (Column col2 : t2.getColumns()) {
                        if (!t1.getColumns().contains(col2)) {
                            diffs.add(new Diff("column", t2.getName() + "/"
                                    + col2.getName(), 2));
                        }
                    }
                }
            }
        }

        return diffs;
    }

    /**
     * Prints a report for the result of the diff
     *
     * @param aDiffL
     * @throws Exception
     */
    public void printReport(ArrayList<Diff> aDiffL) throws Exception {
        ArrayList<Diff> firstL, secondL;
        String report;
        String newL = System.getProperty("line.separator");

        //separating the diffs depending on their schema
        firstL = new ArrayList();
        secondL = new ArrayList();
        for (Diff d : aDiffL) {
            if (d.getSchema() == 1) {
                firstL.add(d);
            } else if (d.getSchema() == 2) {
                secondL.add(d);
            } else {
                throw new Exception("Invalid Schema number detected");
            }
        }

        //title of the report
        report = "SchemaDiffer Report" + newL + newL;

        //first list results
        if (!firstL.isEmpty()) {
            report += "Elements existing in the first schema ONLY: " + newL + newL;
            for (Diff d : firstL) {
                report += d.toString() + newL;
            }
            report += newL + "--------" + newL + newL;
        } else {
            report += "No Elements" + newL;
        }

        //second list results
        if (!secondL.isEmpty()) {
            DifferUtils.writeFile(report, "report.txt");
            report += "Elements existing in the second schema ONLY: " + newL + newL;
            for (Diff d : secondL) {
                report += d.toString() + newL;
            }
            report += newL + "--------" + newL + newL;
        } else {
            report += "No Elements" + newL + newL;
        }

        report += "End of Report" + newL;
        report += "Brought to you by Alex Hughes <ahughes@ahughes.org> || github.com/ahughes117/schema_differ" + newL;

        //finally writing the file
        DifferUtils.writeFile(report, "report.txt");
    }

}
