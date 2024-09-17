package model;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface UserDaoInterface<T> {
    public void doSave(User user) throws SQLException;
    public boolean doDelete(T obj ) throws SQLException;
    public T retrieveUser(String email, String password) throws SQLException;
    public List<T> retrieveAll() throws SQLException;
    public boolean updateUserInfo(String email,String oldPassword, String newUsername, String newPassword) throws SQLException;

    boolean verifyPassword(String email, String passwordAttuale) throws SQLException, NoSuchAlgorithmException;

    void updatePassword(String email, String nuovaPassword) throws SQLException, NoSuchAlgorithmException;
}