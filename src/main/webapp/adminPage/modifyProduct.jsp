<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica Prodotto</title>
    <link rel="stylesheet" href="/CSS/admin.css">
</head>
<body>

<div id="main" class="clear">
    <h2>Modifica Prodotto</h2>

    <%
        // Ottieni la lista dei prodotti dalla sessione
        ArrayList<Prodotto> prodotti = (ArrayList<Prodotto>) request.getSession().getAttribute("products");

        // Itera su ogni prodotto per mostrare il form di modifica
        for (Prodotto prodotto : prodotti) {
    %>
    <form action="<%= request.getContextPath() %>/AdminModifyProductServlet" method="post">

        <div class="tableRow">
            <p>Nome:</p>
            <p><input type="text" name="nomeProdotto" value="<%= prodotto.getNomeProdotto() %>" required></p>
        </div>

        <div class="tableRow">
            <p>Prezzo:</p>
            <p><input type="text" name="prezzo" value="<%= prodotto.getPrezzo() %>" required></p>
        </div>

        <div class="tableRow">
            <p>Quantità:</p>
            <p><input type="number" name="quantita" value="<%= prodotto.getDisponibility() %>" required></p>
        </div>

        <div class="tableRow">
            <p>Immagine:</p>
            <p><input type="text" name="img" value="<%= prodotto.getImg() %>" required></p>
        </div>

        <div class="tableRow">
            <p>Categoria:</p>
            <p><input type="text" name="categoria" value="<%= prodotto.getCategoria() %>"></p>
        </div>
        
        <div class="tableRow">
            <p></p>
            <p><input type="submit" value="Modifica Prodotto"></p>
        </div>
    </form>
    <% } %>
</div>

</body>
</html>
