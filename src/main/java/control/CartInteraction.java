package control;

import model.Cart;
import model.Prodotto;
import model.ProdottoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/CartInteraction")
public class CartInteraction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Recupera i parametri dalla richiesta
        String nomeProdotto = req.getParameter("nome");
        String quantitaStr = req.getParameter("quantity"); // Cambiato a "quantity"

        // Converte la quantità da stringa a intero (gestendo possibili errori)
        int quantita = 1; // Valore predefinito nel caso in cui ci siano errori
        try {
            quantita = Integer.parseInt(quantitaStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Gestione dell'errore (opzionale)
        }

        ProdottoDao prodottoDao = new ProdottoDao();
        Prodotto prodotto = null;

        try {
            prodotto = prodottoDao.doRetrieveByName(nomeProdotto);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prodotto == null) {
            System.out.println("Prodotto non trovato");
            return;
        }

        // Recupera o crea il carrello dalla sessione
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Aggiunge o aggiorna la quantità del prodotto nel carrello
        cart.addProdotto(prodotto, quantita); // Aggiorna la quantità correttamente

        // Reindirizza alla pagina del carrello
        resp.sendRedirect(req.getContextPath() + "/common/cart.jsp");
    }
}
