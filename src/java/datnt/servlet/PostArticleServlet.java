/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.tbl_Articles.Tbl_Articles_DAO;
import datnt.ultis.Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
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
public class PostArticleServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(PostArticleServlet.class);
    private final String ARTICLE_PAGE = "articlePage";
    private final String POST_ERROR = "postArticlePage";
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
        String url = (String) siteMap.get(POST_ERROR);
        String tittle = request.getParameter("txtTittle").trim();
        String shortDes = request.getParameter("txtShortDes").trim();
        String content = request.getParameter("txtContent");
        int statusId = 1;
        Timestamp today = Tools.toDay();
        try {
//            System.out.println(tittle);
//            System.out.println(shortDes);
//            System.out.println(content);
//            content = Tools.detectLineBreak(content);
//            System.out.println(today);
            HttpSession session = request.getSession(false);
            if (session != null) {
                Tbl_Account_DTO userLogin = (Tbl_Account_DTO) session.getAttribute("USERLOGIN");
                if (userLogin != null) {
                    Tbl_Articles_DAO articleDAO = new Tbl_Articles_DAO();
                    boolean exist = articleDAO.checkExistedTittle(tittle);
//                    exist = true;
                    if (!exist) {
                        int result = articleDAO.postArticle(tittle, userLogin.getEmail(), userLogin.getName(), content, shortDes, today, statusId);
                        if (result > 0) {
                            url = (String) siteMap.get(SEARCH_ARTICLE_ACTION);
                            request.setAttribute("NOTIFICATIONS", "Post article successfully!!!");
                            LOGGER.info("Post article succ");
                        } else {
                            request.setAttribute("POST_ERROR_NOTI", "Something went wrong during the posting process!!!");
                            LOGGER.info("Post article error!!!");
                        }
                    } else {
                        request.setAttribute("DUP_TITTLE", "This tittle has already been existed!!!");
                        LOGGER.info("Post article error (duplicate tittle)!!!");
                    }
                } else {
                    url = (String) siteMap.get(SEARCH_ARTICLE_ACTION);
                    request.setAttribute("NOTIFICATIONS", "Your session timed out!!!");
                    LOGGER.info("session timeout!!!");
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
