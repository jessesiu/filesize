package filesize;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class database {

	Connection con;
	/*
	 * String url="jdbc:postgresql://192.168.208.73:5432/gigadb_v3/"; String
	 * password="gigadb2013"; String user="gigadb";
	 */
	// set database username and password
	String url = "jdbc:postgresql://localhost:5432/gigadb_v3/";
	String password = "gigadb2013";
	String user = "gigadb_jesse";

	Statement stmt;
	PreparedStatement prepforall = null;

	public database() {

		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, user, password);
			// this is important
			con.setAutoCommit(true);
			stmt = con.createStatement();

			// int i=1;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList updatefilesizev2(int id) throws SQLException

	{

		String query = "select location from file where dataset_id=" + id + " and size < 1;";
		ResultSet rs = null;
		String location = null;
		System.out.println(query);
		ArrayList<String> aa = new ArrayList<String>();
		rs = stmt.executeQuery(query);
		while (rs.next()) {
			location = rs.getString(1);

			aa.add(location);

		}

		return aa;

	}

	@SuppressWarnings("null")
	public void updatefilesize(String filelocation, BigInteger size) throws SQLException

	{

		PreparedStatement prep1 = null;
		System.out.println(size);

		String query1 = "update file set size= " + size + " where location= " + "'" + filelocation + "'";

		System.out.println(query1);
		prep1 = con.prepareStatement(query1);
		prep1.executeUpdate();

		// query= "delete from dataset_author where dataset_id="+doi;
		// System.out.println(query);
		// prep1= con.prepareStatement(query);
		// prep1.execute(query);
	}

	public void close() throws SQLException {
		con.close();

	}

	public static void main(String[] args) throws Exception {
		database db = new database();

	}

}
