<%@ page import="model.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ProdottoDao" %>
<%@ page import="java.util.Base64" %>
<%@ page import="model.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%

    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        // Reindirizza l'utente alla pagina di login se non è autenticato
        response.sendRedirect(request.getContextPath() + "/common/login.jsp");
        return; // Assicurati di fermare l'esecuzione del codice dopo il reindirizzamento
    }
    // Istanziazione del DAO e recupero della lista di prodotti
    ArrayList<Prodotto> listaProdotti = null;
    ProdottoDao prodottoDao = new ProdottoDao();

    try {
        listaProdotti = prodottoDao.doRetrieveAll(); // Recupera tutti i prodotti
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>SweetSavor</title>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/home.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <script src="<%= request.getContextPath() %>/Javascript/loginStatus.js"></script>
</head>
<body>

    <jsp:include page="/fragments/header.jsp"/>

    
<div class="home-container">
    <h1 class="home-header">I NOSTRI PRODOTTI</h1>
    <div class="main-home-container">
        <% if (listaProdotti != null && !listaProdotti.isEmpty()) { %>
            <% for (Prodotto prodotto : listaProdotti) { %>
                <div class="main-product-item">
                    <a href="<%= request.getContextPath() %>/categorie/prodotto.jsp?nome=<%=prodotto.getNomeProdotto() %>">
                        <img src="data:image/jpeg;base64,<%= new String(Base64.getEncoder().encode(prodotto.getImg())) %>" class="main-productImage" width="150" >
                        <p class="main-product-name"><%= prodotto.getNomeProdotto() %></p>
                        <p class="main-product-price"><%= String.format("%.2f",prodotto.getPrezzo())%> &euro;</p>
                    </a>
                    <button class="main-add-to-cart" onclick="addToCart()">Aggiungi al carrello</button>
                </div>
            <% } %>
        <% } else { %>
            <p class="main-no-products">Nessun prodotto disponibile</p>
        <% } %>
    </div>
</div>


    <jsp:include page="/fragments/footer.jsp" />
</body>
</html>
