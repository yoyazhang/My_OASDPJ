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

@WebServlet(name = "MyPhotosServlet",value = "/myPhoto")
public class MyPhotosServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            deletePic(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            getMyPics(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void getMyPics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int page;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }else{
            page = 1;
        }
        long size = imageDAO.allMyPics((Integer) request.getSession().getAttribute("uid"));
        List<Travelimage> myImages = imageDAO.myPics((Integer) request.getSession().getAttribute("uid"),page);
        request.setAttribute("myImages",myImages);
        long pageSize;
        if(size % 6 == 0){
            pageSize = size/ 6;
        }else pageSize = size / 6 + 1;
        request.setAttribute("pageSize",pageSize);
        request.getRequestDispatcher("/jspFiles/admin/MyPhotos.jsp?page="+page).forward(request,response);
    }

    private void deletePic(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        imageDAO.deletePic(id, (Integer) request.getSession().getAttribute("uid"));
        response.sendRedirect(request.getContextPath()+"/myPhoto?method=getPic");
    }
}
