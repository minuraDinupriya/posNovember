package model;

import dto.ItemDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemModel {
    boolean updateItem(ItemDto dto) throws SQLException;
    boolean saveItem(ItemDto dto) throws SQLException;
    boolean deleteItem(String code) throws SQLException;
    List<ItemDto> getAllItems() throws SQLException;
}
