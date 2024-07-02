<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Login</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../CSS/login.css" type="text/css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <script src="../Javascript/commands.js"></script>
</head>
<body>
<header>
    <img id="cup" src="../img/SweetSavor.png">
</header>

<div class="container">
    <h1>Accedi</h1>

    <form action="<%= request.getContextPath() %>/loginServlet" method="post" onsubmit="return validateLoginForm();">
        <div class="mailInput">
            <input type="text" placeholder="Email" id="email" name="email">
            <i class='bx bxs-envelope'></i>
        </div>

        <div class="password">
            <input type="password" placeholder="Password" id="password" name="password">
            <i class='bx bxs-lock-alt'></i>
        </div>

        <div class="not-registered">
            <p>Non hai un account? <a href="<%= request.getContextPath() %>/common/registration.jsp">Registrati</a></p>
        </div>

        <!-- Messaggio di errore -->
        <% String error = request.getParameter("error");
            if (error != null) {
                if (error.equals("empty")) { %>
        <p style="color: red;">Email e/o password vuoti.</p>
        <% } else if (error.equals("invalid")) { %>
        <p style="color: red;">Credenziali non valide.</p>
        <% } else if (error.equals("db")) { %>
        <p style="color: red;">Errore di database.</p>
        <% }
        } %>

        <button type="submit" class="btn">Accedi</button>
    </form>

    <hr style="width:100%;text-align:left;">

    <div class="loginWith">
        <button type="button" class="btn">
            <i class='bx bxl-google'></i> Accedi con Google
        </button>
        <button type="button" class="btn">
            <i class='bx bxl-facebook'></i> Accedi con Facebook
        </button>
    </div>
</div>
</body>
</html>
