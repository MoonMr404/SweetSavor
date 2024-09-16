package control;

import model.User;
import model.UserDao;
import model.UserDaoInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet("/ChangeUserPasswordServlet")
public class ChangeUserPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String passwordAttuale = request.getParameter("passwordAttuale");
        String nuovaPassword = request.getParameter("nuovaPassword");

        HttpSession session = request.getSession(false);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentSessionUser");

            if (currentUser != null) {
                UserDaoInterface userDao = new UserDao();

                try {
                    // Controllo se la password attuale è corretta
                    if (userDao.verifyPassword(currentUser.getEmail(), passwordAttuale)) {
                        // Se corretta, aggiorno la password nel db.
                        userDao.updatePassword(currentUser.getEmail(), nuovaPassword);

                        // Porta alla pagina di conferma
                        response.sendRedirect(request.getContextPath() + "/common/userPasswordChangeConfirmed.jsp");
                    } else {
                        // Password non corretta, mostrare errore.
                        response.sendRedirect(request.getContextPath() + "/common/userPasswordChange.jsp?error=wrongpassword");
                    }
                } catch (SQLException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    response.sendRedirect(request.getContextPath() + "/common/userPasswordChange.jsp?error=db");
                }
            }
        } else {
            // Riporta al login se non è presente una sessione attiva.
            response.sendRedirect(request.getContextPath() + "/common/login.jsp");
        }
    }
}
