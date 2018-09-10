package edu.utfpr.cp.sa.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.utfpr.cp.sa.entity.Country;

public class CountryDAO {
	
	public void insertCountry(Country country){
		
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Insert into Country(name, acronym, phoneDigits) values(initcap(?), ?, ?)";
		
			PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, country.getName());
			stm.setString(2, country.getAcronym());
			stm.setInt(3, country.getPhoneDigits());
			
			stm.execute();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void updateCountry(Country country) {
		try {
			Connection con = new ConnectionFactory().getConnection();
			String sql = "UPDATE Country SET name = ?, acronym = ?, phoneDigits = ? WHERE country_id = ?;";
			
			PreparedStatement stm = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);			
			stm.setString(1, country.getName());
			stm.setString(2, country.getAcronym());
			stm.setInt(3, country.getPhoneDigits());
			stm.setInt(4, country.getCountryId());
			
			stm.execute();
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<Country> findAll(){
		List<Country> countries = new ArrayList<Country>();
		
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Country";
			
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet result = stm.executeQuery();
			
			while(result.next()){
				Country c = new Country();
				c.setCountryId(result.getInt("country_id"));
				c.setName(result.getString("name"));
				c.setAcronym(result.getString("acronym"));
				c.setPhoneDigits(result.getInt("phoneDigits"));
				
				countries.add(c);
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return countries;
	}
	
	public Country findByName(String name){
		Country c = new Country();
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Country where name=initcap(?)";
			
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, name);
			ResultSet result = stm.executeQuery();
			
			if(result.next()){
				c.setCountryId(result.getInt("country_id"));
				c.setName(result.getString("name"));
				c.setAcronym(result.getString("acronym"));
				c.setPhoneDigits(result.getInt("phoneDigits"));
			}
			else{
				c = null;
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
	
	public Country findById(int id){
		Country c = new Country();
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Country where country_id=?";
			
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, id);
			ResultSet result = stm.executeQuery();
			
			if(result.next()){
				c.setCountryId(result.getInt("country_id"));
				c.setName(result.getString("name"));
				c.setAcronym(result.getString("acronym"));
				c.setPhoneDigits(result.getInt("phoneDigits"));
			}
			else{
				c = null;
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}

	public Country findByAcronym(String acronym) {
		Country c = new Country();
		try{
			Connection con = new ConnectionFactory().getConnection();
			String sql = "Select * from Country where acronym=?";
			
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, acronym);
			ResultSet result = stm.executeQuery();
			
			if(result.next()){
				c.setCountryId(result.getInt("country_id"));
				c.setName(result.getString("name"));
				c.setAcronym(result.getString("acronym"));
				c.setPhoneDigits(result.getInt("phoneDigits"));
			}
			else{
				c = null;
			}
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return c;
	}
}
