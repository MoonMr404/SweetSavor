<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Ordine" %>
<%@ page import="model.OrdineDAO" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    // Controlla se l'utente è autenticato come admin
    HttpSession sessionAdmin = request.getSession(false);
    Boolean isAdmin = (sessionAdmin != null) ? (Boolean) sessionAdmin.getAttribute("isAdmin") : false;

    if (isAdmin == null || !isAdmin) {
        // Reindirizza alla pagina di login se non è autenticato
        response.sendRedirect("login.jsp");
        return;
    }
%>


<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Lista ordini</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <script src="admin.js"></script>
</head>
<body>
    <div id="order-details"></div>
</body>
</html>
