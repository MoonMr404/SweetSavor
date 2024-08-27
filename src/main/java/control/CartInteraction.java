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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dal form
        String productName = request.getParameter("nome");
        String quantityStr = request.getParameter("quantity");

        // Verifica se quantityStr è null o vuoto
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            // Gestione errore: quantità non specificata
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            // Gestione errore: quantità non è un numero valido
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
        }

        // Recupera il prodotto dal database
        ProdottoDao prodottoDao = new ProdottoDao();
        Prodotto prodotto = null;
        try {
            prodotto = prodottoDao.doRetrieveByName(productName);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Verifica se il prodotto esiste
        if (prodotto == null) {
            // Prodotto non trovato, gestione dell'errore
            response.sendRedirect(request.getContextPath() + "/errorPage.jsp");
            return;
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
                p.setDisponibility(p.getDisponibility() + quantity); // Aggiorna la quantità se il prodotto è già nel carrello
                found = true;
                break;
            }
        }

        // Se il prodotto non è già nel carrello, aggiungilo con la quantità specificata
        if (!found) {
            prodotto.setDisponibility(quantity);
            cart.add(prodotto);
        }

        // Reindirizza alla pagina del carrello
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}
