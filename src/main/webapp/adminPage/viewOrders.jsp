<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.OrdineDAO" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Lista ordini</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>
<body>
<div class="card section">
    <h1>Lista Ordini</h1>
    <%
        OrdineDAO ordineDAO = new OrdineDAO();
        ArrayList<Ordine> listaOrdini = ordineDAO.selectAllOrdini();

        if (!listaOrdini.isEmpty()) {
            for (Ordine ordine : listaOrdini) {
    %>
    <div class="order">
        <p>ID Ordine: <%= ordine.getOrderID() %></p>
        <p>Data Ordine: <%= ordine.getDataOrdine() %></p>
        <p>Cliente: <%= ordine.getNomeCliente() %> <%= ordine.getCognomeCliente() %></p>
        <p>Indirizzo di consegna: <%= ordine.getIndirizzoDiConsegna() %>, <%= ordine.getCap() %></p>
        <p>Totale: € <%= ordine.getTotale() %></p>
        <p>Stato: <%= ordine.isStato() ? "Completato" : "In corso" %></p>
    </div>
    <%
        }
    } else {
    %>
    <p>Nessun ordine presente.</p>
    <% } %>
</div>
</body>
</html>
