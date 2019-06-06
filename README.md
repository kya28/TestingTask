# Описание принципов работы

* Клиентская часть написана в Eclipse (Java SE7 – Swing).

* Серверная часть - PostgresSql.

## Структрура проекта

 Проект состоит из пакета, в котором есть три класса:
 * DBConnection.java - подключение к базе данных.
 ```java
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
```
При запуске программы выходит сообщение об успешном подключении к бд

https://github.com/kya28/TestingTask/blob/master/resultImg/ConnectionDatabaseMessage.png

* TestingTask.java - интерфейс проекта.
https://github.com/kya28/TestingTask/blob/master/resultImg/ApplicationInterface.png

 * Details.java - модель объкта и методы.
 *** Модель объекта:***
 ```java
 public class Details {
	private String name;
	private Integer amount;
	
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
 ```
 Метод добавления в базу данных:
 ```java
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
 ```
Метод добавления объекта с тем же наименованием (количество суммируется):
 ```java
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
 ```
 Метод удаления объекта:
 ```java
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
 ```
 Метод редактирования наименования :
 ```java
public void editName() {
				try {
					String amount1 = textField_amount.getText();					
					Integer amount = Integer.parseInt(amount1);
					String name = textField_name.getText();
					String sql = "UPDATE details SET name = ?  where amount = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, name);
					preparedStatement.setInt(2, amount);
					
					textField_name.setText("");
					textField_amount.setText("");
					int resultAdd = preparedStatement.executeUpdate();
					if (resultAdd > 0) {
						JOptionPane.showMessageDialog(null, "Изменения внесены успешно!");
						//emptyTextBoxClean(textField_name, textField_amount);
					} else {
						JOptionPane.showMessageDialog(null, "Изменения не произошли! Заполните поля");						
					}
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "Изменения не удалось осуществить!" + e1.getMessage());			
				}
			}		
 ```
 Метод редактирования количества:
 ```java
 public void editAmount() {
				try {
					String amount1 = textField_amount.getText();					
					Integer amount = Integer.parseInt(amount1);
					String name = textField_name.getText();
					String sql = "UPDATE details SET amount = ?  where name = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, amount);
					preparedStatement.setString(2, name);
					textField_name.setText("");
					textField_amount.setText("");
					int resultAdd = preparedStatement.executeUpdate();
					if (resultAdd > 0) {
						JOptionPane.showMessageDialog(null, "Изменения внесены успешно!");
					} else {
						JOptionPane.showMessageDialog(null, "Изменения не произошли! Заполните поля");						
					}
				} catch(SQLException e1) {
					JOptionPane.showMessageDialog(null, "Изменения не удалось осуществить!" + e1.getMessage());			
				}
			}
 ```
 Метод сортировки по именованию и по количеству:
 ```java
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
 ```
 Метод поиска по наименованию:
 ```java
public void searchToName() {
				try {
				String sql = "SELECT * from details where name = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, textField_name.getText());
				ResultSet rs = preparedStatement.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				}catch(Exception e7) {
					JOptionPane.showMessageDialog(null,e7);
				}
			}
 ```
 Метод обновления отображения базы данных:
 ```java
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
 ```
 