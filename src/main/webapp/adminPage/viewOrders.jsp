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
    <meta name="viewport" content="initial-scale=1, width=device-width">
</head>
<body>

<h2 style="text-align: center">Lista Ordini</h2>

<!-- Form di ricerca -->
<form method="get" action="viewOrders.jsp" style="text-align: center; margin-bottom: 20px;">

    <label for="searchCustomerName">Cerca per Cliente:</label>
    <input type="text" id="searchCustomerName" name="searchCustomerName" placeholder="Inserisci Nome Cliente"
           value="<%= request.getParameter("searchCustomerName") != null ? request.getParameter("searchCustomerName") : "" %>">

    <button type="submit">Cerca</button>
</form>

<div class="card section">

    <%
        OrdineDAO ordineDAO = new OrdineDAO();
        ArrayList<Ordine> listaOrdini = ordineDAO.selectAllOrdini();

        // Recupera i parametri di ricerca
        String searchCustomerName = request.getParameter("searchCustomerName");

        // Filtra la lista di ordini se sono stati forniti parametri di ricerca
        if (searchCustomerName != null && !searchCustomerName.isEmpty()) {
            listaOrdini.removeIf(ordine ->
                    !(ordine.getNomeCliente().toLowerCase().contains(searchCustomerName.toLowerCase()) ||
                            ordine.getCognomeCliente().toLowerCase().contains(searchCustomerName.toLowerCase()))
            );
        }

        if (!listaOrdini.isEmpty()) {
    %>
    <table border="1" class="order-table" style="width: 100%; border-collapse: collapse; text-align: left;">
        <thead>
        <tr>
            <th>ID Ordine</th>
            <th>Data Ordine</th>
            <th>Cliente</th>
            <th>Indirizzo di Consegna</th>
            <th>Totale</th>
            <th>Stato</th>
        </tr>
        </thead>
        <tbody>
        <%
            // Itera attraverso la lista di ordini filtrata
            for (Ordine ordine : listaOrdini) {
        %>
        <tr>
            <td><%= ordine.getOrderID() %></td>
            <td><%= ordine.getDataOrdine() %></td>
            <td><%= ordine.getNomeCliente() %> <%= ordine.getCognomeCliente() %></td>
            <td><%= ordine.getIndirizzoDiConsegna() %>, <%= ordine.getCap() %></td>
            <td>€ <%= ordine.getTotale() %></td>
            <td><%= ordine.isStato() ? "Completato" : "In corso" %></td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <%
    } else {
    %>
    <p style="text-align: center;">Nessun ordine presente.</p>
    <% } %>
</div>
</body>
</html>
