import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.JDBC;
public class ExampleDB {
	private static String url1 = "jdbc:sqlite:D:/Downloads/DB.Browser.for.SQLite-3.12.2-win64/bd.db";
	private static ExampleDB instance = null;

	public static synchronized ExampleDB getInstance() throws SQLException {
		if (instance == null)
			instance = new ExampleDB();
		return instance;
	}	
	private Connection connection;
	ExampleDB() throws SQLException {
		DriverManager.registerDriver(new JDBC());
		this.connection = DriverManager.getConnection(url1);
	}	
	public String check() {
		try {
			Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url1)) {
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM city");				
				String data="";
				while (resultSet.next()) {
					int id = resultSet.getInt(1);
					String name = resultSet.getString(2);
					String numberPeople = resultSet.getString(3);
					data = data+id+" "+name+" "+numberPeople+"\n";
				}
				conn.close();
				return data;
			}

		} catch (Exception ex) {
			return ex.toString();
		}		
	}
	public String numberOfPeople() {
		try {
			Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url1)) {
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT SUM (numberPeople) FROM city");				
				String data="";
				while (resultSet.next()) {
					String numberPeople = resultSet.getString(1);
					data =numberPeople.concat(" - number of people in all cities");
				}
				conn.close();
				return data;
			}

		} catch (Exception ex) {
			return ex.toString();
		}		
	}
	public String numberOfCities() {
		try {
			Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
			try (Connection conn = DriverManager.getConnection(url1)) {
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT COUNT (name) FROM city");				
				String data="";
				while (resultSet.next()) {
					String numberCities = resultSet.getString(1);
					data =numberCities.concat(" - number of all cities");
				}
				conn.close();
				return data;
			}

		} catch (Exception ex) {
			return ex.toString();
		}		
	}

}
