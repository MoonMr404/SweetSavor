<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Cart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<%
    Cart cart = (Cart) request.getSession().getAttribute("cart");
%>
<head>
    <title>Checkout</title>
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta name="viewport" content="initial-scale=1, width=device-width">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/CSS/checkout.css">
</head>
<body>
<script src="checkout.js"></script>
<script src="/Javascript/control.js"></script>

<script>
    // Funzione per validare il campo Nome e Cognome
    function validateName(field) {
        const name = document.getElementById(field);
        const nameRegex = /^[A-Za-z]+$/;
        if (!nameRegex.test(name.value)) {
            showError(field, "Inserisci solo lettere.");
        } else {
            clearError(field);
        }
    }

    // Funzione per validare l'email
    function validateEmail() {
        const email = document.getElementById("email");
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        if (!emailRegex.test(email.value)) {
            showError("email", "Inserisci un'email valida.");
        } else {
            clearError("email");
        }
    }

    // Funzione per validare l'indirizzo
    function validateAddress() {
        const address = document.getElementById("address");
        const addressRegex = /^[A-Za-z0-9\s,.-]{5,100}$/;
        if (!addressRegex.test(address.value)) {
            showError("address", "L'indirizzo deve includere solo lettere, numeri, virgole, punti e trattini.");
        } else {
            clearError("address");
        }
    }

    // Funzione per validare la citta
    function validateCity() {
        const city = document.getElementById("city");
        const cityRegex = /^[A-Za-z\s]{2,50}$/;
        if (!cityRegex.test(city.value)) {
            showError("city", "La città deve contenere solo lettere e spazi.");
        } else {
            clearError("city");
        }
    }

    // Funzione per convalidare il CAP
    function validatePostalCode() {
        const postalCode = document.getElementById("postal-code");
        const postalCodeRegex = /^\d{5}$/;
        if (!postalCodeRegex.test(postalCode.value)) {
            showError("postal-code", "Il CAP deve contenere esattamente 5 cifre.");
        } else {
            clearError("postal-code");
        }
    }

    // Funzione per validare la città
    function validateCity() {
        const city = document.getElementById("city");
        const cityRegex = /^[A-Za-z\s]{2,50}$/;
        if (!cityRegex.test(city.value)) {
            showError("city", "La città deve contenere solo lettere e spazi.");
        } else {
            clearError("city");
        }
    }

    // Funzione per validare il Nome sulla Carta
    function validateCardName() {
        const cardName = document.getElementById("card-name");
        const cardNameRegex = /^[A-Za-z\s]{2,50}$/;
        if (!cardNameRegex.test(cardName.value)) {
            showError("card-name", "Il nome sulla carta deve contenere solo lettere e spazi.");
        } else {
            clearError("card-name");
        }
    }

    // Funzione per validare il numero di carta di credito
    function validateCardNumber() {
        const cardNumber = document.getElementById("card-number");
        const cardRegex = /^\d{16}$/;
        if (!cardRegex.test(cardNumber.value)) {
            showError("card-number", "Inserisci un numero di carta valido di 16 cifre.");
        } else {
            clearError("card-number");
        }
    }

    // Funzione per validare la data di scadenza
    function validateExpiryDate() {
        const expiryDate = document.getElementById("expiry-date");
        const dateRegex = /^(0[1-9]|1[0-2])\/\d{2}$/;

        if (!dateRegex.test(expiryDate.value)) {
            showError("expiry-date", "Inserisci una data valida nel formato MM/YY.");
            return;
        }

        // Controllo che la data non sia nel passato
        const [month, year] = expiryDate.value.split("/").map(Number);
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear() % 100; // ottieni le ultime due cifre dell'anno corrente
        const currentMonth = currentDate.getMonth() + 1; // mese corrente (da 1 a 12)

        if (year < currentYear || (year === currentYear && month < currentMonth)) {
            showError("expiry-date", "La data di scadenza non può essere nel passato.");
        } else {
            clearError("expiry-date");
        }
    }


    // Funzione per validare il CVV
    function validateCVV() {
        const cvv = document.getElementById("cvv");
        const cvvRegex = /^\d{3}$/;
        if (!cvvRegex.test(cvv.value)) {
            showError("cvv", "Inserisci un CVV valido di 3 cifre.");
        } else {
            clearError("cvv");
        }
    }

    // Funzioni per mostrare e rimuovere gli errori
    function showError(field, message) {
        const errorField = document.getElementById(field + "-error");
        if (errorField) {
            errorField.innerText = message;
            errorField.style.display = "block";
        }
    }

    function clearError(field) {
        const errorField = document.getElementById(field + "-error");
        if (errorField) {
            errorField.innerText = "";
            errorField.style.display = "none";
        }
    }
