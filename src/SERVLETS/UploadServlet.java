package SERVLETS;

import com.travel.dao.CouRegDAO;
import com.travel.dao.ImageDAO;
import com.travel.db.CouRegUtilImpl;
import com.travel.db.ImageUtilImpl;
import com.travel.entity.GeocountriesRegions;
import com.travel.entity.Travelimage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@WebServlet(name = "UploadServlet",value = "/upload")
public class UploadServlet extends HttpServlet {
    private ImageDAO imageDAO = new ImageUtilImpl();
    private CouRegDAO couRegDAO = new CouRegUtilImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream in = null;
        OutputStream out = null;

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload =  new ServletFileUpload(factory);
        //解析获取的数据
        try{
            List<FileItem> items = upload.parseRequest(request);
            //判断文件是否合法//随机生成一堆数字的文并存入文件夹
            Travelimage newImage;

            Map<String, String> data = new HashMap<>();
            for(FileItem item:items){
                if(item.isFormField())
                    data.put(item.getFieldName(),item.getString());
            }
            if(data.get("ImageID") != null){
                newImage = imageDAO.getImageInfo(Integer.parseInt(data.get("ImageID")));
            }else{
                newImage = new Travelimage();
            }
            for(FileItem item:items){
                if(!item.isFormField()){
                    String fileName = item.getName();
                    if(!fileName.equals("")){
                        String lastName = fileName.substring(fileName.lastIndexOf("."));
                        if(!lastName.equals("jpg") && !lastName.equals("png") && !lastName.equals("gif") && !lastName.equals("bmp")){
                            request.setAttribute("message","您上传的图片不符合规格");

                        }
                        //随机一个名字为Path
                        String randomNum;
                        String newName;
                        String savePath = this.getServletContext().getRealPath("/resources/images/normal/medium");
                        do{
                            randomNum = getRandom();
                            newName = randomNum + lastName;
                        }while (new File(savePath + newName).exists());
                        newImage.setPath(newName);

                        in = item.getInputStream();
                        // 获取输出流
                        out = new FileOutputStream(savePath + "\\" + newName);
                        int len = 0;
                        byte[] buffer = new byte[1024];
                        while((len=in.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                    }
                }
            }
            //将title、content以及这个path等其他数据为newImage赋值
            newImage.setUid((Integer) request.getSession().getAttribute("uid"));
            newImage.setTitle(data.get("UploadPhotoTitle"));
            newImage.setContent(data.get("content"));
            newImage.setDescription(data.get("UploadPhotoDescription"));
            if(!data.get("Cities").equals("default")){
                newImage.setCityCode(Integer.parseInt(data.get("Cities")));
            }else{
                newImage.setCityCode(null);
            }
            Date date = new Date();
            newImage.setCountryRegionCodeIso(data.get("Countries"));
            newImage.setUpdateTime(new Timestamp(date.getTime()));
            if(data.get("ImageID") != null){
                newImage.setImageId(Integer.parseInt(data.get("ImageID")+""));
                if(newImage.getPath() != null){
                    imageDAO.updatePhotoWithPath(newImage);
                }else{
                    imageDAO.updatePhoto(newImage);
                }
            }else{
                imageDAO.addPhoto(newImage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //上传成功 重定向到我的照片页面
        response.sendRedirect(request.getContextPath()+"/myPhoto");
    }
    private String getRandom(){
        String str = "";
        Random random=new Random();
        String k="1234567890";
        //添加10个不同的随机字符串
        for(int i=0;i<10;i++){
            int number = random.nextInt(k.length());
            str += k.charAt(number);
        }
        return str;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("id") != null){
                int id = Integer.parseInt(request.getParameter("id"));
                Travelimage travelimage = imageDAO.getImageInfo(id);
                request.setAttribute("image",travelimage);
            }
            List<GeocountriesRegions> couRegs = couRegDAO.getAllCous();
            request.setAttribute("CouRegs",couRegs);
            request.getRequestDispatcher("/jspFiles/admin/upload.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
