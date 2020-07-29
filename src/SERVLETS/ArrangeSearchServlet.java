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

@WebServlet(name = "ArrangeSearchServlet",value = "/search")
public class ArrangeSearchServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    private void titleSearch(HttpServletRequest request, HttpServletResponse response, String query,String rankMethod,int page) throws SQLException, ServletException, IOException {
        List<Travelimage> searchPics = imageDAO.certainPicsByTitle(query,rankMethod,page);
        request.setAttribute("searchPics",searchPics);
    }
    private void contentSearch(HttpServletRequest request, HttpServletResponse response, String query,String rankMethod,int page) throws SQLException, ServletException, IOException {
        List<Travelimage> searchPics = imageDAO.certainPicsByContent(query,rankMethod,page);
        request.setAttribute("searchPics",searchPics);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page;
        if(request.getParameter("page") != null){
            page = Integer.parseInt(request.getParameter("page"));
        }else{
            page = 1;
        }
        String method = request.getParameter("SearchMethod");
        String rankMethod = request.getParameter("RankMethod");
        String query = "%"+ request.getParameter("query")+"%";
        switch (method){
            case "SearchByTitle":
                try {
                    long pageSize;
                    long size = imageDAO.TitleImageNum(query);
                    if(size % 6 == 0){
                        pageSize = size/ 6;
                    }else pageSize = size / 6 + 1;
                    request.setAttribute("pageSize",pageSize);
                    titleSearch(request,response,query,rankMethod,page);
                } catch (SQLException e) {
                    e.printStackTrace();
                }break;
            case "SearchByContent":
                try {
                    long pageSize;
                    long size = imageDAO.ContentImageNum(query);
                    if(size % 6 == 0){
                        pageSize = size/ 6;
                    }else pageSize = size / 6 + 1;
                    request.setAttribute("pageSize",pageSize);
                    contentSearch(request,response,query,rankMethod,page);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        request.getRequestDispatcher("/jspFiles/Search.jsp?SearchMethod="+method+"&query="+query+"&RankMethod"+rankMethod+"&page="+page).forward(request,response);
    }
}
