
package servlets;
import db.DAOSQLite;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pojo.Book;

/**
 *
 * @author Kevin
 */

public class DBAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String message = "";

        // get real path to the sqlite db
        ServletContext sc = this.getServletContext();
        String dbPath = sc.getRealPath("/WEB-INF/book.db");

        // get parameters passed in from the request
        String action = request.getParameter("action");
        String password = request.getParameter("password");

        // execute the requested action
        if (password != null && password.equals("tomcat")) {
            if (action != null) {
                switch (action) {
                    case "create":
                        DAOSQLite.createTables(dbPath);
                        message = "The table has been created.";
                        break;
                    case "drop":
                        DAOSQLite.dropTables(dbPath);
                        message = "The table has been dropped.";
                        break;
                    case "populate":
                        DAOSQLite.populateTables(dbPath);
                        message = "The table has been populated.";
                        break;
                    case "show":
                        message = "<p>Showing all records:</p>";
                        List<Book> list = DAOSQLite.getAllRecordsAsList(dbPath);
                        for (Book book : list) {
                            message += "<p>" + book.toString() + "</p>";
                        }
                        break;
                    default:
                        message = "This action is not allowed.";
                }
            } else {
                message = "Missing action.";
            }
        } else {
            message = "Incorrect or missing password";
        }

        // send the response
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>DBAdmin Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p><a href=\"home.html\">Return to Home Page</a></p>");
            out.println("<p><a href=\"admin.html\">Return to Database Administration</a></p>");
            out.println("<p>" + message + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
