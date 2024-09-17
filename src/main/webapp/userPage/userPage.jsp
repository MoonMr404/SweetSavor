<%@ page import="model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
 
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>User Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/userPage.css">
    <script src="<%=request.getContextPath()%>/Javascript/userPage.js"></script>
    
</head>
<body>

<div class="container">
    <div class="content">
        <ul>   
            <div class="header">
                <h1>Area Personale</h1>
            </div>
            <li><a href="<%= request.getContextPath() %>/userPage/viewUserOrders.jsp" class="sidebar-link" data-url="viewUserOrders.jsp">I tuoi ordini</a></li>
            <li><a href="<%= request.getContextPath() %>/userPage/userUsernameChange.jsp" class="sidebar-link" data-url="userUsernameChange.jsp">Modifica il tuo username</a></li>
            <li><a href="<%= request.getContextPath() %>/userPage/userPasswordChange.jsp" class="sidebar-link" data-url="userPasswordChange.jsp"> Modifica la tua password</a></li>
            <li><a href="<%= request.getContextPath() %>/LogoutServlet">Logout</a></li>
        </ul>
    </div>
 
</div>

</body>
</html>
