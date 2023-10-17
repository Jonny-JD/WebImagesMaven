package servlet;

import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ImageService;
import util.JspHelper;

import java.io.IOException;

import static util.UrlPath.PERSONAL;

@WebServlet(PERSONAL)
public class PersonalServlet extends HttpServlet {

    private final ImageService imageService = ImageService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userImages = imageService.getUserImages(((UserDTO) (req.getSession().getAttribute("user"))).getId());

        req.setAttribute("userImages", userImages.getUserImagesPaths());
        req.getRequestDispatcher(JspHelper.getPath("personal-page")).forward(req, resp);
    }
}
