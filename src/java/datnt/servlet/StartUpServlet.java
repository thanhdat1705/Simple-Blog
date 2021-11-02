/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import datnt.tbl_Account.Tbl_Account_DAO;
import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.ultis.Tools;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author NTD
 */
public class StartUpServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(StartUpServlet.class);
    private final String ARTICLE_PAGE = "articlePage";
    private final String ADMIN_PAGE = "adminPage";
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
        String url = SEARCH_ARTICLE_ACTION;
//        String url = (String) siteMap.get(SEARCH_ARTICLE_ACTION);
        try {
            Cookie[] cookies = request.getCookies();
            System.out.println(cookies);
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String email = cookie.getName();
                    String password = cookie.getValue();
                    email = Tools.DecodeMail(email);
                    Tbl_Account_DAO dao = new Tbl_Account_DAO();
                    boolean result = dao.checkLogin(email, password);

                    if (result) {
                        HttpSession session = request.getSession();
                        Tbl_Account_DTO userLogin = dao.getAccount();
                        if (userLogin.getRoleId() == 1) {
//                            url = SEARCH_ARTICLE_ACTION;
//                            url = ADMIN_PAGE;

                        }

                        session.setAttribute("USERLOGIN", userLogin);
                        break;

                    }
                }
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.toString());
        } catch (SQLException ex) {
            LOGGER.error(ex.toString());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.error(ex.toString());
        } finally {
            response.sendRedirect(url);
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
