# Описание принципов работы

## Клиентская часть написана в Eclipse (Java SE7 – Swing).

## Серверная часть - PostgresSql.

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
 * Details.java - модель объкта.
 * TestingTask.java - интерфейс проекта.