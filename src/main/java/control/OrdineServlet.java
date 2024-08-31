package control;

import model.Ordine;
import model.OrdineDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/OrdineServlet")
public class OrdineServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    public void init() {
        ordineDAO = new OrdineDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera la lista degli ordini dal database
        ArrayList<Ordine> listaOrdini = ordineDAO.selectAllOrdini();

        // Imposta gli attributi per la JSP
        request.setAttribute("listaOrdini", listaOrdini);

        // Inoltra alla JSP per visualizzare la lista degli ordini
        request.getRequestDispatcher(request.getContextPath() +"/adminPage/viewOrders.jsp").forward(request, response);

    }
}
