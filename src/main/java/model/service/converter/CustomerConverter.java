package model.service.converter;

import model.dao.CustomerDao;
import model.dto.CustomerDto;

import java.util.stream.Collectors;

public class CustomerConverter {

    public static CustomerDto from(CustomerDao entity) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer_id(entity.getCustomer_id());
        customerDto.setCustomerName(entity.getCustomerName());
        customerDto.setReputation(CustomerDto.Reputation.valueOf(entity.getReputation().toString()));
       // customerDto.setProjects(entity.getProjects().stream().map(ProjectConverter::from).collect(Collectors.toSet()));
        return customerDto;
    }

    public static CustomerDao to(CustomerDto entity) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setCustomer_id(entity.getCustomer_id());
        customerDao.setCustomerName(entity.getCustomerName());
        customerDao.setReputation(CustomerDao.Reputation.valueOf(entity.getReputation().toString()));
       // customerDao.setProjects(entity.getProjects().stream().map(ProjectConverter::to).collect(Collectors.toSet()));
        return customerDao;
    }
}

