package model.impl;

import db.DbConnection;
import dto.OrderDto;
import model.OrderModel;

import java.sql.Connection;

public class OderModelImpl implements OrderModel {

    private Connection connection= DbConnection.getConnection();
    @Override
    public boolean saveOrder(OrderDto dto) {
        return false;
    }
}
