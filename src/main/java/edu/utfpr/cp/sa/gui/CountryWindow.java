package edu.utfpr.cp.sa.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import edu.utfpr.cp.sa.dao.CountryDAO;
import edu.utfpr.cp.sa.entity.Country;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;

class CountryTableModel extends AbstractTableModel {
	
	private ArrayList<Country> countries;
	private String columnNames[] = {"Name", "Acronym", "Phone Digits"};
	
	public CountryTableModel(List<Country> list) {
		this.countries = new ArrayList<>(list);
	}
	
	@Override
	public int getRowCount() {
		return countries.size();
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
				return this.countries.get(rowIndex).getName();
				
			case 1:
				return this.countries.get(rowIndex).getAcronym();
				
			case 2:
				return this.countries.get(rowIndex).getPhoneDigits();
	
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
        Country country= countries.get(rowIndex);

        switch (columnIndex) {
        case 0:
            country.setName((String) aValue);
            break;
        case 1:
            country.setAcronym((String) aValue);
            break;
        case 2:
            country.setPhoneDigits(Integer.parseInt((String) aValue));
            break;
        default:
            throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }

        fireTableCellUpdated(rowIndex, columnIndex); 
	}
	
	public Country getCountry(int rowIndex){
		return countries.get(rowIndex);
	}
}

public class CountryWindow extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField acronym;
	private JTextField phoneDigits;
	private JTable table;
	private Set<Country> countries;
	private CountryTableModel ctm = new CountryTableModel(new CountryDAO().findAll());
	
	private void create () {
		Country c = new Country();
		c.setName(name.getText());
		c.setAcronym(acronym.getText());
		c.setPhoneDigits(new Integer(phoneDigits.getText()));
		
		
		
		if((new CountryDAO().findByName(name.getText()))==null){
			new CountryDAO().insertCountry(c);
			this.table.setModel(new CountryTableModel(new CountryDAO().findAll()));
			JOptionPane.showMessageDialog(this, "Country successfully added!");
		}
		else{
			JOptionPane.showMessageDialog(this, "Sorry, country already exists");			
		}
		/*if (this.countries.add(c)) {
			JOptionPane.showMessageDialog(this, "Country successfully added!");
			this.table.setModel(new CountryTableModel(countries));
		
		} else
			JOptionPane.showMessageDialog(this, "Sorry, country already exists");
		*/
		
	}
	
	public void teste(){
		Country c = ctm.getCountry(table.getSelectedRow());
		if(new CountryDAO().findByName(c.getName()) == null && new CountryDAO().findByAcronym(c.getAcronym()) == null) {
			new CountryDAO().updateCountry(c);
			this.table.setModel(new CountryTableModel(new CountryDAO().findAll()));
			JOptionPane.showMessageDialog(this, "Data successfully updated");
		}
		else {
			JOptionPane.showMessageDialog(this, "Sorry, country already exists");
		}
		System.out.println(c.getName());
		System.out.println(c.getAcronym());
	}
	
	public CountryWindow(Set<Country> countries) {
		this.countries = countries;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane panelTable = new JScrollPane();
		contentPane.add(panelTable, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(ctm);
		panelTable.setViewportView(table);
		
		JPanel panelInclusion = new JPanel();
		contentPane.add(panelInclusion, BorderLayout.NORTH);
		panelInclusion.setLayout(new GridLayout(4, 2, 0, 0));
		
		JLabel lblName = new JLabel("Name");
		panelInclusion.add(lblName);
		
		name = new JTextField();
		panelInclusion.add(name);
		name.setColumns(10);
		
		JLabel lblAcronym = new JLabel("Acronym");
		panelInclusion.add(lblAcronym);
		
		acronym = new JTextField();
		panelInclusion.add(acronym);
		acronym.setColumns(10);
		
		JLabel lblPhoneDigits = new JLabel("Phone Digits");
		panelInclusion.add(lblPhoneDigits);
		
		phoneDigits = new JTextField();
		panelInclusion.add(phoneDigits);
		phoneDigits.setColumns(10);
		
		JButton btnCreate = new JButton("Create");
		panelInclusion.add(btnCreate);
		btnCreate.addActionListener(e -> this.create());
		
		JButton btnClose = new JButton("Close");
		panelInclusion.add(btnClose);
		//btnClose.addActionListener(e -> this.dispose());
		btnClose.addActionListener(e -> teste());
		
		this.pack();
		this.setVisible(true);
	}

}
