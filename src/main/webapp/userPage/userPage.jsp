<!--  userPage.jps

Pagine di area personale dell'utente che effettua il login.

Funzioni:
-controllo dei suoi ordini;
-modifica della credenziali:
     -email;
     -password;
     -username
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admiìn.css">
    <script src="<%=request.getContextPath()%>/Javascript/userPage.js"></script>
</head>
<body>
<div class="header">
    <h1>Area Personale</h1>
</div>
<div class="container">
    <div class="sidebar">
        <a href="<%= request.getContextPath() %>/userPage/viewUserOrders.jsp" class="sidebar-link" data-url="viewUserOrders.jsp">I tuoi ordini</a>
        <a href="<%= request.getContextPath() %>/userPage/userUsernameChange.jsp" class="sidebar-link" data-url="userUsernameChange.jsp">Modifica il tuo username</a>
        <a href="<%= request.getContextPath() %>/userPage/userPasswordChange.jsp" class="sidebar-link" data-url="userPasswordChange.jsp"> Modifica la tua password</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" >Logout</a>
    </div>
    <div class="main" id="content">
        <!-- area per il contenuto dinamico -->
    </div>
</div>

</body>
</html>
