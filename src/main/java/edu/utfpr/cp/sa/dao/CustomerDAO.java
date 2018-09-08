package edu.utfpr.cp.sa.dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.utfpr.cp.sa.entity.Country;
import edu.utfpr.cp.sa.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

	public void insertCustomer(Customer customer){
		
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Insert into Customer(name, phone, age, creditLimit, country_id) values(initcap(?), ?, ?, ?, ?)";
		
			PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, customer.getName());
			stm.setString(2, customer.getPhone());
			stm.setInt(3, customer.getAge());
			stm.setDouble(4, customer.getCreditLimit());
			stm.setInt(5, customer.getCountry().getCountryId());
			
			stm.execute();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<Customer> findAll(){
		List<Customer> customers = new ArrayList<Customer>();
		
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Customer, Country where Customer.country_id=Country.country_id";
			
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			
			while(result.next()){
				Customer cm = new Customer();
				Country c = new Country();
				c.setCountryId(result.getInt(7));
				c.setName(result.getString(8));
				c.setAcronym(result.getString(9));
				c.setPhoneDigits(result.getInt(10));
				cm.setCountry(c);
				cm.setCustomerId(result.getInt(1));
				cm.setName(result.getString(2));
				cm.setPhone(result.getString(3));
				cm.setAge(result.getInt(4));
				cm.setCreditLimit(result.getDouble(5));
				customers.add(cm);
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customers;
	}
	
	public Customer findByName(String name){
		Customer cm = new Customer();
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Customer, Country where Customer.country_id=Country.country_id and Customer.name=initcap(?)";
			
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, name);
			ResultSet result = stm.executeQuery();
			
			if(result.next()){
				Country c = new Country();
				c.setCountryId(result.getInt(7));
				c.setName(result.getString(8));
				c.setAcronym(result.getString(9));
				c.setPhoneDigits(result.getInt(10));
				cm.setCountry(c);
				cm.setCustomerId(result.getInt(1));
				cm.setName(result.getString(2));
				cm.setPhone(result.getString(3));
				cm.setAge(result.getInt(4));
				cm.setCreditLimit(result.getDouble(5));
			}
			else{
				cm = null;
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cm;
	}
}
