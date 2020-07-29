package Filter;

import com.travel.dao.UserDAO;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Traveluser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "ShowFavorFilter")
public class ShowFavorFilter implements Filter {
    private UserDAO userDAO = new UserUtilsImpl();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
        if(Integer.parseInt(request.getParameter("uid"))==(int)session.getAttribute("uid")){
            chain.doFilter(req, resp);
        }else{
            try {
                if(userDAO.areFriends(Integer.parseInt(request.getParameter("uid")),(int)session.getAttribute("uid"))){
                    Traveluser user = userDAO.getUser(Integer.parseInt(request.getParameter("uid")));
                    if(user.getState() == 1){
                        chain.doFilter(req, resp);
                    }else request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request,response);
                }else{
                    request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request,response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
