package control;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/saveOrder")
public class SaveOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String jsonData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data.json")))) {
            out.println(jsonData);
        }

        response.getWriter().write("Infomazioni salvate ");
    }
}
