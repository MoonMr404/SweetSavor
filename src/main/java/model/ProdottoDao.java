package model;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDao implements ProdottoDaoInterface {

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
    public void doSave(Prodotto prodotto) throws SQLException {
        String sql = "INSERT INTO prodotto (nome, descrizione, prezzo, disponibilita, disponibile, categoria, imgUrl) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prodotto.getNomeProdotto());
            stmt.setString(2, prodotto.getDescrizione());
            stmt.setDouble(3, prodotto.getPrezzo());
            stmt.setInt(4, prodotto.getDisponibility());
            stmt.setBoolean(5, prodotto.isDisponibile());
            stmt.setString(6, prodotto.getCategoria());
            stmt.setBytes(7, prodotto.getImg());
            stmt.executeUpdate(); // Usiamo executeUpdate() per eseguire l'INSERT
        }
    }

    @Override
    public ArrayList<Prodotto> doRetrieveByCategoria(String categoria) throws SQLException {
        
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM prodotto WHERE categoria = ?";
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, categoria);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");
                double prezzo = resultSet.getDouble("prezzo");
                int disponibilita = resultSet.getInt("disponibilita");
                boolean disponibile = resultSet.getBoolean("disponibile");
                String cat = resultSet.getString("categoria");
                byte[] img = resultSet.getBytes("imgUrl");

                Prodotto prodotto = new Prodotto(nome, descrizione, prezzo, disponibilita, disponibile, cat, img);
                prodotti.add(prodotto);
            }
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return prodotti;
    }

    @Override
    public boolean doDelete(String nome) throws SQLException {
        String sql = "DELETE FROM prodotto WHERE nome = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public Prodotto doRetrieveByName(String name) throws SQLException {
        String sql = "SELECT * FROM prodotto WHERE nome = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String nome = resultSet.getString("nome");
                    String descrizione = resultSet.getString("descrizione");
                    double prezzo = resultSet.getDouble("prezzo");
                    int disponibilita = resultSet.getInt("disponibilita");
                    boolean disponibile = resultSet.getBoolean("disponibile");
                    String categoria = resultSet.getString("categoria");
                    byte[] img = resultSet.getBytes("imgUrl"); // Ottieni l'immagine come array di byte

                    // Crea e restituisci un nuovo oggetto Prodotto con i dati ottenuti dal database
                    return new Prodotto(nome, descrizione, prezzo, disponibilita, disponibile, categoria, img);
                }
            }
        } catch (SQLException e) {
            // Gestione dell'eccezione, log o rilancio a seconda delle necessità dell'applicazione
            throw new SQLException("Errore durante il recupero del prodotto per nome: " + name, e);
        }

        // Se non è stato trovato nessun prodotto con il nome specificato, restituisci null
        return null;
    }



    @Override
    public ArrayList<Prodotto> doRetrieveAll() throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM prodotto";
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String descrizione = resultSet.getString("descrizione");
                double prezzo = resultSet.getDouble("prezzo");
                int disponibilita = resultSet.getInt("disponibilita");
                boolean disponibile = resultSet.getBoolean("disponibile");
                String categoria = resultSet.getString("categoria");
                byte[] img = resultSet.getBytes("imgUrl");

                Prodotto prodotto = new Prodotto(nome, descrizione, prezzo, disponibilita, disponibile, categoria, img);
                prodotti.add(prodotto);
            }
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return prodotti;
    }


    @Override
    public void doUpdateQnt(String nome, int qnt) throws SQLException {
        String selectSql = "SELECT disponibilita FROM prodotto WHERE nome = ?";
        String updateSql = "UPDATE prodotto SET disponibilita = ? WHERE nome = ?";

        try (Connection conn = getConnection()) {
            // Step 1: Ottieni l'attuale disponibilità del prodotto
            int currentQuantity = 0;
            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setString(1, nome);
                ResultSet rs = selectStmt.executeQuery();
                if (rs.next()) {
                    currentQuantity = rs.getInt("disponibilita");
                }
            }

            // Step 2: Calcola la nuova quantità
            int newQuantity = currentQuantity - qnt;

            // Step 3: Aggiorna la quantità solo se rimane positiva
            if (newQuantity >= 0) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, newQuantity);
                    updateStmt.setString(2, nome);
                    updateStmt.executeUpdate();
                }
            } else {
                System.out.println("Errore: Quantità richiesta maggiore della disponibilità attuale.");
            }
        }
    }


    @Override
    public void doUpdate(Prodotto prodotto) throws SQLException {
        String sql = "UPDATE prodotto SET descrizione = ?, prezzo = ?, disponibilita = ?, disponibile = ?, categoria = ?, imgUrl = ? WHERE nome = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prodotto.getDescrizione());
            stmt.setDouble(2, prodotto.getPrezzo());
            stmt.setInt(3, prodotto.getDisponibility());
            stmt.setBoolean(4, prodotto.isDisponibile());
            stmt.setString(5, prodotto.getCategoria());
            stmt.setBytes(6, prodotto.getImg());
            stmt.setString(7, prodotto.getNomeProdotto());
            stmt.executeUpdate();
        }
    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
