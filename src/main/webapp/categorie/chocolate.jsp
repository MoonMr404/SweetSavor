<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Base64" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/fragments/header.jsp" %>
    <title>Sweet Savor</title>
    <link rel="stylesheet" href="<% request.getContextPath();%>/CSS/chocoPage.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    
    <style>
        <%@include file="/CSS/fragments.css"%>
    </style>
</head>
<body>
<main class="main-page">
    <div class="elenco-prodotti">
        <%
            ProdottoDao prodottoDao = new ProdottoDao();
            ArrayList<Prodotto> l = null;
            
            try {
                l = prodottoDao.doRetrieveByCategoria("cioccolata");
            } catch (Exception e) {
                e.printStackTrace();
            }           
            
            if(l != null && !l.isEmpty()) {
                for(Prodotto p : l) {
        %>
        
        <div class="box-prodotto">
            <a href="<%= request.getContextPath() %>/categorie/prodotto.jsp?nome=<%=p.getNomeProdotto() %>">
                <img src="data:image/jpeg;base64,<%= new String(Base64.getEncoder().encode(p.getImg())) %>" class="product-image" width="150">
                <p id="product-name"><%= p.getNomeProdotto() %></p>
                <p id="product-price"><%= String.format("%.2f",p.getPrezzo())%> &euro;</p>
            </a>
                <button class="add-to-cart button" onclick="addToCart()">Aggiungi al carrello</button>
        </div>
        
        <%
            }      
            }else{
        %>
        <h3>Nessun prodotto disponibile al momento</h3>
        <%
            }
        %>
    </div>
</main>
    <%@ include file="/fragments/footer.jsp" %>
</body>
</html>
