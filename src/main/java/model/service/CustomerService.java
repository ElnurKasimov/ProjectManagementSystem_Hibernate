package model.service;

import model.dao.CustomerDao;
import model.dto.CustomerDto;
import model.service.converter.CustomerConverter;
import model.storage.CustomerStorage;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {
    private CustomerStorage customerStorage;

public  CustomerService (CustomerStorage customerStorage) {
    this.customerStorage = customerStorage;
}

    public Optional<CustomerDto> findByName(String name) {
        Optional<CustomerDao> customerDaoFromDb = customerStorage.findByName(name);
        return customerDaoFromDb.map(CustomerConverter::from);
    }

    public List<CustomerDto> findAllCustomers() {
        return customerStorage.findAll().stream()
                .map(CustomerConverter::from)
                .collect(Collectors.toList());
    }

    public String save (CustomerDto customerDto) {
        String result = "";
        Optional<CustomerDao> customerFromDb = customerStorage.findByName(customerDto.getCustomerName());
        if (customerFromDb.isPresent()) {
            result = validateByName(customerDto, CustomerConverter.from(customerFromDb.get()));
        } else {
            customerStorage.save(CustomerConverter.to(customerDto));
            result = ("Customer " + customerDto.getCustomerName() + " successfully added to the database");
        };
        return result;
    }

    public String  validateByName(CustomerDto customerDto, CustomerDto customerFromDb) {
        if (!customerDto.getReputation().toString().equals(customerFromDb.getReputation().toString())) {
            return String.format("Customer with name '%s' already exist with different " +
                            "reputation '%s'. Please enter correct data",
                    customerDto.getCustomerName(), customerFromDb.getReputation().toString());
        } else return "Ok. A customer with such parameters is present in the database already.";
    }

    public String updateCustomer(CustomerDto customerDto) {
        CustomerDto customerDtoToUpdate = null;
        Optional<CustomerDto>  customerDtoFromDb = findByName(customerDto.getCustomerName());
        if (customerDtoFromDb.isEmpty()) {
            return "Unfortunately, there is no customer with such name in the database.  Please enter correct customer name";
        } else {
            customerDtoToUpdate = customerDtoFromDb.get();
            customerDtoToUpdate.setReputation(customerDto.getReputation());
            CustomerDto updatedCustomerDto = CustomerConverter.from(customerStorage.update(CustomerConverter.to(customerDtoToUpdate)));
            return String.format("Customer %s successfully updated.", updatedCustomerDto.getCustomerName());
        }
    }

    public List<String> deleteCustomer (String name) {
        List<String> result = new ArrayList<>();
        Optional<CustomerDao> customerDaoFromDb = customerStorage.findByName(name);
        if(customerDaoFromDb.isPresent()) {
            result =  customerStorage.delete(customerDaoFromDb.get());
        } else { result.add("There is no customer with such name in the database. Please enter correct data.");}
        return result;
    }

 }
