package SERVLETS;

import com.travel.dao.ImageDAO;
import com.travel.db.ImageUtilImpl;
import com.travel.entity.Travelimage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowIndexServlet",value = "/showIndex")
public class ShowIndexServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Travelimage> hotPics = imageDAO.hottestPics();
            List<Travelimage> newestPics = imageDAO.newestPics();
            request.setAttribute("hotPics",hotPics);
            request.setAttribute("newestPics",newestPics);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
