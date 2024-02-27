package com.java.springdemo.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.java.springdemo.entity.Customer;
import com.java.springdemo.util.SortUtils;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

  @Autowired
  private SessionFactory sessionfactory;

  @Override
  public List<Customer> getCustomers() {
    Session currentSession = sessionfactory.getCurrentSession();
    // Sort by Last Name
    Query<Customer> query =
        currentSession.createQuery("from Customer order by lastName", Customer.class);
    List<Customer> customers = query.getResultList();
    return customers;
  }

  @Override
  public void saveCustomer(Customer customer) {
    Session currentSession = sessionfactory.getCurrentSession();
    currentSession.saveOrUpdate(customer);
  }

  @Override
  public Customer getCustomer(int theId) {
    Session currentSession = sessionfactory.getCurrentSession();
    return currentSession.get(Customer.class, theId);
  }

  @Override
  public void deleteCustomer(Customer customer) {
    Session currentSession = sessionfactory.getCurrentSession();
    currentSession.delete(customer);
  }

  @Override
  public List<Customer> searchCustomers(String theSearchName) {
    // get the current hibernate session
    Session currentSession = sessionfactory.getCurrentSession();

    Query<Customer> theQuery = null;

    //
    // only search by name if theSearchName is not empty
    //
    if (theSearchName != null && theSearchName.trim().length() > 0) {
      // search for firstName or lastName ... case insensitive
      theQuery = currentSession.createQuery(
          "from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
          Customer.class);
      theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
    } else {
      // theSearchName is empty ... so just get all customers
      theQuery = currentSession.createQuery("from Customer", Customer.class);
    }

    // execute query and get result list
    List<Customer> customers = theQuery.getResultList();

    // return the results
    return customers;

  }

  @Override
  public List<Customer> getCustomers(int theSortField) {

    // get the current hibernate session
    Session currentSession = sessionfactory.getCurrentSession();

    // determine sort field
    String theFieldName = null;

    switch (theSortField) {
      case SortUtils.FIRST_NAME:
        theFieldName = "firstName";
        break;
      case SortUtils.LAST_NAME:
        theFieldName = "lastName";
        break;
      case SortUtils.EMAIL:
        theFieldName = "email";
        break;
      default:
        // if nothing matches the default to sort by lastName
        theFieldName = "lastName";
    }

    // create a query
    String queryString = "from Customer order by " + theFieldName;
    Query<Customer> theQuery = currentSession.createQuery(queryString, Customer.class);

    // execute query and get result list
    List<Customer> customers = theQuery.getResultList();

    // return the results
    return customers;
  }

}
