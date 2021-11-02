/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.tbl_Articles.Tbl_Articles_DAO;
import datnt.tbl_Articles.Tbl_Articles_DTO;
import datnt.tbl_ArticleStatus.Tbl_ArticleStatus_DAO;
import datnt.tbl_Comment.Tbl_Comment_DAO;
import datnt.ultis.Tools;
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
public class SearchArticleServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(SearchArticleServlet.class);
    private final String ARTICLE_PAGE = "articlePage";
    private final String ADMIN_PAGE = "adminPage";
    private final String ERROR_PAGE = "errorPage";

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
        String url = (String) siteMap.get(ERROR_PAGE);
        int pageSize = request.getParameter("txtPageSize") == null ? Tools.pageSize : Integer.parseInt(request.getParameter("txtPageSize"));
        int pageIndex = request.getParameter("txtPageIndex") == null ? Tools.pageIndex : Integer.parseInt(request.getParameter("txtPageIndex"));
        String searchTittle = request.getParameter("txtSearchTittle") == null ? "" : request.getParameter("txtSearchTittle");
        String searchContent = request.getParameter("txtSearchContent") == null ? "" : request.getParameter("txtSearchContent");
        int statusId = (request.getParameter("cbStatusAr") == null || request.getParameter("cbStatusAr").isEmpty()) ? 2 : Integer.parseInt(request.getParameter("cbStatusAr"));

        try {
            System.out.println(pageSize + " pageSize");
            HttpSession session = request.getSession(false);
            if (session != null) {
                Tbl_Account_DTO userLogin = (Tbl_Account_DTO) session.getAttribute("USERLOGIN");
                if (userLogin != null) {
                    if (userLogin.getRoleId() == 1) {
                        statusId = (request.getParameter("cbStatusAr") == null || request.getParameter("cbStatusAr").isEmpty()) ? 1 : Integer.parseInt(request.getParameter("cbStatusAr"));
                        url = (String) siteMap.get(ADMIN_PAGE);
                    } else {
                        url = (String) siteMap.get(ARTICLE_PAGE);
                    }
                } else {
                    url = (String) siteMap.get(ARTICLE_PAGE);
                }
            } else {
                url = (String) siteMap.get(ARTICLE_PAGE);
            }
            Tbl_Articles_DAO articleDAO = new Tbl_Articles_DAO();
            Tbl_ArticleStatus_DAO articleStatusDAO = new Tbl_ArticleStatus_DAO();
            articleStatusDAO.getStatus();
            int totalPage = articleDAO.totalPage(pageSize, searchContent, statusId, searchTittle);
            articleDAO.searchArticle(pageIndex, pageSize, searchContent, statusId, searchTittle);
            List<Tbl_Articles_DTO> result = new ArrayList<>();
            result = articleDAO.getListArticles();
            if (result != null && result.size() > 0) {
                for (Tbl_Articles_DTO articleDTO : result) {
                    Tbl_Comment_DAO dao = new Tbl_Comment_DAO();
                    int num = dao.getTotalComment(articleDTO.getId());
                    articleDTO.setTotalComment(num);
                }
            }

            request.setAttribute("SEARCHRESULT", result);
            request.setAttribute("ARTICLE_STATUS_LIST", articleStatusDAO.getListArticleStus());
            request.setAttribute("TOTALPAGE", totalPage);
            request.setAttribute("CURRENTPAGEINDEX", pageIndex);
            request.setAttribute("CURRENTPAGESIZE", pageSize);
            request.setAttribute("CURRENTSTATUS", statusId);

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
