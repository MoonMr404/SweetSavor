package model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements UserDaoInterface<User> {

    private String jdbcURL = "jdbc:mysql://localhost:3306/SweetAndSavor";
    private String dbUser = "root";
    private String dbPassword = "Francesco004";


    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    // Metodo privato per ottenere la connessione al database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
    }
    

    
    
    @Override
    public void doSave(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, admin) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setBoolean(4, user.isAmministratore());
            stmt.executeUpdate(); // Usiamo executeUpdate() per eseguire l'inserimento
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean doDelete(User user) throws SQLException {
        String sql = "DELETE FROM users WHERE userID = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public User retrieveUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        User user = null;

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password); // Confronta con la password crittografata nel database

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAmministratore(rs.getBoolean("admin"));
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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
        }
        return userList;
    }

    @Override
    public boolean updateUserInfo(String email, String oldPassword, String newUsername, String newPassword) throws SQLException {
        String updateSql = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        boolean updated = false;

        Connection connection = null;
        PreparedStatement updateStmt = null;

        try {
            connection = getConnection(); // Ottieni la connessione al DB

            // Usa il metodo retrieveUser per ottenere l'utente
            User user = retrieveUser(email, oldPassword);

            // Se l'utente esiste, prepara l'aggiornamento delle credenziali
            if (user != null) {
                updateStmt = connection.prepareStatement(updateSql);
                updateStmt.setString(1, newUsername);
                updateStmt.setString(2, newPassword);  // Assicurati di crittografare la password se necessario
                updateStmt.setString(3, email);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    updated = true;  // Se l'aggiornamento ha successo
                }
            }
        } finally {
            // Chiudi le risorse
            if (updateStmt != null) {
                updateStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return updated;  // Ritorna true se l'aggiornamento ha avuto successo
    }


}