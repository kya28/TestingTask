package packageForTestingTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

public class Details {
	private String name;
	private Integer amount;
	Connection connection = DBConnection.conn();
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	
	public Details() {
		
	}
	public Details(String name, Integer amount) {
		this.name = name;
		this.amount = amount;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private void emptyTextBoxClean(String name, Integer amount) {
		name = null;
		amount = null;
	}		
	public void allDetails(JTable table) {
		try {
			String query = "select * from details";
			Statement statement = connection.createStatement();
			ResultSet resultS = statement.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(resultS));
		}catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	public void addDetailsIfIsInTheDatabase(String name, Integer amount){
			try {
			String query = "UPDATE details SET amount = amount + ? where name = '"+name+"'";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, amount);;
			int resultAdd = preparedStatement.executeUpdate();
			if (resultAdd > 0) {
				JOptionPane.showMessageDialog(null, "Изменения внесены успешно!");
			} else {
				 addDetails(name, amount);
			}
		} catch(SQLException e1) {
			JOptionPane.showMessageDialog(null, "Изменения не удалось осуществить!" + e1.getMessage());			
		}		
	}
	public void addDetails(String name, Integer amount) {
			try {							
			    	preparedStatement = connection
							.prepareStatement("Insert into details (name, amount) values (?,?)");
					preparedStatement.setString(1, name);
					preparedStatement.setInt(2, amount);	
					int resultAdd = preparedStatement.executeUpdate();
					if (resultAdd > 0) {
						JOptionPane.showMessageDialog(null, "Наименование успешно добавлено!");
						emptyTextBoxClean(name, amount);
					} else {
						JOptionPane.showMessageDialog(null, "Наименование не добавлено! Заполните поля");
					}} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "Наименование не добавилось!" + e1.getMessage());
				}
	}
	public void deleteDetails(String name) {
		try {
			preparedStatement = connection
					.prepareStatement("DELETE FROM details Where (name = ?)");
			preparedStatement.setString(1, name);
			
			int resultAdd = preparedStatement.executeUpdate();
			if (resultAdd > 0) {
				JOptionPane.showMessageDialog(null, "Наименование успешно удалено!");
				emptyTextBoxClean(name, amount);
			} else {
				JOptionPane.showMessageDialog(null, "Наименование не удалилось! Заполните поля");						
			}
		} catch(SQLException e1) {
			JOptionPane.showMessageDialog(null, "Наименование не удалилось!" + e1.getMessage());			
		}
	}
	public void sortByName(JTable table) {
		try {
			String query = "SELECT * FROM details ORDER BY name";
			Statement statement = connection.createStatement();
			ResultSet resultS = statement.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(resultS));
		}catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	public void sortByAmountDesc(JTable table) {
		try {
			String query = "SELECT * FROM details ORDER BY amount DESC";
			Statement statement = connection.createStatement();
			ResultSet resultS = statement.executeQuery(query);
			table.setModel(DbUtils.resultSetToTableModel(resultS));
		}catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}
