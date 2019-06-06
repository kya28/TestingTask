package packageForTestingTask;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.transform.Result;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TestingTask {

	private JFrame frame;
	private JTextField textField_name;
	private JTextField textField_amount;
	private JTable table;
	
	Details details = new Details();
	Connection connection = DBConnection.conn();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestingTask window = new TestingTask();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public TestingTask() throws SQLException {
		initialize();
		
		details.allDetails(table);
		
		JButton btnSearch = new JButton("\u041D\u0430\u0439\u0442\u0438 \u043F\u043E \u043D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u044E");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		});
		btnSearch.setBounds(12, 129, 165, 22);
		frame.getContentPane().add(btnSearch);
		
		JButton btn_edit_amount = new JButton("\u0418\u0437\u043C\u0435\u043D\u0438\u0442\u044C \u043A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E");
		btn_edit_amount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btn_edit_amount.setBounds(222, 46, 159, 23);
		frame.getContentPane().add(btn_edit_amount);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lb_name = new JLabel("\u041D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435");
		lb_name.setBounds(21, 11, 80, 22);
		frame.getContentPane().add(lb_name);
		
		JLabel lb_amount = new JLabel("\u041A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u043E");
		lb_amount.setBounds(21, 46, 80, 22);
		frame.getContentPane().add(lb_amount);
		
		textField_name = new JTextField();
		textField_name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
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
		});
		textField_name.setBounds(111, 12, 86, 20);
		frame.getContentPane().add(textField_name);
		textField_name.setColumns(10);
		
		textField_amount = new JTextField();
		textField_amount.setBounds(111, 47, 86, 20);
		frame.getContentPane().add(textField_amount);
		textField_amount.setColumns(10);
		
		JButton btn_add = new JButton("\u0414\u043E\u0431\u0430\u0432\u0438\u0442\u044C");
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String amount1 = textField_amount.getText();					
					Integer amount = Integer.parseInt(amount1);
					String name = textField_name.getText();
					String sql = "SELECT * from details where name = ?";
					PreparedStatement preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setString(1, name);
					preparedStatement.executeQuery();
					details.addDetailsIfIsInTheDatabase(name, amount);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_add.setBounds(12, 95, 89, 23);
		frame.getContentPane().add(btn_add);
		
		JButton btn_delete = new JButton("\u0423\u0434\u0430\u043B\u0438\u0442\u044C");
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				details.deleteDetails(textField_name.getText());
				
			}
		});
		btn_delete.setBounds(111, 95, 89, 23);
		frame.getContentPane().add(btn_delete);
		
		JButton btn_update = new JButton("\u041E\u0431\u043D\u043E\u0432\u0438\u0442\u044C");
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				details.allDetails(table);
			}
		});
		btn_update.setBounds(210, 95, 89, 23);
		frame.getContentPane().add(btn_update);
		
		JButton btn_close = new JButton("\u0417\u0430\u043A\u0440\u044B\u0442\u044C");
		btn_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btn_close.setBounds(307, 95, 89, 23);
		frame.getContentPane().add(btn_close);
		
		
		JButton btn_edit_name = new JButton("\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043D\u0430\u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u0435");
		btn_edit_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btn_edit_name.setBounds(225, 11, 156, 23);
		frame.getContentPane().add(btn_edit_name);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					int row = table.getSelectedRow();
					String Table_click = (table.getModel().getValueAt(row, 0).toString());
					String sql = "SELECT * from details where name ='"+Table_click+"'";
					PreparedStatement statement = connection.prepareStatement(sql);
					ResultSet resultS = statement.executeQuery();
					if(resultS.next()) {
						textField_name.setText(resultS.getString("name"));
						textField_amount.setText(resultS.getString("amount"));
					}
					
				}catch(Exception e) {
					JOptionPane.showMessageDialog(null,e);
				}
			}
		});
		scrollPane.setBounds(193, 124, 217, 126);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					int row = table.getSelectedRow();
					String Table_click = (table.getModel().getValueAt(row, 0).toString());
					String sql = "SELECT * from details where name ='"+Table_click+"'";
					PreparedStatement statement = connection.prepareStatement(sql);
					ResultSet resultS = statement.executeQuery();
					if(resultS.next()) {
						textField_name.setText(resultS.getString("name"));
						textField_amount.setText(resultS.getString("amount"));
					}
				} catch(Exception e1) {
					JOptionPane.showMessageDialog(null,e1);
				}
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btn_sortToName = new JButton("\u0421\u043E\u0440\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \r\n\u043F\u043E \u0438\u043C\u0435\u043D\u043E\u0432\u0430\u043D\u0438\u044E");
		btn_sortToName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				details.sortByName(table);
			}
		});
		btn_sortToName.setBounds(10, 158, 173, 22);
		frame.getContentPane().add(btn_sortToName);
		
		JButton btn_sortToAmount = new JButton("\u0421\u043E\u0440\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043F\u043E \u043A\u043E\u043B\u0438\u0447\u0435\u0441\u0442\u0432\u0443");
		btn_sortToAmount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				details.sortByAmountDesc(table);
			}
		});
		btn_sortToAmount.setBounds(12, 191, 171, 23);
		frame.getContentPane().add(btn_sortToAmount);
	}
}
