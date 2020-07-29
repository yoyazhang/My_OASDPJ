package SERVLETS;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Traveluser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "RegistryServlet",value = "/registry")
public class RegistryServlet extends HttpServlet {
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //将表单信息封装为一个新类
        Traveluser user = new Traveluser();
        Date date = new Date();
        user.setUserName(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setState(1);
        user.setPass(request.getParameter("password"));
        user.setSalt(request.getParameter("salt"));
        Timestamp sqlDate = new Timestamp(date.getTime());
        user.setDateJoined(sqlDate);
        user.setDateLastModified(sqlDate);

        try {
            //判断是否名字重复
            if(!request.getSession().getAttribute("verifyCode").equals(request.getParameter("verifyCode"))){
                request.setAttribute("messageVerifyCode","VerifyCode wrong!");
                request.getRequestDispatcher("/jspFiles/Registry.jsp").forward(request,response);
            }
            if(userDAO.nameRepeat(user.getUserName())){
                //重复:返回表单、填充信息并提示名字重复了
                request.setAttribute("message","Username has existed!");
                request.getRequestDispatcher("/jspFiles/Registry.jsp").forward(request,response);
            }else{
                //不重复：执行注册操作、记录入数据库同时用相应的登录servlet实现自动登录
                userDAO.addUser(user);
                Traveluser newUser = userDAO.getUser(user.getUserName(),user.getPass());
                request.getSession().setAttribute("uid",newUser.getUid());
                request.getSession().setAttribute("username",newUser.getUserName());
                Frame frame = new Frame();
                frame.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(frame, "Registry success!\nWelcome "+ newUser.getUserName());
                switch (request.getParameter("last")){
                    case "index": response.sendRedirect(request.getContextPath()+"/index.jsp");break;
                    case "search":response.sendRedirect(request.getContextPath()+"/jspFiles/Search.jsp");break;
                    default:response.sendRedirect(request.getContextPath()+"/picInfo?id="+request.getParameter("last"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
