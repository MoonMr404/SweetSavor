<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="model.User" %>
<%@page import="model.UserDaoInterface" %>
<%@page import="model.UserDao" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Cambio Password</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>
<body>
<div class="card-sector">
    <h2>Cambio Password</h2>
    <form action="<%=request.getContextPath()%>/ChangeUserPasswordServlet" method="post">
        <label for="passwordAttuale">Inserire password attuale:</label><br>
        <input type="text" id="passwordAttuale" name="passwordAttuale" required><br>
        <label for="nuovaPassword">Inserire nuova password:</label><br>
        <input type="text" id="nuovaPassword" name="nuovaPassword" required><br>
        <br>
        <input type="submit" value="Cambia password">
    </form>

</div>
</body>
</html>
