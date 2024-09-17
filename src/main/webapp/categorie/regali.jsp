<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.net.URLEncoder" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SweetSavor</title>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/prodotto.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <script src="https://kit.fontawesome.com/54779b1c8e.js" crossorigin="anonymous"></script>
</head>
<body>

<%@include file="/fragments/header.jsp" %>
<style><%@include file="/CSS/fragments.css"%></style>
<main>
    <div class="home-container">
        <!-- Showcase prodotti -->
        <%
            ArrayList<Prodotto> lista = null;
            ProdottoDao prodottoDao = new ProdottoDao();

            try {
                lista = prodottoDao.doRetrieveByCategoria("regali"); // Recupera tutti i prodotti
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (lista != null && !lista.isEmpty()) {
                for (Prodotto prodotto : lista) {
        %>

        <div class="product-item">
            <a href="<%= request.getContextPath() %>/prodotto?nome=<%= URLEncoder.encode(prodotto.getNomeProdotto(), "UTF-8") %>">
                <img src="data:image/jpeg;base64, <%= new String(Base64.getEncoder().encode(prodotto.getImg())) %>" class="productImage" />
                <p><%= prodotto.getNomeProdotto() %></p>
                <p><%= prodotto.getPrezzo() %>&euro;</p>
            </a>
        </div>
        <%
            }
        } else {
        %>
        <p>Nessun prodotto disponibile al momento.</p>
        <%
            }
        %>
    </div>
</main>

<%@ include file="/fragments/footer.jsp" %>
</body>
</html>
