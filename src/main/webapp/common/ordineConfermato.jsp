<%@ page import="model.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Ordine Effettuato</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/userPage.css">
    <script src="../Javascript/cart.js"></script>
</head>
<body>

<div class="cart-container">
    <p><i>Il tuo ordine è stato confermato.</i></p>
    <li><a href="<%= request.getContextPath() %>/common/home.jsp">Torna alla Home</a></li>
</div>

</body>
</html>
