package filter;

import dto.UserDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static util.UrlPath.*;


@WebFilter("/*")
public class LoginFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, LOCALE);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        var user = (UserDTO) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");

        if (user != null || isPublicPath(requestURI) || isStaticResource(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).sendRedirect(LOGIN + "?error&log_in=false");

        }
    }

    private boolean isPublicPath(String requestURI) {
        return PUBLIC_PATH.stream().anyMatch(requestURI::startsWith);
    }

    private boolean isStaticResource(String requestURI) {
        return requestURI.contains("resources");
    }
}
