package de.dhbw.blaaah;

import de.dhbw.blaaah.database.csv.CSVDatabase;
import de.dhbw.blaaah.exceptions.DatabaseException;
import de.dhbw.blaaah.parser.MiniSqlParser;
import java_cup.runtime.Symbol;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse stellt eine simple Konsolenanwendung bereit, die sich mit einer Datenbank verbinden
 * kann und dort Befehle ausführen kann.
 */
public class ConsoleApplication {

    private final Database database;

    public ConsoleApplication(String dbName) {
        database = new CSVDatabase("./" + dbName);
    }

    public void run() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line;
            try {
                System.out.print("> ");
                line = reader.readLine();
            } catch (IOException e) {
                break;
            }

            if (line.equalsIgnoreCase("quit"))
                break;
            else if (line.isEmpty())
                continue;

            Symbol sym;
            try {
                sym = MiniSqlParser.parse(new ByteArrayInputStream(line.getBytes()));
            } catch (Exception e) {
                System.err.println("Couldn't parse input.");
                continue;
            }

            List<Statement> statements = (List<Statement>)sym.value;
            for (Statement statement : statements) {
                Result result;
                try {
                    result = statement.execute(database);
                } catch (DatabaseException e) {
                    System.err.println(String.format("An error occurred: %s", e.getMessage()));
                    break;
                }

                System.out.println(formatResult(result));
            }

            database.save();
        }
    }

    public String formatResult(Result result) {
        if (!result.isSuccess()) {
            return String.format("Error: %s", result.error());
        } else {
            if (result.getRowCount() == 0) {
                return String.format("Success!");
            } else {
                List<List<String>> data = new ArrayList<List<String>>(result.getRowCount() + 1);
                int columnCount = 0;

                // Spalten abrufen und formatierte Werte abspeichern
                for (int i = 0; i < result.getRowCount(); ++i) {
                    Row row = result.getRow(i);
                    if (i == 0) {
                        // Kopfzeile hinzufügen
                        List<String> header = new ArrayList<String>();
                        header.add("index");
                        header.addAll(row.getColumnNames());
                        data.add(header);
                        columnCount = row.getColumnNames().size() + 1;
                    }

                    List<String> temp = new ArrayList<String>(row.getColumnNames().size());
                    temp.add(Integer.toString(row.getRowIndex()));
                    for (Object value : row.getValues()) {
                        temp.add(value.toString());
                    }
                    data.add(temp);
                }

                List<Integer> columnWidths = new ArrayList<Integer>();

                // Spaltenbreiten ermitteln
                for (List<String> rowData : data) {
                    for (int j = 0; j < columnCount; ++j) {
                        if (columnWidths.size() <= j)
                            columnWidths.add(rowData.get(j).length());
                        else if (rowData.get(j).length() > columnWidths.get(j))
                            columnWidths.set(j, rowData.get(j).length());
                    }
                }

                int totalWidth = 1; // 1 für linken Tabellenrand
                for (Integer columnWidth : columnWidths) {
                    totalWidth += columnWidth + 1; // +1 für Spaltentrenner
                }


                // Ausgabe zusammensetzen
                String horizontalLine = nTimes('-', totalWidth);
                StringBuilder output = new StringBuilder();

                output.append(horizontalLine);
                output.append('\n');
                for (List<String> rowData : data) {
                    output.append('|');
                    for (int i = 0; i < columnCount; ++i) {
                        inlinePad(output, rowData.get(i), columnWidths.get(i), ' ');
                        output.append('|');
                    }
                    output.append('\n')
                            .append(horizontalLine)
                            .append('\n');
                }
                return output.toString();
            }
        }
    }

    private static String nTimes(char c, int times) {
        char[] data = new char[times];
        Arrays.fill(data, c);
        return new String(data);
    }

    private static void inlinePad(StringBuilder builder, String in, int padLength, char pad) {
        builder.append(in);
        for (int i = in.length(); i < padLength; ++i) {
            builder.append(pad);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please add the databasename to open");
            return;
        }

        ConsoleApplication app = new ConsoleApplication(args[0]);
        app.run();
    }
}