</script>

<div class="checkout-container">
    <div class="checkout-form">
        <h2>Dettagli Utente</h2>

        <form action="<%= request.getContextPath() %>/CheckoutServlet" method="post">
            <div class="form-group">
                <label for="first-name">Nome:</label>
                <input type="text" id="first-name" name="first-name" required pattern="[A-Za-z]+" title="Inserisci solo lettere" onblur="validateName('first-name')">
                <span id="first-name-error" class="error-message" style="display:none; color: red;"></span>
            </div>
            <div class="form-group">
                <label for="last-name">Cognome:</label>
                <input type="text" id="last-name" name="last-name" required pattern="[A-Za-z]+" title="Inserisci solo lettere" onblur="validateName('last-name')">
                <span id="last-name-error" class="error-message" style="display:none; color: red;"></span>
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required onblur="validateEmail()">
                <span id="email-error" class="error-message" style="display:none; color: red;"></span>
            </div>
            <div class="form-group">
                <label for="address">Indirizzo:</label>
                <input type="text" id="address" name="address" required pattern="^[A-Za-z0-9\s,.-]{5,100}$" title="L'indirizzo deve includere solo lettere, numeri, virgole, punti e trattini." onblur="validateAddress()">
                <span id="address-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <label for="city">Città:</label>
                <input type="text" id="city" name="city" required pattern="^[A-Za-z\s]{2,50}$" title="La città deve contenere solo lettere e spazi." onblur="validateCity()">
                <span id="city-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <label for="postal-code">CAP:</label>
                <input type="text" id="postal-code" name="postal-code" required pattern="^\d{5}$" title="Il CAP deve contenere esattamente 5 cifre." onblur="validatePostalCode()">
                <span id="postal-code-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <label for="country">Paese:</label>
                <input type="text" id="country" name="country" required pattern="^[A-Za-z\s]{2,50}$" title="Il paese deve contenere solo lettere e spazi." onblur="validateCountry()">
                <span id="country-error" class="error-message" style="display:none; color: red;"></span>
            </div>


            <h2>Dettagli di Pagamento</h2>
            <div class="form-group">
                <label for="card-name">Nome sulla Carta:</label>
                <input type="text" id="card-name" name="card-name" required pattern="^[A-Za-z\s]{2,50}$" title="Il nome sulla carta deve contenere solo lettere e spazi." onblur="validateCardName()">
                <span id="card-name-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <label for="card-number">Numero di Carta:</label>
                <input type="text" id="card-number" name="card-number" required pattern="\d{16}" maxlength="16" title="Inserisci un numero di carta valido di 16 cifre" onblur="validateCardNumber()">
                <span id="card-number-error" class="error-message" style="display:none; color: red;"></span>
            </div>
            <div class="form-group">
                <label for="expiry-date">Data di Scadenza:</label>
                <input type="text" id="expiry-date" name="expiry-date" placeholder="MM/YY" required pattern="^(0[1-9]|1[0-2])\/\d{2}$" title="Inserisci una data valida nel formato MM/YY" onblur="validateExpiryDate()">
                <span id="expiry-date-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <label for="cvv">CVV:</label>
                <input type="text" id="cvv" name="cvv" required pattern="\d{3}" maxlength="3" title="Inserisci un CVV valido di 3 cifre" onblur="validateCVV()">
                <span id="cvv-error" class="error-message" style="display:none; color: red;"></span>
            </div>

            <div class="form-group">
                <button type="submit" id="checkout-button" onclick="retrieveDataFromCheckout()">Conferma Ordine</button>
            </div>
        </form>
    </div>

    <div class="order-summary">
        <h2>Riepilogo Ordine</h2>
        <%
            if(cart != null && !cart.isEmpty()) {
                List<Prodotto> listaProdotti = cart.getListaProdotti();
        %>
        <ul>
            <% for(Prodotto prodotto : listaProdotti) { %>
            <li><%= prodotto.getDescrizione() %> - €<%= prodotto.getPrezzo() %></li>
            <% } %>
        </ul>
        <p>Totale Prodotti: <%= listaProdotti.size() %></p>
        <p>Totale Importo: €<%= cart.getTotalPrice() %></p>
        <% } else { %>
        <p>Il carrello è vuoto.</p>
        <% } %>
    </div>
</div>


</body>
</html>
