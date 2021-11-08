package com.example.customerService.controller;


import com.example.customerService.entity.AddressEntity;
import com.example.customerService.entity.CustomerEntity;
import com.example.customerService.model.AddressModel;
import com.example.customerService.model.CustomerModel;
import com.example.customerService.model.LoginModel;
import com.example.customerService.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    //API to register customer
    @PostMapping("/registerCustomer")
    public CustomerEntity registerCustomer(@RequestBody CustomerModel customer){
        return accountService.registerCustomer(customer);
    }

    //API to add customer address
    @PatchMapping("/addAddress")
    public CustomerEntity addCustomerAddress(@RequestParam String email, @RequestBody AddressModel address){
        return accountService.addCustomerAddress(email, address);
    }

    //API to get customer details by id
    @GetMapping("/getCustomerById/{email}")
    public CustomerEntity getCustomerById(@PathVariable String email){
        return accountService.getCustomerById(email);
    }

    //API to get all the customer details
    @GetMapping("/get-all-data")
    public List<CustomerEntity> getAllData(){
        return accountService.getAllData();
    }

//    //login API
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginModel loginModel){
        return accountService.loginCustomer(loginModel);
    }
}