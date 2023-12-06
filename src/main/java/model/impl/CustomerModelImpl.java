package model.impl;

import db.DbConnection;
import dto.CustomerDto;
import model.CustomerModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CustomerModelImpl implements CustomerModel {

    Connection connection=DbConnection.getConnection();
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException {
        String sql="INSERT INTO customer VALUES('"+dto.getId()+"','"+dto.getName()+"','"+dto.getAddress()+"','"+dto.getSalary()+"')";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(sql)>0;

//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,dto.getId());
//        preparedStatement.setString(2,dto.getName());
//        preparedStatement.setString(3,dto.getAddress());
//        preparedStatement.setString(4, String.valueOf(dto.getSalary()));
//
//        return preparedStatement.executeUpdate(sql)>0;
    }

    @Override
    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        String updateQuery="UPDATE customer SET name='"+dto.getName()+"',address='"+dto.getAddress()+
                "',salary="+dto.getSalary()+" WHERE id='"+dto.getId()+"'";

        Statement statement = connection.createStatement();
        return statement.executeUpdate(updateQuery)>0;
//        String updateQuery="UPDATE customer SET name=(?), address=(?), salary=(?) WHERE id=(?)";
//
//        PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
//        preparedStatement.setString(1,dto.getName());
//        preparedStatement.setString(2,dto.getAddress());
//        preparedStatement.setString(3,String.valueOf(dto.getSalary()));
//        preparedStatement.setString(4,dto.getId());

        //return preparedStatement.executeUpdate(updateQuery)>0;
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException {
        String deleteQuery="DELETE FROM customer WHERE id='"+id+"'";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(deleteQuery)>0;

//        PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
//        preparedStatement.setString(1,id);
//
//        return preparedStatement.executeUpdate(deleteQuery)>0;
    }

    @Override
    public List<CustomerDto> getAllCustomers() throws SQLException {
        List<CustomerDto> dtoList=new ArrayList<>();

        String selectQuery="SELECT * FROM customer";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()){
            dtoList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4))
                    ));
        }
        return dtoList;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException {
        String searchQuery="SELECT * FROM customer WHERE id='"+id+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(searchQuery);
        resultSet.next();

        CustomerDto customerDto=new CustomerDto(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                Double.parseDouble(resultSet.getString(4) )
        );

        return customerDto;
    }
}
