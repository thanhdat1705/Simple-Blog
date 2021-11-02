/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datnt.servlet;

import datnt.mailValidCode.MailError;
import static datnt.servlet.LoginServlet.LOGGER;
import datnt.tbl_Account.Tbl_Account_DAO;
import datnt.tbl_Account.Tbl_Account_DTO;
import datnt.tbl_ArticleStatus.Tbl_ArticleStatus_DAO;
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
public class VerifyServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(VerifyServlet.class);
    private final String VERIFY_ERROR = "mailCheckPage";
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
        String url = (String) siteMap.get(VERIFY_ERROR);
        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();
        String verifyCode = request.getParameter("txtVerifyCode").trim();
        String code = request.getParameter("txtVerify").trim();

        boolean foundErr = false;

        try {
            System.out.println(verifyCode);
            System.out.println(code);
            if (code.equals(verifyCode)) {
                Tbl_Account_DAO dao = new Tbl_Account_DAO();
                Tbl_ArticleStatus_DAO statusDAO = new Tbl_ArticleStatus_DAO();
                boolean updateStatus = dao.activeAccount(email, statusDAO.getStatusID("Active"));
                if (updateStatus) {
                    Tbl_Account_DTO userLogin = dao.getUserAccount(email, password);
                    if (userLogin != null) {
                        url = SEARCH_ARTICLE_ACTION;
                        HttpSession session = request.getSession(true);
                        session.setAttribute("USERLOGIN", userLogin);
                        Cookie cookie = new Cookie(Tools.EncodeMail(email), password);
                        cookie.setMaxAge(60 * 60);
                        response.addCookie(cookie);
                        LOGGER.info("Login succ");
                    }
                }

            } else {
                foundErr = true;
                url = (String) siteMap.get(VERIFY_ERROR);
                MailError error = new MailError();
                error.setInvalidVerifyCode("Invaid verify Code!");
                request.setAttribute("ERROR", error);
                request.setAttribute("VERIFYCODE", verifyCode);
                request.setAttribute("EMAIL", email);
                request.setAttribute("PASSWORD", password);
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
            if (foundErr) {
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
