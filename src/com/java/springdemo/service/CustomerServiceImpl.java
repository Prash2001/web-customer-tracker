package com.java.springdemo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.java.springdemo.dao.CustomerDAO;
import com.java.springdemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerDAO customerDAO;

  @Override
  @Transactional
  public List<Customer> getCustomers() {
    return customerDAO.getCustomers();
  }

  @Override
  @Transactional
  public void saveCustomer(Customer customer) {
    customerDAO.saveCustomer(customer);
  }

  @Override
  @Transactional
  public Customer getCustomer(int theId) {
    return customerDAO.getCustomer(theId);
  }

  @Override
  @Transactional
  public void deleteCustomer(int theId) {
    customerDAO.deleteCustomer(getCustomer(theId));

  }

  @Override
  @Transactional
  public List<Customer> searchCustomers(String theSearchName) {
    return customerDAO.searchCustomers(theSearchName);
  }

  @Override
  @Transactional
  public List<Customer> getCustomers(int theSortField) {
     return customerDAO.getCustomers(theSortField);
  }

}
