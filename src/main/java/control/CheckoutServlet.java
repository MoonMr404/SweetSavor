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
import java.util.regex.Pattern;
import java.time.YearMonth;
import java.time.LocalDate;

@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Recupera i dati dal modulo
        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String postalCode = request.getParameter("postal-code");
        String country = request.getParameter("country");
        String cardName = request.getParameter("card-name");
        String cardNumber = request.getParameter("card-number");
        String expiryDate = request.getParameter("expiry-date");
        String cvv = request.getParameter("cvv");

        // Pattern di validazione per ciascun campo
        Pattern namePattern = Pattern.compile("^[A-Za-z]+$");
        Pattern addressPattern = Pattern.compile("^[A-Za-z0-9\\s,.-]{5,100}$");
        Pattern cityCountryPattern = Pattern.compile("^[A-Za-z\\s]{2,50}$");
        Pattern postalCodePattern = Pattern.compile("^\\d{5}$");
        Pattern cardNamePattern = Pattern.compile("^[A-Za-z\\s]{2,50}$");
        Pattern cardNumberPattern = Pattern.compile("^\\d{16}$");
        Pattern expiryDatePattern = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");
        Pattern cvvPattern = Pattern.compile("^\\d{3}$");

        // Validazione dei campi
        if (!namePattern.matcher(firstName).matches() || !namePattern.matcher(lastName).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome o cognome non valido.");
            return;
        }

        if (!addressPattern.matcher(address).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Indirizzo non valido.");
            return;
        }

        if (!cityCountryPattern.matcher(city).matches() || !cityCountryPattern.matcher(country).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Città o paese non valido.");
            return;
        }

        if (!postalCodePattern.matcher(postalCode).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CAP non valido.");
            return;
        }

        if (!cardNamePattern.matcher(cardName).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nome titolare non valido.");
            return;
        }

        if (!cardNumberPattern.matcher(cardNumber).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Numero carta non valido.");
            return;
        }

        if (!expiryDatePattern.matcher(expiryDate).matches() || isExpired(expiryDate)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Data di scadenza non valida.");
            return;
        }

        if (!cvvPattern.matcher(cvv).matches()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CVV non valido.");
            return;
        }

        // Recupera il carrello dalla sessione per calcolare il totale
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        double totale = 0;
        if (cart != null) {
            totale = cart.getTotalPrice();
        } else {
            response.sendRedirect(request.getContextPath() + "/common/cart.jsp");
            return;
        }

        // Crea e salva l'ordine
        Ordine ordine = new Ordine();
        ordine.setOrderID(generaOrderID());
        ordine.setDataOrdine();
        ordine.setNomeCliente(firstName);
        ordine.setCognomeCliente(lastName);
        ordine.setCap(postalCode);
        ordine.setIndirizzoDiConsegna(address + ", " + city + ", " + country);
        ordine.setTotale(totale);
        ordine.setStato(false);

        OrdineDAO dao = new OrdineDAO();
        try {
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
        return "ORD-" + System.currentTimeMillis();
    }

    // Metodo per controllare se la data di scadenza è nel passato
    private boolean isExpired(String expiryDate) {
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000;

        YearMonth expiry = YearMonth.of(year, month);
        return expiry.isBefore(YearMonth.now());
    }
}