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
import javax.servlet.RequestDispatcher;
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
public class LoginServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private final String LOGIN_ERROR = "loginPage";
    private final String SEARCH_ARTICLE_ACTION = "searchArticleAction";
    private final String VERIFY_PAGE = "mailCheckPage";

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
        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();
        boolean foundErr = false;
        boolean verify = false;
        String error = "error";
        String url = (String) siteMap.get(LOGIN_ERROR);

        try {
            Tbl_Account_DAO dao = new Tbl_Account_DAO();
            boolean exist = dao.checkExisted(email);
            if (exist) {
                boolean result = dao.checkLogin(email, password);
                if (result) {
                    if (dao.isActive(email)) {
                        Tbl_Account_DTO userLogin = dao.getAccount();
                        url = SEARCH_ARTICLE_ACTION;
                        HttpSession session = request.getSession(true);
                        session.setAttribute("USERLOGIN", userLogin);
                        Cookie cookie = new Cookie(Tools.EncodeMail(email), password);
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        LOGGER.info("Login succ");
                    } else {
                        verify = true;
                        url = (String) siteMap.get(VERIFY_PAGE);
                        String verifyCode = Tools.activeAcountCode(email);
                        request.setAttribute("VERIFYCODE", verifyCode);
                        request.setAttribute("EMAIL", email);
                        request.setAttribute("PASSWORD", password);
                    }

                } else {
                    foundErr = true;
                    error = "Incorrect password!!!";
                    LOGGER.info("Incorrect password!!!");

                }
            } else {
                foundErr = true;
                error = "This email is not Existed!!!";
                LOGGER.info("This email is not Existed!!!");
            }
            if (foundErr) {
                request.setAttribute("LOGINERROR", error);
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
            if (foundErr || verify) {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
                out.close();
            } else {
                response.sendRedirect(url);
                out.close();
            }
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
