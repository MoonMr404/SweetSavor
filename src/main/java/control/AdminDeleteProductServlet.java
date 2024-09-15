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

        // Controlla che il nome del prodotto non sia nullo o vuoto
        if (nomeProdotto != null && !nomeProdotto.trim().isEmpty()) {
            ProdottoDaoInterface prodottoDao = new ProdottoDao();
            try {
                // Esegui l'eliminazione del prodotto
                boolean deleted = prodottoDao.doDelete(nomeProdotto);

                // Imposta il messaggio in base al risultato dell'eliminazione
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

        // Ritorna alla pagina deleteProduct.jsp per visualizzare il messaggio
        request.getRequestDispatcher("/adminPage/deleteProduct.jsp").forward(request, response);
    }

}
