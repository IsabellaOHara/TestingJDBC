package com.qa.testingjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qa.domain.Customer;

public class TestingJDBC {

	String jdbcConnectionURL;
	String username;
	String password;
	
	
	public TestingJDBC(String jdbcConnectionURL, String username, String password) {
		this.jdbcConnectionURL = jdbcConnectionURL;
		this.username = username;
		this.password = password;
	}
	
	public void testConnection() {
		Connection conn = null;
		
		try {
			System.out.println("trying database connection");
			conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
			System.out.println("I've connected");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//CREATE
	public void create(Customer customer) {
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				Statement statement = conn.createStatement();) {
			
			statement.executeUpdate("INSERT INTO customer(first_name, last_name, email) VALUES ('" + customer.getFirstName() + "','" + customer.getLastName() + "','" + customer.getEmail() + "')");
			
			System.out.println("customer created");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//CREATE PREPARED STATEMENT 
	public void createPrepared(Customer customer) {
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				PreparedStatement statement = conn.prepareStatement("INSERT INTO customer (first_name, last_name, email) VALUES (?,?,?)")) {
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			
			statement.executeUpdate();
			
			System.out.println("customer created");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//RESULT SET
	public Customer customerFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String email = resultSet.getString("email");
		
		return new Customer (id, firstName, lastName, email);
	}
	
	
	//READ BY ID
	public Customer readCustomerById(int id)  {
		
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				PreparedStatement statement = conn.prepareStatement("SELECT * FROM customer WHERE ID = ?")) {
			
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			return customerFromResultSet(resultSet);
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//READ ALL
	public List<Customer> readAll() {
		
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				Statement statement = conn.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customer")) {
			
			List<Customer> customers = new ArrayList<>();
			while(resultSet.next()) {
				customers.add(customerFromResultSet(resultSet));
				
			}
			System.out.println(customers);
			return customers;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//UPDATE
	public Customer update(Customer customer) {
		
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				PreparedStatement statement = conn.prepareStatement("UPDATE customer SET first_name =?, last_name = ?, email =? WHERE id =?")) {
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setInt(4, customer.getId());
			
			
			statement.executeUpdate();
			
			System.out.println("update complete");
			
			return readCustomerById(customer.getId());
			
			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	//DELETE
	public void delete(int id) {
		
		try(Connection conn = DriverManager.getConnection(jdbcConnectionURL, username, password);
				PreparedStatement statement = conn.prepareStatement("DELETE FROM customer WHERE id = ?")) {
			
			statement.setInt(1, id);
			
			statement.executeUpdate();
			
			System.out.println("deleted");
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
}
