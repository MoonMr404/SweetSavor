<%@ page import="model.Prodotto, model.ProdottoDao" %>
<%@ page import="java.util.Base64" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
    Prodotto p = (Prodotto) request.getSession().getAttribute("prodotto");

%>
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
<script src="../Javascript/commands.js"></script>

<div class="product-main-box">
    <div class="grid-product-box">
        <%
            String productName = request.getParameter("nome");
            if (productName == null || productName.trim().isEmpty()) {
        %>
        <p>Nessun nome prodotto specificato.</p>
        <%
        } else {
            ProdottoDao prodottoDao = new ProdottoDao();
            Prodotto prodotto = null;

            try {
                prodotto = prodottoDao.doRetrieveByName(productName);
            } catch (Exception e) {
                // Gestione dell'errore
                e.printStackTrace();  // Log dell'errore nel server
                response.sendRedirect(request.getContextPath() + "/errorPage.jsp"); // Reindirizza a una pagina di errore
                return; // Importante terminare l'esecuzione della pagina dopo il reindirizzamento
            }

            if (prodotto != null) {
                byte[] imgBytes = prodotto.getImg();
                String imgBase64 = imgBytes != null ? new String(Base64.getEncoder().encode(imgBytes)) : "";
        %>

        <div class="product-box">
            <div class="image-box">
                <img class="mySlides" src="data:image/jpeg;base64,<%= imgBase64 %>" width="300px">
            </div>

            <div class="user-box">
                <div class="product-description">
                    <p><%= prodotto.getNomeProdotto() %></p>
                    <p><%= String.format("%.2f", prodotto.getPrezzo()) %> &euro;</p>

                    <div class="quantity">
                        <label for="quantity">Quantità:</label>
                        <input type="number" id="quantity" name="quantity" value="1" min="1">
                    </div>
                    <% if (prodotto.isDisponibile()) { %>
                    <p class="availability">Disponibilità: <span class="in-stock">In Stock</span> <i id="check-i" class="fa-solid fa-check"></i></p>
                    <% } else { %>
                    <p class="availability">Disponibilità: Non disponibile</p>
                    <% } %>
                </div>

                <p>Descrizione: <%= prodotto.getDescrizione() %></p>

                <a href="../CartInteraction?action=addProdotto&nome=<%=prodotto.getNomeProdotto()%>&page=prodotto.jsp">
                    <button id="buy-button" >Aggiungi al carrello</button>
                </a>


<%--                //funzione  per addToCart--%>
<%--                function addToCart() {--%>
<%--                const productName = "<%= prodotto.getNomeProdotto() %>";--%>
<%--                const url = `./CartInteraction?action=addProdotto&name=${productName}&page=prodotto.jsp`;--%>

<%--                window.location.href = url;--%>
<%--                }--%>



            <%--                <form action="<%= request.getContextPath() %>/CartInteraction" method="post">--%>
<%--                    <button id="buy-button" >Aggiungi al carrello</button>--%>
<%--                </form>--%>
            </div>
        </div>

        <% } else { %>
        <p>Nessun prodotto disponibile con questo nome.
            <%= productName %></p>
        <% } %>

        <%
            }
        %>
    </div>
</div>
</body>
<%@ include file="../fragments/footer.jsp" %>
</html>
