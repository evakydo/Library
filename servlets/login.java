/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import database.DB_Connection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import mainClasses.JSON_Converter;
import mainClasses.Librarian;
import mainClasses.User;

/**
 *
 * @author evakydo
 */
public class login extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet login at " + request.getContextPath() + "</h1>");
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
        HttpSession session=request.getSession();
        
        if (session.getAttribute("loggedIn") != null) {
            response.setStatus(200);        
        }
        else{
            response.setStatus(403);
        }
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        Gson gson = new Gson();
        User u = gson.fromJson(request.getReader(), User.class); 
        
        ResultSet rs;
        ResultSet rs2;
        
        try {     
            Connection con = DB_Connection.getConnection();
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM students WHERE username = '" + u.getUsername() + "' AND password='"+u.getPassword()+"'");
            rs2 = stmt2.executeQuery("SELECT * FROM librarians WHERE username = '" + u.getUsername() + "' AND password='"+u.getPassword()+"'");
            
            boolean b = rs.next();
            
            if(!b && !rs2.next()){
                response.setStatus(403);
                response.getWriter().write("user doesnt exist");
            }
            else{         
                HttpSession session=request.getSession();
                session.setAttribute("loggedIn",u.getUsername());
                response.setStatus(200);
                if(b) {
                    response.getWriter().write("student exists");
                    session.setAttribute("usertype","student");
                }
                else{
                    response.getWriter().write("librarian exists");
                    session.setAttribute("usertype","librarian");
                }
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
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
