package model.impl;

import db.DbConnection;
import dto.OrderDetailDto;
import model.OrderDetailModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModelImpl implements OrderDetailModel {
    Connection connection= DbConnection.getConnection();

//    public boolean saveOrderDetail(List<OrderDetailDto> list) throws SQLException {
//        boolean isDetailsSaved=true;
//        for (OrderDetailDto dto:list) {
//            String sql="INSERT INTO orderDetail VALUES ('"+dto.getOrderId()+"','"+dto.getItemCode()+"',"+dto.getQty()+","+dto.getUnitPrice()+")";
////            PreparedStatement preparedStatement = connection.prepareStatement(sql);
////            preparedStatement.setString(1,dto.getOrderId());
////            preparedStatement.setString(2,dto.getItemCode());
////            preparedStatement.setInt(3,dto.getQty());
////            preparedStatement.setDouble(4,dto.getUnitPrice());
//            Statement statement = connection.createStatement();
//            System.out.println(4);
//            System.out.println(dto);
//            boolean save=(statement.executeUpdate(sql)>0);
//            System.out.println(save);
//
//            if (!save){
//                System.out.println(5);
//                isDetailsSaved=false;
//            }
//        }
//        System.out.println(6);
//        return isDetailsSaved;
//    }
//}

    public boolean saveOrderDetail(List<OrderDetailDto> list) throws SQLException {
        boolean isDetailsSaved = true;
        System.out.println(list);
        for (OrderDetailDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1,dto.getOrderId());
            pstm.setString(2,dto.getItemCode());
            pstm.setInt(3,dto.getQty());
            pstm.setDouble(4,dto.getUnitPrice());

//            System.out.println(dto);
//            int i=pstm.executeUpdate();
//            System.out.println(i);
//            if(!(i>0)){
//                System.out.println(-1);
//                isDetailsSaved = false;
//            }
//        }
//        return isDetailsSaved;
            if(!(pstm.executeUpdate()>0)){
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }
}
