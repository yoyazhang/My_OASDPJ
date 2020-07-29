package SERVLETS;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "GetSaltServlet",value = "/getSalt")
public class GetSaltServlet extends HttpServlet {
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        try {
            String salt = userDAO.getSalt(username);
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(salt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
