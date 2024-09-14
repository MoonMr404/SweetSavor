<!-- admin.jsp 
Check if logged
-->

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Admin Page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/CSS/admin.css">
    <script src="admin.js"></script>
</head>
<body>
<div class="header">
    <h1>Pagina Admin</h1>
</div>
<div class="container">

    <div class="sidebar">
        <a href="addProduct.jsp" class="sidebar-link" data-url="addProduct.jsp">Aggiungi Prodotti</a>
        <a href="viewOrders.jsp" class="sidebar-link" data-url="viewOrders.jsp">Controlla Ordini</a>
        <a href="modifyProduct.jsp" class="sidebar-link" data-url="modifyProduct.jsp">Modifica Prodotti</a>
        <a href="currentProducts.jsp" class="sidebar-link" data-url="currentProducts.jsp">Prodotti Presenti</a>
        <a href="deleteProduct.jsp" class="sidebar-link" data-url="deleteProduct.jsp">Elimina Prodotti</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet" >Logout</a>
    </div>
    <div class="main" id="content">
        <!-- parte dove viente caricato il  contenuto dinamico -->
    </div>
</div>

</body>
</html>
