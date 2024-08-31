package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/CartInteraction")
public class CartInteraction extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera il nome del prodotto dal form
        String productName = request.getParameter("nome");

        // Recupera il prodotto dal database
        ProdottoDao prodottoDao = new ProdottoDao();
        Prodotto prodotto = null;
        try {
            prodotto = prodottoDao.doRetrieveByName(productName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Aggiungi il prodotto al carrello dell'utente
        HttpSession session = request.getSession();
        ArrayList<Prodotto> cart = (ArrayList<Prodotto>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // Verifica se il prodotto è già nel carrello
        boolean found = false;
        for (Prodotto p : cart) {
            if (p.getNomeProdotto().equals(prodotto.getNomeProdotto())) {
                // Se il prodotto è già nel carrello, aggiorna solo la quantità (opzionale)
                // prodotto.setDisponibility(p.getDisponibility() + 1); // Aggiungi una logica se desideri aggiornare la quantità
                found = true;
                break;
            }
        }

        // Se il prodotto non è già nel carrello, aggiungilo
        if (!found) {
            cart.add(prodotto);
        }

        // Reindirizza alla pagina del carrello
        response.sendRedirect(request.getContextPath() + "/common/cart.jsp");
    }
}
