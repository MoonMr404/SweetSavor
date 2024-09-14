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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <meta name="viewport" content="initial-scale=1, width=device-width">
</head>
<body>
<div class="card section">
    <h2>Elimina Prodotti</h2>

    <form action="/AdminDeleteProductServlet" method="post">
        <label for="nomeProdotto">Nome del Prodotto:</label>
        <input type="text" id="nomeProdotto" name="nomeProdotto" required>
        <button type="submit">Elimina</button>
    </form>

    <%
        String nomeProdotto = request.getParameter("nomeProdotto");

        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            ProdottoDaoInterface prodottoDao = new ProdottoDao();
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
