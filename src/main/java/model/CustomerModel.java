package model;

import dto.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerModel {
    boolean saveCustomer(CustomerDto dto) throws SQLException;
    boolean updateCustomer(CustomerDto dto) throws SQLException;
    boolean deleteCustomer(String id) throws SQLException;
    List<CustomerDto> getAllCustomers() throws SQLException;
    CustomerDto searchCustomer(String id) throws SQLException;
}
