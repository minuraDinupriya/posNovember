package model.impl;

import db.DbConnection;
import dto.ItemDto;
import model.ItemModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemModelImpl implements ItemModel {
    private Connection connection= DbConnection.getConnection();
    @Override
    public boolean updateItem(ItemDto dto) throws SQLException {
        String updateQuery="UPDATE item SET code='"+dto.getCode()+"',description='"+dto.getDescription()+"',unitPrice="+dto.getUnitPrice()+", qtyOnHand="+dto.getQty()+" WHERE code='"+dto.getCode()+"'";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(updateQuery)>0;
    }

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException {
        String updateQuery="INSERT INTO item VALUES('"+dto.getCode()+"','"+dto.getDescription()+"','"+dto.getUnitPrice()+"','"+dto.getQty()+"')";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(updateQuery)>0;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException {
        String deleteQuery="DELETE FROM item WHERE code='"+code+"'";
        Statement statement = connection.createStatement();
        return statement.executeUpdate(deleteQuery)>0;
    }

    @Override
    public List<ItemDto> getAllItems() throws SQLException {
        String selectQuery="SELECT * FROM item";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectQuery);

        List<ItemDto> itemDtoList=new ArrayList<>();
        while (resultSet.next()){
            itemDtoList.add(new ItemDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    Double.parseDouble(resultSet.getString(3)),
                    Integer.parseInt(resultSet.getString(4))
            ));
        }
        return itemDtoList;
    }
}
