/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import static datnt.servlet.SearchArticleServlet.LOGGER;
import datnt.tbl_ArticleStatus.Tbl_ArticleStatus_DAO;
import datnt.tbl_Articles.Tbl_Articles_DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class UpdateArticleStatusServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(UpdateArticleStatusServlet.class);
    private final String SEARCH_ARTICLE_ACTION = "searchArticleAction";

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = request.getServletContext();
        Properties siteMap = (Properties) context.getAttribute("SITE_MAP");
        String url = (String) siteMap.get(SEARCH_ARTICLE_ACTION);
        String action = request.getParameter("btnAction").trim();
        int articleId = Integer.parseInt(request.getParameter("pk").trim());
        String status = "Active";
        try {
            System.out.println(action);
            if (action.equals("Delete")) {
                status = "Deleted";
            }
            if (action.equals("Restore")) {
                status = "New";
            }
            Tbl_ArticleStatus_DAO statusDAO = new Tbl_ArticleStatus_DAO();
            int statusId = statusDAO.getStatusID(status);
            if (statusId > 0) {
                Tbl_Articles_DAO articleDAO = new Tbl_Articles_DAO();
                int result = articleDAO.updateStatus(articleId, statusId);
                if (result > 0) {
                    LOGGER.info("Update Article Status succ");
                }
            }

        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
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
