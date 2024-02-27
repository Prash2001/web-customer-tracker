package com.java.springdemo.dao;

import java.util.List;
import com.java.springdemo.entity.Customer;

public interface CustomerDAO {
public List<Customer> getCustomers();

public void saveCustomer(Customer customer);

public Customer getCustomer(int theId);

public void deleteCustomer(Customer customer);

public List<Customer> searchCustomers(String theSearchName);

public List<Customer> getCustomers(int theSortField);

}
