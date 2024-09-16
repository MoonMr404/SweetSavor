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

@WebServlet("/RemoveProductFromCart")
public class RemoveProductFromCart extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            // Ottieni il nome del prodotto da rimuovere dalla richiesta
            String productName = req.getParameter("nome");

            if (productName != null && !productName.trim().isEmpty()) {
                ProdottoDao prodottoDao = new ProdottoDao();
                Prodotto prodotto = null;

                try {
                    prodotto = prodottoDao.doRetrieveByName(productName);
                } catch (SQLException e) {
                    e.printStackTrace();
                    resp.sendRedirect(req.getContextPath() + "/errorPage.jsp");
                    return;
                }

                if (prodotto != null) {
                    // Rimuovi il prodotto dal carrello
                    cart.rimuoviProdotto(prodotto);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/common/cart.jsp");
    }
}
