package com.qa.testingjdbc;

import com.qa.domain.Customer;

public class Runner {

	public static void main(String[] args) {
		
		TestingJDBC myJDBC = new TestingJDBC("jdbc:mysql://localhost:3306/testingjdbcbd", "root", "Root123!");
		
		//myJDBC.testConnection();
		
		
		Customer bella = new Customer("Bella", "O'Hara", "isabellaohara@btinternet.com");
		Customer maddy = new Customer(1, "Maddy", "OHara", "madeleineohara@btinternet.com");
		Customer amanda = new Customer("Amanda", "O'Hara", "amandaohara@hotmail.com");
		
		//myJDBC.createPrepared(amanda);
		
		//myJDBC.create(maddy);
		
		//myJDBC.readAll();
		
		//System.out.println(myJDBC.readCustomerById(1));
		
		//maddy.setLastName("O'Hara");
		
		//maddy.setEmail("maddyohara@btinternet.com");
		
		//myJDBC.update(maddy);
		
		//System.out.println(myJDBC.readCustomerById(1));
		
		myJDBC.delete(4);
		
	}


	
	
	
	
}
