/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import static datnt.servlet.SearchArticleServlet.LOGGER;
import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.tbl_ArticleStatus.Tbl_ArticleStatus_DAO;
import datnt.tbl_Articles.Tbl_Articles_DAO;
import datnt.tbl_Articles.Tbl_Articles_DTO;
import datnt.tbl_Comment.Tbl_Comment_DAO;
import datnt.tbl_Comment.Tbl_Comment_DTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class GetArticleDetailServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(GetArticleDetailServlet.class);
    private final String LOGIN_ERROR = "loginPage";
    private final String ARTICLE_DETAIL_PSAGE = "articleDetailsPage";

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
        String url = (String) siteMap.get(ARTICLE_DETAIL_PSAGE);
        int articleId = Integer.parseInt(request.getParameter("pk"));
        String status = request.getParameter("txtStatus");
        String email = "";
        try {
            HttpSession session = request.getSession();
            Tbl_Account_DTO userLogin = (Tbl_Account_DTO) session.getAttribute("USERLOGIN");
            if (userLogin != null) {
                email = userLogin.getEmail();
                if (userLogin.getRoleId() != 1) {
                    status = "Active";
                }
            } else {
                status = "Active";
            }
            Tbl_ArticleStatus_DAO statusDAO = new Tbl_ArticleStatus_DAO();
            int statusID = statusDAO.getStatusID(status);

            Tbl_Articles_DAO articleDAO = new Tbl_Articles_DAO();
            Tbl_Articles_DTO articleDetail = articleDAO.viewArticle(articleId, statusID);
            if (articleDetail != null) {
                Tbl_Comment_DAO cmtDAO = new Tbl_Comment_DAO();
                cmtDAO.getListCommentByArticle(articleId, email);
                List<Tbl_Comment_DTO> listCmt = new ArrayList<>();
                listCmt = cmtDAO.getListComments();

                if (listCmt != null) {
                    request.setAttribute("LIST_COMMENT", listCmt);
                    articleDetail.setTotalComment(listCmt.size());
                }
                request.setAttribute("VIEWRESULT", articleDetail);
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
