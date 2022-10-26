package model.service.converter;

import model.dao.CustomerDao;
import model.dto.CustomerDto;

public class CustomerConverter {

    public static CustomerDto from(CustomerDao entity) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomer_id(entity.getCustomer_id());
        customerDto.setCustomerName(entity.getCustomerName());
        customerDto.setReputation(CustomerDto.Reputation.valueOf(entity.getReputation().toString()));
        return customerDto;
    }

    public static CustomerDao to(CustomerDto entity) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.setCustomer_id(entity.getCustomer_id());
        customerDao.setCustomerName(entity.getCustomerName());
        customerDao.setReputation(CustomerDao.Reputation.valueOf(entity.getReputation().toString()));
        return customerDao;
    }
}

