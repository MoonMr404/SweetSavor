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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prodottoNome = request.getParameter("nome");
        String quantitaStr = request.getParameter("quantity");
        int quantita = Integer.parseInt(quantitaStr);

        // Recupera il carrello dalla sessione
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Crea un'istanza di ProdottoDao e recupera il prodotto
        ProdottoDao prodottoDao = new ProdottoDao();
        Prodotto prodotto = null;
        try {
            prodotto = prodottoDao.doRetrieveByName(prodottoNome);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (prodotto != null) {
            // Aggiungi il prodotto al carrello
            cart.addProdotto(prodotto, quantita);
        }

        // Reindirizza a una pagina di conferma o al carrello
        response.sendRedirect(request.getContextPath() + "/common/cart.jsp");
    }
}
