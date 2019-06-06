package packageForTestingTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DBConnection {
	public static String host = "jdbc:postgresql://localhost:5432/detailsKMPO";
	public static String userName = "postgres";
	public static String password = "r7c8.9i4f";
	
	
	public static Connection conn() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(host, userName, password);
			JOptionPane.showMessageDialog(null, "Подключение к бд прошло успешно");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static ResultSet resultSetQuery(String query) {
		try {
			Statement stmt = conn().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void sqlQuery(String query) {
		try {
			Statement stmt = conn().createStatement();
			stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		}

	}
	
