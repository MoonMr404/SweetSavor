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

@WebServlet("/addToCartServlet")
public class AddToCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ottieni i parametri dalla richiesta
        String productName = request.getParameter("nomeProdotto");
        String productPriceStr = request.getParameter("prezzo");
        byte[] productImageBytes = request.getParameter("img").getBytes(); // Ottieni i byte dell'immagine

        // Converti il prezzo in double
        double productPrice = Double.parseDouble(productPriceStr);

        // Creazione dell'oggetto Prodotto con i dettagli ricevuti dalla richiesta
        Prodotto prodotto = new Prodotto();
        prodotto.setNomeProdotto(productName);
        prodotto.setPrezzo(productPrice);

        // Assegna i byte dell'immagine al prodotto
        prodotto.setImg(productImageBytes);

        // Recupero o creazione del carrello dalla sessione dell'utente
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Aggiunta del prodotto al carrello
        cart.addProdotto(prodotto);

        // Risposta al client con stato HTTP 200 per successo
        response.setStatus(HttpServletResponse.SC_OK);
    }
}