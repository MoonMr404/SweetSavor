<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Registrati</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../CSS/registration.css" type="text/css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
</head>
<body>

<script src="../Javascript/commands.js"></script>
<header>
    <img id="cup" src="../logo/SweetSavor.png">
</header>

<script src="/Javascript/control.js"></script>
<div class="container">
    <h1>Registrati</h1>

    <form action="<%= request.getContextPath() %>/register" method="post" onsubmit="validateRegistrationForm()">
    <div class="nameInput">
            <input type="text" placeholder="Nome" id="username" name="username" required>
            <i class='bx bxs-user'></i>
        </div>

        <div class="mailInput">
            <input type="text" placeholder="Email" id="email" name="email" required>
            <i class='bx bxs-envelope'></i>
        </div>

        <div class="password">
            <input type="password" placeholder="Password" id="password" name="password" required>
            <i class='bx bxs-lock-alt'></i>
        </div>
                
        <p>Già registrato?<a href="login.jsp">Accedi</a></p>

        <div class="terms">
            <label><input type="checkbox" required>Accetto i <a href="#">Termini e Condizioni</a></label><br><br>
            <label><input type="checkbox">Iscriviti alla <a href="#">Newsletter</a></label>
        </div>

        <button type="submit" class="btn">Iscriviti</button>
    </form>

    <hr style="width:100%;text-align:left;">

    <div class="loginWith">
        <button type="button" class="btn">
            <i class='bx bxl-google'></i> Continua con Google
        </button>
        <button type="button" class="btn">
            <i class='bx bxl-facebook'></i> Continua con Facebook
        </button>
    </div>
</div>

</body>
</html>
