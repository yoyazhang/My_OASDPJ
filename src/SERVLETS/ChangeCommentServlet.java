package SERVLETS;

import com.travel.dao.CommentDAO;
import com.travel.dao.ImageDAO;
import com.travel.db.CommentUtilImpl;
import com.travel.entity.Comments;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.StyleSheet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ChangeCommentServlet",value = "/changeComment")
public class ChangeCommentServlet extends HttpServlet {
    private CommentDAO commentDAO = new CommentUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int CommentID = Integer.parseInt(request.getParameter("commentID"));

        try {
            commentDAO.deleteComment(CommentID);
            response.sendRedirect(request.getContextPath()+"/picInfo?id="+request.getParameter("ImageID"));
        } catch (SQLException e) {
            e.printStackTrace();
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
        List<Comments> comments = null;
        try {
            comments = commentDAO.getImageComment(id,rankMethod);
            if(request.getSession().getAttribute("uid") != null){
                commentDAO.setCanLike(comments,(int)request.getSession().getAttribute("uid"));
            }else{
                commentDAO.setCanLike(comments);
            }
            JSONArray jsonArray = JSONArray.fromObject(comments);
            String json = jsonArray.toString();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
