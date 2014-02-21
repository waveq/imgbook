package com.waveq.imgbook.filters;
 
import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.waveq.imgbook.controllers.UserBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
 
/**
 * Filter checks if UserBean has loggedIn property set to true.
 * If it is not set then request is being redirected to the index.xhml page.
 *
 * @author Szym
 *
 */
public class LoginFilter implements Filter {
 
    /**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
        UserBean userBean = (UserBean)((HttpServletRequest)request).getSession().getAttribute("userBean");    
        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully  
        if (userBean == null || !userBean.isLoggedIn()) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
            
        }
        chain.doFilter(request, response);
             
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }  
     
}