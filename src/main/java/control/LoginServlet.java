package control;

import model.User;
import model.UserDao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validazione base (opzionale)
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/common/login.jsp?error=emptyfields");
            return;
        }

        try {
            // Creazione DAO e recupero utente
            UserDao userDao = new UserDao();
            User user = userDao.retrieveUser(email, hashPassword(password));

            //Stampa della password
            System.out.println(hashPassword(password));
            
            if (user != null && validatePassword(password, user.getPassword())) {
                // Verifica se l'utente è un admin
                if (user.isAmministratore()) {
                    // Autenticazione riuscita per admin, reindirizza alla pagina admin.jsp
                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", user);
                    response.sendRedirect(request.getContextPath() + "/adminPage/adminPage.jsp");
                } else {
                    // Autenticazione riuscita per utente non admin, reindirizza alla home page
                    HttpSession session = request.getSession(true);
                    session.setAttribute("currentSessionUser", user);
                    response.sendRedirect(request.getContextPath() + "/common/home.jsp");
                }
            } else {
                // Credenziali errate
                response.sendRedirect(request.getContextPath() + "/common/login.jsp");
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            // Errore database
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/common/login.jsp");
        }
    }

    private boolean validatePassword(String enteredPassword, String storedHashedPassword) throws NoSuchAlgorithmException {
        String enteredPasswordHash = hashPassword(enteredPassword);
        return enteredPasswordHash.equals(storedHashedPassword);
    }

    private String hashPassword(String password) {
        String hashString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            hashString = "";
            for (int i = 0; i < hash.length; i++) {
                hashString += Integer.toString((hash[i] & 0xff) | 0x100, 16).substring(1, 3);
            }
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return hashString;
    }
}
