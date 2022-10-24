package model.service;

import model.dao.CompanyDao;
import model.dao.CustomerDao;
import model.dto.CustomerDto;
import model.service.converter.CustomerConverter;
import model.storage.CustomerStorage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerService {
    private CustomerStorage customerStorage;

public  CustomerService (CustomerStorage customerStorage) {
    this.customerStorage = customerStorage;
}


    public String save (CustomerDto customerDto) {
        String result = "";
        Optional<CustomerDao> customerFromDb = customerStorage.findByName(customerDto.getCustomer_name());
        if (customerFromDb.isPresent()) {
            result = validateByName(customerDto, CustomerConverter.from(customerFromDb.get()));
        } else {
            customerStorage.save(CustomerConverter.to(customerDto));
            result = ("Customer " + customerDto.getCustomer_name() + " successfully added to the database");
        };
        return result;
    }

    public String  validateByName(CustomerDto customerDto, CustomerDto customerFromDb) {
        if (!customerDto.getReputation().toString().equals(customerFromDb.getReputation().toString())) {
            return String.format("Customer with name '%s' already exist with different " +
                            "reputation '%s'. Please enter correct data",
                    customerDto.getCustomer_name(), customerFromDb.getReputation().toString());
        } else return "Ok. A customer with such parameters is present in the database already.";
    }

    public Optional<CustomerDto> findByName(String name) {
        Optional<CustomerDao> customerDaoFromDb = customerStorage.findByName(name);
        return customerDaoFromDb.map(CustomerConverter::from);
    }

    public List<CustomerDto> findAllCustomers() {
        return customerStorage.findAll()
                .stream().map(Optional::get)
                .map(CustomerConverter::from)
                .collect(Collectors.toList());
    }

    public String deleteCustomer (String name) {
        String result = "";
        Optional<CustomerDao> customerDaoFromDb = customerStorage.findByName(name);
        if(customerDaoFromDb.isPresent()) {
            customerStorage.delete(customerDaoFromDb.get());
            result = "Customer " + name + " successfully deleted from the database";
        } else { result = "There is no customer with such name in the database. Please enter correct data.";}
        return result;
    }

    public String updateCustomer(CustomerDto customerDto) {
        CustomerDto customerDtoToUpdate = null;
        Optional<CustomerDto>  customerDtoFromDb = findByName(customerDto.getCustomer_name());
        if (customerDtoFromDb.isEmpty()) {
            return "Unfortunately, there is no customer with such name in the database.  Please enter correct customer name";
        } else {
            customerDtoToUpdate = customerDtoFromDb.get();
            customerDtoToUpdate.setReputation(customerDto.getReputation());
            CustomerDto updatedCustomerDto = CustomerConverter.from(customerStorage.update(CustomerConverter.to(customerDtoToUpdate)));
            return String.format("Customer %s successfully updated.", updatedCustomerDto.getCustomer_name());
        }
    }
}
