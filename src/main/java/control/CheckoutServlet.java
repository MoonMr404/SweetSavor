package control;

import model.Cart;
import model.Ordine;
import model.OrdineDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // Recupera i dati dalmodulo
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal-code");
        String country = request.getParameter("country");

        // Recupera il carrello dalla sessione per calcolare il totale
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        double totale = 0;
        if (cart != null) {
            totale = cart.getTotalPrice();
        } else {
            response.sendRedirect(request.getContextPath() + "/common/cart.jsp"); // Reindirizza al carrello se vuoto
            return;
        }

        Ordine ordine = new Ordine();
        ordine.setOrderID(generaOrderID());  // Imposta l'ID dell'ordine
        ordine.setDataOrdine();  // Imposta la data dell'ordine
        ordine.setNomeCliente(firstName);  // Imposta il nome del cliente
        ordine.setCognomeCliente(lastName);  // Imposta il cognome del cliente
        ordine.setCap(postalCode);  // Imposta il CAP
        ordine.setIndirizzoDiConsegna(address + ", " + city + ", " + country);  // Indirizzo completo
        ordine.setTotale(totale);  // Imposta il totale calcolato dal carrello
        ordine.setStato(false);  // Imposta lo stato dell'ordine (non completato)

        OrdineDAO dao = new OrdineDAO();
        try {
            // Rimuove solo il carrello dalla sessione
            request.getSession().removeAttribute("cart");

            dao.insertOrdine(ordine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Verifica se l'utente è loggato e mantiene la sessione
        if (request.getSession().getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/common/ordineConfermato.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/common/login.jsp");
        }
    }

    // Metodo per generare un ID ordine unico
    private String generaOrderID() {
        return "ORD-" + System.currentTimeMillis(); // Semplice generatore di ID basato sul timestamp
    }

}
