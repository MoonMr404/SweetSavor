package control;

import model.Prodotto;
import model.ProdottoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/addProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AdminAddProductServlet extends HttpServlet {

    private ProdottoDao prodottoDao;

    @Override
    public void init() throws ServletException {
        super.init();
        prodottoDao = new ProdottoDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mostra il form per aggiungere un nuovo prodotto
        request.getRequestDispatcher("/adminProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ottiene tutte le parti dalla richiesta
        Collection<Part> parts = request.getParts();

        if (!parts.isEmpty()) {
            // Estrae i dati dal form
            String nome = request.getParameter("nomeProdotto");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int disponibilita = Integer.parseInt(request.getParameter("disponibilita"));
            String categoria = request.getParameter("categoria");

            byte[] image = null;

            // Elabora ogni parte (assumendo un solo caricamento di file con nome "img")
            for (Part part : parts) {
                if (part.getName().equals("img")) {
                    // Legge i byte dall'input stream della parte
                    image = part.getInputStream().readAllBytes();
                    break; // Supponendo un solo caricamento di file
                }
            }

            Prodotto prodotto = new Prodotto(nome, descrizione, prezzo, disponibilita, true, categoria, image);

            try {
                // Salva il prodotto usando il DAO
                prodottoDao.doSave(prodotto);
                
                // Messaggio da mostrare in JavaScript dopo l'aggiornamento
                String message = "Prodotto aggiunto con successo";

                // Ottieni il context path
                String contextPath = request.getContextPath();

                //script JavaScript con il context path nel reindirizzamento
                String script = "<script>alert('" + message + "');" +
                        "window.location.href='" + contextPath + "/adminPage/adminPage.jsp';</script>";

                // Scrivi lo script come risposta
                response.setContentType("text/html");
                response.getWriter().write(script);
                
                
                
                
            } catch (SQLException e) {
                
                
                throw new ServletException("Errore durante l'aggiunta del prodotto", e);
                
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nessun file caricato o dati del form mancanti");
        }
    }
}
