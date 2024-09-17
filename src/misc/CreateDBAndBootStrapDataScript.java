package misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDBAndBootStrapDataScript {
	public static void main(String[] args) throws SQLException, IOException {
		Connection connection = DriverManager.getConnection("jdbc:derby:myDB;create=true");
		Statement statement = connection.createStatement();
		BufferedReader reader = new BufferedReader(new FileReader("src/misc/sqlCreateTables.sql"));
		String line;
		StringBuilder sql = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sql.append(line);
			if (line.endsWith(";")) {
				String exec = sql.toString();
				//System.out.println(exec);
				statement.execute(exec.substring(0, exec.length()-1));
				sql = new StringBuilder();
			}
		}
		reader.close();
		reader = new BufferedReader(new FileReader("src/misc/sqlAddTestData.sql"));
		sql = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			sql.append(line);
			if (line.endsWith(";")) {
				String exec = sql.toString();
				System.out.println(exec);
				statement.execute(exec.substring(0, exec.length()-1));
				sql = new StringBuilder();
			}
		}
		reader.close();
		statement.close();
		connection.close();
	}
}
