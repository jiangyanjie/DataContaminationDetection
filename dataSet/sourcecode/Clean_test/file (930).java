package de.dhbw.blaaah.database.csv;

import de.dhbw.blaaah.*;
import de.dhbw.blaaah.database.TableDefinition;
import de.dhbw.blaaah.exceptions.NoSuchTableException;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementierung der {@link Database}-Schnittstelle mithilfe von CSV-Dateien in einem Ordner. Jede Datei entspricht
 * einer Tabelle in der Datenbank
 */
public class CSVDatabase implements Database {
    /**
     * Basispfad der Datenbank. Dies muss ein Ordner sein, der Ordnername ist der Datenbankname.
     */
    protected final File basePath;

    /**
     * Die Tabellen in dieser Datenbank
     */
    protected final Map<String, CSVTable> tables;

    private Date lastModification;

    public CSVDatabase(String basePath) {
        this(new File(basePath));
    }

    public CSVDatabase(File basePath) {
        this.basePath = basePath;
        // Verzeichnis erstellen
        if (!this.basePath.exists() && !this.basePath.mkdirs())
            throw new IllegalStateException("Couldn't access the database.");

        this.tables = new HashMap<String, CSVTable>();

        loadAllTables();
        modified();
    }

    /**
     * Diese Methode wird von Tabellen aufgerufen, wenn diese verändert wurden.
     */
    protected void modified() {
        lastModification = new Date();
    }

    @Override
    public Date getLastModification() {
        return lastModification;
    }

    @Override
    public String getName() {
        return basePath.getName();
    }

    @Override
    public Iterable<? extends Table> getTables() {
        loadAllTables();

        return tables.values();
    }

    @Override
    public void save() {
        for (CSVTable table : tables.values()) {
            try {
                table.sync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Result createTable(TableDefinition tableDef) {
        File tableFile = new File(basePath, tableDef.getName() + ".csv");

        if (tables.containsKey(tableDef.getName()))
            return getResultFactory().createErrorResult("A table named " + tableDef.getName() + " already exists.");

        CSVTable table = new CSVTable(tableFile, this, tableDef.getColumns());

        tables.put(tableDef.getName(), table);

        return getResultFactory().createSuccessResult();
    }

    @Override
    public Result removeTable(String name) {
        if (!tables.containsKey(name)) {
            return getResultFactory().createErrorResult("No such table: " + name);
        } else {
            CSVTable table = tables.get(name);
            File tableFile = table.getTableFile();

            if (!tableFile.delete()) {
                return getResultFactory().createErrorResult("Table couldn't be deleted!");
            } else {
                tables.remove(name);
                return getResultFactory().createSuccessResult();
            }
        }
    }

    @Override
    public void vacuum() {
        // TODO: Datenbank aufräumen
    }

    @Override
    public Table getTable(String name) {
        if (tables.containsKey(name))
            return tables.get(name);
        else
            return null;
    }

    @Override
    public ResultFactory getResultFactory() {
        return ResultFactory.getDefault();
    }

    @Override
    public RowFactory getRowFactory() {
        return RowFactory.getDefault();
    }

    protected void loadAllTables() {
        File[] tableFiles = basePath.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".csv");
            }
        });

        for (File tableFile : tableFiles) {
            CSVTable table;
            try {
                table = new CSVTable(tableFile, this);
            } catch (NoSuchTableException e) {
                continue;
            }

            tables.put(table.getName(), table);
        }
    }
}
