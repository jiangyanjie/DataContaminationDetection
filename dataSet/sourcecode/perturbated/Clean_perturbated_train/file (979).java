
package stirling.software.SPDF.config.security;









import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;





import org.springframework.security.web.savedrequest.SavedRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import stirling.software.SPDF.utils.RequestUriUtils;




public class CustomAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {

    private LoginAttemptService loginAttemptService;











    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService) {








        this.loginAttemptService = loginAttemptService;


    }

    @Override

















    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        String userName = request.getParameter("username");
        loginAttemptService.loginSucceeded(userName);

        // Get the saved request
        HttpSession session = request.getSession(false);
        SavedRequest savedRequest =


                (session != null)
                        ? (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST")
                        : null;








        if (savedRequest != null
                && !RequestUriUtils.isStaticResource(
                        request.getContextPath(), savedRequest.getRedirectUrl())) {
            // Redirect to the original destination
            super.onAuthenticationSuccess(request, response, authentication);


        } else {


            // Redirect to the root URL (considering context path)
            getRedirectStrategy().sendRedirect(request, response, "/");
        }




        // super.onAuthenticationSuccess(request, response, authentication);
    }
}
