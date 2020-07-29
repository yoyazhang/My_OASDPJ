package SERVLETS;

import com.travel.dao.ImageDAO;
import com.travel.dao.UserDAO;
import com.travel.db.ImageUtilImpl;
import com.travel.db.UserUtilsImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ArrangeFavorServlet",value = "/arrangeFavor")
public class ArrangeFavorServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        int uid = (int) request.getSession().getAttribute("uid");
        switch (method){
            case "change":
                try {
                    doChange(request,response,uid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "remove":
                int id = Integer.parseInt(request.getParameter("id"));
                try {
                    imageDAO.removeFromFavor(uid,id);
                    response.sendRedirect(request.getContextPath()+"/showFavor?uid="+uid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

    }
    private void doChange(HttpServletRequest request, HttpServletResponse response,int uid) throws SQLException {
        String view = request.getParameter("view");
        if(view.equals("off")){
            userDAO.cantSee(uid);
        }else if(view.equals("open")){
            userDAO.canSee(uid);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
