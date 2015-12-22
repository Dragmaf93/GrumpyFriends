package project;

import java.io.IOException;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonDBManager extends AbstractDBManager
{
    private static JsonDBManager instance;

    public static JsonDBManager getInstance()
    {
        if (instance == null) {
            instance = new JsonDBManager();
        }
        return instance;
    }

    private final SimpleDateFormat dateSDF = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timestampSDF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    
	public String getMenuDaily(String data) {

		String json = null;
		final String query = "SELECT * FROM DAILYMENU, DISH WHERE DISH.ID=DAILYMENU.DISH AND DAILYMENU.MENUDATE=?";

		final Connection conn = createConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			java.sql.Date dataDB = new Date(dateSDF.parse(data).getTime());
			ps = conn.prepareStatement(query);
			ps.setDate(1, dataDB);

			rs = ps.executeQuery();

			json = toJSON(rs);
		} catch (final SQLException e)
		{
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally
		{
			closeResultSet(rs);
			closeStatement(ps);
			closeConnection(conn);
		}
		return json;
	}
    

    protected Map<String, Object> getEntityFromResultSet(final ResultSet resultSet) throws SQLException
    {
        final ResultSetMetaData metaData = resultSet.getMetaData();
        final int columnCount = metaData.getColumnCount();
        final Map<String, Object> resultsMap = new HashMap<>();
        for (int i = 1; i <= columnCount; ++i)
        {
            final String columnName = metaData.getColumnName(i).toLowerCase();
            final Object object = resultSet.getObject(i);
            resultsMap.put(columnName, object);
        }
        return resultsMap;
    }

    protected String toJSON(final ResultSet resultSet)
    {
        try
        {
            final StringWriter stringWriter = new StringWriter();
            final JsonFactory f = new JsonFactory();
            final JsonGenerator g = f.createGenerator(stringWriter);

            final ResultSetMetaData metaData = resultSet.getMetaData();
            final int columnCount = metaData.getColumnCount();
            g.writeStartArray();
            //            g.writeStartObject();
            while (resultSet.next())
            {
                //                for (int i = 1; i <= columnCount; ++i)
                //                {
                //                    final String columnName = metaData.getColumnName(i).toLowerCase();
                //                    final Object object = resultSet.getObject(i);
                //                    g.writeObjectField(columnName, object);
                //                }

                g.writeStartObject();
                for (int i = 1; i < columnCount + 1; i++)
                {
                    final String column_name = metaData.getColumnName(i).toLowerCase();

                    if (metaData.getColumnType(i) == java.sql.Types.BOOLEAN)
                    {
                        g.writeBooleanField(column_name, resultSet.getBoolean(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.BLOB)
                    {
                        g.writeObjectField(column_name, resultSet.getBlob(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.DOUBLE)
                    {
                        g.writeNumberField(column_name, resultSet.getDouble(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.FLOAT)
                    {
                        g.writeNumberField(column_name, resultSet.getFloat(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.INTEGER)
                    {
                        g.writeNumberField(column_name, resultSet.getInt(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.NVARCHAR)
                    {
                        g.writeStringField(column_name, resultSet.getNString(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.VARCHAR)
                    {
                        final String string = resultSet.getString(column_name);
                        g.writeStringField(column_name, string);
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.TINYINT)
                    {
                        g.writeNumberField(column_name, resultSet.getInt(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.SMALLINT)
                    {
                        g.writeNumberField(column_name, resultSet.getInt(column_name));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.DATE)
                    {
                        final Date date = resultSet.getDate(column_name);
                        g.writeStringField(column_name, dateSDF.format(date));
                    }
                    else if (metaData.getColumnType(i) == java.sql.Types.TIMESTAMP)
                    {
                        final Date date = resultSet.getDate(column_name);
                        g.writeStringField(column_name, timestampSDF.format(date));
                    }
                    else
                    {
                        g.writeObjectField(column_name, resultSet.getObject(column_name));
                    }
                }
                g.writeEndObject();
            }
            //            g.writeEndObject();
            g.writeEndArray();
            g.close();
            return stringWriter.toString();
        }
        catch (final JsonGenerationException e)
        {
            e.printStackTrace();
        }
        catch (final JsonProcessingException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }


}
