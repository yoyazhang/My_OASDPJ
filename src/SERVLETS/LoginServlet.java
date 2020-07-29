package SERVLETS;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Comments;
import com.travel.entity.Traveluser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import java.awt.*;
import java.io.IOException;

import java.sql.SQLException;

@WebServlet(name = "LoginServlet",value = "/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserUtilsImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lastWay = request.getParameter("last");
        String username  = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");

        try {
            if(!request.getSession().getAttribute("verifyCode").equals(request.getParameter("verifyCode"))){
                request.setAttribute("messageVerifyCode","VerifyCode wrong!");
                request.getRequestDispatcher("/jspFiles/login.jsp").forward(request,response);
                return;
            }
            Traveluser user = userDAO.getUser(username,password);
            if(user != null){
                HttpSession session = request.getSession();
                session.setAttribute("uid",user.getUid());
                session.setAttribute("username",user.getUserName());
                username = user.getUserName();
                Frame frame = new Frame();
                frame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(frame, "Login success!\nWelcome "+ username);

                switch (lastWay){
                    case "index":response.sendRedirect(request.getContextPath()+"/index.jsp");break;
                    case "search":response.sendRedirect(request.getContextPath()+"/jspFiles/Search.jsp");break;
                    default:response.sendRedirect(request.getContextPath()+"/picInfo?id="+lastWay);
                }
            }
            else{
                request.setAttribute("message","UserName or password wrong!");
                request.getRequestDispatcher("/jspFiles/login.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
