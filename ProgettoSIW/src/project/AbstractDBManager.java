package project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class AbstractDBManager
{
    private static String dbPassword = "ciao";
    private static String dbUrl = "jdbc:mariadb://localhost/progettoSIW";
    private static String dbUsername = "root";

    private static final String dbDriver = "org.mariadb.jdbc.Driver"; 
    
    
    private static Properties getPropertiesQuietly()
    {
        final Properties properties = new Properties();
        try
        {
            properties.load(AbstractDBManager.class.getClassLoader().getResourceAsStream("db.properties"));
        }
        catch (final IOException e)
        {
            throw new RuntimeException("Cannot load db properties file", e);
        }
        return properties;
    }

    private static void loadDriver(final String dbDriver)
    {
        try
        {
            Class.forName(dbDriver);
        }
        catch (final ClassNotFoundException e)
        {
            throw new RuntimeException("Cannot load the driver class", e);
        }
    }

    protected static void loadDBProperties()
    {
//        final Properties properties = getPropertiesQuietly();
//        final String dbDriver = properties.getProperty("db.driver");
//        dbUrl = properties.getProperty("db.url");
//        dbUsername = properties.getProperty("db.username");
//        dbPassword = properties.getProperty("db.password");

        loadDriver(dbDriver);
    }

    protected AbstractDBManager()
    {
        loadDBProperties();
    }

    protected void closeConnection(final Connection c)
    {
        try {
            c.close();
        }
        catch (final SQLException e) { }
    }

    protected void closeResultSet(final ResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (final SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected void closeStatement(final Statement statement)
    {
        if (statement != null)
        {
            try
            {
                statement.close();
            }
            catch (final SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected java.sql.Date convertJavaDateToSqlDate(final java.util.Date date)
    {
        return new java.sql.Date(date.getTime());
    }

    protected Connection createConnection()
    {
        try
        {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }
        catch (final SQLException e)
        {
            throw new RuntimeException("Cannot create the connection", e);
        }
    }

    protected Statement getStatement(final Connection conn)
    {
        try
        {
            return conn.createStatement();
        }
        catch (final SQLException e)
        {
            throw new RuntimeException("Cannot create the statement", e);
        }
    }
}

