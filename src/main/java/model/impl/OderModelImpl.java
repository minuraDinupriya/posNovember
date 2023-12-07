package model.impl;

import db.DbConnection;
import dto.OrderDto;
import model.OrderDetailModel;
import model.OrderModel;

import java.sql.*;
import java.util.List;

public class OderModelImpl implements OrderModel {

    private Connection connection= DbConnection.getConnection();
    OrderDetailModel orderDetailModel=new OrderDetailModelImpl();

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException {
        try {
            connection.setAutoCommit(false);
//            String saveQuery = "INSERT INTO orders VALUES('"+dto.getOrderId()+"','"+dto.getDate()+"','"+dto.getCustomerID()+"')";
            String saveQuery = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(saveQuery);
            preparedStatement.setString(1, dto.getOrderId());
            preparedStatement.setString(2, dto.getDate());
            preparedStatement.setString(3, dto.getCustomerID());
//            Statement statement = connection.createStatement();
            System.out.println(1);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println(2);
                boolean isDetailsSaved = orderDetailModel.saveOrderDetail(dto.getOrderDetailDtoList());
                if (isDetailsSaved) {
                    System.out.println(3);
                    connection.commit();
                    return true;
                }
            }
        }catch (SQLException  ex){
            connection.rollback();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public OrderDto lastOrder() throws SQLException {
        String selectQuery="SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);
        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }


}
