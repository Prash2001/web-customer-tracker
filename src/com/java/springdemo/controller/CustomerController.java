package com.java.springdemo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.java.springdemo.entity.Customer;
import com.java.springdemo.service.CustomerService;
import com.java.springdemo.util.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @GetMapping("/showFormForAdd")
  public String showFormForAdd(Model model) {
    Customer customer = new Customer();
    model.addAttribute("customer", customer);
    return "customer-form";
  }

  @PostMapping("/saveCustomer")
  public String saveCustomer(@ModelAttribute("customer") Customer customer) {
    customerService.saveCustomer(customer);
    return "redirect:/customer/list";
  }

  @GetMapping("/showFormForUpdate")
  public String showFormForUpdate(@RequestParam("customerId") int theId, Model model) {

    model.addAttribute("customer", customerService.getCustomer(theId));
    return "customer-form";
  }

  @GetMapping("/deleteCustomer")
  public String deleteCustomer(@RequestParam("customerId") int theId, Model model) {
    customerService.deleteCustomer(theId);
    return "redirect:/customer/list";
  }

  @GetMapping("/search")
  public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
      Model theModel) {
    // search customers from the service
    List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

    // add the customers to the model
    theModel.addAttribute("customers", theCustomers);
    return "list-customer";
  }

  @GetMapping("/list")
  public String listCustomer(Model theModel, @RequestParam(required = false) String sort) {

    // get customers from the service
    List<Customer> theCustomers = null;

    // check for sort field
    if (sort != null) {
      int theSortField = Integer.parseInt(sort);
      theCustomers = customerService.getCustomers(theSortField);
    } else {
      // no sort field provided ... default to sorting by last name
      theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
    }

    // add the customers to the model
    theModel.addAttribute("customers", theCustomers);

    return "list-customer";
  }

}
