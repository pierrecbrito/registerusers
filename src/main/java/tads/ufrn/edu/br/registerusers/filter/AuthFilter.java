package tads.ufrn.edu.br.registerusers.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebFilter(urlPatterns = {"/users", "/dashboard"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        boolean isAuthenticated = session != null && session.getAttribute("user") != null;

        if (isAuthenticated) {
            chain.doFilter(req, res); // usuário autenticado → continua
        } else {
            response.sendRedirect("/cadastro"); // não autenticado → redireciona
        }
    }
}
