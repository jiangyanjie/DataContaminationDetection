package br.ufc.quixada.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import br.ufc.quixada.dao.jdbc.ConnectionFactory;
import br.ufc.quixada.dao.jdbc.exception.ErroAoExecutarOperacaoException;

/**
 * Servlet Filter implementation class FilterLogin
 */
@WebFilter("/filter")
public class ConnectionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ConnectionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (uri.contains("javax.faces.resource") || uri.matches(".+\\.(jpg|gif|png|xhtml)")) {
			chain.doFilter(request, response);
		} else{
			Connection connection = ConnectionFactory.getConnection();
			System.out.println("Abrindo conexão");
			chain.doFilter(request, response);	
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ErroAoExecutarOperacaoException("Fechar conexão", e.getMessage());
			}	
			System.out.println("conexão fechada");
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
