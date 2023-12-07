package model;

import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderModel {
    boolean saveOrder(OrderDto dto) throws SQLException;
    OrderDto lastOrder() throws SQLException;
}
