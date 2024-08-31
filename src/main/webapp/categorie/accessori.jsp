<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.net.URLEncoder" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/fragments/header.jsp" %>
    <style><%@include file="/CSS/fragments.css" %></style>
    <title>SweetSavor</title>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/prodotto.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <script src="https://kit.fontawesome.com/54779b1c8e.js" crossorigin="anonymous"></script>
    <script src="/Javascript/commands.js"></script>
</head>
<body>

<main class="main-page">
    <div class="main-home-container">
        <!-- Showcase prodotti -->
        <%
            ArrayList<Prodotto> lista = null;
            ProdottoDao prodottoDao = new ProdottoDao();


            try {
                lista = prodottoDao.doRetrieveByCategoria("accessori"); // Recupera tutti i prodotti
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (lista != null && !lista.isEmpty()) {
                for (Prodotto prodotto : lista) {
        %>

        <div class="main-product-item">
            <a href="<%= request.getContextPath() %>/prodotto.jsp?nome=<%=prodotto.getNomeProdotto() %>">
                <img src="data:image/jpeg;base64,<%= new String(Base64.getEncoder().encode(prodotto.getImg())) %>" class="main-productImage" width="150">
                <p class="main-product-name"><%= prodotto.getNomeProdotto() %></p>
                <p class="main-product-price"><%= String.format("%.2f",prodotto.getPrezzo())%> &euro;</p>
            </a>
            <button class="main-add-to-cart" onclick="addToCart()">Aggiungi al carrello</button>
        </div>
        <%
            }
        } else {
        %>
        <p class="main-no-products">Nessun prodotto disponibile al momento.</p>
        <%
            }
        %>
    </div>
</main>

<%@ include file="/fragments/footer.jsp" %>
</body>
</html>
