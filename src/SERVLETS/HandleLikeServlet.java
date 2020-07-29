package SERVLETS;

import com.travel.dao.ImageDAO;
import com.travel.db.ImageUtilImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "HandleLikeServlet",value = "/handleLike")
public class HandleLikeServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String way = request.getParameter("way");
        int id = Integer.parseInt(request.getParameter("id"));
        int uid = (int) request.getSession().getAttribute("uid");
        switch (way){
            case "add":
                try {
                    add(id,uid);
                    response.sendRedirect(request.getContextPath()+"/picInfo?id="+id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }break;
            case "remove":
                try {
                    remove(id,uid);
                    response.sendRedirect(request.getContextPath()+"/picInfo?id="+id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
    public void add(int id,int uid) throws SQLException {
        imageDAO.addToFavor(uid,id);
    }
    public void remove(int id,int uid) throws SQLException {
        imageDAO.removeFromFavor(uid,id);
    }

}
