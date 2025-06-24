package br.cin.ufpe.nesc2cpn.repository.file;

import br.cin.ufpe.nesc2cpn.repository.Line;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author avld
 */
public abstract class DatabaseUtil
{
    public static String DB_DIR    = "database.diretory";
    public static String DB_EXTRA  = "database.extra";
    public static String DB_PREFER = "database.prefer";
    public static String EXT = ".xml";

    private List<String> diretoryList;
    private String prefer;

    public DatabaseUtil()
    {
        init();
    }

    private void init()
    {
        if( !System.getProperties().containsKey( DB_DIR ) )
        {
            System.setProperty( DB_DIR , "./database/" );
        }

        if( !System.getProperties().containsKey( DB_PREFER ) )
        {
            System.setProperty( DB_PREFER , System.getProperty( DB_DIR ) );
        }

        // -------------------------- //

        diretoryList = new ArrayList<String>();
        diretoryList.add( System.getProperty( DB_DIR ) );

        if( System.getProperties().containsKey( DB_EXTRA ) )
        {
            String[] parts = System.getProperty(DB_EXTRA).split(";");
            diretoryList.addAll( Arrays.asList( parts ) );
        }

        prefer = System.getProperty( DB_PREFER );
    }

    protected Map<String,Schema> list(String module) throws Exception
    {
        Map<String,Schema> schemaMap = new HashMap<String, Schema>();

        for( String dir : diretoryList )
        {
            schemaMap.putAll( list( dir , module ) );
        }

        return schemaMap;
    }

    private Map<String,Schema> list(String dir, String module) throws Exception
    {
        Map<String,Schema> schemaMap = new HashMap<String, Schema>();

        File dirFile = new File( dir );
        File[] fileArray = dirFile.listFiles( new DatabaseFilenameFilter( module ) );

        if( fileArray == null )
        {
            return schemaMap;
        }

        for( File f : fileArray )
        {
            Schema schema = new Schema();
            schema.open( f.getAbsolutePath() );
            schemaMap.put( f.getName() , schema );
        }

        return schemaMap;
    }

    protected Schema open(String module, boolean create) throws Exception
    {
        Line line = new Line();
        line.setModuleName( module );

        return open( line , create );
    }

    protected Schema open(Line line, boolean create) throws Exception
    {
        Schema schema = new Schema();

        for( int i = 0; i < diretoryList.size(); i++ )
        {
            String dir = diretoryList.get( i );
            String filename = filename( dir , line );

            try
            {
                schema.open( filename );
                return schema;
            }
            catch(Exception err)
            {
                if( i + 1 >= diretoryList.size() && !create )
                {
                    return schema;
                }
            }
        }

        String filename = filename( prefer , line );
        createFile( filename );
        schema.open( filename );

        return schema;
    }

    protected String filename(String dir, Line line)
    {
        String filename = dir + File.separator + line.getModuleName() + EXT;
        return filename;
    }

    protected void createFile(String filename) throws Exception
    {
        File file = new File( filename );
        
        File dir = new File( file.getParent() );
        if( !dir.exists() )
        {
            dir.mkdir();
        }

        file.createNewFile();

        FileWriter writer = new FileWriter( file );
        writer.write( getFileExample() );
        writer.close();
    }

    private String getFileExample()
    {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"+
                "<!DOCTYPE properties SYSTEM \"http://java.sun.com/dtd/properties.dtd\">\n"+
                "<properties>\n"+
                "<entry key=\"size\">0</entry>\n"+
                "</properties>";
    }
}