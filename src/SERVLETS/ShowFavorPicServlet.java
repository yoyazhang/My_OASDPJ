package SERVLETS;

import com.travel.dao.FavorImageDAO;
import com.travel.dao.ImageDAO;
import com.travel.dao.UserDAO;
import com.travel.db.FavorImageUtilImpl;
import com.travel.db.ImageUtilImpl;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Travelimage;
import com.travel.entity.Traveluser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ShowFavorPicServlet",value = "/showFavor")
public class ShowFavorPicServlet extends HttpServlet {
    private FavorImageDAO favorImageDAO = new FavorImageUtilImpl();
    private ImageDAO imageDAO = new ImageUtilImpl();
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //查谁的
            int userID;
            if(request.getParameter("uid") != null){
                userID = Integer.parseInt(request.getParameter("uid"));
            }else userID = (Integer) request.getSession().getAttribute("uid");
            //页码问题
            int page;
            if(request.getParameter("page") != null){
                page = Integer.parseInt(request.getParameter("page"));
            }else{
                page = 1;
            }
            long size = favorImageDAO.allFavorPics(userID);
            long pageSize;
            if(size % 6 == 0){
                pageSize = size/ 6;
            }else pageSize = size / 6 + 1;
            request.setAttribute("pageSize",pageSize);

            Traveluser favorOwner = userDAO.getUser(userID);
            List<Travelimage> favorPics = favorImageDAO.getUserFavorImages(userID,page);

            List<Travelimage> historyPics = imageDAO.historyPics((Integer) request.getSession().getAttribute("uid"));
            request.setAttribute("favorPics",favorPics);
            request.setAttribute("favorOwner",favorOwner);
            request.setAttribute("historyPics",historyPics);

            //转发
            request.getRequestDispatcher("/jspFiles/admin/Favorites.jsp?uid="+userID+"&page="+page).forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
