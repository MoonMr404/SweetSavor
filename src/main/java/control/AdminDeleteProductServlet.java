package control;

import model.ProdottoDao;
import model.ProdottoDaoInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminDeleteProductServlet")
public class AdminDeleteProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeProdotto = request.getParameter("nomeProdotto");

        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            ProdottoDaoInterface prodottoDao = new ProdottoDao();
            try {
                boolean deleted = prodottoDao.doDelete(nomeProdotto);
                if (deleted) {
                    request.setAttribute("message", "Prodotto eliminato con successo.");
                } else {
                    request.setAttribute("message", "Prodotto non trovato.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "Si è verificato un errore durante l'eliminazione del prodotto.");
            }
        } else {
            request.setAttribute("message", "Nome del prodotto non valido.");
        }

        // Redirezione alla pagina deleteProduct.jsp
        request.getRequestDispatcher("/adminPage/adminPage.jsp").forward(request, response);

    }
}
