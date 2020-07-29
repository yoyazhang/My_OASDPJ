package SERVLETS;

import com.travel.dao.CityDAO;
import com.travel.db.CityUtilImpl;
import com.travel.entity.Geocities;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetCityServlet",value = "/getCity")
public class GetCityServlet extends HttpServlet {
    private CityDAO cityDAO = new CityUtilImpl();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Geocities> geocities = cityDAO.getCity(request.getParameter("ISO"));
            JSONArray jsonArray = JSONArray.fromObject(geocities);
            String json = jsonArray.toString();
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(json);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
