package project;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import project.beans.Category;
import project.beans.Dish;
import project.beans.User;

public class BeanDBManager extends AbstractDBManager
{
    private static BeanDBManager instance;

    public static BeanDBManager getInstance() {
        if (instance == null) {
            instance = new BeanDBManager();
        }
        return instance;
    }

    private BeanDBManager() {
        super();
    }

    public List<Dish> getDishesByDay(String data)
	{
		final List<Dish> toReturn = new ArrayList<>();
		final String query = "SELECT * FROM DAILYMENU, DISH WHERE DISH.ID=DAILYMENU.DISH AND DAILYMENU.MENUDATE=?";

		final Connection conn = createConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			java.sql.Date dataDB = new Date(Long.parseLong(data));
			ps = conn.prepareStatement(query);
			ps.setDate(1, dataDB);

			rs = ps.executeQuery();

			while (rs.next())
			{
				final Dish dish = new Dish();
				dish.setID(rs.getInt("ID"));
				dish.setName(rs.getString("NAME"));
				dish.setDescription(rs.getString("DESCRIPTION"));
				dish.setRating(rs.getFloat("RATING"));
				dish.setImageUrl(rs.getString("IMAGE_URL"));
				dish.setCategory(Category.getDishCategoryByID(rs.getString("CATEGORY")));
				toReturn.add(dish);
			}
		} catch (final SQLException e)
		{
			e.printStackTrace();
		} finally {
//			closeResultSet(rs);
//			closeStatement(ps);
//			closeConnection(conn);
		}
		return toReturn;
	}
    

	public boolean saveUser(final User user)
    {
		if (getUser(user.getUsername()) == null) {
			String procedure = "{call addUser(?,?,?,?)}";
			CallableStatement callableStatement = null;
			final Connection connection = createConnection();
			try
			{
				callableStatement = connection.prepareCall(procedure);
				callableStatement.setString(1, user.getUsername());
				callableStatement.setString(2, user.getPassword());
				callableStatement.setString(3, user.getEmail());
				callableStatement.setString(4, user.getSocial());
				callableStatement.executeUpdate();
				return true;
	        }
	        catch (final SQLException e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
				closeStatement(callableStatement);
				closeConnection(connection);
	        }
		}
		return false;
    }
    
    public boolean setImageProfile(final User user) {
    	String procedure = "{call setImageProfile(?,?)}";
		CallableStatement callableStatement = null;
		final Connection connection = createConnection();
		try
		{
			callableStatement = connection.prepareCall(procedure);
			callableStatement.setString(1, user.getUsername());
			callableStatement.setString(2, user.getImageUrl());
			callableStatement.executeUpdate();
			return true;
        }
        catch (final SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
			closeStatement(callableStatement);
			closeConnection(connection);
        }
	return false;
    }
    
//    public int countAllUser()
//    {
//        int count = 0;
//        final String query = "SELECT count(id) as num FROM user";
//
//        try (Connection con = createConnection(); PreparedStatement ps = con.prepareStatement(query);)
//        {
//            try (ResultSet rs = ps.executeQuery();)
//            {
//                if (rs.next())
//                {
//                    count = rs.getInt("num");
//                }
//            }
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return count;
//    }

