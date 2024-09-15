<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.User" %>
<%@ page import="model.UserDao" %>
<%@page import="model.UserDaoInterface" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Cambio email</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
</head>
<body>
<div class="card-sector">
    <h2>Cambio Email</h2>
    <p>La tua attuale email è:</p>>
    <%
        //recupero dell'attuale email
    %>
    <form action="userEmailChange.jsp" method="post">
        <label for="newEmail">Inserisci il <strong>nuovo</strong> indirizzo email che vuoi utilizzare:</label><br>
        <input type="email" id="newEmail" name="newEmail" required><br>

        <label for="password">Inserisci <strong>l'attuale</strong> password per confermare:</label><br>
        <input type="password" id="password" name="password" required><br>
        <br>
        <input type="submit" value="Aggiorna Email">
    </form>

    <%
        //effettaure la sostituizione dell'email solo se la password corrisponde,
        //altrimenti mostrare relativo messaggio di errore.
    %>

</div>
</body>
</html>