package control;

import model.Cart;
import model.Prodotto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@WebServlet("/CartServlet")
public class CartServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Recupera la lista dei prodotti dal carrello dalla sessione
            ArrayList<Prodotto> listaProdotti = (ArrayList<Prodotto>) request.getSession().getAttribute("listaProdotti");

            // Se la lista è null, crea una nuova lista
            if (listaProdotti == null) {
                listaProdotti = new ArrayList<Prodotto>();
            }

            // Creazione del prodotto da aggiungere (per esempio)
            Prodotto prodotto = new Prodotto(
                    "Laptop",            // nomeProdotto
                    "Laptop 15 pollici", // descrizione
                    999.99,              // prezzo
                    10,                  // disponibilita
                    true,                // disponibile
                    "Cioccolata",        // categoria
                    null                 // img
            );

            // Aggiungi il prodotto alla lista
            listaProdotti.add(prodotto);

            // Calcola i totali
            int totalProdotti = listaProdotti.size();
            double totalImporto = listaProdotti.stream().mapToDouble(p -> p.getPrezzo()).sum();

            // Imposta gli attributi nella richiesta
            request.getSession().setAttribute("listaProdotti", listaProdotti);
            request.setAttribute("totalProdotti", totalProdotti);
            request.setAttribute("totalImporto", totalImporto);

            // Inoltra la richiesta al JSP
            request.getRequestDispatcher("/common/cart.jsp").forward(request, response);
        
    }
    
}   
