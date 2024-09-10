package misc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ConsoleAppGetDBData {
	private static final String[] options = {"0. Leave the app",
			"1. List all Korisnik",
			"2. List all Organizator",
			"3. List all Dogadjaj",
			"4. List all Izmjene",
			"5. List all Lokacija",
			"6. List all Sektor",
	};
	static Connection connection;
	public static void main(String[] args) throws SQLException, IOException {
		connection = DriverManager.getConnection("jdbc:derby:myDB");
		Scanner scanner = new Scanner(System.in);
		boolean continueLoop = true;
		while (continueLoop) {
			int option = scanner.nextInt();
			switch (option) {
			case 0:
				continueLoop = false;
				break;
			case 1:
				printAllKorisnik();
				break;
			case 2:
				printAllOrganizator();
				break;
			case 3:
				printAllDogadjaj();
				break;
			case 4:
				printAllIzmjene();
				break;
			case 5:
				printAllLokacija();
				break;
			case 6:
				printAllSektor();
				break;
			default:
				System.out.println("Option does not exist");
				for (String i : options) {
					System.out.println(i);
				}
			}
		}
		scanner.close();
		connection.close();
	}

	private static void printAllSektor() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Sektor");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}

	private static void printAllLokacija() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Lokacija");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}

	private static void printAllIzmjene() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Izmjene");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}

	private static void printAllDogadjaj() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Dogadjaj");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}

	private static void printAllOrganizator() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Organizator");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}

	private static void printAllKorisnik() throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("SELECT * FROM Korisnik");
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while(rs.next()) {
			for(int i = 1 ; i <= columnsNumber; i++){
			      System.out.print(rs.getString(i) + " ");
			}
			System.out.println("");
		}
		statement.close();
	}
}
