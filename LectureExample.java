import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LectureExample {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/jdbc_example";
	
	static final String USER = "root";
	static final String PASSWORD = "mypassword"; //REPLACE WITH YOUR PASSWORD
	
	private static List<String> getRecordFromLine(String line) {
	    List<String> values = new ArrayList<String>();
	    try (Scanner rowScanner = new Scanner(line)) {
	        rowScanner.useDelimiter(",");
	        while (rowScanner.hasNext()) {
	            values.add(rowScanner.next());
	        }
	    }
	    return values;
	}

	public static void createTables(Connection conn) throws SQLException {
		// create the menu and order table in jdbc_example
		
		System.out.println("Creating menu table...");

		Statement statement = conn.createStatement();
		// construct the sql statement in a string
		String createMenuSql = "CREATE TABLE if not exists Menu(\n"
				+ "\n"
				+ "	drink varchar(100) not null,\n"
				+ "	category varchar(100),\n"
				+ "	price decimal(10, 2),\n"
				+ "	primary key(drink));\n";
				
		statement.execute(createMenuSql);
		
		
		System.out.println("Creating order table...");
		String createOrderSql = "CREATE TABLE if not exists Orders(\n"
				+ "\n"
				+ "	drink varchar(100) not null,\n"
				+ "	customer varchar(50) not null,\n"
				+ "	quantity int,\n"
				+ "	is_member boolean,\n"
				+ "	primary key(drink, customer),\n"
				+ "	foreign key(drink) references Menu(drink));";
		statement.execute(createOrderSql);
	}
	
	
	public static void insertMenu(Connection conn, String filename) {
		
		PreparedStatement statement = null;
		String insertSql = " INSERT IGNORE  into menu values (?, ?, ?)";
		try {
			statement = conn.prepareStatement(insertSql);
			// setXXX() methods to set the values of these ?
			statement.setString(1, "Coffee");
			statement.setString(2, "Coffee");
			statement.setDouble(3, 5);
			System.out.println(statement);
			statement.executeUpdate();
			// to execute the statememt,
			// executeQuery() -> return a ResultSet
			// executeUpdate() -> to update the database without returning a ResultSet
							// instead, it returns an integer that tells us how many records in the 
							// database were affected
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		/**
		// Reading from the csv file
		List<List<String>> menuRecords = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File("menu.csv"));) {
		    while (scanner.hasNextLine()) {
		    	menuRecords.add(getRecordFromLine(scanner.nextLine()));
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
/**
	public static void insertOrders(Connection conn, String filename) {
		
		// Reading from the csv file

		List<List<String>> orderRecords = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File("order.csv"));) {
			while (scanner.hasNextLine()) {
				orderRecords.add(getRecordFromLine(scanner.nextLine()));
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		
		// open a connection
		// execute a query -> constructed with String concatenation
		try {
			System.out.println("Connecting to database ...");
			conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			createTables(conn);
			//insertMenu(conn, "menu.csv");
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		
	}

}
