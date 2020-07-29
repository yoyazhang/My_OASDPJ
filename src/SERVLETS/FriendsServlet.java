package SERVLETS;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Traveluser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FriendsServlet",value = "/myFriends")
public class FriendsServlet extends HttpServlet {
    private UserDAO userDAO = new UserUtilsImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        if(method == null){
            try {
                List<Traveluser> searchUsers = userDAO.searchUsers("%"+request.getParameter("Username")+"%",(int)request.getSession().getAttribute("uid"));
                request.setAttribute("searchUsers",searchUsers);
                if(searchUsers == null){
                    request.setAttribute("messageNOUser","nosuchUser");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            switch (method){
                case "reject":
                    try {
                        userDAO.rejectRequest(Integer.parseInt(request.getParameter("uid")),(int)request.getSession().getAttribute("uid"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }break;
                case "accept":
                    try {
                        userDAO.acceptRequest(Integer.parseInt(request.getParameter("uid")),(int)request.getSession().getAttribute("uid"));
                        } catch (SQLException e) {
                        e.printStackTrace();
                    }break;
                case "send":
                    try {
                        userDAO.sendRequest((int)request.getSession().getAttribute("uid"),Integer.parseInt(request.getParameter("uid")));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }break;
            }
        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int uid = (int)request.getSession().getAttribute("uid");
        try {
            List<Traveluser> friends = userDAO.getFriends(uid);
            List<Traveluser> receiveList = userDAO.getReceivedList(uid);
            List<Traveluser> sendList = userDAO.getSendList(uid);
            request.setAttribute("friends",friends);
            request.setAttribute("receiveList",receiveList);
            request.setAttribute("sendList",sendList);
            request.getRequestDispatcher("/jspFiles/admin/MyFriends.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
