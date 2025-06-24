package br.cin.ufpe.nesc2cpn.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author avld
 */
public class ConfigurationUtil
{
    private static String FILENAME = "./nesc2cpn.conf";
    private static Properties properties;
    private static Configuration configuration;

    private Properties setDefault()
    {
        properties = new Properties();
        properties.setProperty( "db.host"     , "jdbc:mysql://localhost:3306/" );
        properties.setProperty( "db.user"     , "root" );
        properties.setProperty( "db.password" , "" );
        properties.setProperty( "db.driver"   , "com.mysql.jdbc.Driver" );

        properties.setProperty( "bootstrap.enabled" , "false" );
        properties.setProperty( "bootstrap.size"    , "1000"  );

        return properties;
    }
    
    private void verification()
    {
        properties.getProperty( "db.host" , "jdbc:mysql://localhost:3306/" );
        properties.getProperty( "db.user" , "root" );
        properties.getProperty( "db.password" , "" );
        properties.getProperty( "db.driver" , "com.mysql.jdbc.Driver" );
        
        verificationBoolean( "bootstrap.enabled" , "false" );
        verificationInt( "bootstrap.size"    , "1000"  );
    }

    private Properties loadProperties()
    {
        if( properties != null )
        {
            return properties;
        }
        
        try
        {
            FileInputStream fis = new FileInputStream( FILENAME );
            
            properties = new Properties();
            properties.loadFromXML( fis );
            verification();

            fis.close();
        }
        catch (IOException ex)
        {
            setDefault();
        }

        return properties;
    }

    private Configuration loadConfiguration()
    {
        if( configuration != null )
        {
            return configuration;
        }

        configuration = new Configuration();
        configuration.setDbHost( properties.getProperty( "db.host" ) );
        configuration.setDbUser( properties.getProperty( "db.user" ) );
        configuration.setDbPassword( properties.getProperty( "db.password" ) );
        configuration.setDbDriver( properties.getProperty( "db.driver" ) );

        boolean bsEnabled = Boolean.parseBoolean( properties.getProperty( "bootstrap.enabled" ) );
        configuration.setBootstrapEnabled( bsEnabled );

        int bsSize = Integer.parseInt( properties.getProperty( "bootstrap.size" ) );
        configuration.setBootstrapSize( bsSize );

        return configuration;
    }

    public Configuration get()
    {
        loadProperties();
        loadConfiguration();

        return configuration;
    }

    public void saveConfiguration() throws Exception
    {
        properties.setProperty( "db.host"     , configuration.getDbHost()     );
        properties.setProperty( "db.user"     , configuration.getDbUser()     );
        properties.setProperty( "db.password" , configuration.getDbPassword() );
        properties.setProperty( "db.driver"   , configuration.getDbDriver()   );

        properties.setProperty( "bootstrap.enabled" , configuration.isBootstrapEnabled() + "" );
        properties.setProperty( "bootstrap.size"    , configuration.getBootstrapSize() + ""   );
        
        saveProperties();
    }
    
    public void saveProperties() throws Exception
    {
        properties.storeToXML( new FileOutputStream( FILENAME )
                             , "Nesc2Cpn configuration" );
    }

    // ---------------------------------- //
    // ---------------------------------- //
    // ---------------------------------- //

    private void verificationBoolean( String property , String value )
    {
        String txt = properties.getProperty( property , value );
        
        try{ Boolean.parseBoolean( txt ); }
        catch(Exception err){ properties.setProperty( property , value ); }
    }

    private void verificationDouble( String property , String value )
    {
        String txt = properties.getProperty( property , value );

        try{ Double.parseDouble( txt ); }
        catch(Exception err){ properties.setProperty( property , value ); }
    }

    private void verificationInt( String property , String value )
    {
        String txt = properties.getProperty( property , value );

        try{ Integer.parseInt( txt ); }
        catch(Exception err){ properties.setProperty( property , value ); }
    }
}
