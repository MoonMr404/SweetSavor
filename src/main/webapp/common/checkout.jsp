<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>x
    <title>Checkout</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="../CSS/checkout.css">
</head>
<body>
<script src="checkout.js"></script>

<script src="/Javascript/control.js"></script>
<div class="checkout-container">
    <div class="checkout-form">
        <h2>Dettagli Utente</h2>
        <form action="processCheckout" method="post">
            <div class="form-group">
                <label for="first-name">Nome:</label>
                <input type="text" id="first-name" name="first-name" required>
            </div>
            <div class="form-group">
                <label for="last-name">Cognome:</label>
                <input type="text" id="last-name" name="last-name" required>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-group">
                <label for="address">Indirizzo:</label>
                <input type="text" id="address" name="address" required>
            </div>
            <div class="form-group">
                <label for="city">Città:</label>
                <input type="text" id="city" name="city" required>
            </div>
            <div class="form-group">
                <label for="postal-code">CAP:</label>
                <input type="text" id="postal-code" name="postal-code" required>
            </div>
            <div class="form-group">
                <label for="country">Paese:</label>
                <input type="text" id="country" name="country" required>
            </div>

            <h2>Dettagli di Pagamento</h2>
            <div class="form-group">
                <label for="card-name">Nome sulla Carta:</label>
                <input type="text" id="card-name" name="card-name" required>
            </div>
            <div class="form-group">
                <label for="card-number">Numero di Carta:</label>
                <input type="text" id="card-number" name="card-number" required>
            </div>
            <div class="form-group">
                <label for="expiry-date">Data di Scadenza:</label>
                <input type="text" id="expiry-date" name="expiry-date" placeholder="MM/YY" required>
            </div>
            <div class="form-group">
                <label for="cvv">CVV:</label>
                <input type="text" id="cvv" name="cvv" required>
            </div>

            <div class="form-group">
                <button type="submit" id="checkout-button">Conferma Ordine</button>
            </div>
        </form>
    </div>

    <div class="order-summary">
        <h2>Riepilogo Ordine</h2>
        <% 
            Cart cart = (Cart) request.getSession().getAttribute("cart");
            if(cart != null && !cart.isEmpty()) {
                List<Prodotto> listaProdotti = cart.getListaProdotti();
        %>
        <ul>
            <% for(Prodotto prodotto : listaProdotti) { %>
                <li><%= prodotto.getDescrizione() %> - €<%= prodotto.getPrezzo() %></li>
            <% } %>
        </ul>
        <p>Totale Prodotti: <%= listaProdotti.size() %></p>
        <p>Totale Importo: €<%= cart.getTotalPrize() %></p>
        <% } else { %>
            <p>Il carrello è vuoto.</p>
        <% } %>
    </div>
</div>

<style><%@ include file="../CSS/home.css" %></style>

</body>
</html>
