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
        String name = req.getParameter("nome");
        String quantityParam = req.getParameter("quantity");
        int quantity = 1; // Valore predefinito nel caso in cui la quantità non venga inviata correttamente

        // Converte il parametro quantity in un intero
        try {
            quantity = Integer.parseInt(quantityParam);
        } catch (NumberFormatException e) {
            System.out.println("Errore nella conversione della quantità, verrà utilizzata la quantità predefinita (1).");
        }

        ProdottoDao dao = new ProdottoDao();
        Prodotto prodotto = null;
        try {
            prodotto = dao.doRetrieveByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (prodotto == null) {
            System.out.println("Prodotto non trovato");
            return;
        }

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Controlla se il prodotto è già nel carrello
        if (cart.cartRead(prodotto)) {
            // Aggiorna la quantità del prodotto nel carrello
            cart.updateProdotto(prodotto, quantity);
        } else {
            // Aggiunge il prodotto con la quantità specificata
            cart.addProdotto(prodotto, quantity);
        }

        resp.sendRedirect(req.getContextPath() + "/common/cart.jsp");
    }
}
