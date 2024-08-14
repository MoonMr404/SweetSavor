<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDao" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Aggiungi Prodotti</title>
    <link rel="stylesheet" href="../CSS/admin.css">
</head>
<body>
<script src="/Javascript/control.js"></script>
<div class="card section">
    <h2>Aggiungi Prodotti</h2>

    <form action="../addProduct" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="productName">Nome Prodotto:</label>
            <input type="text" id="productName" name="nomeProdotto" required>
        </div>
        <div class="form-group">
            <label for="productDescription">Descrizione Prodotto:</label>
            <textarea id="productDescription" name="descrizione" required></textarea>
        </div>
        <div class="form-group">
            <label for="productPrice">Prezzo Prodotto:</label>
            <input type="number" id="productPrice" name="prezzo" step="0.01" required>
        </div>
        <div class="form-group">
            <label for="productStock">Stock Prodotto:</label>
            <input type="number" id="productStock" name="disponibilita" required>
        </div>
        <div class="form-group">
            <label for="productCategory">Categoria Prodotto:</label>
            <select id="productCategory" name="categoria" required>
                <option value="cioccolata">Cioccolata</option>
                <option value="tisana">Tisana</option>
                <option value="te">Tè</option>
                <option value="regalo">Regalo</option>
                <option value="accessori">Accessori</option>
            </select>
        </div>
        <div class="form-group">
            <label for="productImage">Aggiungi Immagine </label>
            <input type="file" id="productImage" name="img" class="file-input" alt="Err_Img" required>
        </div>
        <div class="form-group">
            <button type="submit" class="custom-file-button">Aggiungi Prodotto</button>
        </div>
    </form>
</div>

</body>
</html>
