<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <meta name="viewport" content="initial-scale=1, width=device-width">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/fragments.css">
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<%
  User user = new User();
  
  
  
%>
<header>
  <div class="logo-container">
    <img id="cup" src="<%= request.getContextPath() %>/logo/SweetSavor.png" width="150" alt="SweetSavor Logo">
  </div>
</header>

<div class="nav-bar">
  <ul class="menu">
    <li><a href="<%= request.getContextPath() %>/common/home.jsp">HOME</a></li>
    <li><a href="<%= request.getContextPath() %>/categorie/te.jsp">T&Egrave</a></li>
    <li><a href="<%= request.getContextPath() %>/categorie/cioccolate.jsp">CIOCCOLATA</a></li>
    <li><a href="<%= request.getContextPath() %>/categorie/tisane.jsp">TISANE</a></li>
    <li><a href="<%= request.getContextPath() %>/categorie/accessori.jsp">ACCESSORI</a></li>
    <li><a href="<%= request.getContextPath() %>/categorie/regali.jsp">REGALI</a></li>
    
    <ul class="user-interaction-box">
      <%
        if (request.getSession(false) != null && session.getAttribute("currentSessionUser") != null) {
      %>
      <li><button onclick="logout()">Logout</button></li>
      <% } else { %>
      <li><a href="<%= request.getContextPath() %>/common/login.jsp">Login<i class='bx bxs-user' id="accBox"></i></a></li>
      <% } %>
      <div class="cart-icon">
        <li><a href="<%= request.getContextPath() %>/common/cart.jsp">Carrello<i class='bx bx-cart-add'></i></a></li>
      </div>
    </ul>
  </ul>
</div>
</body>
</html>