//    public boolean deleteUser(final User user)
//    {
//        boolean res = false;
//        final String query = "DELETE FROM user WHERE id=?";
//
//        try (Connection con = createConnection(); PreparedStatement ps = con.prepareStatement(query);)
//        {
//            ps.setInt(1, user.getId());
//            res = ps.execute();
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return res;
//    }
//
//    public List<User> getAllUser()
//    {
//        final List<User> toRet = new ArrayList<>();
//        final String query = "SELECT * FROM user";
//
//        try (Connection con = createConnection(); Statement statement = con.createStatement();)
//        {
//            try (ResultSet rs = statement.executeQuery(query);)
//            {
//                while (rs.next())
//                {
//                    final User u = new User();
//                    u.setId(rs.getInt("id"));
//                    u.setUsername(rs.getString("username"));
//                    u.setPassword(rs.getString("password"));
//                    u.setCreationDate(rs.getDate("creation_date"));
//                    u.setScore(rs.getDouble("score"));
//                    toRet.add(u);
//                }
//            }
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return toRet;
//    }
//
//    public User getUser(final int id)
//    {
//        final User u = new User();
//        final String query = "SELECT * FROM user WHERE id = ?";
//
//        try (Connection con = createConnection(); PreparedStatement ps = con.prepareStatement(query);)
//        {
//            ps.setInt(1, id);
//            try (ResultSet rs = ps.executeQuery();)
//            {
//                if (rs.next())
//                {
//                    u.setId(rs.getInt("id"));
//                    u.setUsername(rs.getString("username"));
//                    u.setPassword(rs.getString("password"));
//                    u.setCreationDate(rs.getDate("creation_date"));
//                    u.setScore(rs.getDouble("score"));
//                }
//            }
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return u;
//    }
//
//    public User getUser(final String username, final String password)
//    {
//        final User u = new User();
//        final String query = "SELECT * FROM user WHERE username = ? AND password = ?";
//
//        try (Connection con = createConnection(); PreparedStatement ps = con.prepareStatement(query);)
//        {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            try (ResultSet rs = ps.executeQuery();)
//            {
//                if (rs.next())
//                {
//                    u.setId(rs.getInt("id"));
//                    u.setUsername(rs.getString("username"));
//                    u.setPassword(rs.getString("password"));
//                    u.setCreationDate(rs.getDate("creation_date"));
//                    u.setScore(rs.getDouble("score"));
//                }
//            }
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return u;
//    }
//
//    public int saveUser(final User user)
//    {
//        int id = -1;
//        final String query = "INSERT INTO user (username, password, creation_date," + " score) VALUES (?, ?, ?, ?)";
//
//        final Date now = convertJavaDateToSqlDate(new java.util.Date());
//        user.setCreationDate(now);
//
//        try (Connection con = createConnection();
//                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);)
//        {
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            ps.setDate(3, now);
//            ps.setDouble(4, user.getScore());
//            ps.execute();
//            try (ResultSet keys = ps.getGeneratedKeys();)
//            {
//                if (keys.next())
//                {
//                    id = keys.getInt(1);
//                    user.setId(id);
//                }
//            }
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return id;
//    }

    /**
     * Le tabelle devono avere engine InnoDB
     */
//    public void saveUsers(final List<User> users)
//    {
//        final String query = "INSERT INTO user (username, password, creation_date, score) VALUES (?, ?, ?, ?)";
//
//        final Connection conn = createConnection();
//
//        try
//        {
//            // 
//            conn.setAutoCommit(false);
//
//            for (final User user : users)
//            {
//                final Date now = convertJavaDateToSqlDate(new java.util.Date());
//                user.setCreationDate(now);
//                final PreparedStatement ps = conn.prepareStatement(query);
//                ps.setString(1, user.getUsername());
//                ps.setString(2, user.getPassword());
//                ps.setDate(3, now);
//                ps.setDouble(4, user.getScore());
//                ps.executeUpdate();
//            }
//            //
//            Integer.parseInt("ERRORE");
//            conn.commit();
//        }
//        catch (final Exception e)
//        {
//            try
//            {
//                //
//                conn.rollback();
//            }
//            catch (final SQLException e1)
//            {
//                e1.printStackTrace();
//            }
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally
//        {
//            //            conn.setAutoCommit(true);
//            try
//            {
//                conn.close();
//            }
//            catch (final SQLException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }

    public void testConn(final Connection conn) throws SQLException
    {
        final boolean oldValue = conn.getAutoCommit();
        conn.setAutoCommit(false);

        try
        {

            // ...
            conn.commit();
        }
        catch (final Exception e)
        {
            conn.rollback();
        }
        finally
        {
            conn.setAutoCommit(oldValue);
        }

    }
    
    public User getUser(final String username)
    {
		User user = null;
		final String query = "SELECT * FROM USER WHERE USERNAME = ?";
        final Connection conn = createConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setProfileName(rs.getString("PROFILE_NAME"));
				user.setImageUrl(rs.getString("IMAGE_URL"));
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(conn);
        }
		return user;
    }

    public User getUser(final String username, final String password)
    {
		User user = null;
		final String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
        final Connection conn = createConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
				user = new User();
				user.setUsername(rs.getString("USERNAME"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setEmail(rs.getString("EMAIL"));
				user.setProfileName(rs.getString("PROFILE_NAME"));
				user.setImageUrl(rs.getString("IMAGE_URL"));
            }
        }
        catch (final SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(conn);
        }
		return user;
    }
//    public boolean updateUser(final User user)
//    {
//        boolean res = false;
//        final String query = "UPDATE user SET username=?, password=?, score=? " + "WHERE id=?";
//
//        try (Connection con = createConnection(); PreparedStatement ps = con.prepareStatement(query);)
//        {
//            ps.setString(1, user.getUsername());
//            ps.setString(2, user.getPassword());
//            // creationDate NO
//            ps.setDouble(3, user.getScore());
//            ps.setInt(4, user.getId());
//            res = ps.execute();
//        }
//        catch (final SQLException e)
//        {
//            e.printStackTrace();
//        }
//        return res;
//    }
}
