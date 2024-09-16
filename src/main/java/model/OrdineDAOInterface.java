package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrdineDAOInterface {
    void insertOrdine(Ordine ordine) throws SQLException;
    Ordine selectOrdine(String orderID);
    ArrayList<Ordine> selectAllOrdini();
    boolean deleteOrdine(String orderID) throws SQLException;
    boolean updateOrdine(Ordine ordine) throws SQLException;
    ArrayList<Ordine> selectUserOrder(String userName, String email)throws SQLException;
}
