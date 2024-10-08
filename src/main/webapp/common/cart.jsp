<%@ page import="model.Prodotto" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Cart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Carrello</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/cart.css">
    <script src="<%= request.getContextPath() %>/Javascript/cart.js"></script>
</head>
<body>
<jsp:include page="/fragments/header.jsp"/>
<%
    // Recupera il carrello dalla sessione
    Cart cart = (Cart) session.getAttribute("cart");

    // Se il carrello è null, inizializzalo
    if (cart == null) {
        cart = new Cart();
        request.setAttribute("cart", cart);
    }
    // Recupera la lista dei prodotti nel carrello
    ArrayList<Prodotto> listaProdotti = (ArrayList<Prodotto>) cart.getListaProdotti();

    // Calcola il totale dei prodotti e l'importo totale
    int totalProdotti = cart.getTotaleQuantitaProdotti();  // Usa il nuovo metodo
    double totalImporto = cart.getTotalPrice();
%>

<div class="cart-container">
    <% if (!cart.isEmpty()) { %>
    <div class="product-list" id="cart-list">
        <h2>Carrello</h2>

        <!-- Modulo per il checkout -->
        
            <% for (Prodotto prodotto : listaProdotti) {
                int quantitaProdotto = cart.getQuantita(prodotto); // Ottieni la quantità del prodotto
            %>
            <div class="product-item">
                <a href="<%= request.getContextPath() %>/categorie/prodotto.jsp?nome=<%=prodotto.getNomeProdotto() %>">
                <div class="image-box">
                    <img src="data:image/jpeg;base64,<%= new String(java.util.Base64.getEncoder().encode(prodotto.getImg())) %>" height="100">
                </div>
                <div class="product-info">
                    <p>Nome Prodotto: <%= prodotto.getNomeProdotto() %></p>
                    <p>Prezzo: €<%= String.format("%.2f", prodotto.getPrezzo()) %></p>

                    <p class="availability">Disponibilità: <span class="in-stock">In Stock</span> <i class="fa-solid fa-check"></i></p>
                    <form action="<%= request.getContextPath() %>/RemoveProductFromCart " method="post">
                        <input type="hidden" name="nomeProdotto" value="<%= prodotto.getNomeProdotto() %>" />
                        <button type="submit" class="remove-button">Rimuovi</button>
                    </form>
                </div>
            </div>
            <% } %>
            <a href="<%= request.getContextPath() %>/common/checkout.jsp"><button id="checkout-button">Procedi al Pagamento</button></a>
    </div>

    <div class="checkout-box">
        <div class="content-box">
            <h2>Riepilogo Ordine</h2>
            <p>Totale Prodotti: <%= totalProdotti %></p>
            <p>Totale Importo: €<%= String.format("%.2f", totalImporto) %></p>
        </div>
    </div>
    <% } else { %>
    <p>Il carrello è vuoto.</p>
    <% } %>
</div>

<jsp:include page="/fragments/footer.jsp" />


</body>
</html>
