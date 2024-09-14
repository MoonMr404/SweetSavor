<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="model.ProdottoDaoInterface" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Elimina Prodotti</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    
</head>
<body>
<div class="card section">
    <h2>Elimina Prodotti</h2>

    <%
        // Recupera la lista dei prodotti dal database
        ProdottoDaoInterface prodottoDao = new ProdottoDao();
        ArrayList<Prodotto> listaProdotti = null;
        try {
            listaProdotti = prodottoDao.doRetrieveAll();  // Metodo per ottenere tutti i prodotti
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (listaProdotti != null && !listaProdotti.isEmpty()) {
    %>

    <table class="product-table">
        <thead>
        <tr>
            <th>Nome del Prodotto</th>
            <th>Prezzo</th>
            <th>Azione</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Itera su tutti i prodotti e crea una riga con pulsante "Elimina"
            for (Prodotto prodotto : listaProdotti) {
        %>
        <tr>
            <td><%= prodotto.getNomeProdotto() %></td>
            <td><%= prodotto.getPrezzo() %>€</td>
            <td>
                <form action="<%= request.getContextPath() %>/AdminDeleteProductServlet" method="post">
                    <input type="hidden" name="nomeProdotto" value="<%= prodotto.getNomeProdotto() %>">
                    <button type="submit" class="delete-button">Elimina</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <%
    } else {
    %>
    <h3>Nessun prodotto disponibile.</h3>
    <%
        }
    %>

    <%
        // Gestione dell'eliminazione dei prodotti
        String nomeProdotto = request.getParameter("nomeProdotto");

        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            try {
                boolean deleted = prodottoDao.doDelete(nomeProdotto);
                if (deleted) {
    %>
    <h3>Prodotto eliminato con successo.</h3>
    <%
    } else {
    %>
    <h3>Prodotto non trovato.</h3>
    <%
        }
    } catch (SQLException e) {
        e.printStackTrace();
    %>
    <h3>Si è verificato un errore durante l'eliminazione del prodotto.</h3>
    <%
            }
        }
    %>
</div>
</body>
</html>
