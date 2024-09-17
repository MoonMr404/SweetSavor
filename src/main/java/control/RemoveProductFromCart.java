package control;

import model.Cart;
import model.Prodotto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/RemoveProductFromCart")
public class RemoveProductFromCart extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            String nomeProdotto = req.getParameter("nome");
            Prodotto prodotto = cart.getProdottoByName(nomeProdotto);

            if (prodotto != null) {
                cart.rimuoviProdotto(prodotto);
            }
        }

        resp.sendRedirect( "/common/cart.jsp");
    }
}
