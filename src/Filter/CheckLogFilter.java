package Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CheckLogFilter")
public class CheckLogFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        if(session.getAttribute("uid") != null){
            chain.doFilter(req, resp);
        }else{
            req.getRequestDispatcher("/jspFiles/login.jsp?last=index").forward(req,resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
