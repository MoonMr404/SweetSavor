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
import java.util.ArrayList;

@WebServlet("/CartInteraction")
public class CartInteraction extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("nome");
        
        
      
        ProdottoDao dao = new ProdottoDao();
        Prodotto prodotto = null;
        try {
            prodotto = dao.doRetrieveByName(name);
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        if(prodotto == null) {
            System.out.println("Prodotto non trovato");
            return;
        }

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if(cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
                
        
        if(cart.cartRead(prodotto) == true ) {
            
        }else {
            cart.addProdotto(prodotto);
        }
        
        
        resp.sendRedirect(req.getContextPath() + "/common/cart.jsp");
    }
}
