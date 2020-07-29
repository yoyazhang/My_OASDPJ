package Filter;

import com.travel.dao.ImageDAO;
import com.travel.dao.UserDAO;
import com.travel.db.ImageUtilImpl;
import com.travel.db.UserUtilsImpl;
import com.travel.entity.Travelimage;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter(filterName = "UnMatchedUidFilter")
public class UnMatchedUidFilter implements Filter {
    private ImageDAO imageDAO = new ImageUtilImpl();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();
        if(request.getParameter("id") != null){
            int ImageID = Integer.parseInt(request.getParameter("id"));
            try {
                Travelimage travelimage = imageDAO.getImageInfo(ImageID);
                if((int)session.getAttribute("uid")==travelimage.getUid()){
                    chain.doFilter(req, resp);
                }else{
                    request.getRequestDispatcher("/WEB-INF/Error.jsp").forward(request,response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            chain.doFilter(req, resp);
        }


    }

    public void init(FilterConfig config) throws ServletException {

    }

}
