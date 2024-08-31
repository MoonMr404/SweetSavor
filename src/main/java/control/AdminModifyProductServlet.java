package control;

import model.Prodotto;
import model.ProdottoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/AdminModifyProductServlet")
public class AdminModifyProductServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dal form
        String nomeProdotto = request.getParameter("nomeProdotto");
        String prezzoString = request.getParameter("prezzo");
        String quantitaString = request.getParameter("quantita");
        String img = request.getParameter("img");
        String categoria = request.getParameter("categoria");
        String idProdotto = request.getParameter("idProdotto");

        // Sostituisci la virgola con il punto nel prezzo
        String prezzoFormatted = prezzoString.replace(",", ".");

        // Crea un oggetto Prodotto con i dati aggiornati
        Prodotto prodotto = new Prodotto();
        prodotto.setNomeProdotto(nomeProdotto);

        // Converte il prezzo nel formato corretto
        try {
            double prezzo = Double.parseDouble(prezzoFormatted);
            prodotto.setPrezzo(prezzo);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Formato prezzo non valido: " + prezzoString, e);
        }

        // Imposta la quantità
        int quantita = Integer.parseInt(quantitaString);
        prodotto.setDisponibility(quantita);

        // Imposta immagine e categoria
        prodotto.setImg(img.getBytes()); // Assumendo che l'immagine sia memorizzata come byte[] nel modello
        prodotto.setCategoria(categoria);

        // Aggiorna il prodotto nel database
        ProdottoDao dao = new ProdottoDao();

        try {
            dao.doUpdate(prodotto); // Chiamata al metodo doUpdate che aggiorna il prodotto nel database
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'aggiornamento del prodotto", e);
        }

        // Messaggio da mostrare in JavaScript dopo l'aggiornamento
        String message = "Modifica effettuata con successo";

        // Ottieni il context path
        String contextPath = request.getContextPath();

        // Costruisci lo script JavaScript con il context path nel reindirizzamento
        String script = "<script>alert('" + message + "');" +
                "window.location.href='" + contextPath + "/adminPage.jsp';</script>";

        // Scrivi lo script come risposta
        response.setContentType("text/html");
        response.getWriter().write(script);
    }

}
