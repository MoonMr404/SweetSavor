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
    </head>
    <body>
    <jsp:include page="/fragments/header.jsp"/>
    <%
        // Recupera il carrello dalla sessione
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        // Se il carrello è vuoto, inizializzalo
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }

        // Recupera la lista dei prodotti nel carrello
        ArrayList<Prodotto> listaProdotti = (ArrayList<Prodotto>) cart.getListaProdotti();

        // Calcola il totale dei prodotti e l'importo totale
        int totalProdotti = listaProdotti.size();
        double totalImporto = cart.getTotalPrice();
    %>

    <div class="cart-container">
        <% if (!cart.isEmpty()) { %>
        <div class="product-list" id="cart-list">
            <h2>Carrello</h2>

            <form action="<%=request.getContextPath()%>/processCheckout" method="post" id="cart-form" >
                <% for (int i = 0; i < listaProdotti.size(); i++) {
                    Prodotto prodotto = listaProdotti.get(i);
                %>
                <div class="product-item">
                    <div class="image-box">
                        <img src="data:image/jpeg;base64,<%= new String(java.util.Base64.getEncoder().encode(prodotto.getImg())) %>" height="100">
                    </div>
                    <div class="product-info">
                        <p>Nome Prodotto: <%= prodotto.getNomeProdotto() %></p>
                        <p>Prezzo: €<%= String.format("%.2f", prodotto.getPrezzo()) %></p>
                        <div class="quantity">
                            <label for="quantity_<%= i %>">Quantità:</label>
                            <input type="number" id="quantity_<%= i %>" name="quantity_<%= i %>" class="quantity-input" value="1" min="1" max="10" data-index="<%= i %>">
                        </div>
                        <p class="availability">Disponibilità: <span class="in-stock">In Stock</span> <i class="fa-solid fa-check"></i></p>
                        <button type="button" class="remove-button" onclick="removeProduct(<%= i %>)">Rimuovi</button>
                    </div>
                </div>
                <% } %>
            </form>
        </div>

        <div class="checkout-box">
            <div class="content-box">
                <h2>Riepilogo Ordine</h2>
                <p>Totale Prodotti: <%= totalProdotti %></p>
                <p>Totale Importo: €<%= String.format("%.2f", totalImporto) %></p>
                <a href="<%= request.getContextPath() %>/common/checkout.jsp"><button id="checkout-button">Procedi al Pagamento</button></a>
            </div>
        </div>
        <% } else { %>
        <p>Il carrello è vuoto.</p>
        <% } %>
    </div>

    <%@ include file="../fragments/footer.jsp" %>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/fragments.css">
    <script src="<%= request.getContextPath() %>/Javascript/cart.js"></script>

    </body>
    </html>
