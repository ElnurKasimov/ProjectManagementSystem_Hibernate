package model.service.converter;

import model.dao.CustomerDao;
import model.dto.CustomerDto;

public class CustomerConverter {

    public static CustomerDto from(CustomerDao entity) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(entity.getCustomerId());
        customerDto.setCustomerName(entity.getCustomerName());
        customerDto.setReputation(CustomerDto.Reputation.valueOf(entity.getReputation().toString()));
        return customerDto;
    }

    public static CustomerDao to(CustomerDto entity) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setCustomerId(entity.getCustomerId());
        customerDao.setCustomerName(entity.getCustomerName());
        customerDao.setReputation(CustomerDao.Reputation.valueOf(entity.getReputation().toString()));
        return customerDao;
    }
}

