<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Password aggiornata</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="../CSS/cart.css">
    <script src="../Javascript/cart.js" defer></script>
    <jsp:include page="<%= request.getContextPath() %>/fragments/header.jsp"/>
</head>
<body>

<div class="cart-container">
    <p><i>La tua password è stata modificata correttamente.</i></p>
</div>

<jsp:include page="../fragments/footer.jsp" />

<script src="/Javascript/cart.js" >

</script>

</body>
</html>
