/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import static datnt.servlet.GetArticleDetailServlet.LOGGER;
import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.tbl_Articles.Tbl_Articles_DAO;
import datnt.tbl_Comment.Tbl_Comment_DAO;
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
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class CommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(CommentServlet.class);
    private final String DETAIL_ACTION = "detailAction";

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
        String url = (String) siteMap.get(DETAIL_ACTION);
        String comment = request.getParameter("txtComment");
        int articleId = Integer.parseInt(request.getParameter("pk"));
        Tbl_Account_DTO userLogin = null;
        try {
            if (!comment.isEmpty()) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    userLogin = (Tbl_Account_DTO) session.getAttribute("USERLOGIN");
                    if (userLogin != null) {
                        Tbl_Articles_DAO artDAO = new Tbl_Articles_DAO();
                        String status = artDAO.getArticleStatus(articleId);
                        if (status.equals("Active")) {
                            Tbl_Comment_DAO cmtDAO = new Tbl_Comment_DAO();
                            int reslut = cmtDAO.insertComment(comment, userLogin.getEmail(), articleId);
                            if (reslut > 0) {
                                LOGGER.info("Comment succ");
                            }
                        }
                    }
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
