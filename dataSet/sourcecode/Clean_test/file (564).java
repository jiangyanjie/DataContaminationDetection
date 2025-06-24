package com.savin.playtox.Convector;

import com.savin.playtox.column.Column;
import com.savin.playtox.column.ColumnFactory;
import com.savin.playtox.column.money.Money;
import com.savin.playtox.writer.TxtWriter;
import com.savin.playtox.writer.Writer;
import com.savin.playtox.parser.StringParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Convector implements AutoCloseable {
    private List<Column> columns = new ArrayList<>();
    private FileReader fileReader;
    private Scanner sc;
    Writer writer;

    public Convector(File csvFile, File file) throws IOException {
        writer = new TxtWriter(file);
        fileReader = new FileReader(csvFile);
        sc = new Scanner(fileReader);
        StringBuilder ss = new StringBuilder(sc.nextLine());
        String s1 = ss.toString();
        int columnNumber = 0;
        while (ss.length() > 0) {
            s1 = StringParser.getFirstSubstring(ss, ";");
            columns.add(ColumnFactory.getNewClass(s1, columnNumber));
            columnNumber++;
        }
    }

    public void addAllRow() {
        while (sc.hasNext()) {
            addRow(sc.nextLine());
        }
    }


    public void convert() throws IOException {
        int numberOfRow = 0;
        writer.write("\n");
        boolean flag = false;
        int fullLength = 0;
        int columnDataSize = columns.get(0).getDataSize();
        for (Column column : columns) {
            if (column.getDataSize() != columnDataSize)
                throw new IndexOutOfBoundsException("Missing data on the csv-table");
            fullLength += column.maxLength();
        }
        writer.writeStartTable(fullLength, columns.size());

        while (numberOfRow < columnDataSize) {
            int i = 0;
            LinkedHashMap<Integer, List<String>> strings = new LinkedHashMap<>();
            for (Column column : columns) {
                if (column.getClass().getSimpleName().equals("StringColumn")) {
                    strings.put(i, StringParser.parsString(column.getData().get(numberOfRow).toString(), " "));

                }
                i++;
            }
            int max = Collections.max(strings.values(), new Comparator<List<String>>() {
                public int compare(List<String> o1, List<String> o2) {
                    return o1.size() - o2.size();
                }
            }).size();
            this.writeRow(numberOfRow, max, strings, flag);
            writer.writeEndRow(columns);
            flag = false;

            numberOfRow++;

        }
    }

    public void close() {
        writer.close();
        sc.close();
    }

    private void addRow(String row) {
        StringBuilder ss = new StringBuilder(row);
        String s1 = ss.toString();
        int l = 0;
        String columnType;
        while (ss.length() > 0) {
            s1 = StringParser.getFirstSubstring(ss, ";");
            for (Column column : columns) {
                columnType = column.getClass().getSimpleName();
                if (l == column.getColumnNumber() && columnType.equals("MoneyColumn")) {
                    s1 = s1.replace(',', '.');
                    column.add(Money.stringToMoney(s1));
                } else if (l == column.getColumnNumber() && columnType.equals("StringColumn")) {
                    column.add(s1);
                } else if (l == column.getColumnNumber() && columnType.equals("IntegerColumn")) {
                    column.add(Integer.valueOf(s1));
                }
            }
            l++;
        }
    }

    private void writeRow(int numberOfRow, int maxStringRow, LinkedHashMap<Integer, List<String>> strings, boolean flag) throws IOException {
        String columnType, s;
        int j;
        for (int i = 0; i < maxStringRow; i++) {
            j = 0;
            for (Column column : columns) {
                columnType = column.getClass().getSimpleName();
                if (columnType.equals("MoneyColumn")) {
                    if (!flag)
                        writer.write(column.toString(numberOfRow, columns.size()));
                    else {
                        writer.write(column.toString(-1, columns.size()));
                    }
                }
                if (columnType.equals("StringColumn")) {
                    if (strings.get(j).size() > i)
                        s = strings.get(j).get(i);
                    else
                        s = "";
                    writer.write(StringParser.convString(columns.size(), column.getColumnNumber(),
                            column.spaceString(s, " "), s));
                }
                if (columnType.equals("IntegerColumn")) {
                    if (!flag)
                        writer.write(column.toString(numberOfRow, columns.size()));
                    else
                        writer.write(column.toString(-1, columns.size()));
                }
                j++;

            }
            flag = true;
            writer.write("\n");
        }
    }
}



