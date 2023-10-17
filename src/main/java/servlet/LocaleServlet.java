package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static util.UrlPath.LOCALE;

@WebServlet(LOCALE)
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var lang = req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);

        var prevPage = req.getHeader("referer").replaceAll("\\?lang=.{5}", "");
        resp.sendRedirect(prevPage + "?lang=" + lang);
    }
}
