package dskt.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

public class FiltroAcesso implements Filter {

    public FiltroAcesso() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession(); //BUSCA A SESSÃO
        if (session.getAttribute("token") == session.getId() && session.getAttribute("user") != null) {

            chain.doFilter(request, response);

        } else {

            session.invalidate();
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {
    }

}
