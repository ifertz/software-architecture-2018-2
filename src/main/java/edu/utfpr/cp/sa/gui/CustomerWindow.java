package edu.utfpr.cp.sa.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import edu.utfpr.cp.sa.dao.CountryDAO;
import edu.utfpr.cp.sa.dao.CustomerDAO;
import edu.utfpr.cp.sa.entity.Country;
import edu.utfpr.cp.sa.entity.Customer;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

class CustomerTableModel extends AbstractTableModel {
	
	private ArrayList<Customer> customers;
	private String columnNames[] = {"Name", "Phone", "Credit Limit", "Age", "Country"};
	
	public CustomerTableModel(List<Customer> list) {
		this.customers = new ArrayList<>(list);
	}
	
	@Override
	public int getRowCount() {
		return customers.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		switch (columnIndex) {
			case 0:
				return this.customers.get(rowIndex).getName();
				
			case 1:
				return this.customers.get(rowIndex).getPhone();
				
			case 2:
				return this.customers.get(rowIndex).getCreditLimit();

			case 3:
				return this.customers.get(rowIndex).getAge();

			case 4:
				return this.customers.get(rowIndex).getCountry().getName();
				
			default:
				break;
		}
		
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Customer customer= customers.get(rowIndex);

        switch (columnIndex) {
        case 0:
            try {
				customer.setName((String) aValue);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            break;
        case 1:
            try {
				customer.setPhone((String) aValue);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            break;
        case 2:
			customer.setCreditLimit(Double.parseDouble((String) aValue));
			break;
		case 3:
			customer.setAge(Integer.parseInt((String) aValue));
			break;
		case 4:
			try {
				customer.setCountry(new CountryDAO().findByName((String) aValue));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        default:
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        fireTableCellUpdated(rowIndex, columnIndex); 
	}
	
	public Customer getCustomer(int rowIndex){
		return customers.get(rowIndex);
	}
	
	
}

public class CustomerWindow extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField phone;
	private JTextField age;
	private JComboBox<String> country;
	private JTable table;
	private CustomerTableModel cutm = new CustomerTableModel(new CustomerDAO().findAll());
	
	
	private void create () {
		Customer c = new Customer();
		Country selected = new CountryDAO().findByName((String)country.getSelectedItem());
		
		try {
			c.setCountry(selected);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
			
		}
		
		c.setAge(new Integer (age.getText()));
		
		try {
			c.setName(name.getText());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
			
		}
		
		try {
			c.setPhone(phone.getText());

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			return;
			
		}
		
		if((new CustomerDAO().findByName(name.getText()))==null){
			new CustomerDAO().insertCustomer(c);
			this.table.setModel(new CustomerTableModel(new CustomerDAO().findAll()));
			JOptionPane.showMessageDialog(this, "Customer successfully added!");
			this.pack();
		}
		else{
			JOptionPane.showMessageDialog(this, "Sorry, customer already exists");			
		}
						
	}
	
	public void update(){
		Customer c = cutm.getCustomer(table.getSelectedRow());
		new CustomerDAO().updateCustomer(c);
		this.table.setModel(new CustomerTableModel(new CustomerDAO().findAll()));
		JOptionPane.showMessageDialog(this, "Data successfully updated");
		
	}
	
	public void delete(){
		Customer c = cutm.getCustomer(table.getSelectedRow());
		new CustomerDAO().deleteCustomer(c);
		this.table.setModel(new CustomerTableModel(new CustomerDAO().findAll()));
		JOptionPane.showMessageDialog(this, "Data successfully deleted");
	}
	
	public CustomerWindow() {
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{452, 0};
		gbl_contentPane.rowHeights = new int[]{115, 23, 427, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JPanel panelInclusion = new JPanel();
		GridBagConstraints gbc_panelInclusion = new GridBagConstraints();
		gbc_panelInclusion.anchor = GridBagConstraints.NORTH;
		gbc_panelInclusion.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelInclusion.insets = new Insets(0, 0, 5, 0);
		gbc_panelInclusion.gridx = 0;
		gbc_panelInclusion.gridy = 0;
		contentPane.add(panelInclusion, gbc_panelInclusion);
		panelInclusion.setLayout(new GridLayout(5, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panelInclusion.add(lblName);
		
		name = new JTextField();
		panelInclusion.add(name);
		name.setColumns(10);
		
		JLabel lblPhone = new JLabel("Phone");
		panelInclusion.add(lblPhone);
		
		phone = new JTextField();
		panelInclusion.add(phone);
		phone.setColumns(10);
		
		JLabel lblAge = new JLabel("Age");
		panelInclusion.add(lblAge);
				
		age = new JTextField();
		panelInclusion.add(age);
		age.setColumns(10);
				
		JLabel lblCountry = new JLabel("Country");
		panelInclusion.add(lblCountry);
				
		country = new JComboBox<>(new CountryDAO().findAll().stream().map(Country::getName).toArray(String[]::new));
		panelInclusion.add(country);
				
		JButton btnCreate = new JButton("Create");
		panelInclusion.add(btnCreate);
				
		JButton btnClose = new JButton("Close");
		panelInclusion.add(btnClose);
		btnClose.addActionListener(e -> this.dispose());
		btnCreate.addActionListener(e -> this.create());
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnDeletar = new JButton("Delete");
		panel.add(btnDeletar);
		
		
		JButton btnAlterar = new JButton("Update");
		panel.add(btnAlterar);
		
		JScrollPane panelTable = new JScrollPane();
		GridBagConstraints gbc_panelTable = new GridBagConstraints();
		gbc_panelTable.anchor = GridBagConstraints.NORTHWEST;
		gbc_panelTable.gridx = 0;
		gbc_panelTable.gridy = 2;
		contentPane.add(panelTable, gbc_panelTable);
		
		table = new JTable();
		table.setModel(cutm);
		panelTable.setViewportView(table);
		
		btnAlterar.addActionListener(e -> update());
		btnDeletar.addActionListener(e -> delete());
		
		this.pack();
		this.setVisible(true);
	}

}
