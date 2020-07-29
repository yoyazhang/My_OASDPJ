package SERVLETS;

import com.travel.dao.CommentDAO;
import com.travel.dao.ImageDAO;
import com.travel.db.CommentUtilImpl;
import com.travel.db.ImageUtilImpl;
import com.travel.entity.Comments;
import com.travel.entity.Travelimage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PicInfoServlet",value = "/picInfo")
public class PicInfoServlet extends HttpServlet {
    private ImageDAO imageDAO =  new ImageUtilImpl();
    private CommentDAO commentDAO = new CommentUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        int uid = (int)request.getSession().getAttribute("uid");
        switch(method){
            case "comment":
                try {
                    Comments comment = new Comments();
                    comment.setUid(uid);
                    comment.setImageId(Integer.parseInt(request.getParameter("ImageID")));
                    comment.setConcreteContent(request.getParameter("concreteContent"));
                    commentDAO.addComment(comment);
                    response.sendRedirect(request.getContextPath()+"/picInfo?id="+request.getParameter("ImageID"));
                } catch (SQLException e) {
                    e.printStackTrace();
                }break;
            case "removeLike":
                try {
                    commentDAO.removeLike(Integer.parseInt(request.getParameter("commentID")),uid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "like":
                try {
                    commentDAO.addLike(Integer.parseInt(request.getParameter("commentID")),uid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String rankMethod;
        if(request.getParameter("rankMethod") != null){
            rankMethod = request.getParameter("rankMethod");
        }else{
            rankMethod = "RankByHot";
        }
        request.setAttribute("rankMethod",rankMethod);
        try {
            Travelimage image = imageDAO.getImageInfo(id);
            if(image.getCityCode() != null){
                image.setCity(imageDAO.getImageCity(image.getCityCode()));
            }
            image.setKudoNum((int) imageDAO.getImageKudos(id));
            request.setAttribute("image",image);

            List<Comments> comments = commentDAO.getImageComment(id,rankMethod);
            if(request.getSession().getAttribute("uid") != null){
                imageDAO.addToHistory(id, (Integer) request.getSession().getAttribute("uid"));
                request.setAttribute("likeOrNot",imageDAO.likeOrNot(id, (Integer) request.getSession().getAttribute("uid")));
                commentDAO.setCanLike(comments,(int)request.getSession().getAttribute("uid"));
            }else{
                request.setAttribute("likeOrNot","noUid");
            }
            request.setAttribute("comments",comments);
            request.getRequestDispatcher("/jspFiles/PictureInformation.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
