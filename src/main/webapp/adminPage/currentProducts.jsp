<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="model.ProdottoDaoInterface" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Base64" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Tutti i prodotti</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <meta name="viewport" content="initial-scale=1, width=device-width">
</head>
<body>
<div class="card section">
    <h2>Tutti i Prodotti</h2>

    <%
        ProdottoDaoInterface prodottoDao = new ProdottoDao();
        ArrayList<Prodotto> prodotti = new ArrayList<>();

        try {
            prodotti = prodottoDao.doRetrieveAll();
        } catch (SQLException e) {
            e.printStackTrace();
    %>
    <h3>Si è verificato un errore durante il recupero dei prodotti.</h3>
    <%
        }
    %>

    <table border="1">
        <thead>
        <tr>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Prezzo</th>
            <th>Disponibilità</th>
            <th>Disponibile</th>
            <th>Categoria</th>
            <th>Immagine</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Prodotto prodotto : prodotti) {
        %>
        <tr>
            <td><%= prodotto.getNomeProdotto() %></td>
            <td><%= prodotto.getDescrizione() %></td>
            <td><%= prodotto.getPrezzo() %></td>
            <td><%= prodotto.getDisponibility() %></td>
            <td><%= prodotto.isDisponibile() ? "Sì" : "No" %></td>
            <td><%= prodotto.getCategoria() %></td>
            <td><img src="data:image/jpeg;base64,<%= new String(Base64.getEncoder().encode(prodotto.getImg())) %>" class="main-productImage" width="150">
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>