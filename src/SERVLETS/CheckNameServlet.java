package SERVLETS;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckNameServlet",value = "/checkName")
public class CheckNameServlet extends HttpServlet {
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if(method.equals("name")){
            String name = request.getParameter("name");
            try {
                if(userDAO.nameRepeat(name)){
                    response.getWriter().print("repeat");
                }else response.getWriter().print("noRepeat");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            String verifyCode = request.getParameter("code");
            if(verifyCode.equals(request.getSession().getAttribute("verifyCode"))){
                response.getWriter().print("right");
            }else{
                response.getWriter().print("wrong");
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
