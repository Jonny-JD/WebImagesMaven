package servlet;

import dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ImageService;
import util.PropertiesUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

import static util.UrlPath.ADD_IMAGE;

@MultipartConfig()
@WebServlet(ADD_IMAGE)
public class AddImageServlet extends HttpServlet {
    private static final String BASE_PATH = "user.image.base.path";

    private final ImageService imageService = ImageService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO image validation
        var newImage = req.getPart("newImage");
        final var imageId = Instant.now().toEpochMilli();
        UserDTO currentUser = (UserDTO) req.getSession().getAttribute("user");
        imageService.addNewImage
                (Path.of(
                        PropertiesUtil.get(BASE_PATH),
                        currentUser.getEmail(),
                        imageId + newImage.getSubmittedFileName()),
                newImage.getInputStream(),
                currentUser.getId());

        var prevPage = req.getHeader("referer").replaceAll(ADD_IMAGE, "");
        resp.sendRedirect(prevPage);
    }
}
