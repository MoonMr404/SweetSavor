package control;

import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = request.getParameter("username");

        // Verifica se username è null o una stringa vuota
        if (username == null || username.isEmpty()) {
            showError(response, "L'username non può essere vuoto");
            return;
        }
        username.trim();
        email.trim();
        User user = new User(username, password, email, false); // Assumi isAdmin a false

        try {
            userDao.doSave(user);
            response.sendRedirect(request.getContextPath() + "/common/home.jsp");
        } catch (SQLException e) {
            showError(response, "Registrazione fallita");
            e.printStackTrace();
        }
    }

    private void showError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + message + "');");
        out.println("location='registration.jsp';");
        out.println("</script>");
    }
}
