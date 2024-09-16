<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.OrdineDAO" %>
<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista ordini</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <meta name="viewport" content="initial-scale=1, width=device-width">
</head>
<body>
<div class="card-section">
    <h1>I miei ordini</h1>
    <%

        User user = (User) session.getAttribute("user"); 
        OrdineDAO ordineDAO = new OrdineDAO();
        ArrayList<Ordine> listaOrdini = ordineDAO.selectUserOrder(user.getUsername(), user.getEmail());
        
        
        if (!listaOrdini.isEmpty() {
            for (Ordine ordine : listaOrdini) {

    %>

    <div class="order">
        <p>ID Ordine: <%= ordine.getOrderID() %></p>
        <p>Data Ordine: <%= ordine.getDataOrdine() %></p>
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
