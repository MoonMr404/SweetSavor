package model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;

public class UserDao implements UserDaoInterface<User> {

    // Variabili per la connessione al database
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SweetAndSavor";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Francesco004";

    // Caricamento del driver MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Metodo privato per ottenere la connessione al database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void doSave(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, admin) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword()); // Criptografia della password può essere aggiunta qui
            stmt.setString(3, user.getEmail());
            stmt.setBoolean(4, user.isAmministratore());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error during saving user", e);
        }
    }

    @Override
    public boolean doDelete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE email = ?"; // Uso di email come identificatore univoco
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SQLException("Error during user deletion", e);
        }
    }

    @Override
    public User retrieveUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        User user = null;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password); // Criptografia può essere aggiunta per confrontare password criptografata
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAmministratore(rs.getBoolean("admin"));
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user retrieval", e);
        }
        return user;
    }

    @Override
    public List<User> retrieveAll() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAmministratore(rs.getBoolean("admin"));
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user list retrieval", e);
        }
        return userList;
    }

    @Override
    public boolean updateUserInfo(String email, String oldPassword, String newUsername, String newPassword) throws SQLException {
        String updateSql = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        boolean updated = false;

        try (Connection connection = getConnection()) {
            // Usa il metodo retrieveUser per ottenere l'utente
            User user = retrieveUser(email, oldPassword);

            // Se l'utente esiste, prepara l'aggiornamento delle credenziali
            if (user != null) {
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, newUsername);
                    updateStmt.setString(2, newPassword);  // Assicurati di criptografare la password se necessario
                    updateStmt.setString(3, email);

                    int rowsAffected = updateStmt.executeUpdate();
                    updated = rowsAffected > 0;
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error during user update", e);
        }

        return updated;
    }

    @Override
    public boolean verifyPassword(String email, String passwordAttuale) throws SQLException, NoSuchAlgorithmException {
        String query = "SELECT password FROM users WHERE email = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                return hashPassword(passwordAttuale).equals(storedPassword); // Assicurati di avere un metodo hashPassword
            }
        } catch (SQLException e) {
            throw new SQLException("Error during password verification", e);
        }
        return false;
    }

    @Override
    public void updatePassword(String email, String nuovaPassword) throws SQLException, NoSuchAlgorithmException {
        String query = "UPDATE users SET password = ? WHERE email = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, hashPassword(nuovaPassword)); // Assicurati di criptografare la nuova password
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error during password update", e);
        }
    }

    // Metodo per hash della password
    private String hashPassword(String password) {
        String hashString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashString = "";
            for (int i = 0; i < hash.length; i++) {
                hashString += Integer.toString((hash[i] & 0xff) | 0x100, 16).substring(1, 3);
            }
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashString;
    }
}
